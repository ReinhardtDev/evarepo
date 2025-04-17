package event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class EventService {

    public EventService() {
        this.events = new ArrayList<>();
    }

    public int createEvent(String title, String location, LocalDate date, int quota) {
        if (quota < 0) {
            throw new IllegalArgumentException("The quota must not be negative!");
        }

        if (!date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The event must take place in the future!");
        }

        idCounter++;
        events.add(new Event(idCounter, title, location, date, quota));

        return idCounter;
    }

    public void updateEvent(Event event) {
        int i = 0;
        for (Event e : events) {
            if (e.getId() == event.getId()) {
                events.set(i, event);
                return;
            }
            i++;
        }
        throw new IllegalArgumentException("Event with the ID = " + event.getId() + " does not exist!");
    }

    public void deleteEvent(int id) {
        for (Event e : events ) {
            if (e.getId() == id) {
                events.remove(e);
                return;
            }
        }
    }

    public Event getEventById(int id) {
        for (Event e : events ) {
            if (e.getId() == id)
                return e;
        }

        throw new IllegalArgumentException("Event with the ID = " + id + " does not exist!");
    }

    public Collection<Event> getAllEvents() {
        return (Collection<Event>) events.clone();
    }

    private ArrayList<Event> events;

    private int idCounter;

}
