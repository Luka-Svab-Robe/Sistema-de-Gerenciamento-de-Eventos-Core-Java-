package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import model.Category;
import model.Event;
import model.User;

/**
 * Responsável por toda a interação com o usuário no console.
 * Exibe menus, solicita dados e formata a saída de informações.
 * Não contém nenhuma lógica de negócio.
 */
public class ConsoleView {
    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayWelcomeMessage() {
        System.out.println("=============================================");
        System.out.println("  Bem-vindo ao Sistema de Eventos da Cidade! ");
        System.out.println("=============================================");
    }

    public void displayMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Cadastrar Novo Evento");
        System.out.println("2. Ver Próximos Eventos");
        System.out.println("3. Ver Eventos Ocorrendo Agora");
        System.out.println("4. Ver Eventos Passados");
        System.out.println("5. Confirmar Presença em um Evento");
        System.out.println("6. Ver Meus Eventos Confirmados");
        System.out.println("7. Cancelar Presença em um Evento");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int getUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Limpar buffer
            return -1; // Retorna um valor inválido
        }
    }

    public User promptForUserCreation() {
        System.out.println("\n--- Cadastro de Usuário ---");
        System.out.print("Digite seu nome: ");
        String name = scanner.nextLine();
        System.out.print("Digite seu e-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite sua cidade: ");
        String city = scanner.nextLine();
        System.out.println("Usuário cadastrado com sucesso!");
        return new User(name, email, city);
    }

    public Event promptForEventDetails() {
        System.out.println("\n--- Cadastro de Novo Evento ---");
        System.out.print("Nome do evento: ");
        String name = scanner.nextLine();
        System.out.print("Endereço: ");
        String address = scanner.nextLine();
        Category category = promptForCategory();
        LocalDateTime dateTime = promptForDateTime();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();

        return new Event(name, address, category, dateTime, description);
    }

    private Category promptForCategory() {
        while (true) {
            System.out.println("Categorias disponíveis:");
            for (Category cat : Category.values()) {
                System.out.println("- " + cat.name());
            }
            System.out.print("Digite a categoria do evento: ");
            String categoryInput = scanner.nextLine().toUpperCase();
            try {
                return Category.valueOf(categoryInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Categoria inválida. Tente novamente.");
            }
        }
    }

    private LocalDateTime promptForDateTime() {
        while (true) {
            System.out.print("Data e Hora (dd/MM/yyyy HH:mm): ");
            String dateTimeInput = scanner.nextLine();
            try {
                return LocalDateTime.parse(dateTimeInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data/hora inválido. Use dd/MM/yyyy HH:mm. Tente novamente.");
            }
        }
    }

    public UUID promptForEventId() {
        while (true) {
            System.out.print("\nDigite o ID do evento desejado: ");
            String idInput = scanner.nextLine();
            try {
                return UUID.fromString(idInput);
            } catch (IllegalArgumentException e) {
                System.out.println("ID inválido. Por favor, copie e cole o ID exatamente como exibido.");
            }
        }
    }

    public void displayEvents(String title, List<Event> events) {
        System.out.println("\n--- " + title.toUpperCase() + " ---");
        if (events.isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
        } else {
            for (Event event : events) {
                System.out.println("-----------------------------------------");
                System.out.println("ID: " + event.getId());
                System.out.println("  Nome: " + event.getName());
                System.out.println("  Endereço: " + event.getAddress());
                System.out.println("  Categoria: " + event.getCategory());
                System.out.println("  Data/Hora: " + event.getDateTime().format(formatter));
                System.out.println("  Descrição: " + event.getDescription());
            }
            System.out.println("-----------------------------------------");
        }
    }

    public void displayMessage(String message) {
        System.out.println("\n[INFO] " + message);
    }

    public void displayError(String error) {
        System.out.println("\n[ERRO] " + error);
    }

    public void displayInvalidOptionMessage() {
        System.out.println("\n[ERRO] Opção inválida. Por favor, tente novamente.");
    }

    public void displayGoodbyeMessage() {
        System.out.println("\nObrigado por usar o sistema. Até logo!");
    }
}