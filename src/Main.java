import client.Client;
import client.PerformanceClient;
import client.TicketShop;
import idservice.IDServiceParallel;

public class Main {
    public static void main(String[] args) {
        //Regular Client
        /*IDServiceParallel idService = new IDServiceParallel(10000);
        TicketShop ticketShop = new TicketShop(idService);
        Client client = new Client(ticketShop);
        client.readUserChoice();*/

        //Performance Client
        IDServiceParallel idService = new IDServiceParallel();
        TicketShop ticketShop = new TicketShop(idService);
        PerformanceClient performanceClient = new PerformanceClient(ticketShop);
        performanceClient.run();

        //Parallel IDService speed test
        /*IDServiceParallel idServiceParallel = new IDServiceParallel();
        long startTime = System.nanoTime();
        idServiceParallel.generateID(10000);
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime) / 1000000;
        System.out.println("Elapsed time: " + elapsedTime + " ms");*/
    }
}