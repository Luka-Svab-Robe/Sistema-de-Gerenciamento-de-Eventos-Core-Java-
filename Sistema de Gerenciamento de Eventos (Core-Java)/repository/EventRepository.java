package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Event;

public class EventRepository {
    private static final String FILE_NAME = "events.data";

    public void saveEvents(List<Event> events) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(events);
        } catch (IOException e) {
            System.err.println("Erro ao salvar eventos no arquivo: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                events = (List<Event>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar eventos do arquivo: " + e.getMessage());
            }
        }
        return events;
    }
}
