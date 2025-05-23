package event;

import idservice.IDService;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventService implements EventServiceInterface {

    public EventService(IDService idService) {
        this.events = new ArrayList<>();
        this.idService = idService;
    }

    public static EventService getInstance(IDService idService) {
        if (INSTANCE == null) {
            INSTANCE = new EventService(idService);
        }
        return INSTANCE;
    }


    @Override
    public long createEvent(String title, String location, LocalDate date, int quota) {
        if (quota < 0) {
            throw new IllegalArgumentException("The quota must not be negative!");
        }

        if (!date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The event must take place in the future!");
        }

        long id = idService.generateID();
        if (id == -1) {
            throw new IllegalArgumentException("Couldn't generate an ID!");
        }
        events.add(new Event(id, title, location, date, quota));
        return id;
    }

    @Override
    public void deleteEvent(long id) {
        for (Event e : events ) {
            if (e.getId() == id) {
                events.remove(e);
                idService.deleteID(e.getId());
                return;
            }
        }
    }

    @Override
    public Event getEventById(long id) {
        for (Event e : events ) {
            if (e.getId() == id)
                return e;
        }

        throw new IllegalArgumentException("Event with the ID = " + id + " does not exist!");
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        return events;
    }


    private static EventService INSTANCE;

    private ArrayList<Event> events;

    private IDService idService;

    public void reduceQuota(Event event){
        event.setQuota(event.getQuota() - 1);
    }
}
