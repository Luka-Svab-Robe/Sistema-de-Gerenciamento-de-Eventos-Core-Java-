package service;

import java.util.List;
import java.util.stream.Collectors;
import model.Event;
import model.User;

public class UserService {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void attendEvent(Event event) {
        if (currentUser != null && event != null) {
            currentUser.addAttendingEvent(event.getId());
        }
    }

    public void cancelAttendance(Event event) {
        if (currentUser != null && event != null) {
            currentUser.removeAttendingEvent(event.getId());
        }
    }

    public List<Event> getAttendingEvents(EventService eventService) {
        if (currentUser == null) {
            return List.of();
        }

        return currentUser.getAttendingEventIds().stream()
                .map(eventService::findEventById)
                .filter(java.util.Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
    }
}