package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final String name;
    private final String email;
    private final String city;
    private final Set<UUID> attendingEventIds;

    public User(String name, String email, String city) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.city = city;
        this.attendingEventIds = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public Set<UUID> getAttendingEventIds() {
        return attendingEventIds;
    }

    public void addAttendingEvent(UUID eventId) {
        this.attendingEventIds.add(eventId);
    }

    public void removeAttendingEvent(UUID eventId) {
        this.attendingEventIds.remove(eventId);
    }
}
