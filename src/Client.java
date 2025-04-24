import event.Event;
import event.EventService;
import idservice.IDService;
import kundenservice.CustomerService;
import kundenservice.Kunde;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Client {
    private final EventService eventService;
    private final CustomerService kundeService;
    public Client(IDService idService) {
        this.eventService = new EventService(idService);
        this.kundeService = new CustomerService(idService);
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

    private long scanCustomerId(){
        System.out.println("Customer ID: ");
        Scanner customerIdScanner = new Scanner(System.in);

        long customerId = customerIdScanner.nextLong();
        kundeService.getCustomerByID(customerId);
        return customerId;
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
        System.out.println("""
                        Welchen Service benötigen Sie?\s
                        Wählen Sie 1 für den EventService,
                        Wählen Sie 2 für den KundenService
                        Wählen Sie 0 um den Vorgang abzubrechen""");
        Scanner serviceScanner = new Scanner(System.in);
        int service = Integer.parseInt(serviceScanner.nextLine());

        switch (service) {
            case 0 -> {
                System.out.println("Vorgang abgebrochen");
            }
            case 2 -> readUserChoiceKundenService();
            case 1 -> {

                System.out.println("""
                        Was möchten Sie machen?\s
                        Wählen Sie 1 um ein neues Event zu erstellen,
                        Wählen Sie 2 um ein Event anzuzeigen,
                        Wählen Sie 3 um ein Event zu ändern,
                        Wählen Sie 4 um ein Event zu löschen,
                        Wählen Sie 5 um alle Events anzuzeigen,
                        Wählen Sie 6 um alle Events zu löschen,
                        Wählen Sie 0 um einen anderen Service auszuwählen""");

                switch (scanUserChoice()) {
                    case 0 -> readUserChoice();
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

    //AB HIER KUNDENSERVICE CODES
    //______________________________________________________________________
    private void readUserChoiceKundenService(){
        System.out.println("""
                        Was möchten Sie machen?\s
                        Wählen Sie 1 um einen neuen Kunden hinzuzufügen,
                        Wählen Sie 2 um Kundeninformationen abzurufen,
                        Wählen Sie 3 um Kundeninformationen zu ändern,
                        Wählen Sie 4 um einen Kunden aus dem System zu löschen,
                        Wählen Sie 5 um alle Kundeninformationen anzuzeigen,
                        Wählen Sie 6 um alle Kundeninformationen zu löschen,
                        Wählen Sie 0 um einen anderen Service auszuwählen""");

        switch (scanUserChoice()) {
            case 0 -> readUserChoice();
            case 1 -> scanNewKunde();
            case 2 -> showKundenInformation(scanCustomerId());
            case 3 -> changeKundenInformation(scanCustomerId());
            case 4 -> deleteKunde(scanCustomerId());
            case 5 -> showAllKundenInformation();
            case 6 -> deleteAllKundenInformation();
            default -> {
                System.out.println("ungültige Eingabe, bitte versuchen Sie es erneut\n");
                readUserChoice();
            }
        }
    }

    private void scanNewKunde(){
        System.out.println("neuen Kunden erstellen:\nName: ");
        Scanner newKundenScanner = new Scanner(System.in);
        String name = newKundenScanner.nextLine();
        System.out.println("\nEmail: ");
        String email = newKundenScanner.nextLine();
        System.out.println("\nGeburtsdatum: ");
        LocalDate geburtsdatum = LocalDate.parse(newKundenScanner.nextLine());
        long c = kundeService.createCustomer(name, email, geburtsdatum);

        if(c == 1){
            System.out.println("falsches Email Format");
            readUserChoice();
        } else if(c == 2){
            System.out.println("falsches Datum Format");
            readUserChoice();
        } else if(c == 3){
            System.out.println("Kunde muss mind. 18 Jahre alt sein");
            readUserChoice();
        } else System.out.println(name + " wurde erstellt");
        readUserChoice();

    }

    private void showKundenInformation(long id){
        Kunde k = kundeService.getCustomerByID(id);
        if(k != null){
            System.out.println(k);
            readUserChoice();
        } else {
            System.out.println("Es existiert kein Kunde mit der id " + id);
            readUserChoice();
        }
    }

    private void changeKundenInformation(long id){
        System.out.println("""
                Was möchten Sie ändern?\s
                Wählen Sie 1 für Nutzername,
                Wählen Sie 2 für Email,
                Wählen Sie 3 für Geburtsdatum,
                Wählen Sie 0 um den Vorgang abzubrechen""");

        switch (scanUserChoice()) {
            case 1 -> {
                System.out.println("Titel: ");
                Scanner nameScanner = new Scanner(System.in);
                kundeService.getCustomerByID(id).setNutzername(nameScanner.nextLine());
                System.out.println("Name wurde erfolgreich geändert");
                readUserChoice();
            }
            case 2 -> {
                System.out.println("Email: ");
                Scanner locationScanner = new Scanner(System.in);
                kundeService.getCustomerByID(id).setEmail(locationScanner.nextLine());
                System.out.println("Email wurde erfolgreich geändert");
                readUserChoice();
            }
            case 3 -> {
                System.out.println("Geburtsdatum: ");
                Scanner geburtstagsscanner = new Scanner(System.in);
                kundeService.getCustomerByID(id).setGeburtsdatum(LocalDate.parse(geburtstagsscanner.nextLine()));
                System.out.println("Geburtsdatum wurde erfolgreich geändert");
                readUserChoice();
            }
            case 0 -> readUserChoice();

            default -> {
                System.out.println("ungültige Eingabe, versuchen Sie es noch einmal\n");
                changeEvent(id);
            }
        }
    }

    private void deleteKunde(long id){
        kundeService.deleteCustomer(id);
        System.out.println("Kunde wurde erfolgreich gelöscht");
        readUserChoice();
    }

    private void showAllKundenInformation(){
        System.out.println("Hier sind alle Kundeninformationen: \n");
        ArrayList<Kunde> alleKunden = kundeService.getAllCustomer();
        if(alleKunden.isEmpty()){ System.out.println("keine Kundeninformationen vorhanden"); }
        for (Kunde alleKunde : alleKunden) {
            System.out.println(alleKunde.toString() + "\n");
        }
        readUserChoice();
    }

    private void deleteAllKundenInformation(){
        kundeService.deleteAllCustomers();
        System.out.println("Alle Kundeninformationen wurden gelöscht");
        readUserChoice();
    }


}
