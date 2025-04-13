import java.util.ArrayList;

public class EventService {
    ArrayList<Event> events;

    public EventService(ArrayList<Event> events) {
        this.events = events;
    }

    private void createEvent(Event event) {
        events.add(event);
    }
}

