package client;

import event.EventService;
import event.EventServiceInterface;
import idservice.IDServiceParallel;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import ticketservice.TicketService;
import ticketservice.TicketServiceInterface;

public class TicketShop {
    private final CustomerServiceInterface customerServiceInterface;
    private final EventServiceInterface eventServiceInterface;
    private final TicketServiceInterface ticketServiceInterface;

    public TicketShop(IDServiceParallel idService) {
        this.customerServiceInterface = CustomerService.getInstance(idService);
        this.eventServiceInterface = EventService.getInstance(idService);
        this.ticketServiceInterface = TicketService.getInstance(idService, eventServiceInterface, customerServiceInterface);
    }

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
