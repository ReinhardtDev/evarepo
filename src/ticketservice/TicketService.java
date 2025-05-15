package ticketservice;
import event.Event;
import event.EventServiceInterface;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import event.EventService;
import idservice.IDService;

import java.time.LocalDate;
import java.util.ArrayList;

public class TicketService implements TicketServiceInterface {
    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private ArrayList<Ticket> tickets;
    private final IDService idService;
    private static TicketService INSTANCE;

    public TicketService(IDService idService, EventServiceInterface eventService, CustomerServiceInterface customerService) {
        this.idService = idService;
        this.eventService = eventService;
        this.customerService = customerService;
        this.tickets = new ArrayList<>();
    }

    public static TicketService getInstance(IDService idservice, EventServiceInterface eventService, CustomerServiceInterface customerService) {
        if (INSTANCE == null) {
            INSTANCE = new TicketService(idservice, eventService, customerService);
        }
        return INSTANCE;
    }


    @Override
    public long createTicket(LocalDate purchaseDate, long customerId, long eventId) {
        Event event = eventService.getEventById(eventId);
        int eventQuota = event.getQuota();
        if (eventQuota <= 0) {
            throw new RuntimeException("Event quota must be greater than zero");
        }

        Kunde kunde = customerService.getCustomerByID(customerId);

        if(kunde.isMaxTicketAmount(eventId)) {
            throw new RuntimeException("Customer has purchased max amount of tickets");
        }

        long ticketId = idService.generateID();
        tickets.add(new Ticket(ticketId, purchaseDate, customerId, eventId));
        eventService.reduceQuota(event);
        return ticketId;
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
}


