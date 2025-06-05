package client;

import event.EventServiceInterface;
import kundenservice.CustomerServiceInterface;
import ticketservice.TicketServiceInterface;

import java.util.Scanner;

public class TestClient {
    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private final TicketServiceInterface ticketService;
    private final TicketShopServer ticketShop;

    public TestClient(TicketShopServer ticketShop) {
        this.eventService = ticketShop.getEventServiceInterface();
        this.customerService = ticketShop.getCustomerServiceInterface();
        this.ticketService = ticketShop.getTicketServiceInterface();
        this.ticketShop = ticketShop;
    }

    public String callService() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String serviceCall = scanner.nextLine();
            try {
                String value = ticketShop.callService(serviceCall);
                System.out.println(value);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }



}
