import event.Event;
import event.EventService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            EventService eventService = new EventService();

            int buchmesseId = eventService.createEvent("Buchmesse", "Leipzig", LocalDate.parse("2026-03-29"), 1000);
            int laternenfestId = eventService.createEvent("Laternenfest", "Halle", LocalDate.parse("2025-08-29"), 2000);
            int pokalfinaleId = eventService.createEvent("DFB-Pokalfinale", "Berlin", LocalDate.parse("2025-05-24"), 74475);

            System.out.println(eventService.getEventById(pokalfinaleId));

            Event laternenfest = eventService.getEventById(laternenfestId);

            laternenfest.setDate(LocalDate.parse("2025-08-30"));
            laternenfest.setQuota(500);
            eventService.updateEvent(laternenfest);
            System.out.println(eventService.getEventById(laternenfestId));

            eventService.deleteEvent(buchmesseId);

            for (Event e : eventService.getAllEvents())
                System.out.println(e);

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}