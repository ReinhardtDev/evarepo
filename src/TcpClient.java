import client.TicketShopServer;
import event.EventServiceInterface;
import kundenservice.CustomerServiceInterface;
import ticketservice.TicketServiceInterface;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private final TicketServiceInterface ticketService;
    private final TicketShopServer ticketShop;

    public TcpClient(TicketShopServer ticketShop) {
        this.eventService = ticketShop.getEventServiceInterface();
        this.customerService = ticketShop.getCustomerServiceInterface();
        this.ticketService = ticketShop.getTicketServiceInterface();
        this.ticketShop = ticketShop;
    }

    public String getResult() {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 5000);
        try {
            socket.bind(inetSocketAddress);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        try(DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());) {
            while (true) {
                System.out.println("Format: <service>,<command>,<parameter>");
                String serviceCall = scanner.nextLine();
                try {
                    out.writeUTF(serviceCall);
                    String output = in.readUTF();
                    System.out.println(output);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
