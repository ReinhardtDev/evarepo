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
        //double starTime = System.nanoTime();
        long id = primeNumberGenerator.generate();
        //double endTime = System.nanoTime();
        //System.out.println("Primality check exec time: "  + (endTime - starTime) / 1000000 + "ms");
        if(id != -1) {
            idStore.add(id);
            return id;
        }
        return -1;
    }

    public void deleteID(long id) {
        idStore.remove(id);
    }
}
