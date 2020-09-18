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
}