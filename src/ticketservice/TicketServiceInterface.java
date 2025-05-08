package ticketservice;

import java.time.LocalDate;

public interface TicketServiceInterface {
    long createTicket(LocalDate purchaseDate, long customerId, long eventId);

    Ticket getTicketById(long id);

    void deleteTicketById(long id);

    boolean isValidTicket(long ticketID);
}
