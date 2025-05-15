import idservice.IDService;

public class Main {
    public static void main(String[] args) {
        //Regular Client
        /*IDService idService = new IDService();
        TicketShop ticketShop = new TicketShop(idService);
        Client client = new Client(ticketShop);
        client.readUserChoice();*/

        //Performance Client
        IDService idService = new IDService();
        TicketShop ticketShop = new TicketShop(idService);
        PerformanceClient performanceClient = new PerformanceClient(ticketShop);
        performanceClient.run();
    }
}