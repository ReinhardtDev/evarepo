package kundenservice;

import idservice.IDService;
import idservice.IDServiceParallel;

import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerService implements CustomerServiceInterface {
    private ArrayList<Kunde> customers;
    private IDService idService;
    private static CustomerService INSTANCE;

    public CustomerService(IDService idService) {
        this.idService = idService;
        this.customers = new ArrayList<>();
    }

    public static CustomerService getInstance(IDService idService) {
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
    public long createCustomer(String username, String email, LocalDate birthdate) {
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

        long customerID = idService.generateID();
        customers.add(new Kunde(customerID, username, email, birthdate));
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
