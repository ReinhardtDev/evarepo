import client.*;
import event.EventService;
import event.ServerEventService;
import event.ServerEventServiceInterface;
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
        ServerTicketShop serverTicketShop = new ServerTicketShop(idServiceParallel);
        TestClient testClient = new TestClient(serverTicketShop);
        testClient.callService();*/

        /*IDServiceParallel idServiceParallel = new IDServiceParallel();
        ServerTicketShop serverTicketShop = new ServerTicketShop(idServiceParallel);
        int port = 12345;
        TCPServer server = new TCPServer(port, serverTicketShop);
        server.start();
        TcpClient tcpClient = new TcpClient("localhost", port);
        tcpClient.startInteractive();*/

        IDServiceParallel idServiceParallel = new IDServiceParallel();
        ServerTicketShop ticketShop = new ServerTicketShop(idServiceParallel);
        int port = 12345;
        TCPServer server =  new TCPServer(port, ticketShop);
        server.start();
        ClientTicketShop clientTicketShop = new ClientTicketShop(idServiceParallel, port);

        ClientHaha client = new ClientHaha(clientTicketShop);
        client.readUserChoice();
    }
}