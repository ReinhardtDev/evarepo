package idservice;

import java.util.*;
import java.util.concurrent.*;

public class IDServiceParallel {
    private final Set<Long> idStore;
    private final LinkedBlockingQueue<Long> bucket;
    private final ConcurrentMap<Long, Boolean> uniqueCheckMap = new ConcurrentHashMap<>();
    private final PrimeNumberGenerator primeNumberGenerator;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public IDServiceParallel() {
        this.idStore = ConcurrentHashMap.newKeySet();
        this.bucket = new LinkedBlockingQueue<>();
        this.primeNumberGenerator = new PrimeNumberGenerator(1000000000L, 9999999999L);
        batchGenerateID(100);
    }

    public List<Long> batchGenerateID(int count) {
        List<Future<Long>> futures = new ArrayList<>(count);
        List<Long> ids = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(() -> {
                long id;
                while (true) {
                    id = primeNumberGenerator.generate();
                    if (uniqueCheckMap.putIfAbsent(id, Boolean.TRUE) == null) {
                        idStore.add(id);
                        bucket.add(id);
                        break;
                    }
                }
                return id;
            }));
        }

        for (Future<Long> future : futures) {
            try {
                ids.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task execution failed: " + e.getMessage());
            }
        }

        return ids;
    }

    private long generateNext() {
        long id = primeNumberGenerator.generate();
        uniqueCheckMap.put(id, Boolean.TRUE);
        bucket.add(id);
        idStore.add(id);
        return id;
    }

    public Set<Long> getIds() {
        return new HashSet<>(idStore); // Return a copy to prevent external mutation
    }

    public void deleteID(long id) {
        idStore.remove(id);
        uniqueCheckMap.remove(id);
    }

    public void deleteAll() {
        idStore.clear();
        uniqueCheckMap.clear();
    }

    public void printIDs() {
        System.out.println(idStore);
    }

    public long getID() {
        Future<Long> bucketTask = executor.submit(bucket::take);

        Future<?> idTask = executor.submit(this::generateNext);

        try {
            return bucketTask.get();

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            System.err.println("Task execution failed: " + e.getMessage());
        }
        throw new RuntimeException("Task execution failed");
    }

    public void shutdown() {
        executor.shutdown();
    }
}
