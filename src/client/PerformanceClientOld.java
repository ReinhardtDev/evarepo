package client;

import event.Event;
import event.EventServiceInterface;
import event.ServerEventServiceInterface;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import kundenservice.ServerCustomerServiceInterface;
import ticketservice.ServerTicketServiceInterface;
import ticketservice.TicketServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;

public class PerformanceClientOld {
/*
    private final ServerEventServiceInterface eventService;
    private final ServerCustomerServiceInterface customerService;
    private final ServerTicketServiceInterface ticketService;

    public PerformanceClientOld(ClientTicketShop ticketShop) {
        this.eventService = ticketShop.getEventServiceInterface();
        this.customerService = ticketShop.getCustomerServiceInterface();
        this.ticketService = ticketShop.getTicketServiceInterface();
    }

    public void createEvents(int count) {
        for (int i = 0; i < count; i++) {
            eventService.createEvent("Name " + i, "Location " + i, LocalDate.now().plusDays(1), 20000000);
        }
    }

    public void createCustomers(int count) {
        for (int i = 0; i < count; i++) {
            customerService.createCustomer("Name " + i, "mail@" + i + ".com", LocalDate.now().minusYears(20));
        }
    }

    public void buyOneTicketPerCustomerForEachEvent() {
        ArrayList<Kunde> kunden = customerService.getAllCustomer();
        ArrayList<Event> events = eventService.getAllEvents();

        for (Kunde kunde : kunden) {
            for (Event event : events) {
                ticketService.createTicket(LocalDate.now(), kunde.getId(), event.getId());
            }
        }
    }

    public void buyMoreTickets() {
        ArrayList<Long> eventIds = createEvents(100);
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Kunde> kunden = customerService.getAllCustomer();

        for (Long eventId : eventIds) {
            events.add(eventService.getEventById(eventId));
        }

        for(Event event : events) {
            for (Kunde kunde : kunden) {
                ticketService.createTicket(LocalDate.now(), kunde.getId(), event.getId());
                ticketService.createTicket(LocalDate.now(), kunde.getId(), event.getId());
            }
        }


    }

    public void run() {
        long startTime = System.nanoTime();
        createEvents(100);
        createCustomers(1000);
        buyOneTicketPerCustomerForEachEvent();
        buyMoreTickets();
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime) / 1000000;
        System.out.println("Elapsed time: " + elapsedTime);
    }
*/
}