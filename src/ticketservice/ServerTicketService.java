package ticketservice;
import event.Event;
import event.EventServiceInterface;
import event.ServerEventServiceInterface;
import idservice.IDServiceParallel;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import event.EventService;
import idservice.IDService;
import kundenservice.ServerCustomerServiceInterface;
import logservice.LogService;
import logservice.LogServiceInterface;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerTicketService implements ServerTicketServiceInterface {
    private final ServerEventServiceInterface eventService;
    private final ServerCustomerServiceInterface customerService;
    private ArrayList<Ticket> tickets;
    private final IDServiceParallel idService;
    private static ServerTicketService INSTANCE;
    private LogServiceInterface logService;
    private Socket socket;
    private final PrintWriter out;
    private final ObjectInputStream in;

    public ServerTicketService(IDServiceParallel idService, ServerEventServiceInterface eventService, ServerCustomerServiceInterface customerService, Socket socket, PrintWriter out, ObjectInputStream in) {
        this.idService = idService;
        this.eventService = eventService;
        this.customerService = customerService;
        this.tickets = new ArrayList<>();
        this.logService = LogService.getInstance();
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    public static ServerTicketService getInstance(IDServiceParallel idservice, ServerEventServiceInterface eventService, ServerCustomerServiceInterface customerService, Socket socket, PrintWriter out, ObjectInputStream in) {
        if (INSTANCE == null) {
            INSTANCE = new ServerTicketService(idservice, eventService, customerService, socket,  out, in);
        }
        return INSTANCE;
    }

    @Override
    public synchronized void createTicket(LocalDate purchaseDate, long customerId, long eventId) {
        String call = "create,ticket," + purchaseDate + "," + customerId + "," + eventId;

        ArrayList<Ticket> result = connect(call);
        System.out.println(result);
    }

    private ArrayList<Ticket> connect(String call) {
        try {
            out.println(call);
            return (ArrayList<Ticket>) in.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void getAllTickets() {
        String call = "getAll,ticket";
        ArrayList<Ticket> result = connect(call);
        for (Ticket ticket : result) {
            System.out.println(ticket);
        }
        System.out.println("\n");
    }
}


