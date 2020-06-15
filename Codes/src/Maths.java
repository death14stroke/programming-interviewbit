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

    // TODO: optimize factorial
    // https://www.interviewbit.com/problems/sorted-permutation-rank/
    static int findRank(String s) {
        int p = 1000003, MAX_CHAR = 256;
        // count[i] : count of characters <= s.charAt(i)
        int[] count = new int[MAX_CHAR];

        // count frequency initially
        for (char c : s.toCharArray())
            count[c]++;

        // get cumulative frequency
        for (int i = 1; i < MAX_CHAR; i++)
            count[i] += count[i - 1];

        int n = s.length();
        long rank = 1, fact;

        for (int i = 0; i < s.length(); i++) {
            // fact(n) % p
            fact = modFact(n - 1 - i, p);

            char c = s.charAt(i);
            // rank = rank + (#chars less than c)*fact(#chars to the right)
            rank += ((long) count[c - 1] * fact) % p;

            // remove current character from cumulative frequency of all following characters
            for (int j = c; j < MAX_CHAR; j++)
                count[j]--;
        }

        rank %= p;

        return (int) rank;
    }

    private static long modFact(int n, int p) {
        long fact = 1;

        for (long i = 1; i <= n; i++)
            fact = (fact * i) % p;

        return fact % p;
    }

    // TODO: simpler approach from editorial hint and solution
    // https://www.interviewbit.com/problems/largest-coprime-divisor/
    static int cpFact(int a, int b) {
        a /= gcd(a, b);

        int ans = 1;

        for (int i = 1; i * i <= a; i++) {
            if (a % i == 0) {
                if (a / i > ans && gcd(a / i, b) == 1)
                    ans = a / i;
                else if (i > ans && gcd(i, b) == 1)
                    ans = i;
            }
        }

        return ans;
    }

    // TODO: modular inverse for prime number
    // https://www.interviewbit.com/problems/sorted-permutation-rank-with-repeats/
    static int findRepeatRank(String s) {
        int p = 1000003, MAX_CHAR = 256;

        int n = s.length();
        int[] count = new int[MAX_CHAR];

        for (char c : s.toCharArray())
            count[c]++;

        for (int i = 1; i < MAX_CHAR; i++)
            count[i] += count[i - 1];

        long rank = 1, fact;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            fact = modFact(n - 1 - i, p);
            long num = ((long) count[c - 1] * fact) % p;
            long den = modFact(count[0], p);

            for (int j = 1; j < MAX_CHAR; j++) {
                fact = modFact(count[j] - count[j - 1], p);
                den = (den * fact) % p;
            }

            rank = (rank + (num * modularInverse(den, p)) % p) % p;

            for (int j = c; j < MAX_CHAR; j++)
                count[j]--;
        }

        return (int) rank;
    }

    private static long modularInverse(long a, int p) {
        return modPower(a, p - 2, p);
    }

    private static long modPower(long x, int y, int p) {
        long res = 1;

        while (y > 0) {
            if (y % 2 != 0)
                res = (res * x) % p;

            x = (x * x) % p;
            y /= 2;
        }

        return res;
    }

    // TODO: O(1) space and O(n) time mathematically
    // https://www.interviewbit.com/problems/grid-unique-paths/
    static int uniquePaths(int m, int n) {
        if (m == 1 && n == 1)
            return 1;

        int[][] dp = new int[m][n];

        dp[m - 1][n - 1] = 0;

        for (int i = 0; i < m - 1; i++)
            dp[i][n - 1] = 1;

        for (int j = 0; j < n - 1; j++)
            dp[m - 1][j] = 1;

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--)
                dp[i][j] = dp[i][j + 1] + dp[i + 1][j];
        }

        for (int[] arr : dp) {
            for (int val : arr)
                System.out.printf("%2d ", val);
            System.out.println();
        }
        System.out.println();

        return dp[0][0];
    }

    // https://www.interviewbit.com/problems/rearrange-array/
    static void arrange(ArrayList<Integer> a) {
        int temp = -1;
        for (int i = 0; i < a.size(); i++) {
            int pos = a.get(i);

            if (pos >= i) {
                if (temp == -1)
                    temp = a.get(i);
                a.set(i, a.get(pos));
            } else {
                a.set(pos, temp);
                temp = -1;
            }
        }
    }
}