package client;

import event.EventService;
import event.EventServiceInterface;
import idservice.IDServiceParallel;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import logservice.LogService;
import logservice.LogServiceInterface;
import ticketservice.TicketService;
import ticketservice.TicketServiceInterface;

import java.io.IOException;

public class TicketShop {
    private final CustomerServiceInterface customerServiceInterface;
    private final EventServiceInterface eventServiceInterface;
    private final TicketServiceInterface ticketServiceInterface;
    private final LogServiceInterface logServiceInterface;

    public TicketShop(IDServiceParallel idService) throws IOException {
        this.customerServiceInterface = CustomerService.getInstance(idService);
        this.eventServiceInterface = EventService.getInstance(idService);
        this.ticketServiceInterface = TicketService.getInstance(idService, eventServiceInterface, customerServiceInterface);
        this.logServiceInterface = LogService.getInstance();
    }

    public LogServiceInterface getLogServiceInterface() { return logServiceInterface; }

    public CustomerServiceInterface getCustomerServiceInterface() {
        return customerServiceInterface;
    }

    public EventServiceInterface getEventServiceInterface() {
        return eventServiceInterface;
    }

    public TicketServiceInterface getTicketServiceInterface() {
        return ticketServiceInterface;
    }
}
