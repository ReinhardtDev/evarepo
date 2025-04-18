package idservice;

import java.util.ArrayList;

public class PrimeNumberGenerator {

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
        long y;
        boolean isprime;
        for (i = lower; i <= upper; i++) {
            if (i == 1 || i == 0)
                continue;

            // Using flag variable to check
            // if x is prime or not
            isprime = true;

            for (y = 2; y <= i / 2; ++y) {
                if (i % y == 0) {
                    isprime = false;
                    break;
                }
            }

            // If flag is 1 then x is prime but
            // if flag is 0 then x is not prime
            if (isprime) {
                primzahlen.add(i);
                this.lower = i + 1;
                return i;
            }
        }
        return -1;
    }
}