package event;

import java.time.LocalDate;

public interface ServerEventServiceInterface {
    void createEvent(String title, String location, LocalDate date, int quota);

    void getAllEvents();
}
