package kundenservice;

import idservice.IDServiceParallel;
import logservice.LogService;
import logservice.LogServiceInterface;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServerCustomerService implements ServerCustomerServiceInterface {
    private ArrayList<Kunde> customers;
    private IDServiceParallel idService;
    private static ServerCustomerService INSTANCE;
    private LogServiceInterface logService;
    private Socket socket;
    private PrintWriter out;
    private ObjectInputStream in;

    public ServerCustomerService(IDServiceParallel idService, Socket socket, PrintWriter out, ObjectInputStream in) {
        this.idService = idService;
        this.customers = new ArrayList<>();
        this.logService = LogService.getInstance();
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    public static ServerCustomerService getInstance(IDServiceParallel idService, Socket socket, PrintWriter out, ObjectInputStream in) {
        if (INSTANCE == null) {
            INSTANCE = new ServerCustomerService(idService, socket, out, in);
        }
        return INSTANCE;
    }

    public boolean isValidEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            String[] part1 = email.split("@");
            String[] part2 = part1[1].split("\\.");
            return !part1[0].isEmpty() && !part2[0].isEmpty() && !part2[1].isEmpty();
        }
        return false;
    }

    @Override
    public synchronized void createCustomer(String username, String email, LocalDate birthdate) {
        if(username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        /*if (!isValidEmail(email) || email.isEmpty()) {
            throw new IllegalArgumentException("Invalid email");
        }*/

        if(birthdate.isAfter(LocalDate.now().minusYears(18L))) {
            throw new IllegalArgumentException("Invalid birthdate");
        }

        String call = "create,customer," + username + "," + email + "," + birthdate;
        ArrayList<Kunde> result = connect(call);

        System.out.println(result);
    }

    private ArrayList<Kunde> connect(String call) {
        try {
            out.println(call);
            return (ArrayList<Kunde>) in.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void getAllCustomer() {
        String call = "getAll,customer";
        ArrayList<Kunde> result = connect(call);
        for (Kunde kunde : result) {
            System.out.println(kunde);
        }
        System.out.println("\n");
    }

}
