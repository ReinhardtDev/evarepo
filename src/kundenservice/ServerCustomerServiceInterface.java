package kundenservice;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ServerCustomerServiceInterface {
    void createCustomer(String username, String email, LocalDate birthdate);

    void getAllCustomer();
}
