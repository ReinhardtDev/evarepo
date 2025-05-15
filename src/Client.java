import event.Event;
import event.EventService;
import event.EventServiceInterface;
import idservice.IDService;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import ticketservice.Ticket;
import ticketservice.TicketService;
import ticketservice.TicketServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Client {
    private final EventServiceInterface eventService;
    private final CustomerServiceInterface kundeService;
    private final TicketServiceInterface ticketService;

    public Client(TicketShop ticketShop) {
        this.eventService = ticketShop.getEventServiceInterface();
        this.kundeService = ticketShop.getCustomerServiceInterface();
        this.ticketService = ticketShop.getTicketServiceInterface();
    }

    private long scanEventId(){
        System.out.println("Event ID: ");
        Scanner eventIdScanner = new Scanner(System.in);
        try {
            long eventId = eventIdScanner.nextLong();
            eventService.getEventById(eventId);
            return eventId;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            readUserChoice();
        }
        return 0;
    }

    private long scanCustomerId(){
        System.out.println("Customer ID: ");
        Scanner customerIdScanner = new Scanner(System.in);

        try{
            long customerId = customerIdScanner.nextLong();
            kundeService.getCustomerByID(customerId);
            return customerId;
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            readUserChoice();
        }
        return 0;
    }

    private int scanUserChoice(){
        Scanner userChoiceScanner = new Scanner(System.in);
        try {
            return Integer.parseInt(userChoiceScanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe");
            readUserChoice();
        }
        return 0;
    }

    private void scanNewEvent(){
        try{
            System.out.println("neues Event erstellen:\nTitel: ");
            Scanner newEventScanner = new Scanner(System.in);
            String title = newEventScanner.nextLine();
            System.out.println("\nLocation: ");
            String location = newEventScanner.nextLine();
            System.out.println("\nDatum: ");
            LocalDate date = LocalDate.parse(newEventScanner.nextLine());
            System.out.println("\nQuota: ");
            int quota = Integer.parseInt(newEventScanner.nextLine());

            long id = eventService.createEvent(title, location, date, quota);
            System.out.println("Event " + title + " " + id + " wurde erstellt");
            readUserChoice();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            readUserChoice();
        }


    }
    public void readUserChoice() {
        System.out.println("""
                        Welchen Service benötigen Sie?\s
                        Wählen Sie 1 für den EventService,
                        Wählen Sie 2 für den KundenService
                        Wählen Sie 3 für den TicketService
                        Wählen Sie 0 um den Vorgang abzubrechen""");
        int service = scanUserChoice();

        switch (service) {
            case 0 -> {
                System.out.println("Vorgang abgebrochen");
                System.exit(0);
            }
            case 2 -> readUserChoiceKundenService();
            case 3 -> readUserChoiceTicketService();
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

        try{
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
        } catch (Exception e) {
            System.out.println("Event mit ID " + id + " nicht gefunden.");
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
        if(alleEvents.isEmpty()) {
            System.out.println("keine Events vorhanden");
            readUserChoice();
        }

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
                System.out.println("Invalid input, try again\n");
                readUserChoice();
            }
        }
    }

    private void scanNewKunde(){
        try {
            System.out.println("Create new customer:\nName: ");
            Scanner newKundenScanner = new Scanner(System.in);
            String name = newKundenScanner.nextLine();
            System.out.println("\nEmail: ");
            String email = newKundenScanner.nextLine();
            System.out.println("\nBirthdate: ");
            LocalDate geburtsdatum = LocalDate.parse(newKundenScanner.nextLine());

            long c = kundeService.createCustomer(name, email, geburtsdatum);
            System.out.println("Customer with ID " + c + " successfully created\n");
            readUserChoice();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            readUserChoice();
        }
    }

    private void showKundenInformation(long id){
        Kunde k = kundeService.getCustomerByID(id);
        if(k != null){
            System.out.println(k);
            readUserChoice();
        } else {
            System.out.println("No customer with ID " + id + " found");
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

        try{
            switch (scanUserChoice()) {
                case 1 -> {
                    System.out.println("Title: ");
                    Scanner nameScanner = new Scanner(System.in);
                    kundeService.getCustomerByID(id).setNutzername(nameScanner.nextLine());
                    System.out.println("Name successfully changed");
                    readUserChoice();
                }
                case 2 -> {
                    System.out.println("Email: ");
                    Scanner locationScanner = new Scanner(System.in);
                    kundeService.getCustomerByID(id).setEmail(locationScanner.nextLine());
                    System.out.println("Email successfully changed");
                    readUserChoice();
                }
                case 3 -> {
                    System.out.println("Birthdate: ");
                    Scanner geburtstagsscanner = new Scanner(System.in);
                    kundeService.getCustomerByID(id).setGeburtsdatum(LocalDate.parse(geburtstagsscanner.nextLine()));
                    System.out.println("Birthdate successfully changed");
                    readUserChoice();
                }
                case 0 -> readUserChoice();

                default -> {
                    System.out.println("Invalid input, try again\n");
                    changeKundenInformation(id);
                }
            }
        } catch (Exception e) {
            System.out.println("Customer with  " + id + " not found");
            readUserChoice();
        }

    }

    private void deleteKunde(long id){
        kundeService.deleteCustomer(id);
        System.out.println("Customer successfully deleted");
        readUserChoice();
    }

    private void showAllKundenInformation(){
        System.out.println("Customer data: \n");
        ArrayList<Kunde> alleKunden = kundeService.getAllCustomer();
        if(alleKunden.isEmpty()){ System.out.println("No customers available"); }
        for (Kunde alleKunde : alleKunden) {
            System.out.println(alleKunde.toString() + "\n");
        }
        readUserChoice();
    }

    private void deleteAllKundenInformation(){
        kundeService.deleteAllCustomers();
        System.out.println("Successfully deleted all customers");
        readUserChoice();
    }

    //AB HIER TICKETSERVICE CODE
    //___________________________________________________________________
    private long scanTicketId(){
        System.out.println("Ticket ID: ");
        Scanner ticketIdScanner = new Scanner(System.in);

        try{
            long customerId = ticketIdScanner.nextLong();
            ticketService.getTicketById(customerId);
            return customerId;
        } catch (Exception e){
            System.out.println(e.getMessage());
            readUserChoice();
        }
        return 0;
    }

    private void readUserChoiceTicketService(){
        System.out.println("""
                        Was möchten Sie machen?\s
                        Wählen Sie 1 um ein neues Ticket zu kaufen,
                        Wählen Sie 2 um ein Ticket zu stornieren,
                        Wählen Sie 3 um ein Ticket anzuzeigen,
                        Wählen Sie 4 um die Gültigkeit eines Tickets zu überprüfen,
                        Wählen Sie 0 um einen anderen Service auszuwählen
                        """);

        switch (scanUserChoice()) {
            case 0 -> readUserChoice();
            case 1 -> buyNewTicket();
            case 2 -> deleteTicket(scanTicketId());
            case 3 -> showTicket(scanTicketId());
            case 4 -> checkTicketValid(scanTicketId());
            default -> {
                System.out.println("Invalid input, try again. \n");
                readUserChoice();
            }
        }
    }

    private void buyNewTicket(){
            System.out.println("Ticket kaufen:\nEvent ID: ");
            Scanner newTicketScanner = new Scanner(System.in);

            try{
                long eventID = Long.parseLong(newTicketScanner.nextLine());
                System.out.println("\nUser ID: ");
                long userID = Long.parseLong(newTicketScanner.nextLine());
                LocalDate now = LocalDate.now();
                long c = ticketService.createTicket(now, userID, eventID);

                System.out.println("Ticket with ID " + c  + " succesfully bought.");
                readUserChoice();
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                readUserChoice();
            }

        }

    private void deleteTicket(long id){
        try{
            ticketService.deleteTicketById(id);
            System.out.println("Ticket successfully cancelled");
            readUserChoice();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            readUserChoice();
        }
    }

    private void showTicket(long id){
        try{
            Ticket t =  ticketService.getTicketById(id);
            System.out.println(t);
            readUserChoice();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            readUserChoice();
        }
    }

    private void checkTicketValid(long id){
        boolean isValidTicket = ticketService.isValidTicket(id);
        if(isValidTicket){
            System.out.println("Ticket " + id + " is valid.");
        } else {
            System.out.println("Ticket " + id + " is invalid.");
        }
        readUserChoice();
    }
}
