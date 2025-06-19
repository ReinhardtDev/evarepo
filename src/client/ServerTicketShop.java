package client;

import event.Event;
import event.EventService;
import event.EventServiceInterface;
import idservice.IDServiceParallel;
import kundenservice.CustomerService;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import logservice.LogService;
import logservice.LogServiceInterface;
import ticketservice.Ticket;
import ticketservice.TicketService;
import ticketservice.TicketServiceInterface;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServerTicketShop {
    private final CustomerServiceInterface customerServiceInterface;
    private final EventServiceInterface eventServiceInterface;
    private final TicketServiceInterface ticketServiceInterface;
    private final LogServiceInterface logServiceInterface;

    public ServerTicketShop(IDServiceParallel idService) throws IOException {
        this.customerServiceInterface = CustomerService.getInstance(idService);
        this.eventServiceInterface = EventService.getInstance(idService);
        this.ticketServiceInterface = TicketService.getInstance(idService, eventServiceInterface, customerServiceInterface);
        this.logServiceInterface = LogService.getInstance();
    }

    public String callService(String operation) {
        String[] temp = operation.split(",", 3);
        if (temp.length < 1) {
            System.out.println("Invalid operation");
        } else if (temp.length == 1) {
            System.out.println("Invalid operation");
        }
        String serviceName = temp[1];
        String operationName = temp[0];
        String args = "";
        if (temp.length > 2) {
            args = temp[2];
        }

        switch (serviceName) {
            case "event":
                return callEventMethod(operationName, args);
            case "customer":
                return callCustomerMethod(operationName, args);
            case "ticket":
                return callTicketMethod(operationName, args);
            default:
                throw new RuntimeException("Unknown service name " + serviceName);
        }
    }

    private String callEventMethod(String operationName, String args) {
        String[] arguments = args.split(",");
        switch (operationName) {
            case "create":
                long id = eventServiceInterface.createEvent(arguments[0], arguments[1], LocalDate.parse(arguments[2]), Integer.parseInt(arguments[3]));
                Event event = eventServiceInterface.getEventById(id);
                return event.toString();
            case "getAll":
                ArrayList<Event> events = eventServiceInterface.getAllEvents();
                return events.toString();
        }
        throw new RuntimeException("Unknown operation " + operationName);
    }

    private String callCustomerMethod(String operationName, String args) {
        String[] arguments = args.split(",");
        switch (operationName) {
            case "create":
                long id = customerServiceInterface.createCustomer(arguments[0], arguments[1], LocalDate.parse(arguments[2]));
                Kunde customer = customerServiceInterface.getCustomerByID(id);
                return customer.toString();
            case "getAll":
                ArrayList<Kunde> customers = customerServiceInterface.getAllCustomer();
                return customers.toString();
        }
        throw new RuntimeException("Unknown operation " + operationName);
    }

    private String callTicketMethod(String operationName, String args) {
        String[] arguments = args.split(",");
        switch (operationName) {
            case "create":
                Event event = eventServiceInterface.getEventById(Long.parseLong(arguments[2]));
                int eventQuota = event.getQuota();
                if (eventQuota <= 0) {
                    throw new RuntimeException("Event quota must be greater than zero");
                }

                Kunde kunde = customerServiceInterface.getCustomerByID(Long.parseLong(arguments[1]));

                if(kunde.isMaxTicketAmount(Long.parseLong(arguments[2]))) {
                    throw new RuntimeException("Customer has purchased max amount of tickets");
                }

                long id = ticketServiceInterface.createTicket(LocalDate.parse(arguments[0]), Long.parseLong(arguments[1]), Long.parseLong(arguments[2]));
                Ticket ticket = ticketServiceInterface.getTicketById(id);
                return ticket.toString();
            case "getAll":
                ArrayList<Ticket> tickets = ticketServiceInterface.getAllTickets();
                return tickets.toString();
        }
        throw new RuntimeException("Unknown operation " + operationName);
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
