import java.util.ArrayList;

public class TwoPointers {
    // https://www.interviewbit.com/problems/merge-two-sorted-lists-ii/
    static void merge(ArrayList<Integer> A, ArrayList<Integer> B) {
        int m = A.size(), n = B.size();
        int i = m - 1, j = n - 1, pos = m + n - 1;

        // increase size of A
        for (int k = 0; k < n; k++)
            A.add(0);

        // traverse both list in reverse. Set the greater element at end of list A
        while (i >= 0 && j >= 0) {
            if (A.get(i) > B.get(j)) {
                A.set(pos, A.get(i));
                i--;
            } else {
                A.set(pos, B.get(j));
                j--;
            }

            pos--;
        }

        // add remaining elements of list A
        while (i >= 0) {
            A.set(pos, A.get(i));
            i--;
            pos--;
        }

        // else add remaining elements of list B
        while (j >= 0) {
            A.set(pos, B.get(j));
            j--;
            pos--;
        }
    }
}