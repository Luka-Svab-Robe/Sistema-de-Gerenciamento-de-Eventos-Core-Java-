package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Event implements Serializable, Comparable<Event> {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final String name;
    private final String address;
    private final Category category;
    private final LocalDateTime dateTime;
    private final String description;

    public Event(String name, String address, Category category, LocalDateTime dateTime, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.category = category;
        this.dateTime = dateTime;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "  Nome: " + name + "\n" +
                "  Endereço: " + address + "\n" +
                "  Categoria: " + category + "\n" +
                "  Data/Hora: " + dateTime + "\n" +
                "  Descrição: " + description;
    }

    @Override
    public int compareTo(Event other) {
        // Ordena os eventos pela data/hora.
        return this.getDateTime().compareTo(other.getDateTime());
    }
}
