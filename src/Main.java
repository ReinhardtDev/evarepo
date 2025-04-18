import event.EventService;
import java.time.LocalDate;
import event.Event;

public class Main {
    public static void main(String[] args) {

        Client client = new Client();
        client.readUserChoice();
        /*try {
            EventService eventService = new EventService();
            long buchmesseId = eventService.createEvent("Buchmesse", "Leipzig", LocalDate.parse("2026-03-29"), 1000);
            long laternenfestId =  eventService.createEvent("Laternenfest", "Halle", LocalDate.parse("2025-08-29"), 2000);
            long pokalfinaleID = eventService.createEvent("DFB-Pokalfinale", "Berlin", LocalDate.parse("2025-05-24"), 74475);



            Event laternenfest = eventService.getEventById(laternenfestId);

            laternenfest.setDate(LocalDate.parse("2025-08-30"));
            laternenfest.setQuota(500);
            eventService.updateEvent(laternenfest);

            eventService.deleteEvent(buchmesseId);

            for (Event e : eventService.getAllEvents())
                System.out.println(e);

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }*/
    }
}