package kundenservice;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CustomerServiceInterface {
    long createCustomer(String username, String email, LocalDate birthdate);

    Kunde getCustomerByID(long id);

    void deleteCustomer(long id);

    ArrayList<Kunde> getAllCustomer();

    void deleteAllCustomers();
}
