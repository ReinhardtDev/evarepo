package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private ServerSocket serverSocket = new ServerSocket(5000);
    private BufferedReader inputStream;
    private PrintWriter outputStream;

    public TCPServer(Socket socket) throws IOException {
        this.serverSocket = serverSocket;
    }

    public Socket connection()  throws IOException {
        return serverSocket.accept();
    }

    public String dataRead() throws IOException {
        inputStream = new BufferedReader(new InputStreamReader(connection().getInputStream()));
        return inputStream.readLine();
    }

    public void dataWrite(String message) throws IOException {
        outputStream = new PrintWriter(connection().getOutputStream(), true);
        outputStream.println("insert Message here" + message);
    }

    public void closeConnection() throws IOException {
        connection().close();
    }

    public void closeServer() throws IOException {
        serverSocket.close();
    }
}
