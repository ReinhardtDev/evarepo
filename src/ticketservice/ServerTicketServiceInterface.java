package ticketservice;

import java.time.LocalDate;

public interface ServerTicketServiceInterface {
    void createTicket(LocalDate purchaseDate, long customerId, long eventId);

    void getAllTickets();
}
