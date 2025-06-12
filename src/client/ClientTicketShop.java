package client;

import event.EventService;
import event.EventServiceInterface;
import event.ServerEventService;
import idservice.IDServiceParallel;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import logservice.LogService;
import logservice.LogServiceInterface;
import ticketservice.TicketService;
import ticketservice.TicketServiceInterface;

import java.io.IOException;

public class ClientTicketShop {
    private final CustomerServiceInterface customerServiceInterface;
    private final EventServiceInterface eventServiceInterface;
    private final TicketServiceInterface ticketServiceInterface;
    private final LogServiceInterface logServiceInterface;

    public ClientTicketShop(IDServiceParallel idService) throws IOException {
        this.customerServiceInterface = CustomerService.getInstance(idService);
        this.eventServiceInterface = ServerEventService.getInstance(idService);
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
