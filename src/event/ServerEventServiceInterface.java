package event;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ServerEventServiceInterface {
    void createEvent(String title, String location, LocalDate date, int quota);

    ArrayList<Event> getAllEvents();
}
