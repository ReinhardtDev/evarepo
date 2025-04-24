import event.Event;
import event.EventService;
import idservice.IDService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

public class Client {
    private final EventService eventService;
    public Client(IDService idService) {
        this.eventService = new EventService(idService);
    }

    private long scanEventId(){
        System.out.println("Event ID: ");
        Scanner eventIdScanner = new Scanner(System.in);

        try{
            long eventId = eventIdScanner.nextLong();
            eventService.getEventById(eventId);
            return eventId;
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private int scanUserChoice(){
        Scanner userChoiceScanner = new Scanner(System.in);
        return Integer.parseInt(userChoiceScanner.nextLine());
    }

    private void scanNewEvent(){
        System.out.println("neues Event erstellen:\nTitel: ");
        Scanner newEventScanner = new Scanner(System.in);
        String title = newEventScanner.nextLine();
        System.out.println("\nLocation: ");
        String location = newEventScanner.nextLine();
        System.out.println("\nDatum: ");
        LocalDate date = LocalDate.parse(newEventScanner.nextLine());
        System.out.println("\nQuota: ");
        int quota = Integer.parseInt(newEventScanner.nextLine());

        eventService.createEvent(title, location, date, quota);
        System.out.println(title + " wurde erstellt");
        readUserChoice();

    }
    public void readUserChoice() {
        System.out.println("Fortfahren? (y/n)");
        Scanner fortfahrenScanner = new Scanner(System.in);
        String fortfahren = fortfahrenScanner.nextLine();

        switch (fortfahren) {
            case "n" -> System.out.println("Vorgang abgebrochen");
            case "y" -> {

                System.out.println("""
                        Was möchten Sie machen?\s
                        Wählen Sie 1 um ein neues Event zu erstellen,
                        Wählen Sie 2 um ein Event anzuzeigen,
                        Wählen Sie 3 um ein Event zu ändern,
                        Wählen Sie 4 um ein Event zu löschen,
                        Wählen Sie 5 um alle Events anzuzeigen,
                        Wählen Sie 6 um alle Events zu löschen,
                        Wählen Sie 0 um den Vorgang abzubrechen""");

                switch (scanUserChoice()) {
                    case 0 -> System.out.println("Vorgang abgebrochen");
                    case 1 -> scanNewEvent();
                    case 2 -> showEvent(scanEventId());
                    case 3 -> changeEvent(scanEventId());
                    case 4 -> deleteEvent(scanEventId());
                    case 5 -> showAllEvents();
                    case 6 -> deleteAllEvents();
                    default -> {
                        System.out.println("ungültige Eingabe, bitte versuchen Sie es erneut\n");
                        readUserChoice();
                    }
                }
            }
            default -> {
                System.out.println("ungültige Eingabe");
                readUserChoice();
            }
        }
    }

    private void showEvent(long id){
        System.out.println(eventService.getEventById(id).toString());
        readUserChoice();
    }

    private void changeEvent(long id){
        System.out.println("""
                Was möchten Sie ändern?\s
                Wählen Sie 1 für Titel,
                Wählen Sie 2 für Location,
                Wählen Sie 3 für Datum,
                Wählen Sie 4 für Quota,
                Wählen Sie 0 um einen anderen Vorgang auszuwählen""");

        switch (scanUserChoice()) {
            case 1 -> {
                System.out.println("Titel: ");
                Scanner titleScanner = new Scanner(System.in);
                eventService.getEventById(id).setTitle(titleScanner.nextLine());
                System.out.println("Titel wurde erfolgreich geändert");
                readUserChoice();
            }
            case 2 -> {
                System.out.println("Location: ");
                Scanner locationScanner = new Scanner(System.in);
                eventService.getEventById(id).setLocation(locationScanner.nextLine());
                System.out.println("Location wurde erfolgreich geändert");
                readUserChoice();
            }
            case 3 -> {
                System.out.println("Datum: ");
                Scanner dateScanner = new Scanner(System.in);
                eventService.getEventById(id).setDate(LocalDate.parse(dateScanner.nextLine()));
                System.out.println("Datum wurde erfolgreich geändert");
                readUserChoice();
            }
            case 4 -> {
                System.out.println("Quota: ");
                Scanner quotaScanner = new Scanner(System.in);
                eventService.getEventById(id).setQuota(Integer.parseInt(quotaScanner.nextLine()));
                System.out.println("Quota wurde erfolgreich geändert");
                readUserChoice();
            }
            case 0 -> readUserChoice();

            default -> {
                System.out.println("ungültige Eingabe, versuchen Sie es noch einmal\n");
                changeEvent(id);
            }
        }
    }

    private void deleteEvent(long id){
        eventService.deleteEvent(id);
        System.out.println("Event wurde erfolgreich gelöscht");
        readUserChoice();
    }

    private void showAllEvents(){
        System.out.println("Hier sind alle Events: \n");
        Collection<Event> alleEvents = eventService.getAllEvents();
        if(alleEvents.isEmpty()){ System.out.println("keine Events vorhanden"); }
        for (Event alleEvent : alleEvents) {
            System.out.println(alleEvent.toString() + "\n");
        }
        readUserChoice();
    }

    private void deleteAllEvents(){
        Collection<Event> alleEvents = eventService.getAllEvents();
        for (Event event : alleEvents) {
            eventService.deleteEvent(event.getId());
        }
        System.out.println("Alle Events wurden gelöscht");
        readUserChoice();
    }
}
