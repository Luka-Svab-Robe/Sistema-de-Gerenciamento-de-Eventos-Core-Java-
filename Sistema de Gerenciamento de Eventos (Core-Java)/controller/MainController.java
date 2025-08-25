package controller;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import model.Event;
import model.User;
import service.EventService;
import service.UserService;
import view.ConsoleView;

public class MainController {
    private final Scanner scanner;
    private final ConsoleView view;
    private final EventService eventService;
    private final UserService userService;

    public MainController() {
        this.scanner = new Scanner(System.in);
        this.view = new ConsoleView(scanner);
        this.eventService = new EventService();
        this.userService = new UserService();
    }

    public void run() {
        view.displayWelcomeMessage();
        eventService.loadEventsFromFile();
        User currentUser = view.promptForUserCreation();
        userService.setCurrentUser(currentUser);

        boolean running = true;
        while (running) {
            view.displayMenu();
            int choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    viewUpcomingEvents();
                    break;
                case 3:
                    viewHappeningNowEvents();
                    break;
                case 4:
                    viewPastEvents();
                    break;
                case 5:
                    attendEvent();
                    break;
                case 6:
                    viewMyEvents();
                    break;
                case 7:
                    cancelAttendance();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    view.displayInvalidOptionMessage();
                    break;
            }
        }
        view.displayGoodbyeMessage();
        scanner.close();
    }

    private void createEvent() {
        Event newEvent = view.promptForEventDetails();
        eventService.addEvent(newEvent);
        view.displayMessage("Evento criado com sucesso!");
    }

    private void viewUpcomingEvents() {
        List upcomingEvents = eventService.getUpcomingEvents();
        view.displayEvents("Próximos Eventos", upcomingEvents);
    }

    private void viewHappeningNowEvents() {
        List happeningNowEvents = eventService.getHappeningNowEvents();
        view.displayEvents("Eventos Ocorrendo Agora", happeningNowEvents);
    }

    private void viewPastEvents() {
        List pastEvents = eventService.getPastEvents();
        view.displayEvents("Eventos que Já Ocorreram", pastEvents);
    }

    private void attendEvent() {
        List upcomingEvents = eventService.getUpcomingEvents();
        if (upcomingEvents.isEmpty()) {
            view.displayMessage("Não há eventos futuros para participar.");
            return;
        }
        view.displayEvents("Escolha um evento para participar", upcomingEvents);
        UUID eventId = view.promptForEventId();

        Event event = eventService.findEventById(eventId);
        User currentUser = userService.getCurrentUser();

        if (event != null && currentUser != null) {
            userService.attendEvent(event);
            view.displayMessage("Presença confirmada no evento: " + event.getName());
        } else {
            view.displayError("Erro: Evento não encontrado.");
        }
    }

    private void viewMyEvents() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            List myEvents = userService.getAttendingEvents(eventService);
            view.displayEvents("Meus Eventos Confirmados", myEvents);
        }
    }

    private void cancelAttendance() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null || userService.getAttendingEvents(eventService).isEmpty()) {
            view.displayMessage("Você não confirmou presença em nenhum evento.");
            return;
        }

        List myEvents = userService.getAttendingEvents(eventService);
        view.displayEvents("Escolha um evento para cancelar a participação", myEvents);
        UUID eventId = view.promptForEventId();

        Event event = eventService.findEventById(eventId);
        if (event != null) {
            userService.cancelAttendance(event);
            view.displayMessage("Participação no evento '" + event.getName() + "' cancelada.");
        } else {
            view.displayError("Erro: Evento não encontrado.");
        }
    }
}