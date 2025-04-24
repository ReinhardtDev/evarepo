package ticketservice;
import event.Event;
import kundenservice.CustomerService;
import kundenservice.Kunde;
import event.EventService;
import idservice.IDService;

import java.time.LocalDate;
import java.util.ArrayList;

public class TicketService {
    private EventService eventService;
    private CustomerService customerService;
    private ArrayList<Ticket> tickets;
    private IDService idService;

    public TicketService(IDService idservice) {
        this.idService = idservice;
        this.tickets = new ArrayList<Ticket>();
    }

    public long createTicket(LocalDate purchaseDate, long customerId, long eventId) {
        Event event = eventService.getEventById(eventId);
        int eventQuota = event.getQuota();
        if (eventQuota <= 0) {
            return 0;
        }

        Kunde kunde = customerService.getCustomerByID(customerId);

        long ticketId = idService.generateID();
        tickets.add(new Ticket(ticketId, purchaseDate, customerId, eventId));
        eventService.reduceQuota(event);
        return ticketId;
    }

    public Ticket getTicketById(long id){
        for(Ticket ticket: tickets) {
            if(ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    public void deleteTicketById(long id){
        for(Ticket ticket: tickets) {
            if(ticket.getId() == id) {
                tickets.remove(ticket);
                idService.deleteID(id);
                System.out.println("ticket deleted.");
            }
        }

    }
}


