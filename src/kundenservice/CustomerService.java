package kundenservice;

import idservice.IDService;
import idservice.IDServiceParallel;
import logservice.LogService;
import logservice.LogServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerService implements CustomerServiceInterface {
    private ArrayList<Kunde> customers;
    private IDServiceParallel idService;
    private static CustomerService INSTANCE;
    private LogServiceInterface logService;

    public CustomerService(IDServiceParallel idService) {
        this.idService = idService;
        this.customers = new ArrayList<>();
        this.logService = LogService.getInstance();
    }

    public static CustomerService getInstance(IDServiceParallel idService) {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService(idService);
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
    public synchronized long createCustomer(String username, String email, LocalDate birthdate) {
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

        long customerID = idService.generateNext();
        customers.add(new Kunde(customerID, username, email, birthdate));
        logService.logEvent("CREATE_CUSTOMER", customerID);

        return customerID;
    }

    @Override
    public Kunde getCustomerByID(long id) {
        for(Kunde kunde: customers) {
            if(kunde.getId() == id) {
                return kunde;
            }
        }
        return null;
    }

    @Override
    public void deleteCustomer(long id) {
        for(Kunde kunde: customers) {
            if(kunde.getId() == id) {
                customers.remove(kunde);
                idService.deleteID(id);
                System.out.println("Customer successfully deleted.");
            }
        }
    }

    @Override
    public ArrayList<Kunde> getAllCustomer() {
        return customers;
    }

    @Override
    public void deleteAllCustomers() {
        for(Kunde kunde: customers) {
            idService.deleteID(kunde.getId());
        }
        customers.clear();
    }
}