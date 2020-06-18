import java.util.List;

public class BinarySearch {
    // https://www.interviewbit.com/problems/count-element-occurence/
    static int findCount(final List<Integer> A, int B) {
        // find first occurrence of B
        int start = findOccurrence(A, B, true);
        // if not found B in list A
        if (start == -1)
            return 0;

        // find last occurrence of B
        int end = findOccurrence(A, B, false);
        return end - start + 1;
    }

    private static int findOccurrence(List<Integer> a, int x, boolean firstOccurrence) {
        int l = 0, r = a.size() - 1, res = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (a.get(mid) == x) {
                res = mid;
                // keep looking on the left for first occurrence
                if (firstOccurrence)
                    r = mid - 1;
                    // keep looking on the right for last occurrence
                else
                    l = mid + 1;
            } else if (x < a.get(mid))
                r = mid - 1;
            else
                l = mid + 1;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/rotated-array/
    static int findMin(final List<Integer> a) {
        // pivot is the element which is greater than both next and prev
        int n = a.size(), pivotPos = findPivotPos(a, n);
        return a.get((pivotPos + 1) % n);
    }

    private static int findPivotPos(List<Integer> a, int n) {
        int l = 0, r = n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            // next and previous in circular array
            int next = (mid + 1) % n, prev = (mid - 1 + n) % n;

            // if mid is the pivot
            if (a.get(mid) >= a.get(prev) && a.get(mid) >= a.get(next))
                return mid;
            // if left half is strictly increasing search in right half
            if (a.get(0) <= a.get(mid))
                l = mid + 1;
                // else right half is strictly increasing search in left half
            else
                r = mid - 1;
        }

        return -1;
    }

    // https://www.interviewbit.com/problems/square-root-of-integer/
    static int sqrt(int n) {
        // sqrt(0) = 0, sqrt(1) = 1
        if (n <= 1)
            return n;

        int l = 2, r = n;
        int sqrt = 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            long prod = (long) mid * (long) mid;

            // perfect square
            if (prod == n) {
                sqrt = mid;
                break;
            }
            // mid might be floor(sqrt(n))
            // Keep looking for larger numbers still
            else if (prod < n) {
                sqrt = mid;
                l = mid + 1;
            }
            // check for smaller numbers
            else
                r = mid - 1;
        }

        return sqrt;
    }
}