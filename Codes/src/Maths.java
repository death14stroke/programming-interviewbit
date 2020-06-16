import java.util.*;

public class Maths {
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

    // https://www.interviewbit.com/problems/prime-sum/
    static ArrayList<Integer> primesum(int n) {
        boolean[] sieve = new boolean[n + 1];
        Arrays.fill(sieve, 2, n + 1, true);

        // find primes up to n with sieve: O(n log log n)
        for (int i = 2; i * i <= n; i++) {
            if (sieve[i]) {
                for (int j = 2 * i; j <= n; j += i)
                    sieve[j] = false;
            }
        }

        for (int i = 2; i <= n / 2; i++) {
            // if i and n-i both are prime
            if (sieve[i] && sieve[n - i]) {
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(i);
                ans.add(n - i);
                return ans;
            }
        }

        return new ArrayList<>();
    }

    // https://www.interviewbit.com/problems/sum-of-pairwise-hamming-distance/
    static int hammingDistance(final List<Integer> a) {
        long sum = 0, p = 1000000007, n = a.size();

        // check for each bit position
        for (int i = 0; i < 32; i++) {
            // # of integers where bit i is 0
            // n - zeroCnt = # of integers where bit i is 1
            long zeroCnt = 0;

            for (int val : a) {
                if ((val & (1 << i)) == 0)
                    zeroCnt++;
            }

            // arrange 1 each from both groups and order matters hence 2x
            sum += (zeroCnt * (n - zeroCnt) * 2L) % p;
        }

        return (int) (sum % p);
    }

    // https://www.interviewbit.com/problems/fizzbuzz/
    static ArrayList<String> fizzBuzz(int n) {
        ArrayList<String> out = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            String str = String.valueOf(i);

            // div by both 3 and 5
            if (i % 15 == 0)
                str = "FizzBuzz";
                // div by only 3
            else if (i % 3 == 0)
                str = "Fizz";
                // div by only 5
            else if (i % 5 == 0)
                str = "Buzz";

            out.add(str);
        }

