package idservice;

import java.util.ArrayList;

public class IDService {
    private ArrayList<Long> idStore;

    private PrimeNumberGenerator primeNumberGenerator;

    public IDService() {
        this.idStore = new ArrayList<>();
        this.primeNumberGenerator = new PrimeNumberGenerator(1000000000L, 9999999999L);
    }

    public long generateID() {
        long id = primeNumberGenerator.generate();
        idStore.add(id);
        return id;
    }

    public void deleteID(long id) {
        idStore.remove(id);
    }
}
