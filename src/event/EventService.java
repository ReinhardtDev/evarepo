package event;

import idservice.IDService;
import idservice.IDServiceParallel;
import kundenservice.Kunde;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventService implements EventServiceInterface {

    public EventService(IDServiceParallel idService) {
        this.events = new ArrayList<>();
        this.idService = idService;
    }

    public static EventService getInstance(IDServiceParallel idService) {
        if (INSTANCE == null) {
            INSTANCE = new EventService(idService);
        }
        return INSTANCE;
    }


    @Override
    public synchronized long createEvent(String title, String location, LocalDate date, int quota) {
        if (quota < 0) {
            throw new IllegalArgumentException("The quota must not be negative!");
        }

        if (!date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The event must take place in the future!");
        }


        long id = idService.generateNext();
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

    private IDServiceParallel idService;

    public synchronized void reduceQuota(Event event){
        event.setQuota(event.getQuota() - 1);
    }
}
