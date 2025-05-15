package event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public interface EventServiceInterface {
    long createEvent(String title, String location, LocalDate date, int quota);

    void deleteEvent(long id);

    Event getEventById(long id);

    ArrayList<Event> getAllEvents();

    void reduceQuota(Event event);
}
