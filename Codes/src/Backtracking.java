import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        int y = (int) (((long) A * A) % C);
        long res;

        // if B is even, res = Mod(A * A, B / 2, C)
        if (B % 2 == 0)
            res = Mod(y, B / 2, C);
            // else res = A * Mod(A * A, B / 2, C)
        else
            res = ((long) A * Mod(y, B / 2, C)) % C;

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

        // for all elements remaining
        for (int i = pos; i < A.size(); i++) {
            // add current element and find more subsets containing it
            subset.add(A.get(i));
            subsetUtil(A, i + 1, subset, res);
            // remove current element to find more subsets without it
            subset.remove(subset.size() - 1);
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
            letterPhoneUtil(A, pos + 1, builder, res);
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
                return dp[start][end] == 1;
            }

            // if characters match, move one step
            if (A.charAt(l) == A.charAt(r)) {
                l++;
                r--;
            }
            // else mark A.substring(start, end+1) as not palindrome and return
            else {
                dp[start][end] = dp[l][r] = 0;
                return false;
            }
        }

        // mark A.substring(start, end+1) as palindrome and return
        dp[start][end] = 1;
        return true;
    }

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
        if (open > 0) {
            builder.append("(");
            generateParenthesis2Util(open - 1, close, builder, res);
            builder.setLength(builder.length() - 1);
        }
        // can add close parenthesis
        if (open < close) {
            builder.append(")");
            generateParenthesis2Util(open, close - 1, builder, res);
            builder.setLength(builder.length() - 1);
        }
    }

    // https://www.interviewbit.com/problems/permutations/
    static ArrayList<ArrayList<Integer>> permutations(ArrayList<Integer> A) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // recursively find all permutations by selecting current position element from remaining one by one
        permutationsUtil(A, 0, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all permutations
    private static void permutationsUtil(ArrayList<Integer> A, int pos, ArrayList<Integer> curr,
                                         ArrayList<ArrayList<Integer>> res) {
        // made a valid permutation
        if (pos == A.size()) {
            res.add(new ArrayList<>(curr));
            return;
        }

        // for all elements remaining
        for (int i = pos; i < A.size(); i++) {
            // mark current element as used by swapping it with current beginning element
            swap(A, i, pos);
            curr.add(A.get(pos));

            // find permutations for remaining positions recursively
            permutationsUtil(A, pos + 1, curr, res);

            // mark current element as unused by bringing it back to its original position
            swap(A, pos, i);
            curr.remove(curr.size() - 1);
        }
    }

    // util to swap numbers at two positions in a list
    private static void swap(ArrayList<Integer> A, int i, int j) {
        int temp = A.get(i);
        A.set(i, A.get(j));
        A.set(j, temp);
    }

    // https://www.interviewbit.com/problems/kth-permutation-sequence/
    static String kthPermutation(int n, int k) {
        // convert to 0-based ranking
        k--;

        // pre-calculate factorials
        long[] fact = new long[n];
        fact[0] = 1;

        for (int i = 1; i < n; i++) {
            fact[i] = i * fact[i - 1];
            // as k is in int range and fact[i] is in denominator,
            // any fact[i] greater than INT_MAX will always give 0 in division
            if (fact[i] > Integer.MAX_VALUE) {
                Arrays.fill(fact, i, n, Integer.MAX_VALUE);
                break;
            }
        }

        // list of all digits from 1 to n
        List<Integer> dig = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            dig.add(i);

        StringBuilder res = new StringBuilder();

        // fill up each position in permutation
        while (n > 0) {
            // position of digit to be added in the digits list
            int pos = (int) (k / fact[n - 1]);
            res.append(dig.get(pos));

            // remove current digit
            dig.remove(pos);

            // update remaining rank
            k = (int) (k % fact[n - 1]);
            n--;
        }

        return res.toString();
    }

    // https://www.interviewbit.com/problems/gray-code/
    static ArrayList<Integer> grayCode(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        // recursively find gray code sequence for n bits
        grayCodeUtil(n, res);

        return res;
    }

    // recursive util to find gray code sequence for n bits
    private static void grayCodeUtil(int n, ArrayList<Integer> res) {
        // base case
        if (n == 1) {
            res.add(0);
            res.add(1);
            return;
        }

        // find gray code sequence for n - 1 bits
        grayCodeUtil(n - 1, res);

        // G(n) = 0G(n-1) + 1R(n-1) where R(n) is the reverse sequence of G(n)
        int len = res.size(), pow = 1 << (n - 1);
        for (int i = len - 1; i >= 0; i--) {
            // appending 1 as the MSB in the binary representation of the number
            int val = res.get(i) | pow;
            res.add(val);
        }
    }

    // https://www.interviewbit.com/problems/nqueens/
    static ArrayList<ArrayList<String>> nQueens(int n) {
        ArrayList<ArrayList<String>> res = new ArrayList<>();

        // initially mark all positions as empty
        char[][] board = new char[n][n];
        for (char[] arr : board)
            Arrays.fill(arr, '.');

        // recursively try for each position column-wise
        nQueensUtil(0, n, board, res);

        return res;
    }

    // recursive util to check if col queens can be placed in col columns
    private static boolean nQueensUtil(int col, int n, char[][] board, ArrayList<ArrayList<String>> res) {
        // found a valid solution
        if (col == n) {
            ArrayList<String> sol = new ArrayList<>();
            for (char[] arr : board)
                sol.add(new String(arr));
            res.add(sol);

            return true;
        }

        // flag to backtrack whether a solution is found in this column or not
        boolean foundSol = false;
        for (int i = 0; i < n; i++) {
            // if queen can be placed at this position
            if (isSafe(board, i, col)) {
                board[i][col] = 'Q';

                // check if solution exists with queen placed here
                if (nQueensUtil(col + 1, n, board, res))
                    foundSol = true;

                // remove queen from current position
                board[i][col] = '.';
            }
        }

        return foundSol;
    }

    // util to check if queen is safe at this position
    private static boolean isSafe(char[][] board, int row, int col) {
        int n = board.length;

        // check in top left direction
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q')
                return false;
        }

        // check in left direction
        for (int j = col - 1; j >= 0; j--) {
            if (board[row][j] == 'Q')
                return false;
        }

        // check in bottom left direction
        for (int i = row + 1, j = col - 1; i < n && j >= 0; i++, j--) {
            if (board[i][j] == 'Q')
                return false;
        }

        // no queens are in the way of this queen
        return true;
    }

    // https://www.interviewbit.com/problems/sudoku/
    static void sudoku(ArrayList<ArrayList<Character>> board) {
        sudokuUtil(0, 0, board);
    }

    // recursive util to find solution starting from board[i][j]
    private static boolean sudokuUtil(int i, int j, ArrayList<ArrayList<Character>> board) {
        // if all rows are filled
        if (i == 9)
            return true;
        // if current row is filled, solve for next row
        if (j == 9)
            return sudokuUtil(i + 1, 0, board);
        // if current place is not empty, move to next cell
        if (board.get(i).get(j) != '.')
            return sudokuUtil(i, j + 1, board);

        // try all digits from 1 to 9
        for (char c = '1'; c <= '9'; c++) {
            // if can place number at current position
            if (isValid(board, i, j, c)) {
                board.get(i).set(j, c);

                // place number at current position and solve for starting from next cell
                if (sudokuUtil(i, j + 1, board))
                    return true;

                // if not found a solution, unset this position and try another number
                board.get(i).set(j, '.');
            }
        }

        // if no number can fit here
        return false;
    }

    // util to check whether character c can be placed at board[row][col] or not
    private static boolean isValid(ArrayList<ArrayList<Character>> board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            // check if current row and column do not contain character c
            if (board.get(row).get(i) == c || board.get(i).get(col) == c)
                return false;
        }

        // find the 3x3 box and check if any number matches current number
        int x = (row / 3) * 3, y = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(x + i).get(y + j) == c)
                    return false;
            }
        }

        // this number is not present in the row, column and box, position is valid
        return true;
    }

    // https://www.interviewbit.com/problems/maximal-string/
    static String maximalString(String A, int B) {
        return maximalStringUtil(A, B, 0);
    }

    // recursive util to find maximal string from A.substring(pos) with B swaps left
    private static String maximalStringUtil(String A, int B, int pos) {
        int n = A.length();
        // if no swaps left or reached end of the string
        if (B == 0 || pos == n)
            return A;

        // find the first maximum char in the substring
        char maxChar = A.charAt(pos);
        for (int i = pos + 1; i < n; i++) {
            if (maxChar < A.charAt(i))
                maxChar = A.charAt(i);
        }

        // if max char is first char, no swap is needed. Check for smaller maximal substrings
        if (maxChar == A.charAt(pos))
            return maximalStringUtil(A, B, pos + 1);

        String res = A;
        StringBuilder builder = new StringBuilder(A);

        // for each char in string after the first position
        for (int i = pos + 1; i < n; i++) {
            // if this char is a maxChar
            if (A.charAt(i) == maxChar) {
                // swap it to the beginning
                swapChar(builder, i, pos);

                // recursively find maximal string for substring starting from pos + 1 and remaining swaps
                String out = maximalStringUtil(builder.toString(), B - 1, pos + 1);
                // update res
                if (out.compareTo(res) > 0)
                    res = out;

                // swap back the maxChar from start to its position
                swapChar(builder, i, pos);
            }
        }

        return res;
    }

    // util to swap two chars in a StringBuilder
    private static void swapChar(StringBuilder builder, int i, int j) {
        char temp = builder.charAt(i);
        builder.setCharAt(i, builder.charAt(j));
        builder.setCharAt(j, temp);
    }
}