package ticketservice;

import idservice.IDService;

import java.time.LocalDate;
import java.util.ArrayList;

public class TicketService {

    private ArrayList<Ticket> tickets;
    private IDService idService;

    public TicketService(IDService idservice) {
        this.idService = idservice;
        this.tickets = new ArrayList<Ticket>();
    }

    public long createTicket() {

        long ticketId = idService.generateID();
        tickets.add(new Ticket(ticketId, purchaseDate, customerId, eventId));
        return ticketId;
    }
}


