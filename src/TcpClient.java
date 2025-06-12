import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {

    private final String host;
    private final int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        System.out.println("Connected with server " + host + ":" + port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public String send(String message) throws IOException {
        if (socket == null || socket.isClosed()) {
            try {
                connect();
            } catch (IOException e) {
                System.err.println("Error while connecting to server: " + e.getMessage());
                return null;
            }
        }
        out.println(message);
        String response = in.readLine();
        return response;
    }

    public void startInteractive() {
        if (socket == null || socket.isClosed()) {
            try {
                connect();
            } catch (IOException e) {
                System.err.println("Error while connecting to server: " + e.getMessage());
                return;
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message (" + host + ":" + port + "), 'exit' to exit:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                String antwort = send(input);
                if (antwort != null) {
                    System.out.println("Answer: " + antwort);
                }
            } catch (IOException e) {
                System.err.println("Error while sending: " + e.getMessage());
                break;
            }
        }

        close();
    }

    public void close() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.err.println("Error while closing: " + e.getMessage());
        }
    }
}
