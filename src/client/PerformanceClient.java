package client;

import event.Event;
import event.EventServiceInterface;
import kundenservice.CustomerServiceInterface;
import kundenservice.Kunde;
import ticketservice.TicketServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PerformanceClient {

    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private final TicketServiceInterface ticketService;
    private final ExecutorService executorService;
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    public PerformanceClient(TicketShop ticketShop) {
        this.eventService = ticketShop.getEventServiceInterface();
        this.customerService = ticketShop.getCustomerServiceInterface();
        this.ticketService = ticketShop.getTicketServiceInterface();
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public CompletableFuture<List<Long>> createEvents(int count) {
        return CompletableFuture.supplyAsync(() -> {
            List<CompletableFuture<Long>> futures = IntStream.range(0, count)
                    .mapToObj(i -> CompletableFuture.supplyAsync(() ->
                            eventService.createEvent("Name " + i, "Location " + i,
                                    LocalDate.now().plusDays(1), 50000), executorService))
                    .collect(Collectors.toList());

            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        }, executorService);
    }



    public CompletableFuture<Void> createCustomers(int count) {
        return CompletableFuture.runAsync(() -> {
            List<CompletableFuture<Void>> futures = IntStream.range(0, count)
                    .mapToObj(i -> CompletableFuture.runAsync(() ->
                            customerService.createCustomer("Name " + i, "mail@" + i + ".com",
                                    LocalDate.now().minusYears(20)), executorService))
                    .collect(Collectors.toList());

            futures.forEach(CompletableFuture::join);
        }, executorService);
    }


    public CompletableFuture<Void> buyOneTicketPerCustomerForEachEvent() {
        return CompletableFuture.runAsync(() -> {
            ArrayList<Kunde> kunden = customerService.getAllCustomer();
            ArrayList<Event> events = eventService.getAllEvents();

            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (Kunde kunde : kunden) {
                for (Event event : events) {
                    futures.add(CompletableFuture.runAsync(() ->
                            ticketService.createTicket(LocalDate.now(), kunde.getId(),
                                    event.getId()), executorService));
                }
            }

            futures.forEach(CompletableFuture::join);
        }, executorService);
    }


    public CompletableFuture<Void> buyMoreTickets() {
        return createEvents(100)
                .thenCompose(eventIds -> {
                    ArrayList<Event> events = new ArrayList<>();
                    ArrayList<Kunde> kunden = customerService.getAllCustomer();

                    for (Long eventId : eventIds) {
                        events.add(eventService.getEventById(eventId));
                    }

                    List<CompletableFuture<Void>> futures = new ArrayList<>();
                    for (Event event : events) {
                        for (Kunde kunde : kunden) {
                            futures.add(CompletableFuture.runAsync(() -> {
                                ticketService.createTicket(LocalDate.now(), kunde.getId(), event.getId());
                                ticketService.createTicket(LocalDate.now(), kunde.getId(), event.getId());
                            }, executorService));
                        }
                    }

                    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
                });
    }


    public void run() {
        long startTime = System.nanoTime();

        try {
            // Sequenzielle Ausführung der Phasen mit Parallelisierung innerhalb jeder Phase
            createEvents(100)
                    .thenCompose(events -> createCustomers(1000))
                    .thenCompose(v -> buyOneTicketPerCustomerForEachEvent())
                    .thenCompose(v -> buyMoreTickets())
                    .join();
        } finally {
            executorService.shutdown();
        }

        int totalQuota = 0;
        List<Event> events = eventService.getAllEvents();
        for (Event event : events) {
            totalQuota += event.getQuota();
        }
        System.out.println("Total quota: " + totalQuota);

        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime) / 1000000;
        System.out.println("Elapsed time: " + elapsedTime);
    }
}
