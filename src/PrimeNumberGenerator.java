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

    public void generate(int anzahl) {
        long i;
        long y;
        long flg;
        for (i = lower; i <= upper; i++) {
            if (i == 1 || i == 0)
                continue;

            // Using flag variable to check
            // if x is prime or not
            flg = 1;

            for (y = 2; y <= i / 2; ++y) {
                if (i % y == 0) {
                    flg = 0;
                    break;
                }
            }

            // If flag is 1 then x is prime but
            // if flag is 0 then x is not prime
            if (flg == 1) {
                primzahlen.add(i);
                System.out.print(i + "\n");
                if(primzahlen.size() == anzahl) {
                    break;
                }
            }
        }
    }
}