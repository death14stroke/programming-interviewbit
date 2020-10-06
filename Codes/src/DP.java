import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class DP {
    // https://www.interviewbit.com/problems/longest-common-subsequence/
    static int lcs(String A, String B) {
        int m = A.length(), n = B.length();
        int[][] dp = new int[m + 1][n + 1];

        // lcs(i, j) = 1 + lcs(i - 1, j - 1) if last character matches else
        // lcs(i, j) = max(lcs(i - 1, j), lcs(i, j - 1))

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if last character matches
                if (A.charAt(i - 1) == B.charAt(j - 1))
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    // else remove current character in either of strings and find maximum
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[m][n];
    }

    // https://www.interviewbit.com/problems/longest-palindromic-subsequence/
    static int lps(String A) {
        String reverse = new StringBuilder(A).reverse().toString();
        // lps of a string is lcs of the string with its reverse
        return lcs(A, reverse);
    }

    // https://www.interviewbit.com/problems/edit-distance/
    static int editDistance(String A, String B) {
        int m = A.length(), n = B.length();
        int[][] dp = new int[m + 1][n + 1];

        // 2nd string is empty
        for (int i = 1; i <= m; i++)
            dp[i][0] = i;

        // 1st string is empty
        for (int j = 1; j <= n; j++)
            dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if last character matches
                if (A.charAt(i - 1) == B.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                    // else take minimum of replace, insert or delete character in string 1
                else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j]));
            }
        }

        return dp[m][n];
    }

    // https://www.interviewbit.com/problems/repeating-subsequence/
    static int repeatingSubsequence(String A) {
        int n = A.length();
        int[][] dp = new int[n + 1][n + 1];

        // longest repeating subsequence is the lcs of string with itself
        // with added condition that character positions must be different

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // if last character matches and is different position in both
                if (A.charAt(i - 1) == A.charAt(j - 1) && i != j)
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    // else max of ignoring both characters one by one
                else
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }

        // longest repeating subsequence length must be >= 2
        return dp[n][n] >= 2 ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/distinct-subsequences/
    static int distinctSubsequences(String A, String B) {
        int m = A.length(), n = B.length();
        // if string A is smaller
        if (m < n)
            return 0;

        // if both string lengths are equal, then only possible way is if their characters are equal
        if (m == n)
            return (A.compareTo(B) == 0) ? 1 : 0;

        int[][] dp = new int[m + 1][n + 1];
        // if string B is empty, only way is to remove all characters
        for (int i = 0; i <= m; i++)
            dp[i][0] = 1;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if last character doesn't match, delete it in string A
                if (A.charAt(i - 1) != B.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j];
                    // else check for the remaining part or delete the character in string A
                else
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }

        return dp[m][n];
    }

    // https://www.interviewbit.com/problems/interleaving-strings/
    static int interleavingStrings(String A, String B, String C) {
        int m = A.length(), n = B.length();
        // if total length doesn't match
        if (m + n != C.length())
            return 0;

        boolean[][] dp = new boolean[m + 1][n + 1];
        // both string empty
        dp[0][0] = true;

        // string B is empty
        for (int i = 1; i <= m; i++) {
            if (A.charAt(i - 1) == C.charAt(i - 1))
                dp[i][0] = true;
            else
                break;
        }

        // string A is empty
        for (int j = 1; j <= n; j++) {
            if (B.charAt(j - 1) == C.charAt(j - 1))
                dp[0][j] = true;
            else
                break;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char a = A.charAt(i - 1), b = B.charAt(j - 1), c = C.charAt(i + j - 1);

                // if A's character matches with C
                if (a == c)
                    dp[i][j] = dp[i - 1][j];
                // also check if B's character matches with C
                if (b == c)
                    dp[i][j] = dp[i][j] || dp[i][j - 1];
            }
        }

        return dp[m][n] ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/regular-expression-match/
    static int regex(final String A, final String B) {
        int m = A.length(), n = B.length();

        // both strings empty
        if (m == 0 && n == 0)
            return 1;
        // input not empty but pattern is empty
        if (n == 0)
            return 0;

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // input is empty but pattern is not empty
        // valid only if pattern is all '*'
        for (int j = 1; j <= n; j++) {
            if (B.charAt(j - 1) == '*')
                dp[0][j] = true;
            else
                break;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if single character matcher in pattern or the characters match
                if (B.charAt(j - 1) == '?' || A.charAt(i - 1) == B.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                    // else if 0 or more characters matcher
                    // CASE-1: consider *,
                    // CASE-2: ignore * (match 0 chars)
                else if (B.charAt(j - 1) == '*')
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
            }
        }

        return dp[m][n] ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/regular-expression-ii/
    static int regex2(final String A, final String B) {
        int m = A.length(), n = B.length();

        // both strings empty
        if (m == 0 && n == 0)
            return 1;
        // input not empty but pattern is empty
        if (n == 0)
            return 0;

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // input is empty but pattern is not empty
        for (int j = 1; j <= n; j++) {
            if (B.charAt(j - 1) == '*')
                dp[0][j] = dp[0][j - 2];
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if single character matcher in pattern or the characters match
                if (B.charAt(j - 1) == '.' || A.charAt(i - 1) == B.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                    // else if * operator
                else if (B.charAt(j - 1) == '*') {
                    // 0 characters matching of previous character in pattern
                    dp[i][j] = dp[i][j - 2];
                    // previous character of pattern is '.' or matches with current
                    if (A.charAt(i - 1) == B.charAt(j - 2) || B.charAt(j - 2) == '.')
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                }
            }
        }

        return dp[m][n] ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/scramble-string/
    private static Map<String, Boolean> map;

    static int scrambleString(final String A, final String B) {
        // hashmap for memoization
        map = new HashMap<>();
        // recursively check
        return scrambleStringUtil(A, B) ? 1 : 0;
    }

    // recursive util to check if B is scrambled string of A or not
    private static boolean scrambleStringUtil(final String A, final String B) {
        String key = A + "," + B;
        // if solution found previously
        if (map.containsKey(key))
            return map.get(key);

        int n = A.length();
        // if both strings not same length
        if (n != B.length()) {
            map.put(key, false);
            return false;
        }

        // if both strings equal
        if (A.compareTo(B) == 0) {
            map.put(key, true);
            return true;
        }

        // if both strings are not anagrams
        if (!areAnagrams(A, B)) {
            map.put(key, false);
            return false;
        }

        // try slicing the string at each possible index
        for (int i = 1; i <= n - 1; i++) {
            // recursively check for first halves of both strings and last halves of both strings
            if (scrambleStringUtil(A.substring(0, i), B.substring(0, i)) &&
                    scrambleStringUtil(A.substring(i, n), B.substring(i, n))) {
                map.put(key, true);
                return true;
            }

            // recursively check for first half of A and last half of B && last half of A and first half of B
            if (scrambleStringUtil(A.substring(0, i), B.substring(n - i, n)) &&
                    scrambleStringUtil(A.substring(i, n), B.substring(0, n - i))) {
                map.put(key, true);
                return true;
            }
        }

        // none of the conditions satisfy
        map.put(key, false);
        return false;
    }

    // util to check if two strings are anagrams or not
    private static boolean areAnagrams(String A, String B) {
        int[] freq = new int[128];
        // increment frequency map
        for (char c : A.toCharArray())
            freq[c]++;
        // decrement frequency map
        for (char c : B.toCharArray())
            freq[c]--;

        for (int val : freq) {
            // if any character is extra is any string
            if (val != 0)
                return false;
        }

        // all character frequencies are equal
        return true;
    }

    // https://www.interviewbit.com/problems/length-of-longest-subsequence/
    static int longestBitonicSubsequence(final int[] A) {
        int n = A.length;
        // base cases
        if (n <= 2)
            return n;

        // compute longest increasing subsequence from 0 to i for each i
        int[] lis = new int[n];
        Arrays.fill(lis, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // if can extend current lis
                if (A[i] > A[j] && lis[i] < lis[j] + 1)
                    lis[i] = lis[j] + 1;
            }
        }

        // compute longest decreasing subsequence from i to n for each i
        int[] lds = new int[n];
        Arrays.fill(lds, 1);

        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                // if can extend current lds
                if (A[i] > A[j] && lds[i] < lds[j] + 1)
                    lds[i] = lds[j] + 1;
            }
        }

        // check for max (lis + lds - the common point) at each position
        int res = lis[0] + lds[0] - 1;
        for (int i = 1; i < n; i++)
            res = Math.max(res, lis[i] + lds[i] - 1);

        return res;
    }

    // https://www.interviewbit.com/problems/smallest-sequence-with-given-primes/
    static int[] smallestSequence(int A, int B, int C, int D) {
        // similar to ugly numbers
        int[] res = new int[D];

        int i1 = -1, i2 = -1, i3 = -1;
        // first multiples of each number
        int next1 = A, next2 = B, next3 = C;

        // merge all three sequences one by one
        for (int i = 0; i < D; i++) {
            // minimum among the tree
            int next = Math.min(next1, Math.min(next2, next3));
            res[i] = next;

            // update next multiple of first sequence
            if (next == next1)
                next1 = res[++i1] * A;
            // update next multiple of second sequence
            if (next == next2)
                next2 = res[++i2] * B;
            // update next multiple of third sequence
            if (next == next3)
                next3 = res[++i3] * C;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/ways-to-decode/
    static int waysToDecode(String A) {
        int n = A.length(), p = 1000000007;
        // mapping starts from 1
        if (A.charAt(0) == '0')
            return 0;

        int[] dp = new int[n + 1];
        // empty string
        dp[0] = 1;
        // single digit which is not zero
        dp[1] = 1;

        // check for each 1 digit and 2 digit pairs
        for (int i = 2; i <= n; i++) {
            // if last digit is not 0
            if (A.charAt(i - 1) != '0')
                dp[i] = dp[i - 1] % p;

            // if last 2 digits form a number between 10 to 26
            if (A.charAt(i - 2) == '1' || (A.charAt(i - 2) == '2' && A.charAt(i - 1) <= '6'))
                dp[i] = (dp[i] + dp[i - 2]) % p;
        }

        return dp[n];
    }

    // https://www.interviewbit.com/problems/stairs/
    static int stairs(int n) {
        // 1 or 2 stairs
        if (n <= 2)
            return n;

        // steps required where last step is 2 jump and 1 jump respectively
        int step2 = 1, step1 = 2;
        int ans = 0;

        for (int i = 3; i <= n; i++) {
            // total steps at this point is either last step is 2 jump or 1 jump
            ans = step2 + step1;

            // update steps variables
            step2 = step1;
            step1 = ans;
        }

        return ans;
    }

    // https://www.interviewbit.com/problems/jump-game-array/
    static int canJump(int[] A) {
        // set current target as the last position
        int n = A.length, target = n - 1;

        // loop from the second last position
        for (int i = n - 2; i >= 0; i--) {
            // if can jump from current to target, update target to current
            if (i + A[i] >= target)
                target = i;
        }

        // if target at the end of loop is start, success
        return target == 0 ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/min-jumps-array/
    static int jump(int[] A) {
        int n = A.length;
        // base case: 0 or 1 positions total
        if (n <= 1)
            return 0;

        // 2 or more points in array, starting point max jumps is 0
        if (A[0] == 0)
            return -1;

        // initialize for the starting point
        int jumps = 1, maxReach = A[0], steps = A[0];

        // check from the 1st position
        for (int i = 1; i < n; i++) {
            // reached destination
            if (i == n - 1)
                return jumps;

            // update maximum reachable position
            maxReach = Math.max(maxReach, i + A[i]);
            // use a step
            steps--;

            // if no steps remaining
            if (steps == 0) {
                // perform a jump
                jumps++;

                // if this is the farthest we can reach
                if (i >= maxReach)
                    return -1;

                // re-initialize steps remaining to take
                steps = maxReach - i;
            }
        }

        return -1;
    }
}