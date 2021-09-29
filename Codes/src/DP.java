import java.util.*;

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
        for (char c : B.toCharArray()) {
            freq[c]--;

            if (freq[c] < 0)
                return false;
        }

        for (int val : freq) {
            // if any character is extra is any string
            if (val != 0)
                return false;
        }

        // all character frequencies are equal
        return true;
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
                    // else if 0 or more characters match
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

    // https://www.interviewbit.com/problems/length-of-longest-subsequence/
    static int longestBitonicSubsequence(final int[] A) {
        int n = A.length;
        // base cases
        if (n <= 2)
            return n;

        // lis[i] = longest increasing subsequence ending at i
        int[] lis = new int[n];
        lis[0] = 1;

        for (int i = 1; i < n; i++) {
            lis[i] = 1;

            for (int j = 0; j < i; j++) {
                // if can extend current lis
                if (A[i] > A[j] && lis[i] < lis[j] + 1)
                    lis[i] = lis[j] + 1;
            }
        }

        // lds[i] = longest decreasing subsequence ending at i
        int[] lds = new int[n];
        lds[n - 1] = 1;

        for (int i = n - 2; i >= 0; i--) {
            lds[i] = 1;

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

    // https://www.interviewbit.com/problems/largest-area-of-rectangle-with-permutations/
    static int largestAreaRectWithPermutation(int[][] A) {
        int m = A.length, n = A[0].length;
        // compute connected rectangle size in each column
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][j] != 0)
                    A[i][j] += A[i - 1][j];
            }
        }

        // sort each row such that largest rectangle is formed at the last column
        for (int[] row : A)
            Arrays.sort(row);

        int res = Integer.MIN_VALUE;
        // for each row compute max area
        for (int[] row : A) {
            // start from the last column to compute max area
            for (int j = n - 1; j >= 0 && row[j] > 0; j--)
                res = Math.max(res, row[j] * (n - j));
        }

        return res;
    }

    // https://www.interviewbit.com/problems/tiling-with-dominoes/
    static int tilingWithDominoes(int A) {
        // base cases
        if (A == 1)
            return 0;

        // A[i] = # ways to form completely filled 3 x i matrix
        // B[i] = # ways to form top row empty or bottom row empty in last col of 3 x i matrix
        long a0 = 1, a1 = 0, b0 = 0, b1 = 1, a2 = 0, b2;
        int MOD = 1_000_000_007;

        for (int i = 2; i <= A; i++) {
            // see https://www.geeksforgeeks.org/tiling-with-dominoes/ for diagram
            a2 = (a0 + 2 * b1) % MOD;
            b2 = (a1 + b0) % MOD;

            a0 = a1;
            a1 = a2;
            b0 = b1;
            b1 = b2;
        }

        return (int) a2;
    }

    // https://www.interviewbit.com/problems/paint-house/
    static int paintHouse(int[][] A) {
        int n = A.length;
        // dp[i][j] = cost to paint houses till i with color j on the ith house
        // = cost to paint current house with color j + min of cost to paint previous i - 1 houses with last color as any other than j
        for (int i = 1; i < n; i++) {
            A[i][0] += Math.min(A[i - 1][1], A[i - 1][2]);
            A[i][1] += Math.min(A[i - 1][0], A[i - 1][2]);
            A[i][2] += Math.min(A[i - 1][0], A[i - 1][1]);
        }

        return Math.min(A[n - 1][0], Math.min(A[n - 1][1], A[n - 1][2]));
    }

    // https://www.interviewbit.com/problems/ways-to-decode/
    static int waysToDecode(String A) {
        int n = A.length(), p = 1_000_000_007;
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
            // if last digit is between 1 and 9
            if (A.charAt(i - 1) != '0')
                dp[i] = dp[i - 1];

            // if last 2 digits form a number between 10 and 26
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

        // step1 = dp[i - 2], step2 = dp[i - 1]
        int step1 = 1, step2 = 2, step3 = 0;
        // dp[i] = dp[i - 1] + dp[i - 2]
        for (int i = 3; i <= n; i++) {
            // total steps at this point is either last step is 1 jump or 2 jump
            step3 = step2 + step1;
            // update steps variables
            step1 = step2;
            step2 = step3;
        }

        return step3;
    }

    // https://www.interviewbit.com/problems/longest-increasing-subsequence/
    static int lis(final int[] A) {
        int n = A.length, res = 1;
        // lis[i] = longest increasing subsequence ending at i
        int[] lis = new int[n];
        lis[0] = 1;

        for (int i = 1; i < n; i++) {
            lis[i] = 1;

            for (int j = 0; j < i; j++) {
                if (A[i] > A[j] && lis[i] < lis[j] + 1)
                    lis[i] = lis[j] + 1;
            }

            res = Math.max(res, lis[i]);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/intersecting-chords-in-a-circle/
    static int intersectingChords(int A) {
        // base case
        if (A == 1)
            return 1;

        int MOD = 1_000_000_007;
        // dp[i] = # ways possible for i chords
        long[] dp = new long[A + 1];
        // base cases
        dp[0] = 1;
        dp[1] = 1;

        // for i chords there are 2 * i points. 1st chord can be either of 1 - 2, 1 - 4, 1 - 6,..., 1 - i.
        // for chord 1 - 3, there will always remain a point empty in between hence no odd numbered end points
        for (int i = 2; i <= A; i++) {
            // for first chord from 1 - (2 * (j + 1)) # ways is the permutation of sol of the both halves formed
            for (int j = 0; j < i; j++)
                dp[i] = (dp[i] + dp[j] * dp[i - j - 1]) % MOD;
        }

        return (int) dp[A];
    }

    // https://www.interviewbit.com/problems/tushars-birthday-bombs/
    static int[] birthdayBombs(int A, int[] B) {
        int n = B.length, min = B[0], minIndex = 0;
        // find friend with minimum strength
        for (int i = 1; i < n; i++) {
            if (B[i] < min) {
                min = B[i];
                minIndex = i;
            }
        }

        // greedily length of answer will be #kicks by the weakest friend
        int hits = A / min;
        // capacity of Tushar full
        if (A % min == 0) {
            int[] res = new int[hits];
            Arrays.fill(res, minIndex);
            return res;
        }

        int j = 0;
        LinkedList<Integer> pos = new LinkedList<>();
        // if capacity remaining, try substituting 1 weak kick with 1 stronger kick if possible before minIndex
        while (A >= 0 && j < minIndex) {
            // this kick will be lexicographically smaller and is possible
            if (A - B[j] >= min * (hits - 1)) {
                pos.add(j);
                A -= B[j];
                hits--;
            }
            // else check for next element
            else
                j++;
        }

        hits = Math.max(hits, 0);
        // if went over capacity, remove last kick from pos
        if (A - min * hits < 0)
            pos.removeLast();

        // copy pos to array
        int[] res = new int[pos.size() + hits];
        int i = 0;
        for (; i < pos.size(); i++)
            res[i] = pos.get(i);
        // add positions of the weakest kick
        for (; i < pos.size() + hits; i++)
            res[i] = minIndex;

        return res;
    }

    // https://www.interviewbit.com/problems/jump-game-array/
    static int canJump(int[] A) {
        // initial maxReach = A[0]
        int n = A.length, maxReach = A[0];
        // keep updating maxReach till we reach destination or get stuck
        for (int i = 1; i <= maxReach && maxReach < n - 1; i++)
            maxReach = Math.max(maxReach, i + A[i]);

        return maxReach >= n - 1 ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/min-jumps-array/
    static int jump(int[] A) {
        int n = A.length;
        // base case
        if (n == 1)
            return 0;
        // 2 or more points in array, starting point max jumps is 0
        if (A[0] == 0)
            return -1;

        // initialize for the starting point
        int i = 1, jumps = 1, maxReach = A[0], steps = A[0];
        // check from the 1st position
        for (; i < n - 1 && i <= maxReach; i++) {
            // update maximum reachable position
            maxReach = Math.max(maxReach, i + A[i]);
            // use a step
            steps--;

            // if no steps remaining
            if (steps == 0) {
                // perform a jump
                jumps++;
                // re-initialize steps remaining to take
                steps = maxReach - i;
            }
        }

        // if reached destination return optimum path length
        return i == n - 1 ? jumps : -1;
    }

    // https://www.interviewbit.com/problems/longest-arithmetic-progression/
    @SuppressWarnings("unchecked")
    static int longestAP(final int[] A) {
        int n = A.length;
        // base cases
        if (n <= 2)
            return n;

        int res = 2;
        // diff map for each position in the array
        HashMap<Integer, Integer>[] dp = new HashMap[n];
        dp[0] = new HashMap<>();

        for (int i = 1; i < n; i++) {
            dp[i] = new HashMap<>();

            for (int j = 0; j < i; j++) {
                int diff = A[i] - A[j];

                // if AP exists at index j with difference "diff", extend it
                if (dp[j].containsKey(diff))
                    dp[i].put(diff, dp[j].get(diff) + 1);
                    // else create new AP at index j with 2 elements
                else
                    dp[i].put(diff, 2);

                // update final result
                res = Math.max(res, dp[i].get(diff));
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/n-digit-numbers-with-digit-sum-s-/
    static int nDigitNumsWithDigitSum(int N, int S) {
        // alternate solution: https://www.geeksforgeeks.org/count-of-n-digit-numbers-whose-sum-of-digits-equals-to-given-sum/
        // base case
        if (N == 1) {
            // numbers start from 1 hence only sum from 1 to 9 possible for N = 1
            if (S == 0 || S >= 10)
                return 0;
            return 1;
        }

        int MOD = 1000000007;
        // dp[i][j] = # i digit numbers whose sum of digits is j
        int[][] dp = new int[N + 1][S + 1];

        // for N = 1, only sum from 1 to 9 possible
        for (int i = 1; i <= Math.min(S, 9); i++)
            dp[1][i] = 1;

        // start filling from 2nd position
        for (int i = 2; i <= N; i++) {
            for (int j = 1; j <= S; j++) {
                // try all numbers from 0 to 9
                for (int num = 0; num <= 9; num++) {
                    // if current number is less than sum j include it else stop
                    if (j - num >= 0)
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - num]) % MOD;
                    else
                        break;
                }
            }
        }

        return dp[N][S];
    }

    // https://www.interviewbit.com/problems/shortest-common-superstring/
    // another possible solution is DP with bitmask (see question hint)
    private static String overlapStrRes;

    static int shortestCommonSuperstring(String[] A) {
        int n = A.length;

        Set<Integer> subsets = new HashSet<>();
        // mark strings which are substring of some other string to remove
        for (int i = 0; i < n - 1; i++) {
            if (subsets.contains(i))
                continue;

            for (int j = i + 1; j < n; j++) {
                if (A[i].length() <= A[j].length() && A[j].contains(A[i]))
                    subsets.add(i);
                else if (A[i].contains(A[j]))
                    subsets.add(j);
            }
        }

        int k = 0;
        // keep pushing the marked strings at the end of the array
        while (k < n) {
            if (subsets.contains(k)) {
                A[k] = A[n - 1];
                n--;
            } else {
                k++;
            }
        }

        overlapStrRes = "";
        // merge the most overlapping strings n - 1 times
        for (int len = n; len > 1; len--) {
            int maxOverlap = 0;
            int l = 0, r = 0;
            String overlapStr = "";
            // find maximum overlap among overlaps between each pair of string
            for (int i = 0; i < len - 1; i++) {
                for (int j = i + 1; j < len; j++) {
                    int overlap = findOverlappingPair(A[i], A[j]);
                    if (maxOverlap < overlap) {
                        maxOverlap = overlap;
                        l = i;
                        r = j;
                        overlapStr = overlapStrRes;
                    }
                }
            }

            // if no overlap merge last string with first
            if (maxOverlap == 0)
                A[0] += A[len - 1];
                // else replace merged string at one position and swap last string at the other
            else {
                A[l] = overlapStr;
                A[r] = A[len - 1];
            }
        }

        return A[0].length();
    }

    // util to find maximum overlap length
    private static int findOverlappingPair(String A, String B) {
        int m = A.length(), n = B.length();
        int maxOverlap = 0;

        // for all breakpoints of the smaller string
        for (int i = 1; i <= Math.min(m, n); i++) {
            // if prefix of A matches with suffix of B, update the overlap
            if (A.substring(0, i).compareTo(B.substring(n - i)) == 0) {
                if (maxOverlap < i) {
                    maxOverlap = i;
                    overlapStrRes = B + A.substring(i);
                }
            }
        }
        // for all breakpoints of the smaller string
        for (int i = 1; i <= Math.min(m, n); i++) {
            // if suffix of A matches with prefix of B, update the overlap
            if (A.substring(m - i).compareTo(B.substring(0, i)) == 0) {
                if (maxOverlap < i) {
                    maxOverlap = i;
                    overlapStrRes = A + B.substring(i);
                }
            }
        }

        return maxOverlap;
    }

    // https://www.interviewbit.com/problems/ways-to-color-a-3xn-board/
    static int color3NBoard(int A) {
        int p = 1000000007;
        // base case: A = 1 can select C(4, 3) * 3! + C(4, 2) * 2 ways
        long color3 = 24, color2 = 12;
        // W(n+1) = 10*Y(n)+11*W(n) where W(n+1) is # of 3 color combinations with (n+1) cols
        // Y(n+1) = 7*Y(n)+5*W(n) where Y(n+1) is # of 2 color combinations with (n+1) cols
        // see https://www.geeksforgeeks.org/ways-color-3n-board-using-4-colors/ for explanation
        for (int i = 2; i <= A; i++) {
            long temp = color3;

            // update 2 color and 3 color combinations count
            color3 = (10 * color2 + 11 * color3) % p;
            color2 = (7 * color2 + 5 * temp) % p;
        }

        // result will be the sum of 2 color and 3 color combinations
        return (int) ((color2 + color3) % p);
    }

    // https://www.interviewbit.com/problems/kth-manhattan-distance-neighbourhood/
    static int[][] kthManhattanDistance(int[][] A, int K) {
        // for K = 0 every member is its own maximum
        if (K == 0)
            return A;

        int m = A.length, n = A[0].length;
        int[][] dp = new int[m][n];

        // for d = K we can find the matrix using the solution for d = K - 1
        for (int d = 1; d <= K; d++) {
            // for each element
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // find K = d - 1 sol for top, left, bottom, right
                    int top = i - 1 >= 0 ? A[i - 1][j] : -1, bottom = i + 1 < m ? A[i + 1][j] : -1;
                    int left = j - 1 >= 0 ? A[i][j - 1] : -1, right = j + 1 < n ? A[i][j + 1] : -1;
                    // update sol for K = d
                    dp[i][j] = Math.max(A[i][j], Math.max(Math.max(top, bottom), Math.max(left, right)));
                }
            }

            // swap matrix references such that A = sol for K = d
            int[][] temp = dp;
            dp = A;
            A = temp;
        }

        return A;
    }

    // https://www.interviewbit.com/problems/best-time-to-buy-and-sell-stock-atmost-b-times/
    static int buyAndSellStocksAtmostBTimes(final int[] A, int B) {
        int n = A.length;
        // only one day or 0 transactions allowed
        if (n == 1 || B == 0)
            return 0;

        // max transactions that can be done = n / 2. Hence, optimize value of B (MLE thrown)
        B = Math.min(B, n / 2);
        // dp[i][k] = max profit obtained by last txn done on ith day with at most k moves
        int[][] dp = new int[n][2];

        for (int k = 1; k <= B; k++) {
            // space optimized - compute current index
            int pos = k % 2;

            for (int i = 1; i < n; i++) {
                // don't perform transaction today
                dp[i][pos] = dp[i - 1][pos];

                for (int j = 0; j < i; j++) {
                    // if we can earn profit today by buying on day j, update maximum profit earned
                    // dp[i][k] = Math.max(dp[i][k], dp[j][k - 1])
                    if (A[i] > A[j])
                        dp[i][pos] = Math.max(dp[i][pos], A[i] - A[j] + dp[j][1 - pos]);
                }
            }
        }

        return dp[n - 1][B % 2];

    }

    // https://www.interviewbit.com/problems/coins-in-a-line/
    static int coinsInLine(int[] A) {
        int n = A.length;
        // dp[i][j] = max score for coins from [i, j]
        int[][] dp = new int[n][n];
        // len = 1
        for (int i = 0; i < n; i++)
            dp[i][i] = A[i];
        // for len = 2 and more
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                // player chooses i and opponent chooses i + 1 in next step
                int x = (i + 2 <= j) ? dp[i + 2][j] : 0;
                // player chooses i and opponent chooses j in next step or
                // player chooses j and opponent chooses i in next step
                int y = (i + 1 <= j - 1) ? dp[i + 1][j - 1] : 0;
                // player chooses j and opponent chooses j - 1 in next step
                int z = (i <= j - 2) ? dp[i][j - 2] : 0;
                // max of player chooses i or player chooses j
                // opponent will choose such that for player's next turn minimum value can be obtained
                dp[i][j] = Math.max(A[i] + Math.min(x, y), A[j] + Math.min(y, z));
            }
        }

        return dp[0][n - 1];
    }

    // https://www.interviewbit.com/problems/evaluate-expression-to-true/
    static int evaluateExpToTrue(String A) {
        int MOD = 1003;
        int n = A.length();
        // base-case
        if (n == 1)
            return A.charAt(0) == 'T' ? 1 : 0;

        // T[i][j] = #ways to evaluate exp from [i, j] as true
        // F[i][j] = #ways to evaluate exp from [i, j] as false
        int[][] T = new int[n][n], F = new int[n][n];

        // base-case: len = 1 (consider only operands hence i += 2)
        for (int i = 0; i < n; i += 2) {
            if (A.charAt(i) == 'T') {
                T[i][i] = 1;
                F[i][i] = 0;
            } else {
                T[i][i] = 0;
                F[i][i] = 1;
            }
        }

        // exp length will be odd i.e len = 3, len = 5,...
        for (int len = 3; len <= n; len += 2) {
            // operands will be at even pos hence i += 2
            for (int i = 0; i + len - 1 < n; i += 2) {
                int j = i + len - 1;
                // for each operator in between, parenthesize the halves on both sides
                for (int k = i + 1; k < j; k += 2) {
                    // Total[i][k - 1] = T[i][k - 1] + F[i][k - 1]
                    // Total[k + 1][j] = T[k + 1][j] + F[k + 1][j]
                    int tik = T[i][k - 1] + F[i][k - 1], tkj = T[k + 1][j] + F[k + 1][j];
                    // check current operator
                    switch (A.charAt(k)) {
                        case '|' -> {
                            // T[i][j] += Total[i][k - 1] * Total[k + 1][j] - F[i][k - 1] * F[k + 1][j] (all cases except both false)
                            T[i][j] = (T[i][j] + tik * tkj - F[i][k - 1] * F[k + 1][j]) % MOD;
                            // F[i][j] += F[i][k - 1] * F[k + 1][j] (both false)
                            F[i][j] = (F[i][j] + F[i][k - 1] * F[k + 1][j]) % MOD;
                        }
                        case '&' -> {
                            // T[i][j] += T[i][k - 1] * T[k + 1][j] (both true)
                            T[i][j] = (T[i][j] + T[i][k - 1] * T[k + 1][j]) % MOD;
                            // F[i][j] += Total[i][k - 1] * Total[k + 1][j] - T[i][k - 1] * T[k + 1][j] (all cases except both true)
                            F[i][j] = (F[i][j] + tik * tkj - T[i][k - 1] * T[k + 1][j]) % MOD;
                        }
                        case '^' -> {
                            // T[i][j] += T[i][k - 1] * F[k + 1][j] + F[i][k - 1] * T[k + 1][j] (T ^ F = T, F ^ T = T)
                            T[i][j] = (T[i][j] + T[i][k - 1] * F[k + 1][j] + F[i][k - 1] * T[k + 1][j]) % MOD;
                            // F[i][j] += T[i][k - 1] * T[k + 1][j] + F[i][k - 1] * F[k + 1][j] (T ^ T = F, F ^ F = F)
                            F[i][j] = (F[i][j] + T[i][k - 1] * T[k + 1][j] + F[i][k - 1] * F[k + 1][j]) % MOD;
                        }
                    }
                }
            }
        }

        return T[0][n - 1];
    }

    // https://www.interviewbit.com/problems/egg-drop-problem/
    static int eggDropProblem(int K, int n) {
        // only 1 egg - start dropping from 1st floor hence worst case drop on all floors
        if (K == 1)
            return n;
        // only 1 floor - drop egg at the only floor
        if (n == 1)
            return 1;

        // dp[k][n] = # drops required for k eggs and n floors
        int[][] dp = new int[K + 1][n + 1];
        // base-case: 1 egg problem
        for (int i = 1; i <= n; i++)
            dp[1][i] = i;

        for (int k = 2; k <= K; k++) {
            // base-case: 1st floor
            dp[k][1] = 1;

            for (int i = 2; i <= n; i++) {
                int l = 1, r = i;
                // use binary search to find the point where the difference between drops required
                // for egg breaking and egg not breaking is minimum
                while (l <= r) {
                    int mid = l + (r - l) / 2;
                    // if egg break case has fewer drops than egg not break case, check for a higher floor
                    if (dp[k - 1][mid - 1] < dp[k][i - mid])
                        l = mid + 1;
                        // else check for a lower floor in middle
                    else
                        r = mid - 1;
                }

                // find #drops for both the floors derived
                int opt1 = Math.max(dp[k - 1][l - 1], dp[k][i - l]);
                int opt2 = Math.max(dp[k - 1][r - 1], dp[k][i - r]);
                // dp[k][i] = current drop + min(drops from lth floor, drops from rth floor)
                dp[k][i] = 1 + Math.min(opt1, opt2);
            }
        }

        return dp[K][n];
    }

    // https://www.interviewbit.com/problems/best-time-to-buy-and-sell-stocks-iii/
    static int buyAndSellStocks3(final int[] A) {
        int n = A.length;
        // base case
        if (n == 0)
            return 0;

        // buy1 = price to buy 1st stock
        // profit1 = profit earned for 1st stock
        // buy2 = price to buy 2nd stock including profit of 1st stock
        // profit2 = profit earned after selling both stocks
        int buy1 = A[0], profit1 = 0, buy2 = Integer.MAX_VALUE, profit2 = 0;

        for (int i = 1; i < n; i++) {
            // minimize 1st stock purchase price
            buy1 = Math.min(buy1, A[i]);
            // maximize 1st stock profit
            profit1 = Math.max(profit1, A[i] - buy1);
            // minimize 2nd stock purchase price including profit earned for 1st stock
            buy2 = Math.min(buy2, A[i] - profit1);
            // maximize profit earned from both stocks
            profit2 = Math.max(profit2, A[i] - buy2);
        }

        return profit2;
    }

    // https://www.interviewbit.com/problems/longest-valid-parentheses/
    // more solutions at https://www.geeksforgeeks.org/length-of-the-longest-valid-substring/
    static int longestValidParentheses(String A) {
        int n = A.length();
        int res = 0;
        // stack to keep track of '(' indexes
        Stack<Integer> s = new Stack<>();
        // push -1 as initial pos
        s.push(-1);

        for (int i = 0; i < n; i++) {
            // push index of '(' on stack
            if (A.charAt(i) == '(')
                s.push(i);
                // if ')' encountered
            else {
                // pop index of the corresponding '('
                if (!s.empty())
                    s.pop();
                // extend current sequence
                if (!s.empty())
                    res = Math.max(res, i - s.peek());
                    // else start a new sequence from current position
                else
                    s.push(i);
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/max-sum-path-in-binary-tree/
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            left = right = null;
        }
    }

    private static int maxSum;

    static int maxSumPathBinaryTree(TreeNode root) {
        // empty tree
        if (root == null)
            return 0;

        maxSum = Integer.MIN_VALUE;
        // max path sum at each node can be found using max path sum from both subtrees by inorder traversal
        treeSumUtil(root);

        return maxSum;
    }

    // util for inorder traversal which returns max height at each node
    private static int treeSumUtil(TreeNode root) {
        // max path sum in left subtree
        int leftSum = root.left != null ? treeSumUtil(root.left) : 0;
        // max path sum in right subtree
        int rightSum = root.right != null ? treeSumUtil(root.right) : 0;

        // max height = max(root, root + max(l, r))
        int maxPathFromRoot = Math.max(root.val, root.val + Math.max(leftSum, rightSum));
        // maxPath = max(maxHeight, complete path)
        int maxPath = Math.max(maxPathFromRoot, root.val + leftSum + rightSum);
        // update result
        maxSum = Math.max(maxSum, maxPath);

        return maxPathFromRoot;
    }

    // https://www.interviewbit.com/problems/kingdom-war/
    static int kingdomWar(int[][] A) {
        int m = A.length, n = A[0].length;
        // Due to the given property of row and column, the largest entry will always be in the bottom right corner
        // and will always be part of the solution. Hence, compute matrix sum for all matrices ending at (m - 1, n - 1)
        // and find maximum sum
        int res = A[m - 1][n - 1];

        // for rightmost column
        for (int i = m - 2; i >= 0; i--) {
            A[i][n - 1] += A[i + 1][n - 1];
            res = Math.max(res, A[i][n - 1]);
        }
        // for bottommost row
        for (int j = n - 2; j >= 0; j--) {
            A[m - 1][j] += A[m - 1][j + 1];
            res = Math.max(res, A[m - 1][j]);
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                // matrix sum - remove the overlapping area
                A[i][j] += A[i + 1][j] + A[i][j + 1] - A[i + 1][j + 1];
                res = Math.max(res, A[i][j]);
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/maximum-path-in-triangle/
    static int maxPathInTriangle(int[][] A) {
        int n = A.length;
        // move from bottom to top and calculate max path for going to (i + 1, j) or (i + 1, j + 1)
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++)
                A[i][j] += Math.max(A[i + 1][j], A[i + 1][j + 1]);
        }

        return A[0][0];
    }

    // https://www.interviewbit.com/problems/maximum-size-square-sub-matrix/
    static int maxSquareSubMatrix(int[][] A) {
        int m = A.length, n = A[0].length;
        int res = 0;
        // check for 1 length squares in first col
        for (int[] row : A) {
            if (row[0] != 0) {
                res = 1;
                break;
            }
        }
        // check for 1 length squares in first row
        for (int j = 1; j < n; j++) {
            if (A[0][j] != 0) {
                res = 1;
                break;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // check if current position is 0 and its top, left and diagonal neighbours form a square.
                // If yes, update the square length at current position
                if (A[i][j] != 0) {
                    A[i][j] = 1 + Math.min(A[i - 1][j], Math.min(A[i][j - 1], A[i - 1][j - 1]));
                    res = Math.max(res, A[i][j]);
                }
            }
        }
        // area = l * l
        return res * res;
    }

    // https://www.interviewbit.com/problems/increasing-path-in-matrix/
    static int increasingPathMatrix(int[][] A) {
        int m = A.length, n = A[0].length;
        // dp[i][j] = T/F for whether there is path to (i, j) or not
        boolean[][] dp = new boolean[m][n];
        dp[0][0] = true;

        // check for first column
        int i = 1;
        for (; i < m && A[i][0] > A[i - 1][0]; i++)
            dp[i][0] = true;
        // check for first row
        int j = 1;
        for (; j < n && A[0][j] > A[0][j - 1]; j++)
            dp[0][j] = true;

        for (i = 1; i < m; i++) {
            for (j = 1; j < n; j++) {
                // check if we can arrive current cell from top or left
                boolean top = A[i][j] > A[i - 1][j] && dp[i - 1][j];
                boolean left = A[i][j] > A[i][j - 1] && dp[i][j - 1];

                dp[i][j] = top || left;
            }
        }

        // if there is a path to (m - 1, n - 1) its length will always be m + n - 1
        // (when only bottom and right dirs are allowed)
        return dp[m - 1][n - 1] ? m + n - 1 : -1;
    }

    // https://www.interviewbit.com/problems/minimum-difference-subsets/
    static int minDiffSubsets(int[] A) {
        // calculate total sum
        int sum = 0;
        for (int val : A)
            sum += val;

        int n = A.length;
        // dp[i][j] = T/F for sum = j can be obtained using subset of first i elements
        boolean[][] dp = new boolean[n + 1][sum + 1];

        // base-case: sum = 0 can be achieved always
        for (int i = 0; i <= n; i++)
            dp[i][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int s = 1; s <= sum; s++) {
                // if we can use current element, either use it or don't
                if (A[i - 1] <= s)
                    dp[i][s] = dp[i - 1][s - A[i - 1]] || dp[i - 1][s];
                    // else don't use current element
                else
                    dp[i][s] = dp[i - 1][s];
            }
        }

        // for each subset sum that can be achieved with n elements, minimize difference between the two subsets
        int res = Integer.MAX_VALUE;
        for (int j = 0; j <= sum / 2; j++) {
            if (dp[n][j])
                res = Math.min(res, sum - 2 * j);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/subset-sum-problem/
    static int subsetSum(int[] A, int B) {
        int n = A.length;
        // dp[i][j] = T/F for whether subset sum = j is possible for first i elements
        boolean[][] dp = new boolean[n + 1][B + 1];
        // base-case: sum = 0 can be achieved with any number of elements
        for (int i = 0; i <= n; i++)
            dp[i][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int b = 1; b <= B; b++) {
                // if we can use current element, either use it or don't
                if (A[i - 1] <= b)
                    dp[i][b] = dp[i - 1][b - A[i - 1]] || dp[i - 1][b];
                    // else don't use current element
                else
                    dp[i][b] = dp[i - 1][b];
            }
        }

        return dp[n][B] ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/unique-paths-in-a-grid/
    static int uniquePathsInGrid(int[][] A) {
        int m = A.length, n = A[0].length;
        // base-case: obstacle at origin
        if (A[0][0] == 1)
            return 0;

        // use A[i][j] as dp[i][j] where dp[i][j] = # ways to reach (i, j)
        int i = 0;
        // for first col, mark # ways = 1 for each cell till obstacle is not encountered
        for (; i < m && A[i][0] == 0; i++)
            A[i][0] = 1;
        // for all cells after obstacle # ways = 0
        for (; i < m; i++)
            A[i][0] = 0;

        int j = 1;
        // for first row, mark # ways = 1 for each cell till obstacle is not encountered
        for (; j < n && A[0][j] == 0; j++)
            A[0][j] = 1;
        // for all cells after obstacle # ways = 0
        for (; j < n; j++)
            A[0][j] = 0;

        for (i = 1; i < m; i++) {
            for (j = 1; j < n; j++) {
                // if no obstacle at current cell, # ways = # ways from top + # ways from left
                if (A[i][j] == 0)
                    A[i][j] = A[i - 1][j] + A[i][j - 1];
                else
                    A[i][j] = 0;
            }
        }

        return A[m - 1][n - 1];
    }

    // https://www.interviewbit.com/problems/dungeon-princess/
    static int dungeonPrincess(int[][] A) {
        int m = A.length, n = A[0].length;
        // use A[][] as dp[][]
        // dp[i][j] = min hit-points reached when going from (i, j) to (m - 1, n - 1)
        // dp[i][j] = min(0, current point hit-points, current hit-points + hit-points of the optimum path)
        A[m - 1][n - 1] = Math.min(A[m - 1][n - 1], 0);
        // move up in the last column
        for (int i = m - 2; i >= 0; i--)
            A[i][n - 1] = min(0, A[i][n - 1], A[i][n - 1] + A[i + 1][n - 1]);
        // move up int the last row
        for (int j = n - 2; j >= 0; j--)
            A[m - 1][j] = min(0, A[m - 1][j], A[m - 1][j] + A[m - 1][j + 1]);

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                // find the best path with max hit-points gained or min hit-points lost
                int bestPath = Math.max(A[i + 1][j], A[i][j + 1]);
                A[i][j] = min(0, A[i][j], A[i][j] + bestPath);
            }
        }

        // we need at least 1 hit-point to survive
        return Math.abs(A[0][0]) + 1;
    }

    // util to find min of n numbers
    private static int min(int... values) {
        int min = values[0];
        for (int i = 1; i < values.length; i++)
            min = Math.min(min, values[i]);

        return min;
    }

    // https://www.interviewbit.com/problems/min-sum-path-in-matrix/
    static int minSumPathMatrix(int[][] A) {
        int m = A.length, n = A[0].length;
        // for first column can only come from top
        for (int i = 1; i < m; i++)
            A[i][0] += A[i - 1][0];
        // for first row can only come from left
        for (int j = 1; j < n; j++)
            A[0][j] += A[0][j - 1];
        // for each point, min cost = current cost + min(top, left)
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++)
                A[i][j] += Math.min(A[i - 1][j], A[i][j - 1]);
        }

        return A[m - 1][n - 1];
    }

    // https://www.interviewbit.com/problems/min-sum-path-in-triangle/
    static int minSumPathTriangle(ArrayList<ArrayList<Integer>> A) {
        int n = A.size();
        // move from bottom to top and calculate min path for going to (i + 1, j) or (i + 1, j + 1)
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < A.get(i).size(); j++)
                A.get(i).set(j, A.get(i).get(j) + Math.min(A.get(i + 1).get(j), A.get(i + 1).get(j + 1)));
        }

        return A.get(0).get(0);
    }

    // https://www.interviewbit.com/problems/max-rectangle-in-binary-matrix/
    static int maxRectInBinaryMatrix(int[][] A) {
        int m = A.length, n = A[0].length;
        int res = 0;

        for (int i = 0; i < m; i++) {
            // if not first row, update height of the bars with previous row
            if (i != 0) {
                for (int j = 0; j < n; j++) {
                    if (A[i][j] != 0)
                        A[i][j] += A[i - 1][j];
                }
            }
            // update result with max area in histogram of current row
            res = Math.max(res, maxAreaInHistogram(A[i]));
        }

        return res;
    }

    // util to find max area in histogram
    private static int maxAreaInHistogram(int[] A) {
        int maxArea = 0;
        Stack<Integer> s = new Stack<>();

        int i = 0;
        while (i < A.length) {
            // keep pushing to make sure stack has non-decreasing order of elements
            if (s.empty() || A[s.peek()] <= A[i])
                s.push(i++);
                // else calculate area with s.top() as height
            else {
                int top = s.pop();
                int area = A[top] * (s.empty() ? i : i - s.peek() - 1);
                maxArea = Math.max(maxArea, area);
            }
        }

        // calculate area with s.top() as height
        while (!s.empty()) {
            int top = s.pop();
            int area = A[top] * (s.empty() ? i : i - s.peek() - 1);
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    // https://www.interviewbit.com/problems/rod-cutting/
    private static int pos;

    static int[] rodCutting(int N, int[] A) {
        int m = A.length;
        // add 2 extra cuts - at 0 and at N
        int[] cuts = new int[m + 2];
        cuts[0] = 0;
        System.arraycopy(A, 0, cuts, 1, m);
        cuts[m + 1] = N;

        // dp[i][j] = min cost by performing cuts from cuts[i + 1] to cuts[j - 1]
        long[][] dp = new long[m + 2][m + 2];
        // minCostPos[i][j] = index of the cut which gives min cost
        int[][] minCostPos = new int[m + 2][m + 2];

        // for all cuts array of size >= 3 (since first and last cut are only for length purpose)
        for (int len = 3; len <= m + 2; len++) {
            for (int i = 0; i + len - 1 < m + 2; i++) {
                int j = i + len - 1;
                dp[i][j] = Long.MAX_VALUE;
                // perform each of the middle cut and optimize
                for (int k = i + 1; k < j; k++) {
                    // newCost = length + minCost for left half + minCost for right half
                    long newCost = cuts[j] - cuts[i] + dp[i][k] + dp[k][j];
                    // if optimum, update min cost and the position of the cut
                    if (newCost < dp[i][j]) {
                        dp[i][j] = newCost;
                        minCostPos[i][j] = k;
                    }
                }
            }
        }

        // result array for the cuts sequence
        int[] res = new int[m];
        pos = 0;
        // recursively compute cuts sequence for all cuts range
        computeMinCutSequence(0, m + 1, cuts, minCostPos, res);

        return res;
    }

    // util to compute the cuts sequence for cuts from cuts[i + 1] to cuts[j - 1]
    private static void computeMinCutSequence(int i, int j, int[] cuts, int[][] minCost, int[] res) {
        // length must be >= 3
        if (i + 1 == j)
            return;

        int k = minCost[i][j];
        // add the min cost cut at current position in res
        res[pos++] = cuts[k];
        // recursively compute cuts sequence for left half and right half
        computeMinCutSequence(i, k, cuts, minCost, res);
        computeMinCutSequence(k, j, cuts, minCost, res);
    }

    // https://www.interviewbit.com/problems/queen-attack/
    // all 8 dirs
    private static final int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

    static int[][] queenAttack(String[] A) {
        int m = A.length, n = A[0].length();
        // dp[i][j][k] = #queens attacking at (i, j) when considered directions from 0 to k
        int[][][] dp = new int[m][n][8];
        for (int[][] arr : dp) {
            for (int[] a : arr)
                Arrays.fill(a, -1);
        }

        int[][] res = new int[m][n];

        // for each direction, traverse each cell and update the result count
        for (int k = 0; k < 8; k++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++)
                    res[i][j] += queenAttackUtil(i, j, k, A, dp);
            }
        }

        return res;
    }

    // util to calculate how many queens attack at (i, j) considering only direction = k
    private static int queenAttackUtil(int i, int j, int k, String[] A, int[][][] dp) {
        // memoization
        if (dp[i][j][k] != -1)
            return dp[i][j][k];

        int m = A.length, n = A[0].length();
        // move in direction = k
        int x = i + dir[k][0], y = j + dir[k][1];

        dp[i][j][k] = 0;
        // if the cell exists
        if (isValid(x, y, m, n)) {
            // if there is queen in neighbouring cell, only one queen can attack current cell
            if (A[x].charAt(y) == '1')
                dp[i][j][k]++;
                // else recursively find how many queens are attacking the neighbouring cell from direction = k
            else
                dp[i][j][k] += queenAttackUtil(x, y, k, A, dp);
        }

        return dp[i][j][k];
    }

    // util to check if point (i, j) is in the bounds
    private static boolean isValid(int i, int j, int m, int n) {
        return i >= 0 && i < m && j >= 0 && j < n;
    }

    // https://www.interviewbit.com/problems/sub-matrices-with-sum-zero/
    static int subMatricesWithSumZero(int[][] A) {
        // input validation
        int m = A.length;
        if (m == 0)
            return 0;
        int n = A[0].length;
        if (n == 0)
            return 0;

        int res = 0;
        // for each pair of rows including with self
        for (int i = 0; i < m; i++) {
            int[] row = new int[n];

            for (int j = i; j < m; j++) {
                // take each pair of rows and add up all the rows between them
                for (int k = 0; k < n; k++)
                    row[k] += A[j][k];
                // update count of sub-arrays with sum = 0 in the newly formed 1D array
                res += subArraysWithZeroSum(row);
            }
        }

        return res;
    }

    // util to find number of sub-arrays with sum = 0
    private static int subArraysWithZeroSum(int[] A) {
        int sum = 0, res = 0;
        Map<Integer, Integer> map = new HashMap<>();

        for (int val : A) {
            // update prefix sum
            sum += val;
            // found a prefix array with sum = 0
            if (sum == 0)
                res++;
            // if found prefix array with sum = current sum,
            // add count of all sub-arrays that can be formed in between having sum = 0
            if (map.containsKey(sum))
                res += map.get(sum);
            // update the sum map count
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/coin-sum-infinite/
    static int coinSumInfinite(int[] A, int N) {
        // can also be done using 2D dp and optimized with dp[2][N + 1]
        int MOD = 1000007;
        // dp[i] = # ways sum = i can be obtained
        int[] dp = new int[N + 1];
        // base case: required sum = 0
        dp[0] = 1;

        for (int val : A) {
            // for each sum higher than current coin, select current coin at least once or don't
            for (int w = val; w <= N; w++)
                dp[w] = (dp[w - val] + dp[w]) % MOD;
        }

        return dp[N];
    }

    // https://www.interviewbit.com/problems/max-product-subarray/
    static int maxProductSubarray(final int[] A) {
        int n = A.length;
        // keep track of max and min product along with result
        int maxProd = A[0], minProd = A[0], res = A[0];

        for (int i = 1; i < n; i++) {
            // save previous maxProd
            int temp = maxProd;
            // maxProd = max(curr [if prev = 0], curr * maxProd [if curr is pos], curr * minProd [if curr is neg])
            maxProd = Math.max(A[i], Math.max(maxProd * A[i], minProd * A[i]));
            // minProd = min(curr [if prev = 0], curr * prev maxProd [if curr is neg], curr * minProd [if curr is pos])
            minProd = Math.min(A[i], Math.min(temp * A[i], minProd * A[i]));
            res = Math.max(res, maxProd);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/best-time-to-buy-and-sell-stocks-i/
    static int buyAndSellStocks1(final int[] A) {
        int minPrice = A[0], profit = 0;
        // for each day after 1st day
        for (int i = 1; i < A.length; i++) {
            // if today price is minimum, buy
            if (A[i] <= minPrice)
                minPrice = A[i];
                // else try selling and maximize profit
            else
                profit = Math.max(profit, A[i] - minPrice);
        }

        return profit;
    }

    // https://www.interviewbit.com/problems/arrange-ii/
    static int arrange2(String A, int K) {
        int n = A.length();
        // #stables > #horses
        if (K > n)
            return -1;
        // #stables = #horses
        if (K == n)
            return 0;

        // dp[i][k] = cost for horses from [0, i] with k stables
        int[][] dp = new int[n][K + 1];

        int white = 0, black = 0;
        // for k = 1, put all horses in the same stable
        for (int i = 0; i < n; i++) {
            if (A.charAt(i) == 'W')
                white++;
            else
                black++;

            dp[i][1] = white * black;
        }

        for (int k = 2; k <= K; k++) {
            for (int i = 0; i < n; i++) {
                // #stables > #horses
                if (k > i + 1)
                    dp[i][k] = Integer.MAX_VALUE;
                    // #stables = #horses
                else if (k == i + 1)
                    dp[i][k] = 0;
                else {
                    dp[i][k] = Integer.MAX_VALUE;
                    white = black = 0;
                    // for each horse j in middle, try putting [0, j] horses in k - 1 stables and [j, i] horses in the kth stable
                    // and optimize
                    for (int j = i - 1; j >= 0; j--) {
                        if (A.charAt(j + 1) == 'W')
                            white++;
                        else
                            black++;
                        // make sure overflow does not occur
                        if (dp[j][k - 1] + white * black >= 0)
                            dp[i][k] = Math.min(dp[i][k], dp[j][k - 1] + white * black);
                    }
                }
            }
        }

        return dp[n - 1][K];
    }

    // https://www.interviewbit.com/problems/chain-of-pairs/
    static int chainOfPairsSubsequence(int[][] A) {
        int n = A.length;
        // use LIS(Longest Increasing Subsequence) algorithm
        // dp[i] = length of the longest increasing pairs subsequence ending at A[i]
        int[] dp = new int[n];
        dp[0] = 1;
        // res = max of the longest increasing pairs subsequence ending at each A[i]
        int res = 0;

        for (int i = 1; i < n; i++) {
            // A[i] itself as subsequence
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                int[] p1 = A[j], p2 = A[i];
                // if we can expand current subsequence at A[j], expand it and optimize
                if (p1[1] < p2[0])
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
            }

            res = Math.max(res, dp[i]);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/max-sum-without-adjacent-elements/
    static int maxSumWithoutAdjacent(int[][] A) {
        int n = A[0].length;
        // base cases
        if (n == 1)
            return Math.max(A[0][0], A[1][0]);
        if (n == 2)
            return Math.max(Math.max(A[0][0], A[1][0]), Math.max(A[0][1], A[1][1]));

        // dp[i] = max sum formed with max element from ith col taken or not taken
        int[] dp = new int[n];
        // base cases
        dp[0] = Math.max(A[0][0], A[1][0]);
        dp[1] = Math.max(dp[0], Math.max(A[0][1], A[1][1]));

        // res = max of dp[]
        int res = Math.max(dp[0], dp[1]);
        // for each col, either include max of current col in the sum or don't
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(Math.max(A[0][i], A[1][i]) + dp[i - 2], dp[i - 1]);
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/merge-elements/
    static int mergeElements(int[] A) {
        int n = A.length;
        // use prefix sum to compute sub-array sum in O(1) time
        int[] prefixSum = new int[n + 1];
        prefixSum[0] = A[0];
        for (int i = 1; i < n; i++)
            prefixSum[i] = prefixSum[i - 1] + A[i];

        // dp[i][j] = cost for merging the sub-array from [i, j]
        int[][] dp = new int[n][n];
        dp[0][0] = 0;
        // base-cases: cost of merging 1 element is 0 and for 2 elements is the sum
        for (int i = 1; i < n; i++) {
            dp[i][i] = 0;
            dp[i - 1][i] = A[i - 1] + A[i];
        }

        // for len >= 3 sub-arrays
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                // find the cut where cost would be minimized
                for (int k = i; k < j; k++) {
                    // cost = cost of [i, k] + cost of [k + 1, j] + sub-array sum of [i, j]
                    int cost = dp[i][k] + dp[k + 1][j] + prefixSum[j] - (i > 0 ? prefixSum[i - 1] : 0);
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n - 1];
    }

    // https://www.interviewbit.com/problems/flip-array/
    static int flipArray(final int[] A) {
        int n = A.length, totalSum = 0;
        // find max sum possible
        for (int val : A)
            totalSum += val;

        // try diving the array into 2 subsets and find #elements required in each subset sum
        // dp[i][sum] = #elements in subset till [0, i] that can add up to sum
        int[][] dp = new int[n + 1][totalSum / 2 + 1];
        // n = 0, sum > 0 not possible
        for (int sum = 1; sum <= totalSum / 2; sum++)
            dp[0][sum] = Integer.MAX_VALUE;

        for (int sum = 1; sum <= totalSum / 2; sum++) {
            for (int i = 1; i <= n; i++) {
                // if we can use current element and subset with the remaining sum is possible, either use it or don't
                if (sum >= A[i - 1] && dp[i - 1][sum - A[i - 1]] != Integer.MAX_VALUE)
                    dp[i][sum] = Math.min(1 + dp[i - 1][sum - A[i - 1]], dp[i - 1][sum]);
                    // cannot use current element
                else
                    dp[i][sum] = dp[i - 1][sum];
            }
        }

        // find the largest possible achievable sum close to totalSum / 2.
        // Sign of all the elements in that subset must be flipped
        int sum = totalSum / 2;
        while (sum > 0 && dp[n][sum] == Integer.MAX_VALUE)
            sum--;

        return dp[n][sum];
    }

    // https://www.interviewbit.com/problems/tushars-birthday-party/
    static int birthdayParty(final int[] A, final int[] B, final int[] C) {
        // find the friend with maximum capacity
        int maxCapacity = A[0];
        for (int i = 1; i < A.length; i++)
            maxCapacity = Math.max(maxCapacity, A[i]);

        // use 0-1 knapsack technique for friend with maximum capacity and minimize cost
        // this will ensure minimum cost for other friends is also calculated
        int n = B.length;
        int[][] dp = new int[n + 1][maxCapacity + 1];
        // use Integer.MAX_VALUE / 2 to avoid overflow
        for (int[] arr : dp)
            Arrays.fill(arr, Integer.MAX_VALUE / 2);
        // base case: capacity = 0
        for (int i = 0; i <= n; i++)
            dp[i][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= maxCapacity; w++) {
                // if friend can eat current dish, either eat at least one of current dish or don't eat current dish
                if (w - B[i - 1] >= 0)
                    dp[i][w] = Math.min(C[i - 1] + dp[i][w - B[i - 1]], dp[i - 1][w]);
                    // else don't eat current dish
                else
                    dp[i][w] = dp[i - 1][w];
            }
        }

        // compute sum of min cost for each friend
        int res = 0;
        for (int c : A)
            res += dp[n][c];

        return res;
    }

    // https://www.interviewbit.com/problems/0-1-knapsack/
    static int knapsack01(int[] A, int[] B, int C) {
        int n = A.length;
        // dp[i][j] = max value obtained with first i objects with max capacity = j
        // dp[i][0] = 0, dp[0][j] = 0
        int[][] dp = new int[n + 1][C + 1];

        for (int i = 1; i <= n; i++)
            for (int w = 1; w <= C; w++) {
                // if we can carry this object, either carry it or don't
                if (w - B[i - 1] >= 0)
                    dp[i][w] = Math.max(A[i - 1] + dp[i - 1][w - B[i - 1]], dp[i - 1][w]);
                    // else don't carry the object
                else
                    dp[i][w] = dp[i - 1][w];
            }

        return dp[n][C];
    }

    // https://www.interviewbit.com/problems/equal-average-partition/
    static int[][] equalAveragePartition(int[] A) {
        int n = A.length, totalSum = 0;
        for (int val : A)
            totalSum += val;

        // dp[i][sum][len] = T/F for whether sum can be achieved with len numbers till A[i]
        boolean[][][] dp = new boolean[n][totalSum + 1][n];
        for (boolean[][] a : dp) {
            for (boolean[] b : a)
                Arrays.fill(b, true);
        }

        // sort for lexicographical order
        Arrays.sort(A);
        // try all length subsets
        for (int len = 1; len < n; len++) {
            // sum(x) / x = (totalSum - sum(x)) / (n - x) simplifies to sum(x) = totalSum * x / n
            // check if sum(x) is a whole number
            if ((totalSum * len) % n != 0)
                continue;

            int sum1 = (totalSum * len) / n;
            LinkedList<Integer> list = new LinkedList<>();
            // find subset of size = len that has sum = sum1
            if (canFormSum(A, 0, sum1, len, list, dp))
                return computePartitions(A, list);
        }

        // partition not possible
        return new int[0][0];
    }

    // util to check if subset of size = len starting from A[pos] is possible with sum
    private static boolean canFormSum(int[] A, int pos, int sum, int len, LinkedList<Integer> list, boolean[][][] dp) {
        // no elements left - check if sum == 0
        if (len == 0)
            return sum == 0;
        // reached end of array
        if (pos == A.length)
            return false;
        // memoization
        if (!dp[pos][sum][len])
            return false;

        // if can include current element, include it in subset
        if (sum >= A[pos]) {
            list.add(A[pos]);
            // recursively check if remaining sum subset is possible
            if (canFormSum(A, pos + 1, sum - A[pos], len - 1, list, dp))
                return true;
            // backtrack
            list.removeLast();
        }

        // exclude current element from subset
        if (canFormSum(A, pos + 1, sum, len, list, dp))
            return true;

        // subset sum not possible
        return dp[pos][sum][len] = false;
    }

    // util to compute result array from the 1st partition list
    private static int[][] computePartitions(int[] A, List<Integer> list) {
        int n = A.length;
        int size1 = list.size(), size2 = n - list.size();
        int[][] res = new int[2][];
        // copy the 1st partition
        res[0] = new int[size1];
        for (int i = 0; i < size1; i++)
            res[0][i] = list.get(i);

        // compute 2nd partition
        res[1] = new int[size2];
        int p1 = 0, p2 = 0;
        for (int val : A) {
            // if already present in 1st partition - skip
            if (p1 < size1 && val == list.get(p1))
                p1++;
                // else add to 2nd partition
            else
                res[1][p2++] = val;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/best-time-to-buy-and-sell-stocks-ii/
    static int buyAndSellStocks2(final int[] A) {
        int profit = 0;

        for (int i = 1; i < A.length; i++) {
            // if price is higher today, sell
            // e.g 1, 2, 9, 3 then 9 - 1 = 9 - 2 + 2 - 1
            if (A[i] > A[i - 1])
                profit += A[i] - A[i - 1];
        }

        return profit;
    }

    // https://www.interviewbit.com/problems/word-break-ii/
    static ArrayList<String> wordBreak2(String A, String[] B) {
        // create trie of all words in dictionary
        Trie trie = new Trie();
        for (String word : B)
            trie.add(word);

        // dp.get(str) = all strings that can be formed by breaking str into words
        Map<String, ArrayList<String>> dp = new HashMap<>();
        // recursively compute result for whole string
        return wordBreak2Util(A, trie.root, dp);
    }

    // util to form all possible word combinations of string A
    private static ArrayList<String> wordBreak2Util(String A, TrieNode root, Map<String, ArrayList<String>> dp) {
        ArrayList<String> res = new ArrayList<>();
        // base-case: empty string
        if (A.isEmpty()) {
            res.add("");
            return res;
        }
        // memoization
        if (dp.containsKey(A))
            return dp.get(A);

        TrieNode curr = root;
        StringBuilder builder = new StringBuilder();
        // keep matching prefix in trie
        for (int i = 0; i < A.length(); i++) {
            char c = A.charAt(i);
            // if prefix is not a word, no more solutions possible
            if (curr.child[c - 'a'] == null) {
                dp.put(A, res);
                return res;
            }

            curr = curr.child[c - 'a'];
            builder.append(c);

            // if current prefix is a word, append all combinations of the suffix by recursively calling the util
            if (curr.isEnd) {
                for (String out : wordBreak2Util(A.substring(i + 1), root, dp)) {
                    if (out.isEmpty())
                        res.add(builder.toString());
                    else
                        res.add(builder + " " + out);
                }
            }
        }

        dp.put(A, res);
        return res;
    }

    // https://www.interviewbit.com/problems/unique-binary-search-trees-ii/
    static int uniqueBST2(int A) {
        // dp[i] = #BST formed with i nodes
        int[] dp = new int[A + 1];
        return uniqueBST2Util(1, A, dp);
    }

    // util to compute #BST formed within range of min to max inclusive
    private static int uniqueBST2Util(int min, int max, int[] dp) {
        // reached end
        if (min > max)
            return 0;
        // leaf node
        if (min == max)
            return 1;
        // memoization
        if (dp[max - min + 1] != 0)
            return dp[max - min + 1];

        // try each node as root
        for (int i = min; i <= max; i++) {
            // find #BST for (i - min) nodes and (max - i) nodes
            int left = uniqueBST2Util(min, i - 1, dp);
            int right = uniqueBST2Util(i + 1, max, dp);

            // count BSTs with only right subtree
            if (left == 0)
                dp[max - min + 1] += right;
                // count BSTs with only left subtree
            else if (right == 0)
                dp[max - min + 1] += left;
                // count BSTs with all left and right subtree combinations
            else
                dp[max - min + 1] += left * right;
        }

        return dp[max - min + 1];
    }

    // https://www.interviewbit.com/problems/count-permutations-of-bst/
    static int countPermutationsBST(int A, int B) {
        // need at least B + 1 nodes to achieve height B
        if (A <= B)
            return 0;
        // 1 node tree has height = 0
        if (A == 1 && B == 0)
            return 1;

        final int MOD = 1_000_000_007;
        // dp[i][j] = #permutations of i nodes with height = j
        int[][] dp = new int[A + 1][B + 1];
        dp[0][0] = 1;
        dp[1][0] = 1;
        // compute and memoize combinations
        long[][] C = computeCombinations(A);

        // iterate for 2 to A node permutations
        for (int i = 2; i <= A; i++) {
            // check for height = 1 to B
            for (int height = 1; height <= B; height++) {
                // try each node in the range as root
                for (int root = 1; root <= i; root++) {
                    int x = root - 1, y = i - root;
                    // sum1 = sum of h = 1 to height - 2 permutations of size = x
                    // sum2 = sum of h = 1 to height - 2 permutations of size = y
                    long sum1 = 0, sum2 = 0, cnt = 0;
                    // compute sum1 and sum2
                    for (int h = 0; h <= height - 2; h++) {
                        sum1 = (sum1 + dp[x][h]) % MOD;
                        sum2 = (sum2 + dp[y][h]) % MOD;
                    }

                    // cnt = cnt + #trees with left half of h = 1 to height - 2 and right half of h = height - 1
                    cnt = (cnt + sum1 * dp[y][height - 1]) % MOD;
                    // cnt = cnt + #trees with left half of h = height - 1 and right half of h = 1 to height - 2
                    cnt = (cnt + sum2 * dp[x][height - 1]) % MOD;
                    // cnt = cnt + #trees with left half of h = height - 1 and right half of h = height - 1
                    cnt = (cnt + (long) dp[x][height - 1] * dp[y][height - 1]) % MOD;
                    // cnt = cnt * #permutations possible (x and y nodes should be in relative order hence fix x or y nodes (x+y)C(y))
                    cnt = (cnt * C[x + y][y]);

                    dp[i][height] = (int) ((dp[i][height] + cnt) % MOD);
                }
            }
        }

        return dp[A][B];
    }

    // util to compute and memoize all combinations
    private static long[][] computeCombinations(int N) {
        long[][] C = new long[N + 1][N + 1];

        for (int n = 0; n < N; n++) {
            // nC0 = 1
            C[n][0] = 1;

            for (int k = 1; k <= n; k++) {
                // nCn = 1
                if (n == k)
                    C[n][k] = 1;
                    // nCk = (n-1)C(k) + (n-1)C(k-1)
                else
                    C[n][k] = (C[n - 1][k] + C[n - 1][k - 1]) % 1000000007;
            }
        }

        return C;
    }

    // https://www.interviewbit.com/problems/palindrome-partitioning-ii/
    static int palindromePartitioning2(String A) {
        int n = A.length();
        // isPalindrome[i][j] = A.substring(i, j + 1) is palindrome or not
        boolean[][] isPalindrome = computePalindromes(A);

        // dp[i] = #cuts for suffix starting at A[i]
        int[] dp = new int[n];
        // max cuts string can have is n hence initialize as n + 1 instead of Integer.MAX_VALUE
        Arrays.fill(dp, 0, n - 1, n + 1);
        dp[n - 1] = 0;

        return palindromePartitioning2Util(n, 0, isPalindrome, dp);
    }

    // util to compute palindrome check for all substrings
    private static boolean[][] computePalindromes(String A) {
        int n = A.length();
        boolean[][] isPalindrome = new boolean[n][n];
        // len = 1 is always palindrome
        for (int i = 0; i < n; i++)
            isPalindrome[i][i] = true;

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // if end characters match
                if (A.charAt(i) == A.charAt(j)) {
                    // if len >= 3
                    if (i + 1 <= j - 1)
                        isPalindrome[i][j] = isPalindrome[i + 1][j - 1];
                        // len = 2
                    else
                        isPalindrome[i][j] = true;
                }
            }
        }

        return isPalindrome;
    }

    // util to recursively compute min cuts
    private static int palindromePartitioning2Util(int n, int l, boolean[][] isPalindrome, int[] dp) {
        // if memoized
        if (dp[l] != n + 1)
            return dp[l];
        // if palindrome, no cuts required
        if (isPalindrome[l][n - 1]) {
            dp[l] = 0;
            return 0;
        }

        // max cuts required = cuts at all pos = length
        int minCuts = n - l + 1;
        for (int i = l; i < n; i++) {
            // if prefix is palindrome, optimize cuts as 1 + cuts for the suffix
            if (isPalindrome[l][i])
                minCuts = Math.min(minCuts, 1 + palindromePartitioning2Util(n, i + 1, isPalindrome, dp));
        }

        dp[l] = minCuts;

        return minCuts;
    }

    // https://www.interviewbit.com/problems/word-break/
    static int wordBreak(String A, String[] B) {
        // create trie of all words in dictionary
        Trie trie = new Trie();
        for (String word : B)
            trie.add(word);

        // recursively check if string can be divided into words of dictionary
        return wordBreakUtil(A, trie.root) ? 1 : 0;
    }

    // util to check if string A can be divided into words of dictionary
    private static boolean wordBreakUtil(String A, TrieNode root) {
        // base-case: empty string
        if (A.isEmpty())
            return true;

        TrieNode curr = root;
        // keep searching for current string in trie
        for (int i = 0; i < A.length(); i++) {
            char c = A.charAt(i);
            // if prefix so far not found, string cannot be divided into words
            if (curr.child[c - 'a'] == null)
                return false;

            curr = curr.child[c - 'a'];
            // if a match is found, recursively check for the remaining half
            if (curr.isEnd && wordBreakUtil(A.substring(i + 1), root))
                return true;
        }

        return false;
    }

    static class TrieNode {
        TrieNode[] child;
        boolean isEnd;

        TrieNode() {
            child = new TrieNode[26];
            isEnd = false;
        }
    }

    static class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        public void add(String word) {
            TrieNode curr = root;

            for (char c : word.toCharArray()) {
                if (curr.child[c - 'a'] == null)
                    curr.child[c - 'a'] = new TrieNode();
                curr = curr.child[c - 'a'];
            }

            curr.isEnd = true;
        }
    }
}