        return out;
    }

    // https://www.interviewbit.com/problems/power-of-two-integers/
    static int isPower(int n) {
        if (n < 2)
            return 1;

        // i^x = n. Check if x is integer or not
        // Largest possible i will be sqrt(n)^2 = n hence loop till sqrt(n)
        for (int i = 2; i * i <= n; i++) {
            double x = Math.log(n) / Math.log(i);

            // x is almost integer
            if (x - (int) x < 0.0000001)
                return 1;
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/excel-column-number/
    static int titleToNumber(String s) {
        int ans = 0, power = 1;

        for (int i = s.length() - 1; i >= 0; i--) {
            // 26^(bit pos) * (int mapping of char) for each bit
            ans += (s.charAt(i) - 'A' + 1) * power;
            power *= 26;
        }

        return ans;
    }

    // https://www.interviewbit.com/problems/excel-column-title/
    static String convertToTitle(int n) {
        char c;
        StringBuilder builder = new StringBuilder("");

        while (n > 0) {
            // 1 is 'A' not 0 hence subtract 1 every time
            n--;

            c = (char) ('A' + (n % 26));
            builder.insert(0, c);

            n /= 26;
        }

        return builder.toString();
    }

    // https://www.interviewbit.com/problems/palindrome-integer/
    static int isPalindrome(int n) {
        if (n < 0)
            return 0;

        // reverse the int and check
        return (n == reverseUtil(n)) ? 1 : 0;
    }

    private static int reverseUtil(int n) {
        int res = 0;

        // multiply res by 10 to shift all digits one pos to left
        while (n > 0) {
            res = (res * 10) + (n % 10);
            n /= 10;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/reverse-integer/
    static int reverse(int n) {
        boolean neg = false;
        // make number positive for reverse func and mark flag
        if (n < 0) {
            neg = true;
            n = -n;
        }

        n = reverseOverflowUtil(n);

        // if original num was negative
        if (neg)
            n = -n;

        return n;
    }

    private static int reverseOverflowUtil(int n) {
        long res = 0;

        // multiply res by 10 to shift all digits one pos to left
        while (n > 0) {
            res = (res * 10L) + (n % 10);

            n /= 10;
        }

        // overflow has occurred
        if (res > Integer.MAX_VALUE)
            return 0;
        return (int) res;
    }

    // https://www.interviewbit.com/problems/greatest-common-divisor/
    static int gcd(int A, int B) {
        if (B == 0)
            return A;
        return gcd(B, A % B);
    }

    // https://www.interviewbit.com/problems/trailing-zeros-in-factorial/
    static int trailingZeroes(int n) {
        int cnt = 0;

        // count the #5's in the factorial as #5's < #2's
        // #5's = (n/5) + (n/25) +... = (n/5) + ((n/5)/5) +...
        while (n > 0) {
            cnt += n / 5;
            n /= 5;
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/sorted-permutation-rank/
    static int findRank(String s) {
        int MAX_CHAR = 256, p = 1000003;
        int n = s.length();

        // count[i] = #characters in string < char i
        int[] count = new int[MAX_CHAR];

        // each character count
        for (char c : s.toCharArray())
            count[c]++;

        // perform cumulative count
        for (int i = 1; i < MAX_CHAR; i++)
            count[i] += count[i - 1];

        // fact[i] = i! mod p
        int[] fact = modFact(n, p);

        char c;
        long rank = 1;

        for (int i = 0; i < n; i++) {
            c = s.charAt(i);

            // rank = rank + (#chars smaller than c)*(#places on the right)!
            rank += ((long) count[c - 1] * (long) fact[n - 1 - i]) % p;

            // remove the current character from all greater characters count
            for (int j = c; j < MAX_CHAR; j++)
                count[j]--;
        }

        rank %= p;

        return (int) rank;
    }

    private static int[] modFact(int n, int p) {
        int[] fact = new int[n + 1];
        fact[0] = 1;

        for (int i = 1; i <= n; i++)
            fact[i] = (int) (((long) fact[i - 1] * (long) i) % p);

        return fact;
    }

    // https://www.interviewbit.com/problems/largest-coprime-divisor/
    static int cpFact(int a, int b) {
        while (gcd(a, b) != 1)
            a /= gcd(a, b);

        return a;
    }

    // https://www.interviewbit.com/problems/sorted-permutation-rank-with-repeats/
    static int findRepeatRank(String s) {
        int MAX_CHAR = 256, p = 1000003;
        int n = s.length();

        // count[i] = #characters in string < char i
        int[] count = new int[MAX_CHAR];

        // each character count
        for (char c : s.toCharArray())
            count[c]++;

        // perform cumulative count
        for (int i = 1; i < 256; i++)
            count[i] += count[i - 1];

        // fact[i] = i! mod p
        int[] fact = modFact(n, p);

        long rank = 1, num, den;
        char c;

        for (int i = 0; i < n; i++) {
            c = s.charAt(i);

            // num = (#chars smaller than c)*(#places on the right)!
            num = ((long) count[c - 1] * (long) fact[n - 1 - i]) % p;
            // den = (p1! * p2! * ... pn!)
            den = fact[count[0]] % p;
            for (int j = 1; j < MAX_CHAR; j++)
                den = (den * (long) (fact[count[j] - count[j - 1]])) % p;

            // rank = rank + num/den
            // modular inverse for modulo division (p is prime)
            rank = (rank + (num * modInverse(den, p)) % p) % p;

            // remove the current character from all greater characters count
            for (int j = c; j < MAX_CHAR; j++)
                count[j]--;
        }

        return (int) rank;
    }

    // Fermat's little theorem: a^(p-1) mod p = a if p is prime
    private static long modInverse(long a, int p) {
        return modPower(a, p - 2, p);
    }

    private static long modPower(long x, int y, int p) {
        long res = 1;

        while (y > 0) {
            if (y % 2 == 1)
                res = (res * x) % p;

            // x^y = (x^2)^(y/2)
            x = (x * x) % p;
            y /= 2;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/numbers-of-length-n-and-value-less-than-k/
    static int solve(ArrayList<Integer> a, int b, int c) {
        int revC = 0, c1 = c, n = a.size();

        while (c1 > 0) {
            revC = revC * 10 + c1 % 10;
            c1 /= 10;
        }

        int[][] dp = new int[n][b];
        dp[0][0] = a.get(0) < (c % 10) ? 1 : 0;

        for (int i = 1; i < n; i++)
            dp[i][0] = dp[i - 1][0] + (a.get(i) < (c % 10) ? 1 : 0);

        for (int i = 0; i < n; i++) {
            c1 = revC;

            for (int j = 1; j < b; j++) {
                if (a.get(i) == 0 && j == b - 1) {
                    dp[i][j] = 0;
                    continue;
                }

                int dig = c1 % 10;

                if (a.get(i) == dig) {
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                } else if (a.get(i) > dig)
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = C(n, j) + dp[i - 1][j];

                c1 /= 10;
            }
        }

        Array.printMatrix(dp);

        return dp[n - 1][b - 1];
    }

    private static int C(int n, int r) {
        if (r > n / 2)
            r = n - r;

        int num = 1, den = 1;

        for (int i = 0; i < r; i++) {
            num *= (n - i);
            den *= (r - i);
        }

        return num / den;
    }

    // TODO: O(1) space and O(n) time mathematically
    // https://www.interviewbit.com/problems/grid-unique-paths/
    static int uniquePaths(int m, int n) {
        return 0;
    }

    // https://www.interviewbit.com/problems/rearrange-array/
    static void arrange(ArrayList<Integer> a) {

    }
}