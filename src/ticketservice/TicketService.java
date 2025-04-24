package ticketservice;
import event.Event;
import ticketservice.Ticket;
import event.EventService;
import idservice.IDService;

import java.time.LocalDate;
import java.util.ArrayList;

public class TicketService {
    private EventService eventService;
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
        long ticketId = idService.generateID();
        tickets.add(new Ticket(ticketId, purchaseDate, customerId, eventId));
        eventService.reduceQuota(event);
        return ticketId;
    }
}


