import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Backtracking {
    // https://www.interviewbit.com/problems/reverse-link-list-recursion
    static LinkedLists.ListNode reverseList(LinkedLists.ListNode A) {
        // 0 or 1 node in linked list
        if (A == null || A.next == null)
            return A;

        // reverse the sublist starting from the next node and save its head
        LinkedLists.ListNode temp = reverseList(A.next);

        // make current node's next link to current node
        A.next.next = A;
        // remove old link of current node to its next
        A.next = null;

        // return the head of the reversed sublist
        return temp;
    }

    // https://www.interviewbit.com/problems/modular-expression/
    static int Mod(int A, int B, int C) {
        // 0 ^ x = 0
        if (A == 0)
            return 0;

        // a ^ 0 = 1
        if (B == 0)
            return 1;

        // y = A * A
        int y = (int) (((long) A * (long) A) % C);
        long res;

        // if B is even, res = Mod(A * A, B / 2, C)
        if (B % 2 == 0)
            res = Mod(y, B / 2, C);
            // else res = A * Mod(A * A, B / 2, C)
        else
            res = ((long) A * (long) Mod(y, B / 2, C)) % C;

        // if res is negative, add C and take mod C to make it positive
        return (int) ((res + C) % C);
    }

    // https://www.interviewbit.com/problems/subset/
    static ArrayList<ArrayList<Integer>> subsets(ArrayList<Integer> A) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        // sort the list to set subsets in lexicographic order
        Collections.sort(A);
        // recursively find subsets with or without current element
        subsetUtil(A, 0, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all subsets
    private static void subsetUtil(ArrayList<Integer> A, int pos, ArrayList<Integer> subset,
                                   ArrayList<ArrayList<Integer>> res) {
        // add current subset to output
        res.add(new ArrayList<>(subset));

        // if all elements have been checked
        if (pos == A.size())
            return;

        // for all elements remaining
        for (int i = pos; i < A.size(); i++) {
            // add current element and find more subsets containing it
            subset.add(A.get(i));
            subsetUtil(A, i + 1, subset, res);
            // remove current element to find more subsets without it
            subset.remove(subset.size() - 1);
        }
    }

    // https://www.interviewbit.com/problems/combinations/
    static ArrayList<ArrayList<Integer>> combinations(int n, int k) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        // recursively find all the combinations with and without every element
        combinationsUtil(n, k, 1, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all combinations
    private static void combinationsUtil(int n, int k, int pos, ArrayList<Integer> curr,
                                         ArrayList<ArrayList<Integer>> res) {
        // if all k places are filled, add current combination to output
        if (k == 0) {
            res.add(new ArrayList<>(curr));
            return;
        }

        // for each number from current position till end
        for (int i = pos; i <= n; i++) {
            // add current number to combination and complete the current combination with remaining ones
            curr.add(i);
            combinationsUtil(n, k - 1, i + 1, curr, res);
            // remove current number to find more combinations without it
            curr.remove(curr.size() - 1);
        }
    }

    // https://www.interviewbit.com/problems/combination-sum/
    static ArrayList<ArrayList<Integer>> combinationSum(ArrayList<Integer> A, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        // sort the list for combinations in non-decreasing order
        Collections.sort(A);
        // recursively find all the combinations with sum = target
        combinationSumUtil(A, 0, target, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all combinations with sum equal target from the start
    private static void combinationSumUtil(ArrayList<Integer> A, int pos, int target,
                                           ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> res) {
        // found target sum combination
        if (target == 0) {
            res.add(new ArrayList<>(curr));
            return;
        }

        // current sum exceeds target, cannot be a valid combination
        if (target < 0)
            return;

        int i = pos;
        // try all elements one by one
        while (i < A.size()) {
            curr.add(A.get(i));
            // find combinations with target sum including the current element
            combinationSumUtil(A, i, target - A.get(i), curr, res);
            // remove current element to try another element in the next iteration
            curr.remove(curr.size() - 1);

            i++;
            // skip duplicates in the candidate set
            while (i < A.size() && A.get(i).equals(A.get(i - 1)))
                i++;
        }
    }

    // https://www.interviewbit.com/problems/combination-sum-ii/
    static ArrayList<ArrayList<Integer>> combinationSum2(ArrayList<Integer> A, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        // sort the list for combinations in non-decreasing order
        Collections.sort(A);
        // recursively find all the combinations with sum = target
        combinationSumUtil2(A, 0, target, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all combinations with sum equal target from the start
    private static void combinationSumUtil2(ArrayList<Integer> A, int pos, int target,
                                            ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> res) {
        // found target sum combination
        if (target == 0) {
            res.add(new ArrayList<>(curr));
            return;
        }

        // current sum exceeds target, cannot be a valid combination
        if (target < 0)
            return;

        int i = pos;
        // try all elements one by one
        while (i < A.size()) {
            curr.add(A.get(i));
            // find combinations with target sum containing the current element once
            combinationSumUtil2(A, i + 1, target - A.get(i), curr, res);
            // remove current element to try another element in the next iteration
            curr.remove(curr.size() - 1);

            i++;
            // skip duplicates in the candidate set
            while (i < A.size() && A.get(i).equals(A.get(i - 1)))
                i++;
        }
    }

    // https://www.interviewbit.com/problems/subsets-ii/
    static ArrayList<ArrayList<Integer>> subsets2(ArrayList<Integer> A) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        // sort the list to set subsets in lexicographic order
        Collections.sort(A);
        // recursively find subsets with or without current element
        subsetUtil2(A, 0, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all subsets
    private static void subsetUtil2(ArrayList<Integer> A, int pos, ArrayList<Integer> subset,
                                    ArrayList<ArrayList<Integer>> res) {
        // add current subset to output
        res.add(new ArrayList<>(subset));

        // if all elements have been checked
        if (pos == A.size())
            return;

        int i = pos;
        // for all elements remaining
        while (i < A.size()) {
            // add current element and find more subsets containing it
            subset.add(A.get(i));
            subsetUtil2(A, i + 1, subset, res);
            // remove current element to find more subsets without it
            subset.remove(subset.size() - 1);

            i++;
            // skip duplicate elements in the given set
            while (i < A.size() && A.get(i).equals(A.get(i - 1)))
                i++;
        }
    }

    // https://www.interviewbit.com/problems/letter-phone/
    private static final char[][] digitMap = {
            {'0'},
            {'1'},
            {'a', 'b', 'c'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'},
            {'j', 'k', 'l'},
            {'m', 'n', 'o'},
            {'p', 'q', 'r', 's'},
            {'t', 'u', 'v'},
            {'w', 'x', 'y', 'z'}
    };

    static ArrayList<String> letterPhone(String A) {
        ArrayList<String> res = new ArrayList<>();

        // try all possible characters mapped to each number
        letterPhoneUtil(A, 0, new StringBuilder(), res);

        return res;
    }

    // recursive util to try all characters mapped to a number on phone
    private static void letterPhoneUtil(String A, int pos, StringBuilder builder, ArrayList<String> res) {
        // completed a combination. Add to result
        if (pos == A.length()) {
            res.add(builder.toString());
            return;
        }

        // try each character mapped to current number.
        // Find all combinations with it and then remove it to try next character.
        for (char c : digitMap[A.charAt(pos) - '0']) {
            builder.append(c);
            letterPhoneUtil(A, pos + 1, new StringBuilder(builder), res);
            builder.setLength(builder.length() - 1);
        }
    }

    // https://www.interviewbit.com/problems/palindrome-partitioning/
    private static int[][] dp;

    @SuppressWarnings("unchecked")
    static ArrayList<ArrayList<String>> palindromicPartitioning(String A) {
        int n = A.length();
        // dp[i][j] = 1, if A.substring(i, j+1) is palindrome else 0. -1 if not computed
        dp = new int[n][n];
        for (int[] arr : dp)
            Arrays.fill(arr, -1);

        ArrayList<ArrayList<String>>[] res = new ArrayList[n + 1];
        // palindromic partition for empty list
        res[n] = new ArrayList<>();
        res[n].add(new ArrayList<>());

        // compute palindromic partitions from the right
        for (int i = n - 1; i >= 0; i--) {
            res[i] = new ArrayList<>();

            // for all substrings starting from i
            for (int j = i; j < n; j++) {
                // if this substring is palindrome
                if (isPalindrome(A, i, j)) {
                    String str = A.substring(i, j + 1);

                    // the palindromic partitions for this substring will be
                    // all the palindromic partitions of the substrings starting from j + 1
                    // with the current substring in all the partitions
                    for (ArrayList<String> list : res[j + 1]) {
                        ArrayList<String> temp = new ArrayList<>();
                        temp.add(str);
                        temp.addAll(list);

                        res[i].add(temp);
                    }
                }
            }
        }

        // res[0] will contain palindromic partitions for the whole string
        return res[0];
    }

    // util to check if A.substring(start, end+1) is palindrome or not with memoization
    private static boolean isPalindrome(String A, int start, int end) {
        int l = start, r = end;

        // move two opposite pointers towards each other
        while (l <= r) {
            // if A.substring(l, r+1) is already calculated
            if (dp[l][r] != -1) {
                // dp[start][end] will be the same
                dp[start][end] = dp[l][r];
                return dp[l][r] == 1;
            }

            // if characters match, move one step
            if (A.charAt(l) == A.charAt(r)) {
                l++;
                r--;
            }
            // else mark A.substring(start, end+1) as not palindrome and return
            else {
                dp[start][end] = 0;
                return false;
            }
        }

        // mark A.substring(start, end+1) as palindrome and return
        dp[start][end] = 1;
        return true;
    }

    // palindromic partition using back tracking
    /*static ArrayList<ArrayList<String>> palindromicPartitioning(String A) {
        // dp[i][j] = 1, if A.substring(i, j+1) is palindrome else 0. -1 if not computed
        dp = new int[A.length()][A.length()];
        for (int[] arr : dp)
            Arrays.fill(arr, -1);

        ArrayList<ArrayList<String>> res = new ArrayList<>();
        // find each palindromic partition using recursion
        partitionUtil(A, 0, new ArrayList<>(), res);

        return res;
    }

    // recursive util to try all substring starting from pos as first string in a partition
    private static void partitionUtil(String A, int pos, ArrayList<String> curr, ArrayList<ArrayList<String>> res) {
        // reached end, add current partition to result
        if (pos == A.length()) {
            res.add(curr);
            return;
        }

        // for each substring starting from pos
        for (int i = pos; i < A.length(); i++) {
            // if the substring is palindrome
            if (isPalindrome(A, pos, i)) {
                // add to current partition and recur to find the remaining elements in partition
                curr.add(A.substring(pos, i + 1));
                partitionUtil(A, i + 1, new ArrayList<>(curr), res);
                // remove from current partition
                curr.remove(curr.size() - 1);
            }
        }
    }*/

    // https://www.interviewbit.com/problems/generate-all-parentheses-ii/
    static ArrayList<String> generateParenthesis2(int n) {
        ArrayList<String> res = new ArrayList<>();

        // call recursive util to generate all parentheses combinations for n > 0
        if (n > 0)
            generateParenthesis2Util(n - 1, n, new StringBuilder("("), res);

        return res;
    }

    // recursive util to generate all parentheses combinations
    private static void generateParenthesis2Util(int open, int close, StringBuilder builder, ArrayList<String> res) {
        // a valid combination has been generated
        if (open == 0 && close == 0) {
            res.add(builder.toString());
            return;
        }

        // can add more open parenthesis
        if (open != 0) {
            builder.append("(");
            generateParenthesis2Util(open - 1, close, new StringBuilder(builder), res);
            builder.setLength(builder.length() - 1);
        }

        // can add close parenthesis
        if (open < close) {
            builder.append(")");
            generateParenthesis2Util(open, close - 1, new StringBuilder(builder), res);
            builder.setLength(builder.length() - 1);
        }
    }
}