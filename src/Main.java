import client.*;
import idservice.IDServiceParallel;
import server.TCPServer;
import ticketservice.TicketService;
import ticketservice.TicketServiceInterface;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //Regular Client
        /*IDServiceParallel idService = new IDServiceParallel(10000);
        TicketShop ticketShop = new TicketShop(idService);
        Client client = new Client(ticketShop);
        client.readUserChoice();*/

        //Performance Client
        /*IDServiceParallel idService = new IDServiceParallel();
        TicketShop ticketShop = new TicketShop(idService);
        PerformanceClient performanceClient = new PerformanceClient(ticketShop);
        performanceClient.run();
        idService.shutdown();*/

        //Parallel IDService speed test
        /*IDServiceParallel idServiceParallel = new IDServiceParallel();
        long startTime = System.nanoTime();
        idServiceParallel.generateID(10000);
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime) / 1000000;
        System.out.println("Elapsed time: " + elapsedTime + " ms");*/

        /*IDServiceParallel idServiceParallel = new IDServiceParallel();
        TicketShopServer ticketShopServer = new TicketShopServer(idServiceParallel);
        TestClient testClient = new TestClient(ticketShopServer);
        testClient.callService();*/

        IDServiceParallel idServiceParallel = new IDServiceParallel();
        TicketShopServer ticketShopServer = new TicketShopServer(idServiceParallel);
        TCPServer server = new TCPServer(ticketShopServer);
        server.start();
        TcpClient tcpClient = new TcpClient("localhost", 5000);
        tcpClient.startInteractive();
    }
}