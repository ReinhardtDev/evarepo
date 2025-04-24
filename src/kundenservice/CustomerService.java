package kundenservice;

import idservice.IDService;

import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerService {
    private ArrayList<Kunde> customers;
    private IDService idService;

    public CustomerService(IDService idService) {
        this.idService = idService;
        this.customers = new ArrayList<>();
    }

    public long createCustomer(String username, String email, LocalDate birthdate) {
        //missing check for valid email
        if(birthdate.isAfter(LocalDate.now().minusYears(18L))) {
            throw new IllegalArgumentException("Muss mindestens 18 Jahre alt sein.");
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

        throw new IllegalArgumentException("Kunde mit ID " + id + "nicht gefunden.");
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
