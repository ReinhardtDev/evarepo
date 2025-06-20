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

    public ServerEventService(IDServiceParallel idService, Socket socket, PrintWriter out, ObjectInputStream in) {
        this.events = new ArrayList<>();
        this.idService = idService;
        this.logService = LogService.getInstance();
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    public static ServerEventService getInstance(IDServiceParallel idService, Socket socket, PrintWriter out, ObjectInputStream in) {
        if (INSTANCE == null) {
            INSTANCE = new ServerEventService(idService, socket, out, in);
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

        ArrayList<Event> result = connect(call);
        System.out.println("Result: " + result.toString());
    }

    private ArrayList<Event> connect(String call) {

        try {
            out.println(call);
            return (ArrayList<Event>) in.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<Event>();
        }
    }

    @Override
    public void getAllEvents() {
        String call = "getAll,event";
        ArrayList<Event> result = connect(call);
        for (Event event : result) {
            System.out.println(event);
        }
    }

    private static ServerEventService INSTANCE;

    private ArrayList<Event> events;

    private IDServiceParallel idService;

    private Socket socket;

    private PrintWriter out;

    private ObjectInputStream in;
}
