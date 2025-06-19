package event;

import idservice.IDServiceParallel;
import logservice.LogService;
import logservice.LogServiceInterface;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServerEventService implements ServerEventServiceInterface {
    private LogServiceInterface logService;

    public ServerEventService(IDServiceParallel idService, Socket socket) {
        this.events = new ArrayList<>();
        this.idService = idService;
        this.logService = LogService.getInstance();
        this.socket = socket;
    }

    public static ServerEventService getInstance(IDServiceParallel idService, Socket socket) {
        if (INSTANCE == null) {
            INSTANCE = new ServerEventService(idService, socket);
        }
        return INSTANCE;
    }


    @Override
    public synchronized void createEvent(String title, String location, LocalDate date, int quota) {
        if (quota < 0) {
            throw new IllegalArgumentException("The quota must not be negative!");
        }

        if (!date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The event must take place in the future!");
        }

        String call = "create,event," + title + "," + location + "," + date + "," + quota;

        String result = connect(call);
        System.out.println("Result: " + result);
    }

    private String connect(String call) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(call);
            return in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    @Override
    public void getAllEvents() {

        String call = "getAll,event";
        String result = connect(call);
        System.out.println("Result: " + result);
    }

    private static ServerEventService INSTANCE;

    private ArrayList<Event> events;

    private IDServiceParallel idService;

    private Socket socket;
}
