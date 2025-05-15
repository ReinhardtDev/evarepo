package idservice;

import java.util.ArrayList;

public class PrimeNumberGenerator implements Runnable {

    long lower;
    long upper;
    ArrayList<Long> primzahlen;

    public PrimeNumberGenerator(long lower, long upper) {
        this.lower = lower;
        this.upper = upper;
        this.primzahlen = new ArrayList<>();
    }

    public long generate() {
        long i;
        for (i = lower; i <= upper; i++) {
            boolean prime = isPrime(i);
            if(prime) {
                lower = i + 1;
                return i;
            }
        }
        return -1;
    }

    //https://www.geeksforgeeks.org/java-prime-number-program/
    private boolean isPrime(long n)  {
        // Corner case
        if (n <= 1)
            return false;
        // For n=2 or n=3 it will check
        if (n == 2 || n == 3)
            return true;
        // For multiple of 2 or 3 This will check
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        // It will check all the others condition
        for (long i = 5; i <= Math.sqrt(n); i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }

    @Override
    public void run() {
        long id = generate();
    }
}