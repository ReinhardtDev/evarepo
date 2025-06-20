package client;

import event.Event;
import event.EventServiceInterface;
import event.ServerEventServiceInterface;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import kundenservice.ServerCustomerServiceInterface;
import ticketservice.ServerTicketServiceInterface;
import ticketservice.Ticket;
import ticketservice.TicketServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class ClientHaha {
    private final ServerEventServiceInterface eventService;
    private final ServerCustomerServiceInterface kundeService;
    private final ServerTicketServiceInterface ticketService;

    public ClientHaha(ClientTicketShop ticketShop) {
        this.eventService = ticketShop.getEventServiceInterface();
        this.kundeService = ticketShop.getCustomerServiceInterface();
        this.ticketService = ticketShop.getTicketServiceInterface();
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

            eventService.createEvent(title, location, date, quota);
            System.out.println("Event " + title + " wurde erstellt");
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
                    case 5 -> showAllEvents();
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

    private void showAllEvents(){
        System.out.println("Hier sind alle Events: \n");
        eventService.getAllEvents();
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
            case 5 -> showAllKundenInformation();
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

            kundeService.createCustomer(name, email, geburtsdatum);
            System.out.println("Customer successfully created\n");
            readUserChoice();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            readUserChoice();
        }
    }

    private void showAllKundenInformation(){
        System.out.println("Customer data: \n");
        kundeService.getAllCustomer();
        readUserChoice();
    }

    //AB HIER TICKETSERVICE CODE
    //___________________________________________________________________
    private void readUserChoiceTicketService(){
        System.out.println("""
                        Was möchten Sie machen?\s
                        Wählen Sie 1 um ein neues Ticket zu kaufen,
                        Wählen Sie 2 um ein Ticket zu stornieren,
                        Wählen Sie 3 um ein Ticket anzuzeigen,
                        Wählen Sie 4 um die Gültigkeit eines Tickets zu überprüfen,
                        Wählen Sie 5 um alle Tickets anzuzeigen,
                        Wählen Sie 0 um einen anderen Service auszuwählen
                        """);

        switch (scanUserChoice()) {
            case 0 -> readUserChoice();
            case 1 -> buyNewTicket();
            case 5 -> getAllTickets();
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
            ticketService.createTicket(now, userID, eventID);
            readUserChoice();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            readUserChoice();
        }
    }

    private void getAllTickets(){
        System.out.println("Tickets:");
        ticketService.getAllTickets();
        readUserChoice();
    }
}