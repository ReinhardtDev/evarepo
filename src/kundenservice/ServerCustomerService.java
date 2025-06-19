package kundenservice;

import idservice.IDServiceParallel;
import logservice.LogService;
import logservice.LogServiceInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServerCustomerService implements ServerCustomerServiceInterface {
    private ArrayList<Kunde> customers;
    private IDServiceParallel idService;
    private static ServerCustomerService INSTANCE;
    private LogServiceInterface logService;
    private Socket socket;

    public ServerCustomerService(IDServiceParallel idService, Socket socket) {
        this.idService = idService;
        this.customers = new ArrayList<>();
        this.logService = LogService.getInstance();
        this.socket = socket;
    }

    public static ServerCustomerService getInstance(IDServiceParallel idService, Socket socket) {
        if (INSTANCE == null) {
            INSTANCE = new ServerCustomerService(idService, socket);
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
        //missing check for valid email
        if(username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (!isValidEmail(email) || email.isEmpty()) {
            throw new IllegalArgumentException("Invalid email");
        }

        if(birthdate.isAfter(LocalDate.now().minusYears(18L))) {
            throw new IllegalArgumentException("Invalid birthdate");
        }

        String call = "create,customer," + username + "," + email + "," + birthdate;
        String result = connect(call);

        System.out.println(result);
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
    public void getAllCustomer() {
        String call = "getAll,customer";
        String result = connect(call);

        System.out.println(result);
    }

}
