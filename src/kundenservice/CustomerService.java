package kundenservice;

import idservice.IDService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerService {
    private ArrayList<Kunde> customers;
    private IDService idService;

    public CustomerService(IDService idService) {
        this.idService = idService;
        this.customers = new ArrayList<>();
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public long createCustomer(String username, String email, LocalDate birthdate) {
        //missing check for valid email
        if(username.isEmpty()) {
            return 0;
        }

        if (!isValidEmail(email) || email.isEmpty()) {
            return 1;
        }

        if(birthdate.isAfter(LocalDate.now().minusYears(18L))) {
            return 3;
        }

        long customerID = idService.generateID();
        customers.add(new Kunde(customerID, username, email, birthdate));
        return customerID;
    }

    public Kunde getCustomerByID(long id) {
        for(Kunde kunde: customers) {
            if(kunde.getId() == id) {
                return kunde;
            }
        }
        return null;
    }

    public void deleteCustomer(long id) {
        for(Kunde kunde: customers) {
            if(kunde.getId() == id) {
                customers.remove(kunde);
                idService.deleteID(id);
                System.out.println("Customer successfully deleted.");
            }
        }
    }

    public ArrayList<Kunde> getAllCustomer() {
        return customers;
    }

    public void deleteAllCustomers() {
        for(Kunde kunde: customers) {
            idService.deleteID(kunde.getId());
        }
        customers.clear();
    }
}
