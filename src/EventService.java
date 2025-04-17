import java.util.ArrayList;

public class EventService {
    ArrayList<Event> events;

    public EventService(ArrayList<Event> events) {
        this.events = events;
    }

    public void createEvent(Event event) {
        events.add(event);
    }

    public void readEvent(int eventid){
        System.out.println(events.get(eventid));
    }

    public void updateEvent(){

    }

    public void deleteEvent(){

    }
}

