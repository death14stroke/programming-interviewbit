import java.util.*;

class DP {
    // https://www.interviewbit.com/problems/longest-common-subsequence/
    static int lcs(String A, String B) {
        int m = A.length(), n = B.length();
        int[][] lcs = new int[m + 1][n + 1];
        // lcs(i, j) = 1 + lcs(i - 1, j - 1) if last character matches else
        // lcs(i, j) = max(lcs(i - 1, j), lcs(i, j - 1))
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if last character matches
                if (A.charAt(i - 1) == B.charAt(j - 1))
                    lcs[i][j] = 1 + lcs[i - 1][j - 1];
                    // else remove current character in either of strings and find maximum
                else
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
            }
        }

        return lcs[m][n];
    }

    // https://www.interviewbit.com/problems/longest-palindromic-subsequence/
    static int lps(String A) {
        // lps of a string is lcs of the string with its reverse
        return lcs(A, new StringBuilder(A).reverse().toString());
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
        // longest repeating subsequence is the lcs of string with itself with added condition that character positions must be different
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
                // if last character matches, use it
                if (A.charAt(i - 1) == B.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                // delete last character
                dp[i][j] += dp[i - 1][j];
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
        // memoization
        if (map.containsKey(key))
            return map.get(key);
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

        int n = A.length();
        // try slicing the string at each possible index
        for (int i = 1; i < n; i++) {
            // recursively check for first halves of both strings and last halves of both strings
            if (scrambleStringUtil(A.substring(0, i), B.substring(0, i)) && scrambleStringUtil(A.substring(i), B.substring(i))) {
                map.put(key, true);
                return true;
            }

            // recursively check for first half of A and last half of B && last half of A and first half of B
            if (scrambleStringUtil(A.substring(0, i), B.substring(n - i)) && scrambleStringUtil(A.substring(i), B.substring(0, n - i))) {
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
            // if any character is extra is string B
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
        // input is empty but pattern is not empty. Valid only if pattern is all '*'
        for (int j = 1; j <= n && B.charAt(j - 1) == '*'; j++)
            dp[0][j] = true;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if single character matcher in pattern or the characters match
                if (B.charAt(j - 1) == '?' || A.charAt(i - 1) == B.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                    // else if 0 or more characters match, (1) consider *, (2) ignore * (match 0 chars)
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
        // input is empty but pattern is not empty (can match only <char>*<char>*<char>* type strings)
        for (int j = 2; j <= n && B.charAt(j - 1) == '*'; j += 2)
            dp[0][j] = true;

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
                        dp[i][j] |= dp[i - 1][j];
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
        for (int i = 1; i <= m && A.charAt(i - 1) == C.charAt(i - 1); i++)
            dp[i][0] = true;
        // string A is empty
        for (int j = 1; j <= n && B.charAt(j - 1) == C.charAt(j - 1); j++)
            dp[0][j] = true;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char a = A.charAt(i - 1), b = B.charAt(j - 1), c = C.charAt(i + j - 1);
                // if A's character matches with C
                if (a == c)
                    dp[i][j] = dp[i - 1][j];
                // also check if B's character matches with C
                if (b == c)
                    dp[i][j] |= dp[i][j - 1];
            }
        }

        return dp[m][n] ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/length-of-longest-subsequence/
    static int longestBitonicSubsequence(final int[] A) {
        int n = A.length;
        // base cases
        if (n <= 1)
            return n;

        // lis[i] = longest increasing subsequence ending at i
        int[] lis = new int[n];
        lis[0] = 1;
        for (int i = 1; i < n; i++) {
            lis[i] = 1;

            for (int j = 0; j < i; j++) {
                // if can extend current lis
                if (A[j] < A[i])
                    lis[i] = Math.max(lis[i], lis[j] + 1);
            }
        }

        // lds[i] = longest decreasing subsequence ending at i
        int[] lds = new int[n];
        lds[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            lds[i] = 1;

            for (int j = i + 1; j < n; j++) {
                // if can extend current lds
                if (A[i] > A[j])
                    lds[i] = Math.max(lds[i], lds[j] + 1);
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
            res[i] = Math.min(next1, Math.min(next2, next3));
            // update next multiple of first sequence
            if (res[i] == next1)
                next1 = res[++i1] * A;
            // update next multiple of second sequence
            if (res[i] == next2)
                next2 = res[++i2] * B;
            // update next multiple of third sequence
            if (res[i] == next3)
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

        int res = 0;
        // for each row compute max area
        for (int[] row : A) {
            // sort each row such that largest rectangle is formed at the last column (can also use count sort)
            Arrays.sort(row);
            // start from the last column to compute max area
            for (int j = n - 1; j >= 0 && row[j] > 0; j--)
                res = Math.max(res, row[j] * (n - j));
        }

        return res;
    }

    // https://www.interviewbit.com/problems/tiling-with-dominoes/
    static int tilingWithDominoes(int A) {
        // base case
        if (A == 1)
            return 0;

        // A[i] = #ways to form completely filled 3 x i matrix
        // B[i] = #ways to form top row empty or bottom row empty in last col of 3 x i matrix
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
    static int stairs(int A) {
        // 1 or 2 stairs
        if (A <= 2)
            return A;

        // step1 = dp[i - 2], step2 = dp[i - 1]
        int step1 = 1, step2 = 2, step3 = 0;
        // dp[i] = dp[i - 1] + dp[i - 2]
        for (int i = 3; i <= A; i++) {
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
                // if a lis can be formed with A[j] as 2nd last and A[i] as last element
                if (A[j] < A[i])
                    lis[i] = Math.max(lis[i], lis[j] + 1);
            }
            // update global result
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
        // dp[i] = #ways possible for i chords
        long[] dp = new long[A + 1];
        // base cases
        dp[0] = dp[1] = 1;

        // for i chords there are 2 * i points. 1st chord can be either of 1 - 2, 1 - 4, 1 - 6,..., 1 - 2 * i.
        // for chord 1 - 3, there will always remain a point empty in between hence no odd numbered end points
        for (int i = 2; i <= A; i++) {
            // for first chord from 1 - (2 * (j + 1)) #ways is the permutation of sol of the both halves formed
            for (int j = 0; j < i; j++)
                dp[i] = (dp[i] + dp[j] * dp[i - j - 1]) % MOD;
        }

        return (int) dp[A];
    }

    // https://www.interviewbit.com/problems/tushars-birthday-bombs/
    static int[] birthdayBombs(int A, int[] B) {
        int n = B.length, minIndex = 0;
        // find friend with minimum strength
        for (int i = 1; i < n; i++) {
            if (B[i] < B[minIndex])
                minIndex = i;
        }

        // greedily length of answer will be #kicks by the weakest friend
        int hits = A / B[minIndex];
        // update remaining capacity
        A %= B[minIndex];
        // capacity of Tushar is full
        if (A == 0) {
            int[] res = new int[hits];
            Arrays.fill(res, minIndex);
            return res;
        }

        int i = 0;
        List<Integer> pos = new LinkedList<>();
        // while capacity remaining, try substituting 1 weak kick with 1 stronger kick if possible before minIndex
        while (A > 0 && i < minIndex && hits > 0) {
            // this kick will be lexicographically smaller and is possible
            if (A + B[minIndex] - B[i] >= 0) {
                pos.add(i);
                A += B[minIndex] - B[i];
                hits--;
            } else { // else check for next element
                i++;
            }
        }

        // copy pos to array
        int[] res = new int[pos.size() + hits];
        i = 0;
        for (int p : pos)
            res[i++] = p;
        // add positions of the weakest kick
        Arrays.fill(res, i, res.length, minIndex);

        return res;
    }

    // https://www.interviewbit.com/problems/jump-game-array/
    static int canJump(int[] A) {
        // initial maxReach = 0
        int n = A.length, maxReach = 0;
        // keep updating maxReach till we reach destination or get stuck
        for (int i = 0; i <= maxReach && i < n; i++) {
            maxReach = Math.max(maxReach, i + A[i]);
            // can reach destination
            if (maxReach >= n - 1)
                return 1;
        }

        return 0;
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
                // perform a jump (from the point where maxReach was updated)
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
        Map<Integer, Integer>[] dp = new Map[n];
        dp[0] = new HashMap<>();

        for (int i = 1; i < n; i++) {
            dp[i] = new HashMap<>();

            for (int j = 0; j < i; j++) {
                int diff = A[i] - A[j];
                // if AP exists at index j with difference "diff", extend it
                if (dp[j].containsKey(diff)) {
                    dp[i].put(diff, dp[j].get(diff) + 1);
                    res = Math.max(res, dp[i].get(diff));
                } else { // else create new AP at index j with 2 elements
                    dp[i].put(diff, 2);
                }
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/n-digit-numbers-with-digit-sum-s-/
    // alternate solution: https://www.geeksforgeeks.org/count-of-n-digit-numbers-whose-sum-of-digits-equals-to-given-sum/
    static int nDigitNumsWithDigitSum(int N, int S) {
        // base case: numbers start from 1 hence only sum from 1 to 9 possible for N = 1
        if (N == 1)
            return S == 0 || S >= 10 ? 0 : 1;

        int MOD = 1000000007;
        // dp[i][j] = # i digit numbers whose sum of digits is j
        int[][] dp = new int[N + 1][S + 1];
        // for N = 1, only sum from 1 to 9 possible
        for (int s = 1; s <= 9 && s <= S; s++)
            dp[1][s] = 1;

        // start filling from 2nd position
        for (int i = 2; i <= N; i++) {
            for (int s = 1; s <= S; s++) {
                // try all numbers from 0 to 9 till current digit is less than sum = s
                for (int k = 0; k <= 9 && k <= s; k++)
                    dp[i][s] = (dp[i][s] + dp[i - 1][s - k]) % MOD;
            }
        }

        return dp[N][S];
    }

    // https://www.interviewbit.com/problems/shortest-common-superstring/
    // another possible solution is DP with bitmask (see question hint)
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
        // remove the subset strings
        for (int i = 0; i < n; i++) {
            if (!subsets.contains(i))
                A[k++] = A[i];
        }

        // merge the most overlapping strings k - 1 times
        for (int len = k; len > 1; len--) {
            int maxOverlap = 0, l = 0, r = 0;
            String overlapStr = "";
            // find maximum overlap among overlaps between each pair of string
            for (int i = 0; i < len - 1; i++) {
                for (int j = i + 1; j < len; j++) {
                    Overlap overlap = findOverlappingPair(A[i], A[j]);
                    if (maxOverlap < overlap.length) {
                        maxOverlap = overlap.length;
                        overlapStr = overlap.str;
                        l = i;
                        r = j;
                    }
                }
            }

            // if no overlap merge last string with first
            if (maxOverlap == 0) {
                A[0] += A[len - 1];
            } else { // else replace merged string at one position and swap last string at the other
                A[l] = overlapStr;
                A[r] = A[len - 1];
            }
        }

        return A[0].length();
    }

    // util to find maximum overlap length
    private static Overlap findOverlappingPair(String A, String B) {
        int m = A.length(), n = B.length();
        // start with maximum possible overlap
        for (int i = Math.min(m, n) - 1; i > 0; i--) {
            // if prefix of A matches with suffix of B
            if (A.substring(0, i).compareTo(B.substring(n - i)) == 0)
                return new Overlap(i, B + A.substring(i));
            // if suffix of A matches with prefix of B
            if (A.substring(m - i).compareTo(B.substring(0, i)) == 0)
                return new Overlap(i, A + B.substring(i));
        }

        return new Overlap(0, null);
    }

    // util class to store the overlap length and the merged string
    static class Overlap {
        int length;
        String str;

        Overlap(int length, String str) {
            this.length = length;
            this.str = str;
        }
    }

    // https://www.interviewbit.com/problems/ways-to-color-a-3xn-board/
    static int color3NBoard(int A) {
        int p = 1000000007;
        // base case: A = 1 can select C(4, 3) * 3! + C(4, 2) * 2 ways
        long color3 = 24, color2 = 12;
        // W(n+1) = 10*Y(n)+11*W(n) where W(n+1) is #3-color combinations with (n+1) cols
        // Y(n+1) = 7*Y(n)+5*W(n) where Y(n+1) is #2-color combinations with (n+1) cols
        // see https://www.geeksforgeeks.org/ways-color-3n-board-using-4-colors/ for explanation
        for (int i = 2; i <= A; i++) {
            long temp = color3;
            // update 2 color and 3 color combinations count
            color3 = (11 * color3 + 10 * color2) % p;
            color2 = (5 * temp + 7 * color2) % p;
        }

        // result will be the sum of 3-color and 2-color combinations
        return (int) ((color3 + color2) % p);
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
                    int top = i > 0 ? A[i - 1][j] : -1;
                    int bottom = i + 1 < m ? A[i + 1][j] : -1;
                    int left = j > 0 ? A[i][j - 1] : -1;
                    int right = j + 1 < n ? A[i][j + 1] : -1;
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

        // max transactions that can be done = n / 2. Hence, optimize value of B
        B = Math.min(B, n / 2);
        // buy[i] = state of buying ith transaction
        // sell[i] = state of profit after ith transaction
        int[] buy = new int[B], sell = new int[B];
        buy[0] = A[0];
        Arrays.fill(buy, 1, B, Integer.MAX_VALUE);

        for (int i = 1; i < n; i++) {
            // update first transaction state
            buy[0] = Math.min(buy[0], A[i]);
            sell[0] = Math.max(sell[0], A[i] - buy[0]);

            for (int k = 1; k < B; k++) {
                // buying kth txn cost = today's price - previous txn profit
                buy[k] = Math.min(buy[k], A[i] - sell[k - 1]);
                // profit after kth txn = today's price - kth txn buying cost
                sell[k] = Math.max(sell[k], A[i] - buy[k]);
            }
        }

        return sell[B - 1];
    }

    // https://www.interviewbit.com/problems/coins-in-a-line/
    static int coinsInLine(int[] A) {
        int n = A.length;
        // dp[i][j] = max score for coins from [i, j]
        int[][] dp = new int[n][n];
        dp[0][0] = A[0];
        // len = 1 and len = 2
        for (int i = 1; i < n; i++) {
            dp[i][i] = A[i];
            dp[i - 1][i] = Math.max(A[i - 1], A[i]);
        }
        // for len = 3 and more
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                // we choose i and opponent chooses i + 1 or j such that we get minimum score
                int left = A[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]);
                // we choose j and opponent chooses i or j - 1 such that we get minimum score
                int right = A[j] + Math.min(dp[i + 1][j - 1], dp[i][j - 2]);
                // make the best choice
                dp[i][j] = Math.max(left, right);
            }
        }

        return dp[0][n - 1];
    }

    // https://www.interviewbit.com/problems/evaluate-expression-to-true/
    static int evaluateExpToTrue(String A) {
        int n = A.length();
        if (n == 1)
            return A.charAt(0) == 'T' ? 1 : 0;

        int MOD = 1003;
        // T[i][j] = #ways to evaluate exp from [i, j] as true
        // F[i][j] = #ways to evaluate exp from [i, j] as false
        int[][] T = new int[n][n], F = new int[n][n];
        // base-case: len = 1 (consider only operands hence i += 2)
        for (int i = 0; i < n; i += 2) {
            if (A.charAt(i) == 'T')
                T[i][i] = 1;
            else
                F[i][i] = 1;
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
                    char c = A.charAt(k);
                    // check current operator
                    if (c == '|') {
                        // T[i][j] += Total[i][k - 1] * Total[k + 1][j] - F[i][k - 1] * F[k + 1][j] (all cases except both false)
                        T[i][j] = (T[i][j] + tik * tkj - F[i][k - 1] * F[k + 1][j]) % MOD;
                        // F[i][j] += F[i][k - 1] * F[k + 1][j] (both false)
                        F[i][j] = (F[i][j] + F[i][k - 1] * F[k + 1][j]) % MOD;
                    } else if (c == '&') {
                        // T[i][j] += T[i][k - 1] * T[k + 1][j] (both true)
                        T[i][j] = (T[i][j] + T[i][k - 1] * T[k + 1][j]) % MOD;
                        // F[i][j] += Total[i][k - 1] * Total[k + 1][j] - T[i][k - 1] * T[k + 1][j] (all cases except both true)
                        F[i][j] = (F[i][j] + tik * tkj - T[i][k - 1] * T[k + 1][j]) % MOD;
                    } else {
                        // T[i][j] += T[i][k - 1] * F[k + 1][j] + F[i][k - 1] * T[k + 1][j] (T ^ F = T, F ^ T = T)
                        T[i][j] = (T[i][j] + T[i][k - 1] * F[k + 1][j] + F[i][k - 1] * T[k + 1][j]) % MOD;
                        // F[i][j] += T[i][k - 1] * T[k + 1][j] + F[i][k - 1] * F[k + 1][j] (T ^ T = F, F ^ F = F)
                        F[i][j] = (F[i][j] + T[i][k - 1] * T[k + 1][j] + F[i][k - 1] * F[k + 1][j]) % MOD;
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

        // dp[k][n] = #drops required for k eggs and n floors
        int[][] dp = new int[K + 1][n + 1];
        // base-case: 1 egg problem
        for (int i = 1; i <= n; i++)
            dp[1][i] = i;

        for (int k = 2; k <= K; k++) {
            // base-case: 1 floor
            dp[k][1] = 1;

            for (int i = 2; i <= n; i++) {
                int l = 1, r = i;
                // use binary search to find the point where the difference between drops required
                // for egg breaking and egg not breaking is minimum
                while (l <= r) {
                    int mid = l + (r - l) / 2;
                    // if diff < 0
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
        if (n <= 1)
            return 0;

        // buy1 = price to buy 1st stock
        // sell1 = profit earned for 1st stock
        // buy2 = price to buy 2nd stock including profit of 1st stock
        // sell2 = profit earned after selling both stocks
        int buy1 = A[0], sell1 = 0, buy2 = Integer.MAX_VALUE, sell2 = 0;

        for (int i = 1; i < n; i++) {
            // minimize 1st stock purchase price
            buy1 = Math.min(buy1, A[i]);
            // maximize 1st stock profit
            sell1 = Math.max(sell1, A[i] - buy1);
            // minimize 2nd stock purchase price including profit earned for 1st stock
            buy2 = Math.min(buy2, A[i] - sell1);
            // maximize profit earned from both stocks
            sell2 = Math.max(sell2, A[i] - buy2);
        }

        return sell2;
    }

    // https://www.interviewbit.com/problems/longest-valid-parentheses/
    // more solutions at https://www.geeksforgeeks.org/length-of-the-longest-valid-substring/
    static int longestValidParentheses(String A) {
        int n = A.length();
        int left = 0, right = 0, res = 0;
        // move left to right
        for (int i = 0; i < n; i++) {
            if (A.charAt(i) == '(')
                left++;
            else {
                right++;
                // found valid sequence - update result
                if (left == right)
                    res = Math.max(res, 2 * left);
                    // invalid sequence - reset
                else if (right > left)
                    left = right = 0;
            }
        }

        left = right = 0;
        // move right to left
        for (int i = n - 1; i >= 0; i--) {
            if (A.charAt(i) == ')')
                right++;
            else {
                left++;
                // found valid sequence - update result
                if (left == right)
                    res = Math.max(res, 2 * left);
                    // invalid sequence - reset
                else if (left > right)
                    left = right = 0;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/max-edge-queries/
    @SuppressWarnings("unchecked")
    public static int[] solve(int[][] A, int[][] B) {
        int n = A.length + 1;
        // adjacency list for the tree
        List<int[]>[] adj = new List[n + 1];
        for (int i = 1; i <= n; i++)
            adj[i] = new LinkedList<>();
        for (int[] edge : A) {
            int u = edge[0], v = edge[1], w = edge[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
        }

        // height = #2^i levels
        int height = (int) Math.ceil(Math.log(n) / Math.log(2));
        // level[i] = level of node i
        int[] level = new int[n + 1];
        // parent[i][j] = 2^j th parent of node i
        // dp[i][j] = max edge between node i to its 2^j th parent
        int[][] parent = new int[n + 1][height + 1], dp = new int[n + 1][height + 1];

        dfs(1, 1, 0, height, adj, dp, parent, level);

        int q = B.length;
        int[] res = new int[q];
        for (int i = 0; i < q; i++) {
            int u = B[i][0], v = B[i][1];
            res[i] = getMaxDistance(u, v, dp, parent, level);
        }

        return res;
    }

    // util to compute 2^j th parent and the max edge using binary lifting
    private static void dfs(int u, int p, int w, int height, List<int[]>[] adj, int[][] dp, int[][] parent, int[] level) {
        // 1st parent
        parent[u][0] = p;
        dp[u][0] = w;

        for (int i = 1; i <= height; i++) {
            // 2^i th parent of u = 2^(i - 1)th parent of 2^(i - 1)th parent of u
            parent[u][i] = parent[parent[u][i - 1]][i - 1];
            dp[u][i] = Math.max(dp[u][i - 1], dp[parent[u][i - 1]][i - 1]);
        }

        for (int[] v : adj[u]) {
            if (v[0] != p) {
                // update level of node and recursively compute for subtree
                level[v[0]] = level[u] + 1;
                dfs(v[0], u, v[1], height, adj, dp, parent, level);
            }
        }
    }

    // util to get max edge weight between two nodes using binary lifting
    private static int getMaxDistance(int u, int v, int[][] dp, int[][] parent, int[] level) {
        if (level[u] < level[v])
            return getMaxDistance(v, u, dp, parent, level);

        int diff = level[u] - level[v], res = 0;
        // make sure both nodes come at same level by going bit by bit jumps in diff from MSB
        while (diff > 0) {
            int log = (int) (Math.log(diff) / Math.log(2));
            // update result and move up
            res = Math.max(res, dp[u][log]);
            u = parent[u][log];
            // update remaining diff
            diff -= (1 << log);
        }

        // keep moving up till lca not reached
        while (u != v) {
            int i = (int) (Math.log(level[u]) / Math.log(2));
            // keep reducing jump size by half if jumps above lca
            while (i > 0 && parent[u][i] == parent[v][i])
                i--;

            // update result and the nodes
            res = Math.max(res, Math.max(dp[u][i], dp[v][i]));
            u = parent[u][i];
            v = parent[v][i];
        }

        return res;
    }

    // https://www.interviewbit.com/problems/max-sum-path-in-binary-tree/
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
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
        // Due to the given property of row and column, the largest entry will always be in the bottom right corner and will always
        // be part of the solution. Hence, compute matrix sum for all matrices ending at (m - 1, n - 1) and find maximum sum
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
            if (row[0] == 1) {
                res = 1;
                break;
            }
        }
        // check for 1 length squares in first row
        for (int j = 1; j < n; j++) {
            if (A[0][j] == 1) {
                res = 1;
                break;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // check if current position is 0 and its top, left and diagonal neighbours form a square.
                // If yes, update the square length at current position
                if (A[i][j] == 1) {
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
        for (int i = 1; i < m && A[i][0] > A[i - 1][0]; i++)
            dp[i][0] = true;
        // check for first row
        for (int j = 1; j < n && A[0][j] > A[0][j - 1]; j++)
            dp[0][j] = true;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // check if we can arrive current cell from top or left
                boolean top = A[i][j] > A[i - 1][j] && dp[i - 1][j];
                boolean left = A[i][j] > A[i][j - 1] && dp[i][j - 1];
                dp[i][j] = top || left;
            }
        }

        // if there is a path to (m - 1, n - 1) its length will always be m + n - 1 (when only bottom and right dirs are allowed)
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
        // (upper limit sum / 2 as if subset of sum = s can be formed, we can form sum - s subset also)
        boolean[][] dp = new boolean[n + 1][sum / 2 + 1];
        // base-case: sum = 0 can be achieved always
        for (int i = 0; i <= n; i++)
            dp[i][0] = true;

        for (int i = 1; i <= n; i++) {
            // cannot use current element for sum smaller than it
            for (int s = 1; s < A[i - 1] && s <= sum / 2; s++)
                dp[i][s] = dp[i - 1][s];
            // for sum >= A[i - 1], either use current element or don't
            for (int s = A[i - 1]; s <= sum / 2; s++)
                dp[i][s] = dp[i - 1][s - A[i - 1]] || dp[i - 1][s];
        }

        // find the largest possible achievable sum close to totalSum / 2. The difference will be minimum
        int s = sum / 2;
        while (s > 0 && !dp[n][s])
            s--;

        return sum - 2 * s;
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
            // cannot use current element for sum smaller than it
            for (int b = 1; b < A[i - 1] && b <= B; b++)
                dp[i][b] = dp[i - 1][b];
            // for sum >= A[i - 1], either use current element or don't
            for (int b = A[i - 1]; b <= B; b++)
                dp[i][b] = dp[i - 1][b - A[i - 1]] || dp[i - 1][b];
        }

        return dp[n][B] ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/unique-paths-in-a-grid/
    static int uniquePathsInGrid(int[][] A) {
        int m = A.length, n = A[0].length;
        // base-case: obstacle at origin or destination
        if (A[0][0] == 1 || A[m - 1][n - 1] == 1)
            return 0;

        // use A[i][j] as dp[i][j] where dp[i][j] = #ways to reach (i, j)
        int i = 0;
        // for first col, mark #ways = 1 for each cell till obstacle is not encountered
        for (; i < m && A[i][0] == 0; i++)
            A[i][0] = 1;
        // for all cells after obstacle #ways = 0
        for (; i < m; i++)
            A[i][0] = 0;

        int j = 1;
        // for first row, mark #ways = 1 for each cell till obstacle is not encountered
        for (; j < n && A[0][j] == 0; j++)
            A[0][j] = 1;
        // for all cells after obstacle #ways = 0
        for (; j < n; j++)
            A[0][j] = 0;

        for (i = 1; i < m; i++) {
            for (j = 1; j < n; j++) {
                // if no obstacle at current cell, #ways = #ways from top + #ways from left
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
        A[m - 1][n - 1] = Math.min(0, A[m - 1][n - 1]);
        // move up in the last column
        for (int i = m - 2; i >= 0; i--)
            A[i][n - 1] = Math.min(0, Math.min(A[i][n - 1], A[i][n - 1] + A[i + 1][n - 1]));
        // move up int the last row
        for (int j = n - 2; j >= 0; j--)
            A[m - 1][j] = Math.min(0, Math.min(A[m - 1][j], A[m - 1][j] + A[m - 1][j + 1]));

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                // find the best path with max hit-points gained or min hit-points lost
                int bestPath = Math.max(A[i + 1][j], A[i][j + 1]);
                A[i][j] = Math.min(0, Math.min(A[i][j], A[i][j] + bestPath));
            }
        }

        // we need at least 1 hit-point to survive
        return Math.abs(A[0][0]) + 1;
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
        int res = maxAreaInHistogram(A[0]);

        for (int i = 1; i < m; i++) {
            // update height of the bars with previous row
            for (int j = 0; j < n; j++) {
                if (A[i][j] != 0)
                    A[i][j] += A[i - 1][j];
            }
            // update result with max area in histogram of current row
            res = Math.max(res, maxAreaInHistogram(A[i]));
        }

        return res;
    }

    // util to find max area in histogram
    private static int maxAreaInHistogram(int[] A) {
        int n = A.length;
        int maxArea = 0;
        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            // keep popping from stack till current bar is smaller than the stack top and update area
            while (!s.empty() && A[i] < A[s.peek()]) {
                int top = s.pop();
                int areaWithTop = A[top] * (s.empty() ? i : i - s.peek() - 1);
                maxArea = Math.max(maxArea, areaWithTop);
            }
            // push current bar to stack
            s.push(i);
        }

        // calculate area with s.top() as height
        while (!s.empty()) {
            int top = s.pop();
            int areaWithTop = A[top] * (s.empty() ? n : n - s.peek() - 1);
            maxArea = Math.max(maxArea, areaWithTop);
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
    private static final int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

    static int[][] queenAttack(String[] A) {
        int m = A.length, n = A[0].length();
        int[][] res = new int[m][n];
        // dp[i][j] = #queens attacking at (i, j) when considered direction = k
        int[][] dp = new int[m][n];
        // for each direction, traverse each cell and update the result count
        for (int k = 0; k < 8; k++) {
            // reset the dp array
            for (int[] row : dp)
                Arrays.fill(row, -1);
            // for each point
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++)
                    res[i][j] += queenAttackUtil(i, j, k, A, dp);
            }
        }

        return res;
    }

    // util to calculate how many queens attack at (i, j) considering only direction = k
    private static int queenAttackUtil(int i, int j, int k, String[] A, int[][] dp) {
        // memoization
        if (dp[i][j] != -1)
            return dp[i][j];

        int m = A.length, n = A[0].length();
        // move in direction = k
        int x = i + dir[k][0], y = j + dir[k][1];
        // if the cell exists
        if (isValid(x, y, m, n)) {
            // if there is queen in neighbouring cell, only one queen can attack current cell
            if (A[x].charAt(y) == '1')
                return dp[i][j] = 1;
            // else recursively find how many queens are attacking the neighbouring cell from direction = k
            return dp[i][j] = queenAttackUtil(x, y, k, A, dp);
        }
        // neighbour cell does not exist
        return dp[i][j] = 0;
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
            // if found prefix array with sum = current sum, add count of all sub-arrays that can be formed in between having sum = 0
            if (map.containsKey(sum))
                res += map.get(sum);
            // update the sum map count
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/coin-sum-infinite/
    static int coinSumInfinite(int[] A, int B) {
        // can also be done using 2D dp and optimized with dp[2][B + 1]
        int MOD = 1000007;
        // dp[i] = #ways sum = i can be obtained
        int[] dp = new int[B + 1];
        // base case: required sum = 0
        dp[0] = 1;

        for (int val : A) {
            // for each sum higher than current coin, select current coin at least once or don't
            for (int w = val; w <= B; w++)
                dp[w] = (dp[w - val] + dp[w]) % MOD;
        }

        return dp[B];
    }

    // https://www.interviewbit.com/problems/max-product-subarray/
    static int maxProductSubarray(final int[] A) {
        // keep track of max and min product along with result
        int maxProd = A[0], minProd = A[0], res = A[0];

        for (int i = 1; i < A.length; i++) {
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
        int n = A.length;
        // empty array
        if (n == 0)
            return 0;

        int minPrice = A[0], profit = 0;
        // for each day after 1st day
        for (int i = 1; i < n; i++) {
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
    static int arrange2(String A, int B) {
        int n = A.length();
        // #stables > #horses
        if (B > n)
            return -1;
        // #stables = #horses
        if (B == n)
            return 0;

        // dp[i][k] = cost for horses from [0, i] with k stables
        int[][] dp = new int[n][B + 1];
        int white = 0, black = 0;
        // for k = 1, put all horses in the same stable
        for (int i = 0; i < n; i++) {
            if (A.charAt(i) == 'W')
                white++;
            else
                black++;

            dp[i][1] = white * black;
        }

        for (int k = 2; k <= B; k++) {
            // #stables > #horses - use INT_MAX / 2 to avoid overflow
            for (int i = 0; i + 1 < k && i < n; i++)
                dp[i][k] = Integer.MAX_VALUE / 2;
            // #stables = #horses - dp[k][k] = 0
            // for remaining cases
            for (int i = k; i < n; i++) {
                dp[i][k] = Integer.MAX_VALUE / 2;
                white = black = 0;
                // for each horse j in middle, try putting [0, j] horses in k - 1 stables and [j, i] horses in the kth stable
                for (int j = i; j > 0; j--) {
                    if (A.charAt(j) == 'W')
                        white++;
                    else
                        black++;

                    dp[i][k] = Math.min(dp[i][k], dp[j - 1][k - 1] + white * black);
                }
            }
        }

        return dp[n - 1][B];
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
                // if we can expand current subsequence at A[j], expand it and optimize
                if (A[j][1] < A[i][0])
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
        // for each col, either include max of current col in the sum or don't
        for (int i = 2; i < n; i++)
            dp[i] = Math.max(Math.max(A[0][i], A[1][i]) + dp[i - 2], dp[i - 1]);

        return dp[n - 1];
    }

    // https://www.interviewbit.com/problems/merge-elements/
    static int mergeElements(int[] A) {
        int n = A.length;
        // use prefix sum to compute sub-array sum in O(1) time
        int[] prefixSum = new int[n + 1];
        for (int i = 1; i < n; i++)
            prefixSum[i] = prefixSum[i - 1] + A[i - 1];

        // dp[i][j] = cost for merging the sub-array from [i, j]
        int[][] dp = new int[n][n];
        // base-case: cost of merging 1 element is 0 and for 2 elements is the sum
        for (int i = 1; i < n; i++)
            dp[i - 1][i] = A[i - 1] + A[i];

        // for len >= 3 sub-arrays
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                // find the cut where cost would be minimized
                for (int k = i; k < j; k++) {
                    // cost = cost of [i, k] + cost of [k + 1, j] + sub-array sum of [i, j]
                    int cost = dp[i][k] + dp[k + 1][j] + prefixSum[j + 1] - prefixSum[i];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n - 1];
    }

    // https://www.interviewbit.com/problems/flip-array/
    static int flipArray(final int[] A) {
        int n = A.length, S = 0;
        // find max sum possible
        for (int val : A)
            S += val;

        // try dividing the array into 2 subsets and find min #elements required in each subset sum
        // dp[i][sum] = min #elements in subset till [0, i] that can add up to sum
        int[][] dp = new int[n + 1][S / 2 + 1];
        // n = 0, sum > 0 not possible (take upper bound of input range)
        for (int sum = 1; sum <= S / 2; sum++)
            dp[0][sum] = 101;

        for (int i = 1; i <= n; i++) {
            // cannot use current element for sum smaller than it
            for (int sum = 1; sum <= S / 2 && sum < A[i - 1]; sum++)
                dp[i][sum] = dp[i - 1][sum];
            // for sum >= A[i - 1], either use current element or don't
            for (int sum = A[i - 1]; sum <= S / 2; sum++)
                dp[i][sum] = Math.min(1 + dp[i - 1][sum - A[i - 1]], dp[i - 1][sum]);
        }

        // find the largest possible achievable sum close to totalSum / 2. Sign of all the elements in that subset must be flipped
        int sum = S / 2;
        while (sum > 0 && dp[n][sum] > n)
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
        // base case: no dishes available cannot fill eating capacity
        // use Integer.MAX_VALUE / 2 to avoid overflow
        for (int j = 1; j <= maxCapacity; j++)
            dp[0][j] = Integer.MAX_VALUE / 2;

        for (int i = 1; i <= n; i++) {
            // cannot eat dishes with weight more than current capacity
            for (int w = 1; w <= maxCapacity && w < B[i - 1]; w++)
                dp[i][w] = dp[i - 1][w];
            // for dishes with weight less than current capacity, either eat at least one or don't
            for (int w = B[i - 1]; w <= maxCapacity; w++)
                dp[i][w] = Math.min(C[i - 1] + dp[i][w - B[i - 1]], dp[i - 1][w]);
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
        int[][] dp = new int[n + 1][C + 1];
        // dp[i][0] = 0, dp[0][j] = 0
        for (int i = 1; i <= n; i++) {
            // cannot carry objects with weight more than capacity
            for (int w = 1; w <= C && w < B[i - 1]; w++)
                dp[i][w] = dp[i - 1][w];
            // for capacity more than weight of current item, either carry it or don't
            for (int w = B[i - 1]; w <= C; w++)
                dp[i][w] = Math.max(A[i - 1] + dp[i - 1][w - B[i - 1]], dp[i - 1][w]);
        }

        return dp[n][C];
    }

    // https://www.interviewbit.com/problems/equal-average-partition/
    static int[][] equalAveragePartition(int[] A) {
        int n = A.length, totalSum = 0;
        for (int val : A)
            totalSum += val;

        // dp[i][sum][len] = T/F for whether sum can be achieved with len numbers from A[i] to A[n - 1]
        boolean[][][] dp = new boolean[n][totalSum + 1][n / 2 + 1];
        for (boolean[][] a : dp) {
            for (boolean[] b : a)
                Arrays.fill(b, true);
        }

        // sort for lexicographical order
        Arrays.sort(A);
        // try all length subset-1
        for (int len = 1; len <= n / 2; len++) {
            // sum(x) / x = (totalSum - sum(x)) / (n - x) simplifies to sum(x) = totalSum * x / n
            // check if sum(x) is a non-negative integer
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
        // memoization (cannot form subset)
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
            // if price is higher today, sell e.g 1, 2, 9, 3 then 9 - 1 = 9 - 2 + 2 - 1
            if (A[i] > A[i - 1])
                profit += A[i] - A[i - 1];
        }

        return profit;
    }

    // https://www.interviewbit.com/problems/word-break-ii/
    private static ArrayList<String>[] dp;

    @SuppressWarnings("unchecked")
    static ArrayList<String> wordBreak2(String A, String[] B) {
        // create trie of all words in dictionary
        Trie trie = new Trie();
        for (String word : B)
            trie.add(word);

        // dp[i] = all strings that can be formed by breaking A.substring(i) into words
        dp = new ArrayList[A.length()];
        // recursively compute result for whole string
        return wordBreak2Util(A, 0, trie.root);
    }

    // util to form all possible word combinations of string A
    private static ArrayList<String> wordBreak2Util(String A, int start, TrieNode root) {
        // memoization
        if (dp[start] != null)
            return dp[start];

        dp[start] = new ArrayList<>();

        int n = A.length();
        TrieNode curr = root;
        StringBuilder builder = new StringBuilder();
        // keep matching prefix in trie
        for (int i = start; i < n; i++) {
            char c = A.charAt(i);
            // if prefix is not a word, no more solutions possible
            if (curr.child[c - 'a'] == null)
                return dp[start];

            curr = curr.child[c - 'a'];
            builder.append(c);
            // if current prefix is a word, append all combinations of the suffix by recursively calling the util
            if (curr.isEnd) {
                // this is the last word
                if (i + 1 == n) {
                    dp[start].add(builder.toString());
                    return dp[start];
                }
                // append all combinations of the suffix with ' '
                for (String s : wordBreak2Util(A, i + 1, root))
                    dp[start].add(builder.toString() + ' ' + s);
            }
        }

        return dp[start];
    }

    static class Trie {
        private final TrieNode root;

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

    static class TrieNode {
        TrieNode[] child;
        boolean isEnd;

        TrieNode() {
            child = new TrieNode[26];
        }
    }

    // https://www.interviewbit.com/problems/unique-binary-search-trees-ii/
    static int uniqueBST2(int A) {
        // base-case
        if (A == 1)
            return 1;

        // dp[i] = #BST formed with i nodes
        int[] dp = new int[A + 1];
        dp[0] = dp[1] = 1;
        // compute #BST formed with i nodes
        for (int i = 2; i <= A; i++) {
            // for each node as root
            for (int j = 1; j <= i; j++)
                dp[i] += dp[j - 1] * dp[i - j];
        }

        return dp[A];
    }

    // https://www.interviewbit.com/problems/count-permutations-of-bst/
    static int countPermutationsBST(int A, int B) {
        // need at least B + 1 nodes to achieve height B
        if (A <= B)
            return 0;
        // 1 node tree has height = 0
        if (A == 1 && B == 0)
            return 1;

        // compute and memoize combinations
        long[][] C = computeCombinations(A - 1);
        final int MOD = 1_000_000_007;
        // dp[i][j] = #permutations of i nodes with height = j
        long[][] dp = new long[A + 1][B + 1];
        dp[0][0] = dp[1][0] = 1;
        // presum[i] = #permutations of i nodes with heights computed till now
        long[] presum = new long[A + 1];
        presum[0] = presum[1] = 1;

        // iterate for height = 1 to B
        for (int height = 1; height <= B; height++) {
            // iterate for height + 1 to A node permutations
            for (int i = height + 1; i <= A; i++) {
                // try each node in the range as root
                for (int root = 1; root <= i; root++) {
                    int x = root - 1, y = i - root;
                    // sum1 = sum of h = 1 to height - 2 permutations of size = x
                    long sum1 = presum[x] - dp[x][height] - dp[x][height - 1];
                    // sum2 = sum of h = 1 to height - 2 permutations of size = y
                    long sum2 = presum[y] - dp[y][height] - dp[y][height - 1];
                    // cnt = cnt + #trees with left half of h = 1 to height - 2 and right half of h = height - 1
                    long cnt = (sum1 * dp[y][height - 1]) % MOD;
                    // cnt = cnt + #trees with left half of h = height - 1 and right half of h = 1 to height - 2
                    cnt = (cnt + sum2 * dp[x][height - 1]) % MOD;
                    // cnt = cnt + #trees with left half of h = height - 1 and right half of h = height - 1
                    cnt = (cnt + dp[x][height - 1] * dp[y][height - 1]) % MOD;
                    // cnt = cnt * #ways to choose sequence such that mutual ordering of both permutations is maintained
                    cnt = (cnt * C[x + y][y]) % MOD;

                    dp[i][height] = (dp[i][height] + cnt) % MOD;
                }
                // update presum array
                presum[i] = (presum[i] + dp[i][height]) % MOD;
            }
        }

        return (int) dp[A][B];
    }

    // util to compute and memoize all combinations
    private static long[][] computeCombinations(int N) {
        long[][] C = new long[N + 1][];
        final int MOD = 1_000_000_007;

        for (int n = 0; n <= N; n++) {
            C[n] = new long[n + 1];
            // nC0 = nCn = 1
            C[n][0] = C[n][n] = 1;
            // nCk = (n-1)C(k) + (n-1)C(k-1)
            for (int k = 1; k < n; k++)
                C[n][k] = (C[n - 1][k] + C[n - 1][k - 1]) % MOD;
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

        return palindromePartitioning2Util(n, 0, isPalindrome, dp);
    }

    // util to compute palindrome check for all substrings
    private static boolean[][] computePalindromes(String A) {
        int n = A.length();
        boolean[][] isPalindrome = new boolean[n][n];
        isPalindrome[0][0] = true;
        // len = 1 is always palindrome and compute for len = 2
        for (int i = 1; i < n; i++) {
            isPalindrome[i][i] = true;
            isPalindrome[i - 1][i] = A.charAt(i - 1) == A.charAt(i);
        }

        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                // end characters should match and middle half must be palindrome
                isPalindrome[i][j] = A.charAt(i) == A.charAt(j) && isPalindrome[i + 1][j - 1];
            }
        }

        return isPalindrome;
    }

    // util to recursively compute min cuts
    private static int palindromePartitioning2Util(int n, int start, boolean[][] isPalindrome, int[] dp) {
        // if memoized
        if (dp[start] != n + 1)
            return dp[start];
        // if palindrome, no cuts required
        if (isPalindrome[start][n - 1])
            return dp[start] = 0;

        // max cuts required = cuts at all pos = length
        int minCuts = n - start + 1;
        for (int i = start; i < n; i++) {
            // if prefix is palindrome, optimize cuts as 1 + cuts for the suffix
            if (isPalindrome[start][i])
                minCuts = Math.min(minCuts, 1 + palindromePartitioning2Util(n, i + 1, isPalindrome, dp));
        }

        return dp[start] = minCuts;
    }

    // https://www.interviewbit.com/problems/word-break/
    static int wordBreak(String A, String[] B) {
        // create trie of all words in dictionary
        Trie trie = new Trie();
        for (String word : B)
            trie.add(word);

        int n = A.length();
        // dp[i] = true/false is A.substring(i) can be formed from dictionary
        Boolean[] dp = new Boolean[n];

        // recursively check if string can be divided into words of dictionary
        return wordBreakUtil(A, 0, trie.root, dp) ? 1 : 0;
    }

    // util to check if string A can be divided into words of dictionary
    private static boolean wordBreakUtil(String A, int start, TrieNode root, Boolean[] dp) {
        // memoization
        if (dp[start] != null)
            return dp[start];

        int n = A.length();
        TrieNode curr = root;
        // keep searching for current string in trie
        for (int i = start; i < n; i++) {
            char c = A.charAt(i);
            // if prefix so far not found, string cannot be divided into words
            if (curr.child[c - 'a'] == null)
                return dp[start] = false;

            curr = curr.child[c - 'a'];
            // if a match is found, recursively check for the remaining half if present
            if (curr.isEnd && (i + 1 == n || wordBreakUtil(A, i + 1, root, dp)))
                return dp[start] = true;
        }

        return dp[start] = false;
    }

    // https://www.interviewbit.com/problems/potions/
    static int minSmoke(int[] A) {
        int n = A.length;
        // only one element
        if (n == 1)
            return 0;

        // use prefix sum to compute sub-array sum in O(1) time
        int[] prefixSum = new int[n + 1];
        for (int i = 1; i <= n; i++)
            prefixSum[i] = prefixSum[i - 1] + A[i - 1];

        // dp[i][j] = cost for mixing the sub-array from [i, j]
        int[][] dp = new int[n][n];
        // base-case: cost of mixing 1 color is 0 and for 2 colors is the product
        for (int i = 1; i < n; i++)
            dp[i - 1][i] = A[i - 1] * A[i];

        // for len >= 3 sub-arrays
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                // find the cut where cost would be minimized
                for (int k = i; k < j; k++) {
                    // color formed after merging [i, k]
                    int a = (prefixSum[k + 1] - prefixSum[i]) % 100;
                    // color formed after merging [k + 1, j]
                    int b = (prefixSum[j + 1] - prefixSum[k + 1]) % 100;

                    // condition to handle wrong input where 100 is given as color in array
                    if (k == i && A[i] == 100)
                        a = 100;
                    else if (k == j - 1 && A[j] == 100)
                        b = 100;

                    // cost = cost of [i, k] + cost of [k + 1, j] + product of [i, k] and [k + 1, j]
                    int cost = dp[i][k] + dp[k + 1][j] + a * b;
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n - 1];
    }

    // https://www.interviewbit.com/problems/dice-throw/
    static int findDiceSum(int A, int B, int C) {
        int MOD = 1_000_000_007;
        int[][] dp = new int[A + 1][C + 1];
        // if you do not have any data, then the value must be 0, so the result is 1
        dp[0][0] = 1;
        // iterate over dices
        for (int i = 1; i <= A; i++) {
            // iterate over sum
            for (int j = 1; j <= C; j++) {
                // The result is obtained in two ways, pin the current dice and spending 1 of the value,
                // so we have mem[i-1][j-1] remaining combinations, to find the remaining combinations we
                // would have to pin the values ??above 1 then we use mem[i][j-1] to sum all combinations
                // that pin the remaining j-1's. But there is a way, when "j-f-1> = 0" we would be adding
                // extra combinations, so we remove the combinations that only pin the extrapolated dice face and
                // subtract the extrapolated combinations.
                dp[i][j] = (dp[i][j - 1] + dp[i - 1][j - 1]) % MOD;
                if (j - B - 1 >= 0)
                    dp[i][j] = (dp[i][j] - dp[i - 1][j - B - 1] + MOD) % MOD;
            }
        }

        return dp[A][C];
    }

    static int findDiceSum2(int A, int B, int C) {
        // min sum = 1 on all dices, max sum = B on all dices
        if (C < A || C > A * B)
            return 0;
        // base-cases
        if (C == A || C == A * B)
            return 1;

        int MOD = 1_000_000_007;
        // dp[i][j] = #ways to achieve sum = j with i dices
        int[][] dp = new int[A + 1][C + 1];
        // for only one dice sum = 1 to min(B, C) is possible
        for (int j = 1; j <= B && j <= C; j++)
            dp[1][j] = 1;

        for (int i = 2; i <= A; i++) {
            for (int j = 1; j <= C; j++) {
                // try for all possible outcomes on the ith dice
                for (int k = 1; k <= B && k < j; k++)
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % MOD;
            }
        }

        return dp[A][C];
    }

    // https://www.interviewbit.com/problems/unique-binary-search-trees/
    @SuppressWarnings("unchecked")
    static ArrayList<TreeNode> generateTrees(int A) {
        if (A == 1) {
            ArrayList<TreeNode> res = new ArrayList<>();
            res.add(new TreeNode(1));
            return res;
        }

        // dp[i][i] = BSTs generated with nodes numbered from i to i
        ArrayList<TreeNode>[][] dp = new ArrayList[A + 1][A + 1];
        // base-case: only one node
        for (int i = 1; i <= A; i++) {
            dp[i][i] = new ArrayList<>();
            dp[i][i].add(new TreeNode(i));
        }

        return generateTreesUtil(1, A, dp);
    }

    // util to generate all BSTs with nodes from start to end
    private static ArrayList<TreeNode> generateTreesUtil(int start, int end, ArrayList<TreeNode>[][] dp) {
        // invalid range
        if (start > end) {
            ArrayList<TreeNode> res = new ArrayList<>();
            res.add(null);
            return res;
        }
        // memoization
        if (dp[start][end] != null)
            return dp[start][end];

        dp[start][end] = new ArrayList<>();
        // for each node as root
        for (int i = start; i <= end; i++) {
            // compute all BSTs for left and right half
            ArrayList<TreeNode> leftList = generateTreesUtil(start, i - 1, dp);
            ArrayList<TreeNode> rightList = generateTreesUtil(i + 1, end, dp);
            // add each combination of left and right subtree to result
            for (TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    dp[start][end].add(root);
                }
            }
        }

        return dp[start][end];
    }
}