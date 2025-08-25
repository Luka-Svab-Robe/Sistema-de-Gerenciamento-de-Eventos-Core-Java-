package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import model.Event;
import repository.EventRepository;

public class EventService {
    private List<Event> events;
    private final EventRepository eventRepository;

    public EventService() {
        this.eventRepository = new EventRepository();
        this.events = new ArrayList<>();
    }

    public void loadEventsFromFile() {
        this.events = eventRepository.loadEvents();
    }

    public void addEvent(Event event) {
        events.add(event);
        eventRepository.saveEvents(events);
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }

    public Event findEventById(UUID id) {
        return events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Event> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> upcoming = events.stream()
                .filter(event -> event.getDateTime().isAfter(now))
                .collect(Collectors.toList());
        Collections.sort(upcoming);
        return upcoming;
    }

    public List<Event> getHappeningNowEvents() {
        LocalDateTime now = LocalDateTime.now();
        return events.stream()
                .filter(event -> event.getDateTime().toLocalDate().equals(now.toLocalDate()) &&
                        event.getDateTime().toLocalTime().isBefore(now.toLocalTime()))
                .collect(Collectors.toList());
    }

    public List<Event> getPastEvents() {
        LocalDateTime now = LocalDateTime.now();
        return events.stream()
                .filter(event -> event.getDateTime().isBefore(now))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }
}
