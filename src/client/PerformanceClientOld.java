package client;

import event.Event;
import event.EventServiceInterface;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import ticketservice.TicketServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;

public class PerformanceClientOld {

    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private final TicketServiceInterface ticketService;

    public PerformanceClientOld(TicketShop ticketShop) {
        this.eventService = ticketShop.getEventServiceInterface();
        this.customerService = ticketShop.getCustomerServiceInterface();
        this.ticketService = ticketShop.getTicketServiceInterface();
    }

    public ArrayList<Long> createEvents(int count) {
        ArrayList<Long> events = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            long eventId = eventService.createEvent("Name " + i, "Location " + i, LocalDate.now().plusDays(1), 20000000);
            events.add(eventId);
        }
        return events;
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

}