package ticketservice;
import event.Event;
import event.EventServiceInterface;
import idservice.IDServiceParallel;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import event.EventService;
import idservice.IDService;
import logservice.LogService;
import logservice.LogServiceInterface;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements TicketServiceInterface {
    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private ArrayList<Ticket> tickets;
    private final IDServiceParallel idService;
    private static TicketService INSTANCE;
    private LogServiceInterface logService;

    public TicketService(IDServiceParallel idService, EventServiceInterface eventService, CustomerServiceInterface customerService) {
        this.idService = idService;
        this.eventService = eventService;
        this.customerService = customerService;
        this.tickets = new ArrayList<>();
        this.logService = LogService.getInstance();
    }

    public static TicketService getInstance(IDServiceParallel idservice, EventServiceInterface eventService, CustomerServiceInterface customerService) {
        if (INSTANCE == null) {
            INSTANCE = new TicketService(idservice, eventService, customerService);
        }
        return INSTANCE;
    }


    @Override
    public synchronized long createTicket(LocalDate purchaseDate, long customerId, long eventId) {
        Event event = eventService.getEventById(eventId);
        int eventQuota = event.getQuota();
        if (eventQuota <= 0) {
            throw new RuntimeException("Event quota must be greater than zero");
        }

        Kunde kunde = customerService.getCustomerByID(customerId);

        if(kunde.isMaxTicketAmount(eventId)) {
            throw new RuntimeException("Customer has purchased max amount of tickets");
        }

        long id = idService.getID();
        tickets.add(new Ticket(id, purchaseDate, customerId, eventId));
        eventService.reduceQuota(event);
        logService.logEvent("CREATE_TICKET", id);
        return id;
    }

    @Override
    public Ticket getTicketById(long id){
        for(Ticket ticket: tickets) {
            if(ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public void deleteTicketById(long id){
        for(Ticket ticket: tickets) {
            if(ticket.getId() == id) {
                tickets.remove(ticket);
                idService.deleteID(id);
                System.out.println("ticket deleted.");
            }
        }
    }

    @Override
    public boolean isValidTicket(long ticketID) {
        Ticket ticket = getTicketById(ticketID);
        if(ticket == null) {
            return false;
        }

        long customerID = ticket.getCustomerId();
        Kunde customer = customerService.getCustomerByID(customerID);
        if(customer == null) {
            return false;
        }

        if(!customer.getTickets().contains(ticket)) {
            return false;
        }

        Event event = eventService.getEventById(ticket.getEventId());

        if (event == null) {
            return false;
        }

        return event.getTickets().contains(ticket);

    }

    @Override
    public ArrayList<Ticket> getAllTickets() {
        return tickets;
    }
}


