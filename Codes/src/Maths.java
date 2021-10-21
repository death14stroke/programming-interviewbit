import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Maths {
    // https://www.interviewbit.com/problems/all-factors/
    public static ArrayList<Integer> allFactors(int A) {
        ArrayList<Integer> res = new ArrayList<>();
        // add all factors till sqrt(A)
        for (int i = 1; i * i <= A; i++) {
            if (A % i == 0)
                res.add(i);
        }

        // add the remaining complement factors (larger the factor till sqrt(A), smaller its complement will be)
        for (int i = res.size() - 1; i >= 0; i--) {
            int fact = res.get(i);
            // if not the same factor, add to result
            if (A / fact != fact)
                res.add(A / fact);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/verify-prime/
    public static int isPrime(int A) {
        // 0,1 are not prime
        if (A < 2)
            return 0;

        for (int i = 2; i * i <= A; i++) {
            // A has factors other than 1 and A
            if (A % i == 0)
                return 0;
        }

        return 1;
    }

    // https://www.interviewbit.com/problems/prime-numbers/
    public static ArrayList<Integer> sieve(int A) {
        // Time complexity = O (A log log A)
        ArrayList<Integer> res = new ArrayList<>();
        if (A < 2)
            return res;

        // mark initially as prime
        boolean[] sieve = new boolean[A + 1];
        Arrays.fill(sieve, 2, A + 1, true);

        // i = 2 to sqrt(A)
        for (int i = 2; i * i <= A; i++) {
            if (sieve[i]) {
                // mark all multiples of i as not prime
                for (int j = i * i; j <= A; j += i)
                    sieve[j] = false;
            }
        }

        for (int i = 2; i <= A; i++) {
            if (sieve[i])
                res.add(i);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/binary-representation/
    public static String findDigitsInBinary(int A) {
        if (A == 0)
            return "0";

        StringBuilder builder = new StringBuilder();
        // keep dividing by 2 till A = 0
        while (A > 0) {
            // insert the remainder bit at the beginning
            builder.append(A % 2);
            A /= 2;
        }

        return builder.reverse().toString();
    }

    // https://www.interviewbit.com/problems/total-moves-for-bishop/
    static int movesForBishop(int A, int B) {
        // calculate moves possible in all 4 directions
        int topLeft = Math.min(A - 1, B - 1);
        int topRight = Math.min(A - 1, 8 - B);
        int bottomLeft = Math.min(8 - A, B - 1);
        int bottomRight = Math.min(8 - A, 8 - B);

        return topLeft + topRight + bottomLeft + bottomRight;
    }

    // https://www.interviewbit.com/problems/distribute-in-circle/
    static int distributeInCircle(int A, int B, int C) {
        // A: item pos in queue
        // C: position in circle starting from 1
        // B: size of circle
        return (C + A - 1) % B;
    }

    // https://www.interviewbit.com/problems/prime-sum/
    static int[] primeSum(int A) {
        boolean[] sieve = new boolean[A];
        Arrays.fill(sieve, 2, A, true);

        // find primes up to A with sieve: O(A log log A)
        for (int i = 2; i * i <= A; i++) {
            if (sieve[i]) {
                for (int j = i * i; j < A; j += i)
                    sieve[j] = false;
            }
        }

        for (int i = 2; i <= A / 2; i++) {
            // if i and A-i both are prime
            if (sieve[i] && sieve[A - i])
                return new int[]{i, A - i};
        }

        return new int[0];
    }

    // https://www.interviewbit.com/problems/sum-of-pairwise-hamming-distance/
    static int hammingDistance(final int[] A) {
        int n = A.length, MOD = 1000000007;
        long sum = 0;

        // check for each bit position
        for (int i = 0; i < 32; i++) {
            // zeroCnt = # of integers where bit i is 0
            // n - zeroCnt = # of integers where bit i is 1
            long zeroCnt = 0;
            int bitPos = 1 << i;

            for (int val : A) {
                if ((val & bitPos) == 0)
                    zeroCnt++;
            }

            // arrange 1 each from both groups and order matters hence 2x
            sum = (sum + zeroCnt * (n - zeroCnt) * 2L) % MOD;
        }

        return (int) sum;
    }

    // https://www.interviewbit.com/problems/fizzbuzz/
    static String[] fizzBuzz(int A) {
        String[] res = new String[A];

        for (int i = 1; i <= A; i++) {
            // div by both 3 and 5
            if (i % 15 == 0)
                res[i - 1] = "FizzBuzz";
                // div by only 3
            else if (i % 3 == 0)
                res[i - 1] = "Fizz";
                // div by only 5
            else if (i % 5 == 0)
                res[i - 1] = "Buzz";
                // put number as string
            else
                res[i - 1] = String.valueOf(i);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/is-rectangle/
    static int isRectangle(int A, int B, int C, int D) {
        // check if any pair of opposite sides is equal
        if ((A == B && C == D) || (A == C && B == D) || (A == D && B == C))
            return 1;
        // not a rectangle else
        return 0;
    }

    // https://www.interviewbit.com/problems/step-by-step/
    static int stepByStep(int A) {
        // moving in either side is symmetric
        A = Math.abs(A);
        int sum = 0, steps = 0;

        // keep moving forward till either destination is reached or
        // the difference between target and destination is not even
        while (sum < A || (sum - A) % 2 != 0) {
            steps++;
            sum += steps;
        }

        // if difference is even i.e. sum - target = 2 * i,
        // we can take ith step in the opposite direction to reach our destination
        return steps;
    }

    // https://www.interviewbit.com/problems/power-of-two-integers/
    static int isPower(int A) {
        if (A < 2)
            return 1;

        // i^x = A. Check if x is integer or not
        // the largest possible 'i' will be sqrt(A)^2 = A hence loop till sqrt(A)
        for (int i = 2; i * i <= A; i++) {
            double x = Math.log(A) / Math.log(i);
            // x is almost integer
            if (x - (int) x < 0.0000001)
                return 1;
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/excel-column-number/
    static int titleToNumber(String A) {
        int res = 0;
        // move from left to right and multiply by 26 (equivalent of 10 in decimal)
        for (char c : A.toCharArray())
            res = res * 26 + (c - 'A' + 1);

        return res;
    }

    // https://www.interviewbit.com/problems/excel-column-title/
    static String convertToTitle(int A) {
        StringBuilder builder = new StringBuilder();

        while (A > 0) {
            // 1 is 'A' not 0 hence subtract 1 every time
            A--;

            char c = (char) ('A' + A % 26);
            builder.append(c);

            A /= 26;
        }

        return builder.reverse().toString();
    }

    // https://www.interviewbit.com/problems/palindrome-integer/
    static int isPalindrome(int A) {
        if (A < 0)
            return 0;

        // reverse the int and check
        return A == reverseUtil(A) ? 1 : 0;
    }

    // util to reverse number
    private static int reverseUtil(int A) {
        int res = 0;
        // multiply res by 10 to shift all digits one pos to left
        while (A > 0) {
            res = (res * 10) + (A % 10);
            A /= 10;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/reverse-integer/
    static int reverse(int A) {
        int sign = 1;
        // make number positive for reverse func and update sign
        if (A < 0) {
            sign = -1;
            A = -A;
        }

        return reverseOverflowUtil(A) * sign;
    }

    // util to reverse the number and check if it overflows or not
    private static int reverseOverflowUtil(int A) {
        long res = 0;
        // multiply res by 10 to shift all digits one pos to left
        while (A > 0) {
            res = (res * 10) + (A % 10);
            A /= 10;
        }

        // overflow has occurred
        if (res > Integer.MAX_VALUE)
            return 0;
        return (int) res;
    }

    // https://www.interviewbit.com/problems/next-smallest-palindrome/
    static String nextSmallestPalindrome(String A) {
        int n = A.length();
        // Case 1: All '9's in the string
        if (allNines(A)) {
            StringBuilder builder = new StringBuilder("1");
            //noinspection StringRepeatCanBeUsed
            for (int i = 0; i < n - 1; i++)
                builder.append(0);
            builder.append(1);

            return builder.toString();
        }

        int mid = n / 2;
        // start from mid two elements (if even) or the two elements surrounding the middle character (if odd)
        int i = mid - 1;
        int j = (n % 2 == 0) ? mid : mid + 1;

        // Remaining cases: initially skip the common middle part
        while (i >= 0 && A.charAt(i) == A.charAt(j)) {
            i--;
            j++;
        }

        // flag to check if only copying left half to right will not work
        boolean leftSmaller = (i < 0) || (A.charAt(i) < A.charAt(j));

        StringBuilder builder = new StringBuilder(A);
        // copy left half to right half
        while (i >= 0) {
            builder.setCharAt(j, A.charAt(i));
            i--;
            j++;
        }

        // rest of the cases
        if (!leftSmaller)
            return builder.toString();

        // Case 2: Input string is palindrome
        // Case 3: ith digit is less than jth digit hence only copying won't work
        // In both cases: add 1 to middle character and keep adding the carry on left
        // and copying digits to the right
        int carry = 1;
        // if length is odd, add carry to the middle digit
        if (n % 2 == 1) {
            // add carry to digit
            int num = A.charAt(mid) - '0' + carry;
            char c = (char) ('0' + num % 10);
            carry = num / 10;
            // update the middle character
            builder.setCharAt(mid, c);
        }

        // start from mid two elements (if even) or the two elements surrounding the middle element (if odd)
        i = mid - 1;
        j = (n % 2 == 0) ? mid : mid + 1;

        // loop till carry is found or reached left end
        while (i >= 0 && carry > 0) {
            // add carry to digit
            int num = A.charAt(i) - '0' + carry;
            char c = (char) ('0' + num % 10);
            carry = num / 10;

            // update characters at positions i and its mirror j
            builder.setCharAt(i--, c);
            builder.setCharAt(j++, c);
        }

        return builder.toString();
    }

    // util to check if string is of the form "9999...9"
    private static boolean allNines(String A) {
        for (char c : A.toCharArray()) {
            if (c != '9')
                return false;
        }

        return true;
    }

    // https://www.interviewbit.com/problems/greatest-common-divisor/
    // Euclid's algorithm - O(log (min(a, b)))
    static int gcd(int A, int B) {
        if (B == 0)
            return A;
        return gcd(B, A % B);
    }

    // https://www.interviewbit.com/problems/find-nth-fibonacci/
    static int nthFibonacci(int A) {
        // F1 = 1
        if (A == 1)
            return 1;

        int p = 1000000007;
        long[][] matrix = {
                {1, 1},
                {1, 0}
        };
        // Mn = power(Mat({1, 1}, {1, 0}), A - 2) where Mn[0][0] = Fn
        matrix = matrixPowerMod(matrix, A - 2, p);

        return (int) matrix[0][0];
    }

    // util to calculate matrix power modulo
    private static long[][] matrixPowerMod(long[][] A, int x, int p) {
        long[][] res = {
                {1, 1},
                {1, 1}
        };

        // calculate power(A, x) in O(log x)
        while (x > 0) {
            if (x % 2 == 1)
                res = sqMatrixModMult(res, A, p);

            A = sqMatrixModMult(A, A, p);
            x /= 2;
        }

        return res;
    }

    // util to multiply 2 x 2 square matrices
    private static long[][] sqMatrixModMult(long[][] A, long[][] B, int p) {
        long[][] res = new long[2][2];
        // compute each entry in output
        res[0][0] = ((A[0][0] * B[0][0]) % p + (A[0][1] * B[1][0]) % p) % p;
        res[0][1] = ((A[0][0] * B[0][1]) % p + (A[0][1] * B[1][1]) % p) % p;
        res[1][0] = ((A[1][0] * B[0][0]) % p + (A[1][1] * B[1][0]) % p) % p;
        res[1][1] = ((A[1][0] * B[0][1]) % p + (A[1][1] * B[1][1]) % p) % p;

        return res;
    }

    // https://www.interviewbit.com/problems/trailing-zeros-in-factorial/
    static int trailingZeroes(int A) {
        int res = 0;
        // count the #5's in the factorial as #5's < #2's
        // #5's = (A / 5) + (A / 25) +... = (A / 5) + ((A / 5) / 5) +...
        while (A > 0) {
            res += A / 5;
            A /= 5;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/sorted-permutation-rank/
    static int findRank(String A) {
        int MAX_CHAR = 256, p = 1000003;
        int n = A.length();

        // count[i] = #characters in string < char i
        int[] count = new int[MAX_CHAR];
        // each character count
        for (char c : A.toCharArray())
            count[c]++;
        // perform cumulative count
        for (int i = 1; i < MAX_CHAR; i++)
            count[i] += count[i - 1];

        // fact[i] = i! mod p
        int[] fact = modFact(n, p);
        long rank = 1;

        for (int i = 0; i < n; i++) {
            char c = A.charAt(i);
            // rank = rank + (#chars smaller than c)*(#places on the right)!
            rank = (rank + ((long) count[c - 1] * fact[n - 1 - i]) % p) % p;
            // remove the current character from all greater characters count
            for (int j = c; j < MAX_CHAR; j++)
                count[j]--;
        }

        return (int) rank;
    }

    // util to get factorial array with modulo values
    private static int[] modFact(int n, int p) {
        int[] fact = new int[n + 1];
        fact[0] = 1;

        for (int i = 1; i <= n; i++)
            fact[i] = (int) ((fact[i - 1] * (long) i) % p);

        return fact;
    }

    // https://www.interviewbit.com/problems/largest-coprime-divisor/
    static int cpFact(int A, int B) {
        // keep removing the common factors of A and B from A till we find the co-prime factor
        while (gcd(A, B) != 1)
            A /= gcd(A, B);

        return A;
    }

    // https://www.interviewbit.com/problems/sorted-permutation-rank-with-repeats/
    static int findRepeatRank(String A) {
        int MAX_CHAR = 256, p = 1000003;
        int n = A.length();

        // count[i] = #characters in string < char i
        int[] count = new int[MAX_CHAR];
        // each character count
        for (char c : A.toCharArray())
            count[c]++;
        // perform cumulative count
        for (int i = 1; i < 256; i++)
            count[i] += count[i - 1];

        // fact[i] = i! mod p
        int[] fact = modFact(n, p);
        long rank = 1;

        for (int i = 0; i < n; i++) {
            char c = A.charAt(i);
            // num = (#chars smaller than c)*(#places on the right)!
            long num = ((long) count[c - 1] * fact[n - 1 - i]) % p;
            if (num != 0) {
                // den = (p1! * p2! * ... pn!)
                long den = fact[count[0]] % p;
                for (int j = 1; j < MAX_CHAR; j++)
                    den = (den * fact[count[j] - count[j - 1]]) % p;

                // rank = rank + num/den
                // modular inverse for modulo division (p is prime)
                rank = (rank + (num * modInverse(den, p)) % p) % p;
            }
            // remove the current character from all greater characters count
            for (int j = c; j < MAX_CHAR; j++)
                count[j]--;
        }

        return (int) rank;
    }

    // Fermat's little theorem: a^p mod p = a if p is prime
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

    // https://www.interviewbit.com/problems/next-similar-number/
    // similar to finding next permutation for a number
    static String nextSimilarNumber(String A) {
        int n = A.length();
        // only one digit in string
        if (n == 1)
            return "-1";

        int last = n - 2;
        // find the last increasing pair
        while (last >= 0 && A.charAt(last) >= A.charAt(last + 1))
            last--;
        // whole string is in non-increasing order
        if (last < 0)
            return "-1";

        // find successor of first digit of the increasing pair
        int nextPos = findSuccessor(A, last + 1, n - 1, A.charAt(last));
        // swap first digit of pair with its successor
        StringBuilder builder = new StringBuilder(A);
        swap(builder, last, nextPos);

        // reverse the remaining half of the string
        reverse(builder, last + 1, n - 1);

        return builder.toString();
    }

    // util to find next successor in reverse sorted string using binary search
    private static int findSuccessor(String A, int l, int r, char x) {
        int res = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // reverse array - hence search in left half for greater char
            if (A.charAt(mid) <= x) {
                r = mid - 1;
            }
            // mark as potential result and search in right half for a smaller char
            else {
                res = mid;
                l = mid + 1;
            }
        }

        return res;
    }

    // util to swap characters in StringBuilder
    private static void swap(StringBuilder builder, int i, int j) {
        char temp = builder.charAt(i);
        builder.setCharAt(i, builder.charAt(j));
        builder.setCharAt(j, temp);
    }

    // util to reverse StringBuilder from index start to end
    private static void reverse(StringBuilder builder, int start, int end) {
        while (start < end)
            swap(builder, start++, end--);
    }

    // https://www.interviewbit.com/problems/rearrange-array/
    static void arrange(ArrayList<Integer> A) {
        // if x = a + bn where a is old value and b is new value then
        // old value = x % n and new value = x / n
        int n = A.size();

        // add new value b to old value a of each element (a + bn)
        for (int i = 0; i < n; i++) {
            int x = A.get(i), y = A.get(x) % n;
            A.set(i, x + y * n);
        }

        // retrieve new value b from the a + bn sum
        for (int i = 0; i < n; i++)
            A.set(i, A.get(i) / n);
    }

    // https://www.interviewbit.com/problems/numbers-of-length-n-and-value-less-than-k/
    static int solve(int[] A, int B, int C) {
        int MAX_DIG = 10;
        List<Integer> digits = getDigitsFromNumber(C);

        int n = A.length, d = digits.size();
        // CASE-1: no digits in set or output digits > limit number's digits
        if (n == 0 || B > d)
            return 0;

        // CASE-2: output digits < limit number's digits
        if (B < d) {
            if (A[0] == 0 && B != 1)
                return (int) ((n - 1) * Math.pow(n, B - 1));
            else
                return (int) Math.pow(n, B);
        }

        // CASE-3: output digits == limit number's digits
        int[] lower = new int[MAX_DIG + 1];
        // initialize lower for the digits in list a
        for (int dig : A)
            lower[dig + 1] = 1;
        // cumulative count
        for (int i = 1; i <= MAX_DIG; i++)
            lower[i] += lower[i - 1];

        // if limit is single digit number
        if (B == 1)
            return lower[digits.get(0)];

        // dp[i] = #of numbers that can be formed with first i digits of A that is less than first i digits of C
        int[] dp = new int[B + 1];
        dp[0] = 0;
        // first digit can be all digits less than first digit of C except 0
        dp[1] = lower[digits.get(0)];
        if (A[0] == 0)
            dp[1]--;

        // if the first (i - 1) digits of C are present in A
        boolean eqFlag = lower[digits.get(0) + 1] - lower[digits.get(0)] == 1;

        for (int i = 2; i <= B; i++) {
            // for digits lower than digit[i - 2]
            // we can place all the digits in list A after them
            dp[i] = dp[i - 1] * n;

            int currDigit = digits.get(i - 1);
            // if first (i - 1) digits present, pos i can have digits only lower than digit[i - 1] (current)
            // for the equal first half case
            if (eqFlag) {
                dp[i] += lower[currDigit];
                // update the flag by checking if current digit is present in A
                eqFlag = lower[currDigit + 1] - lower[currDigit] == 1;
            }
        }

        return dp[B];
    }

    private static List<Integer> getDigitsFromNumber(int n) {
        List<Integer> digits = new ArrayList<>();
        // if n == 0
        if (n == 0) {
            digits.add(0);
            return digits;
        }

        // add new digit
        while (n > 0) {
            digits.add(n % 10);
            n /= 10;
        }

        // reverse the digits list
        Collections.reverse(digits);

        return digits;
    }

    // https://www.interviewbit.com/problems/grid-unique-paths/
    static int uniquePaths(int A, int B) {
        // total steps that must be taken will be (A - 1) steps right + (B - 1) steps bottom
        // if we choose either (A - 1) steps, remaining (B - 1) steps would be fixed or vice-versa
        // hence # unique paths = C (A + B - 2, A - 1) = C (A + B - 2, B - 1)
        return C(A + B - 2, A - 1);
    }

    private static int C(int n, int r) {
        if (r > n / 2)
            return C(n, n - r);

        int res = 1;
        for (int i = 0; i < r; i++)
            res = res * (n - i) / (i + 1);

        return res;
    }

    //TODO: https://www.interviewbit.com/problems/city-tour/

    // Jump to LEVEL-3 code
    // https://www.interviewbit.com/problems/prettyprint/
    static int[][] prettyPrint(int A) {
        int len = 2 * A - 1;
        int[][] res = new int[len][len];

        // traverse in fashion similar to spiral matrix problem
        int t = 0, b = len - 1, l = 0, r = len - 1;
        while (A > 0) {
            // move towards right in topmost row
            for (int i = l; i <= r; i++)
                res[t][i] = A;
            t++;

            // move towards bottom in rightmost row
            for (int i = t; i <= b; i++)
                res[i][r] = A;
            r--;

            // move towards left in bottommost row
            for (int i = r; i >= l; i--)
                res[b][i] = A;
            b--;

            // move towards top in leftmost row
            for (int i = b; i >= t; i--)
                res[i][l] = A;
            l++;

            // update the number to be filled
            A--;
        }

        return res;
    }
}