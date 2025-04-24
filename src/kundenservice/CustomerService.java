package kundenservice;

import idservice.IDService;

import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerService {
    private ArrayList<Kunden> customers;
    private IDService idService;

    public CustomerService(ArrayList<Kunden> customers, IDService idService) {
        this.customers = customers;
        this.idService = idService;
    }

    public Kunde createCustomer(String username, String email, LocalDate birthdate) {
        //missing check for valid email
        if(birthdate.isAfter(LocalDate.now().minusYears(18L))) {
            throw new IllegalArgumentException("Muss mindestens 18 Jahre alt sein.");
        }

        long customerID = idService.generateID();
        customers.add(new Kunde(customerID, username, email, birthdate));
    }
}
