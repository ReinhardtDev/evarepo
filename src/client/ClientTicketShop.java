package client;

import event.EventServiceInterface;
import event.ServerEventService;
import event.ServerEventServiceInterface;
import idservice.IDServiceParallel;
import kundenservice.CustomerServiceInterface;
import kundenservice.ServerCustomerService;
import kundenservice.ServerCustomerServiceInterface;
import logservice.LogService;
import logservice.LogServiceInterface;
import ticketservice.ServerTicketService;
import ticketservice.ServerTicketServiceInterface;

import java.io.IOException;
import java.net.Socket;

public class ClientTicketShop {
    private final ServerCustomerServiceInterface customerServiceInterface;
    private final ServerEventServiceInterface eventServiceInterface;
    private final ServerTicketServiceInterface ticketServiceInterface;
    private final LogServiceInterface logServiceInterface;
    private final Socket socket;

    public ClientTicketShop(IDServiceParallel idService, int port) throws IOException {
        this.socket = new Socket("localhost", port);
        this.customerServiceInterface = ServerCustomerService.getInstance(idService, this.socket);
        this.eventServiceInterface = ServerEventService.getInstance(idService, this.socket);
        this.ticketServiceInterface = ServerTicketService.getInstance(idService, eventServiceInterface, customerServiceInterface, this.socket);
        this.logServiceInterface = LogService.getInstance();
    }

    public LogServiceInterface getLogServiceInterface() { return logServiceInterface; }

    public ServerCustomerServiceInterface getCustomerServiceInterface() {
        return customerServiceInterface;
    }

    public ServerEventServiceInterface getEventServiceInterface() {
        return eventServiceInterface;
    }

    public ServerTicketServiceInterface getTicketServiceInterface() {
        return ticketServiceInterface;
    }
}
