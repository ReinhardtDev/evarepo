package ticketservice;
import event.Event;
import event.EventServiceInterface;
import idservice.IDServiceParallel;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import event.EventService;
import idservice.IDService;
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
    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private ArrayList<Ticket> tickets;
    private final IDServiceParallel idService;
    private static ServerTicketService INSTANCE;
    private LogServiceInterface logService;

    public ServerTicketService(IDServiceParallel idService, EventServiceInterface eventService, CustomerServiceInterface customerService, int port) {
        this.idService = idService;
        this.eventService = eventService;
        this.customerService = customerService;
        this.tickets = new ArrayList<>();
        this.logService = LogService.getInstance();
        this.port = port;
    }

    public static ServerTicketService getInstance(IDServiceParallel idservice, EventServiceInterface eventService, CustomerServiceInterface customerService, int port) {
        if (INSTANCE == null) {
            INSTANCE = new ServerTicketService(idservice, eventService, customerService, port);
        }
        return INSTANCE;
    }

    @Override
    public synchronized void createTicket(LocalDate purchaseDate, long customerId, long eventId) {
        Event event = eventService.getEventById(eventId);
        int eventQuota = event.getQuota();
        if (eventQuota <= 0) {
            throw new RuntimeException("Event quota must be greater than zero");
        }

        Kunde kunde = customerService.getCustomerByID(customerId);

        if(kunde.isMaxTicketAmount(eventId)) {
            throw new RuntimeException("Customer has purchased max amount of tickets");
        }

        String call = "create,ticket," + purchaseDate + "," + customerId + "," + eventId;

        String result = connect(call);
        System.out.println("Result: " + result);
    }

    private String connect(String call) {
        if (socket == null || socket.isClosed()) {
            try {
                socket = new Socket("localhost", port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(call);
                return in.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(call);
                return in.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } throw new RuntimeException("Connection failed!");
    }

    private Socket socket;

    private int port;
}


