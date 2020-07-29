import java.util.ArrayList;
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
    private static void subsetUtil(ArrayList<Integer> A, int pos, ArrayList<Integer> subset, ArrayList<ArrayList<Integer>> res) {
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
    private static void combinationsUtil(int n, int k, int pos, ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> res) {
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

    // recursive util to find all combinations with sum equal target from the se
    private static void combinationSumUtil(ArrayList<Integer> A, int pos, int target, ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> res) {
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
            // find combinations with target sun including the current element
            combinationSumUtil(A, i, target - A.get(i), curr, res);
            // remove current element to try another element in the next iteration
            curr.remove(curr.size() - 1);

            i++;
            // skip duplicates in the candidate set
            while (i < A.size() && A.get(i).equals(A.get(i - 1)))
                i++;
        }
    }
}