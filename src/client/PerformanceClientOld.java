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

    private final ServerEventServiceInterface eventService;
    private final ServerCustomerServiceInterface customerService;
    private final ServerTicketServiceInterface ticketService;
    private final ServerTicketShop ticketShop;

    public PerformanceClientOld(ClientTicketShop ticketShop, ServerTicketShop serverTicketShop) {
        this.eventService = ticketShop.getEventServiceInterface();
        this.customerService = ticketShop.getCustomerServiceInterface();
        this.ticketService = ticketShop.getTicketServiceInterface();
        this.ticketShop = serverTicketShop;
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
        ArrayList<Event> events = (ArrayList<Event>) ticketShop.callService("getAll,event");
        ArrayList<Kunde> customers = (ArrayList<Kunde>) ticketShop.callService("getAll,customer");

        for (Kunde kunde : customers) {
            for (Event event : events) {
                ticketService.createTicket(LocalDate.now(), kunde.getId(), event.getId());
            }
        }
    }

    public void buyMoreTickets() {
        createEvents(100);
        ArrayList<Event> events = (ArrayList<Event>) ticketShop.callService("getAll,event");
        ArrayList<Kunde> kunden = (ArrayList<Kunde>) ticketShop.callService("getAll,customer");

        if (events.size() > 100) {
            events.subList(0, events.size() - 100).clear();
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