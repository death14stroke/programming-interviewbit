import java.util.*;

class Backtracking {
    // https://www.interviewbit.com/problems/reverse-link-list-recursion
    static LinkedLists.ListNode reverseList(LinkedLists.ListNode head) {
        // 0 or 1 node in linked list
        if (head == null || head.next == null)
            return head;

        // reverse the sublist starting from the next node and save its head
        LinkedLists.ListNode temp = reverseList(head.next);
        // make current node's next link to current node
        head.next.next = head;
        // remove old link of current node to its next
        head.next = null;
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

    // https://www.interviewbit.com/problems/kth-permutation-sequence/
    static String kthPermutation(int A, int B) {
        // convert to 0-based ranking
        B--;
        // pre-calculate factorials
        int[] fact = new int[A];
        fact[0] = 1;
        for (int i = 1; i < A; i++) {
            fact[i] = i * fact[i - 1];
            // as fact[i] is in denominator, any fact[i] greater than B will always give 0 in division
            if (fact[i] > B) {
                Arrays.fill(fact, i, A, B + 1);
                break;
            }
        }
        // list of all digits from 1 to A
        List<Integer> dig = new ArrayList<>();
        for (int i = 1; i <= A; i++)
            dig.add(i);

        StringBuilder res = new StringBuilder();
        // fill up each position in permutation
        for (; A > 0; A--) {
            // position of digit to be added in the digits list
            int pos = B / fact[A - 1];
            res.append(dig.get(pos));
            // remove current digit
            dig.remove(pos);
            // update remaining rank
            B = B % fact[A - 1];
        }

        return res.toString();
    }

    // https://www.interviewbit.com/problems/gray-code/
    static ArrayList<Integer> grayCode(int A) {
        ArrayList<Integer> res = new ArrayList<>();
        // recursively find gray code sequence for A bits
        grayCodeUtil(A, res);

        return res;
    }

    // recursive util to find gray code sequence for A bits
    private static void grayCodeUtil(int A, ArrayList<Integer> res) {
        // base case
        if (A == 1) {
            res.add(0);
            res.add(1);
            return;
        }

        // find gray code sequence for A - 1 bits
        grayCodeUtil(A - 1, res);
        // G(A) = 0G(A-1) + 1R(A-1) where R(A) is the reverse sequence of G(A)
        int len = res.size(), pow = 1 << (A - 1);
        for (int i = len - 1; i >= 0; i--) {
            // appending 1 as the MSB in the binary representation of the number
            int val = res.get(i) | pow;
            res.add(val);
        }
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
    private static void subsetUtil(ArrayList<Integer> A, int pos, ArrayList<Integer> subset, ArrayList<ArrayList<Integer>> res) {
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
    static ArrayList<ArrayList<Integer>> combinationSum(ArrayList<Integer> A, int B) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // sort the list for combinations in non-decreasing order
        Collections.sort(A);
        // recursively find all the combinations with sum = B
        combinationSumUtil(A, 0, B, new ArrayList<>(), res);

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

        int n = A.size();
        // try all elements one by one
        for (int i = pos; i < n; i++) {
            curr.add(A.get(i));
            // find combinations with target sum including the current element
            combinationSumUtil(A, i, target - A.get(i), curr, res);
            // remove current element to try another element in the next iteration
            curr.remove(curr.size() - 1);
            // skip duplicates in the candidate set
            while (i + 1 < n && A.get(i + 1).equals(A.get(i)))
                i++;
        }
    }

    // https://www.interviewbit.com/problems/combination-sum-ii/
    static ArrayList<ArrayList<Integer>> combinationSum2(ArrayList<Integer> A, int B) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // sort the list for combinations in non-decreasing order
        Collections.sort(A);
        // recursively find all the combinations with sum = B
        combinationSum2Util(A, 0, B, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all combinations with sum equal target from the start
    private static void combinationSum2Util(ArrayList<Integer> A, int pos, int target,
                                            ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> res) {
        // found target sum combination
        if (target == 0) {
            res.add(new ArrayList<>(curr));
            return;
        }
        // current sum exceeds target, cannot be a valid combination
        if (target < 0)
            return;

        int n = A.size();
        // try all elements one by one
        for (int i = pos; i < n; i++) {
            curr.add(A.get(i));
            // find combinations with target sum containing the current element once
            combinationSum2Util(A, i + 1, target - A.get(i), curr, res);
            // remove current element to try another element in the next iteration
            curr.remove(curr.size() - 1);
            // skip duplicates in the candidate set
            while (i + 1 < n && A.get(i + 1).equals(A.get(i)))
                i++;
        }
    }

    // https://www.interviewbit.com/problems/combinations/
    static ArrayList<ArrayList<Integer>> combinations(int A, int B) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // recursively find all the combinations with and without every element
        combinationsUtil(A, B, 1, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all combinations
    private static void combinationsUtil(int A, int B, int num, ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> res) {
        // if all B places are filled, add current combination to output
        if (B == 0) {
            res.add(new ArrayList<>(curr));
            return;
        }

        // for each number from current position till end
        for (int i = num; i <= A; i++) {
            // add current number to combination and complete the current combination with remaining ones
            curr.add(i);
            combinationsUtil(A, B - 1, i + 1, curr, res);
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
        subsets2Util(A, 0, new ArrayList<>(), res);

        return res;
    }

    // recursive util to find all subsets
    private static void subsets2Util(ArrayList<Integer> A, int pos, ArrayList<Integer> subset, ArrayList<ArrayList<Integer>> res) {
        // add current subset to output
        res.add(new ArrayList<>(subset));

        int n = A.size();
        // for all elements remaining
        for (int i = pos; i < n; i++) {
            // add current element and find more subsets containing it
            subset.add(A.get(i));
            subsets2Util(A, i + 1, subset, res);
            // remove current element to find more subsets without it
            subset.remove(subset.size() - 1);
            // skip duplicate elements in the given set
            while (i + 1 < n && A.get(i + 1).equals(A.get(i)))
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

        // try each character mapped to current number. Find all combinations with it and then remove it to try next character.
        for (char c : digitMap[A.charAt(pos) - '0']) {
            builder.append(c);
            letterPhoneUtil(A, pos + 1, builder, res);
            builder.setLength(builder.length() - 1);
        }
    }

    // https://www.interviewbit.com/problems/palindrome-partitioning/
    @SuppressWarnings("unchecked")
    static ArrayList<ArrayList<String>> palindromicPartitioning(String A) {
        int n = A.length();
        // isPalindrome[i][j] = true if A.substring(i, j + 1) is palindrome else false
        boolean[][] isPalindrome = computePalindromeArray(A);
        // res[i] = palindromic partitioning for A.substring(i, n);
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
                if (isPalindrome[i][j]) {
                    String str = A.substring(i, j + 1);
                    // the palindromic partitions for this substring will be all the palindromic partitions of the substrings
                    // starting from j + 1 with the current substring in all the partitions
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

    // util to precompute palindrome 2D array for the input string
    private static boolean[][] computePalindromeArray(String A) {
        int n = A.length();
        boolean[][] isPalindrome = new boolean[n][n];
        isPalindrome[0][0] = true;
        // for len = 1 and len = 2 substrings
        for (int i = 1; i < n; i++) {
            isPalindrome[i][i] = true;
            isPalindrome[i - 1][i] = A.charAt(i) == A.charAt(i - 1);
        }
        // for higher length substrings
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                isPalindrome[i][j] = A.charAt(i) == A.charAt(j) && isPalindrome[i + 1][j - 1];
            }
        }

        return isPalindrome;
    }

    // https://www.interviewbit.com/problems/generate-all-parentheses-ii/
    static ArrayList<String> generateParenthesis2(int A) {
        ArrayList<String> res = new ArrayList<>();
        // call recursive util to generate all parentheses combinations for A > 0
        if (A > 0)
            generateParenthesis2Util(A - 1, A, new StringBuilder("("), res);

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
            builder.append('(');
            generateParenthesis2Util(open - 1, close, builder, res);
            builder.setLength(builder.length() - 1);
        }
        // can add close parenthesis
        if (open < close) {
            builder.append(')');
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
    private static void permutationsUtil(ArrayList<Integer> A, int pos, ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> res) {
        // made a valid permutation
        if (pos == A.size()) {
            res.add(new ArrayList<>(curr));
            return;
        }

        // for all elements remaining
        for (int i = pos; i < A.size(); i++) {
            curr.add(A.get(i));
            // mark current element as used by swapping it with current beginning element
            swap(A, i, pos);
            // find permutations for remaining positions recursively
            permutationsUtil(A, pos + 1, curr, res);
            // mark current element as unused by bringing it back to its original position
            swap(A, i, pos);
            curr.remove(curr.size() - 1);
        }
    }

    // util to swap numbers at two positions in a list
    private static void swap(ArrayList<Integer> A, int i, int j) {
        int temp = A.get(i);
        A.set(i, A.get(j));
        A.set(j, temp);
    }

    // https://www.interviewbit.com/problems/nqueens/
    static ArrayList<ArrayList<String>> nQueens(int A) {
        // T.C - T(n) = n * T(n - 1) + c = O(n!)
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        // initially mark all positions as empty
        char[][] board = new char[A][A];
        for (char[] row : board)
            Arrays.fill(row, '.');
        // optimizing isSafe function
        // values of (row - col) and (row + col) will match for main diagonal and off diagonal entries respectively
        boolean[] rowCheck = new boolean[A];
        boolean[] diagUpCheck = new boolean[2 * A - 1];
        boolean[] diagDownCheck = new boolean[2 * A - 1];
        // recursively try for each position column-wise
        nQueensUtil(0, A, board, res, rowCheck, diagUpCheck, diagDownCheck);

        return res;
    }

    // recursive util to check if col queens can be placed in col columns
    private static void nQueensUtil(int col, int A, char[][] board, ArrayList<ArrayList<String>> res,
                                    boolean[] rowCheck, boolean[] diagUpCheck, boolean[] diagDownCheck) {
        // found a valid solution
        if (col == A) {
            ArrayList<String> sol = new ArrayList<>();
            for (char[] arr : board)
                sol.add(new String(arr));
            res.add(sol);
            return;
        }

        for (int i = 0; i < A; i++) {
            // if queen can be placed at this position
            if (isSafe(i, col, A, rowCheck, diagUpCheck, diagDownCheck)) {
                board[i][col] = 'Q';
                rowCheck[i] = true;
                diagUpCheck[i - col + A - 1] = true;
                diagDownCheck[i + col] = true;
                // recursively compute solution with queen placed here
                nQueensUtil(col + 1, A, board, res, rowCheck, diagUpCheck, diagDownCheck);
                // remove queen from current position
                rowCheck[i] = false;
                diagUpCheck[i - col + A - 1] = false;
                diagDownCheck[i + col] = false;
            }
        }
    }

    // util to check if queen is safe at this position
    private static boolean isSafe(int row, int col, int A, boolean[] rowCheck, boolean[] diagUpCheck, boolean[] diagDownCheck) {
        return !rowCheck[row] && !diagUpCheck[row - col + A - 1] && !diagDownCheck[row + col];
    }

    // https://www.interviewbit.com/problems/sudoku/
    private static int[] rows, cols, boxes;

    static void sudoku(ArrayList<ArrayList<Character>> board) {
        // int mask for each row, column, box
        rows = new int[9];
        cols = new int[9];
        boxes = new int[9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i).get(j) == '.')
                    continue;

                int val = board.get(i).get(j) - '0';
                int mask = (1 << (val - 1));
                int box = (i / 3) * 3 + j / 3;
                // set the bit pos in all masks
                rows[i] |= mask;
                cols[j] |= mask;
                boxes[box] |= mask;
            }
        }

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
            // if we can place number at current position
            if (isValid(i, j, c)) {
                board.get(i).set(j, c);
                int val = c - '0', mask = (1 << (val - 1));
                int box = (i / 3) * 3 + j / 3;
                // set the bit pos in all masks
                rows[i] |= mask;
                cols[j] |= mask;
                boxes[box] |= mask;
                // place number at current position and solve for starting from next cell
                if (sudokuUtil(i, j + 1, board))
                    return true;
                // if not found a solution, unset this position and try another number
                board.get(i).set(j, '.');
                // unset the bit pos in all masks
                rows[i] &= ~mask;
                cols[j] &= ~mask;
                boxes[box] &= ~mask;
            }
        }
        // if no number can fit here
        return false;
    }

    // util to check whether character c can be placed at board[row][col] or not
    private static boolean isValid(int row, int col, char c) {
        int val = c - '0', mask = (1 << (val - 1));
        int box = (row / 3) * 3 + col / 3;
        // check if the bit position in all masks is 0
        return (rows[row] & mask) == 0 && (cols[col] & mask) == 0 && (boxes[box] & mask) == 0;
    }

    // https://www.interviewbit.com/problems/all-possible-combinations/
    static ArrayList<String> specialStrings(ArrayList<String> A) {
        ArrayList<String> res = new ArrayList<>();
        specialStringsUtil(A, 0, new StringBuilder(), res);
        return res;
    }

    // recursive util to take each character from string at pos
    private static void specialStringsUtil(ArrayList<String> A, int pos, StringBuilder builder, ArrayList<String> res) {
        // found valid string
        if (pos == A.size()) {
            res.add(builder.toString());
            return;
        }

        for (char c : A.get(pos).toCharArray()) {
            // add each character from string at pos in StringBuilder at pos
            builder.append(c);
            // recursively find the next characters
            specialStringsUtil(A, pos + 1, builder, res);
            // backtrack
            builder.setLength(builder.length() - 1);
        }
    }

    // https://www.interviewbit.com/problems/all-unique-permutations/
    static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> A) {
        // frequency map of list
        Map<Integer, Integer> map = new HashMap<>();
        for (int val : A)
            map.put(val, map.getOrDefault(val, 0) + 1);

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // recursively permute for each position for each unique number
        permuteUtil(0, A.size(), new ArrayList<>(), map, res);

        return res;
    }

    private static void permuteUtil(int pos, int n, List<Integer> curr, Map<Integer, Integer> map, ArrayList<ArrayList<Integer>> res) {
        // found permutation
        if (pos == n) {
            res.add(new ArrayList<>(curr));
            return;
        }
        // try each unique entry at current position
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            int num = e.getKey();
            int cnt = e.getValue();
            if (cnt == 0)
                continue;

            // add at current position and update remaining count
            curr.add(num);
            map.put(num, cnt - 1);
            // recursively permute for other positions
            permuteUtil(pos + 1, n, curr, map, res);
            // backtrack
            curr.remove(curr.size() - 1);
            map.put(num, cnt);
        }
    }
}