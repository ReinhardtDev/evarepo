package client;

import event.ServerEventService;
import event.ServerEventServiceInterface;
import idservice.IDServiceParallel;
import kundenservice.ServerCustomerService;
import kundenservice.ServerCustomerServiceInterface;
import logservice.LogService;
import logservice.LogServiceInterface;
import ticketservice.ServerTicketService;
import ticketservice.ServerTicketServiceInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTicketShop {
    private final ServerCustomerServiceInterface customerServiceInterface;
    private final ServerEventServiceInterface eventServiceInterface;
    private final ServerTicketServiceInterface ticketServiceInterface;
    private final LogServiceInterface logServiceInterface;
    private final Socket socket;

    public ClientTicketShop(IDServiceParallel idService, int port) throws IOException {
        this.socket = new Socket("localhost", port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        this.eventServiceInterface = ServerEventService.getInstance(idService, this.socket, out, in);
        this.customerServiceInterface = ServerCustomerService.getInstance(idService, this.socket, out, in);
        this.ticketServiceInterface = ServerTicketService.getInstance(idService, eventServiceInterface, customerServiceInterface, this.socket, out, in);
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
