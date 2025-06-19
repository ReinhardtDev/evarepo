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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    public ServerTicketService(IDServiceParallel idService, ServerEventServiceInterface eventService, ServerCustomerServiceInterface customerService, Socket socket) {
        this.idService = idService;
        this.eventService = eventService;
        this.customerService = customerService;
        this.tickets = new ArrayList<>();
        this.logService = LogService.getInstance();
        this.socket = socket;
    }

    public static ServerTicketService getInstance(IDServiceParallel idservice, ServerEventServiceInterface eventService, ServerCustomerServiceInterface customerService, Socket socket) {
        if (INSTANCE == null) {
            INSTANCE = new ServerTicketService(idservice, eventService, customerService, socket);
        }
        return INSTANCE;
    }

    @Override
    public synchronized void createTicket(LocalDate purchaseDate, long customerId, long eventId) {
        String call = "create,ticket," + purchaseDate + "," + customerId + "," + eventId;

        String result = connect(call);
        System.out.println("Result: " + result);
    }

    private String connect(String call) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(call);
            return in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public void getAllTickets() {
        String call = "getAll,ticket";
        String result = connect(call);
    }
}


