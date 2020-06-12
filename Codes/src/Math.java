import java.util.ArrayList;
import java.util.Arrays;

public class Math {
    // https://www.interviewbit.com/problems/all-factors/
    public static ArrayList<Integer> allFactors(int n) {
        ArrayList<Integer> out = new ArrayList<>();
        int pos = 0;

        // smaller the factor, larger will be its complement and
        // they would be at the opposite ends in sorted array hence
        // adding new factor pair in middle of them
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                if (n / i == i)
                    out.add(pos++, i);
                else {
                    out.add(pos++, i);
                    out.add(pos, n / i);
                }
            }
        }

        return out;
    }

    // https://www.interviewbit.com/problems/verify-prime/
    public static int isPrime(int n) {
        // 0,1 are not prime
        if (n < 2)
            return 0;

        // 2 is the only even prime
        if (n == 2)
            return 1;

        for (int i = 2; i * i <= n; i++) {
            // n has factors other than 1 and n
            if (n % i == 0)
                return 0;
        }

        return 1;
    }

    // https://www.interviewbit.com/problems/prime-numbers/
    public static ArrayList<Integer> sieve(int n) {
        // Time complexity = O (n log log n)

        if (n < 2)
            return new ArrayList<>();

        // mark initially as prime
        boolean[] sieve = new boolean[n + 1];
        Arrays.fill(sieve, 2, n + 1, true);

        // i = 2 to sqrt(n)
        for (int i = 2; i * i <= n; i++) {
            if (sieve[i]) {
                // mark all multiples of i as not prime
                for (int j = i + i; j <= n; j += i)
                    sieve[j] = false;
            }
        }

        ArrayList<Integer> out = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (sieve[i])
                out.add(i);
        }

        return out;
    }

    // https://www.interviewbit.com/problems/binary-representation/
    public static String findDigitsInBinary(int n) {
        if (n == 0)
            return "0";

        StringBuilder binary = new StringBuilder();

        while (n > 0) {
            int bit = n % 2;

            // insert the remainder bit at the beginning
            binary.insert(0, bit);

            n /= 2;
        }

        return binary.toString();
    }
}