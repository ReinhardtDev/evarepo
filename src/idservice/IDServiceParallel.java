package idservice;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IDServiceParallel {
    private List<Long> idStore;
    private final Set<Long> uniqueCheckSet = Collections.synchronizedSet(new HashSet<>());
    private final PrimeNumberGenerator primeNumberGenerator;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public IDServiceParallel() {
        this.idStore = Collections.synchronizedList(new ArrayList<>());
        this.primeNumberGenerator = new PrimeNumberGenerator(1000000000L, 9999999999L);
    }

    public ArrayList<Long> generateID(int count) {
        ArrayList<Future<Long>> futures = new ArrayList<>();
        ArrayList<Long> ids = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(() -> {
                long id;
                do {
                    id = primeNumberGenerator.generate();
                } while (!addUniqueId(id));
                return id;
            }));
        }

        for (Future<Long> future : futures) {
            try {
                long id = future.get();
                ids.add(id);
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task execution failed: " + e.getMessage());
            }
        }
        return ids;
    }

    private boolean addUniqueId(Long id) {
        synchronized (uniqueCheckSet) {
            if (uniqueCheckSet.contains(id)) {
                return false;
            }
            uniqueCheckSet.add(id);
            idStore.add(id);
            return true;
        }
    }

    public List<Long> getIds() {
        return idStore;
    }

    public void deleteID(long id) {
        idStore.remove(id);
        uniqueCheckSet.remove(id);
    }

    public void deleteAll() {
        idStore.clear();
        uniqueCheckSet.clear();
    }

    public void printIDs() {
        System.out.println(idStore);
    }
}
