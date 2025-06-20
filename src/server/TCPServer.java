package server;

import client.ServerTicketShop;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    private final int port;
    private ServerSocket serverSocket;
    ServerTicketShop ticketShop;
    ExecutorService threadPool = Executors.newCachedThreadPool();

    public TCPServer(int port, ServerTicketShop ticketShop) throws IOException {
        this.ticketShop = ticketShop;
        this.port = port;
    }

    public void start() {
        Thread serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server started. Listening on port " + port);

                while (!serverSocket.isClosed()) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New Client connected: " + clientSocket.getRemoteSocketAddress());
                    threadPool.submit(new ClientHandler(clientSocket));
                }
            } catch (IOException ignored) {
                System.out.println("Server stopped");
            } finally {
                shutdown();
            }
        });
        serverThread.start();
    }

    public void shutdown() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error while shutting server down: " + e.getMessage());
        }
        threadPool.shutdown();
        System.out.println("Server shut down.");
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Received message by client " + socket.getRemoteSocketAddress() + ": " + line);

                    Object answer;

                    try {
                        answer = ticketShop.callService(line);
                    } catch (Exception e) {
                        System.out.println("Error while calling service " + socket.getRemoteSocketAddress() + ": " + e.getMessage());
                        answer = new ArrayList<>();
                    }

                    objOut.writeObject(answer);
                }
            } catch (IOException e) {
                System.err.println("Error in ClientHandler: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Fehler while closing ClientSocket: " + e.getMessage());
                }
                System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
            }
        }
    }
}
