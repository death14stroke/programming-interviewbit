import java.util.ArrayList;
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

    // https://www.interviewbit.com/problems/matrix-search/
    static int searchMatrix(ArrayList<ArrayList<Integer>> A, int B) {
        int m = A.size();
        if (m == 0)
            return -1;

        int n = A.get(0).size();
        if (n == 0)
            return -1;

        // since matrix is sorted such, we can consider it as a sorted array of m*n
        int l = 0, r = m * n - 1, mid;
        while (l <= r) {
            mid = l + (r - l) / 2;

            // mapping to row and col in matrix
            int row = mid / n, col = mid % n;

            // found B
            if (B == A.get(row).get(col))
                return 1;
            if (B >= A.get(row).get(col))
                l = mid + 1;
            else
                r = mid - 1;
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/search-for-a-range/
    static ArrayList<Integer> searchRange(final List<Integer> A, int x) {
        ArrayList<Integer> out = new ArrayList<>();
        // find first occurrence
        int firstOccurrence = findOccurrence(A, x, true);

        if (firstOccurrence == -1) {
            out.add(-1);
            out.add(-1);
        } else {
            // find last occurrence
            int lastOccurrence = findOccurrence(A, x, false);
            out.add(firstOccurrence);
            out.add(lastOccurrence);
        }

        return out;
    }

    // https://www.interviewbit.com/problems/sorted-insert-position/
    static int searchInsert(ArrayList<Integer> a, int b) {
        int n = a.size();

        // insert b at beginning
        if (b <= a.get(0))
            return 0;
        // insert b at end
        if (b >= a.get(n - 1))
            return n;

        int l = 0, r = n - 1, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;

            // found b
            if (b == a.get(mid))
                return mid;
                // search for smaller pos in left sub-array
            else if (b < a.get(mid)) {
                ans = mid;
                r = mid - 1;
            }
            // search for larger pos in right sub-array
            else {
                ans = mid + 1;
                l = mid + 1;
            }
        }

        return ans;
    }

    // https://www.interviewbit.com/problems/implement-power-function/
    static int pow(int x, int n, int d) {
        // pow(0, n) = 0
        if (x == 0)
            return 0;

        int res = 1;
        while (n > 0) {
            // pow(x, 2k+1) = pow(x, 2k) * x
            if (n % 2 == 1)
                res = (int) (((long) res * (long) x) % d);

            // pow(x, 2k) = pow (x^2, k)
            x = (int) (((long) x * (long) x) % d);
            n /= 2;
        }

        res = (res + d) % d;
        return res;
    }

    // https://www.interviewbit.com/problems/rotated-sorted-array-search/
    static int search(final List<Integer> A, int x) {
        int n = A.size();
        int l = 0, r = n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            // found x
            if (x == A.get(mid))
                return mid;

            // if left sub-array is strictly increasing
            if (A.get(0) <= A.get(mid)) {
                // x lies in left sub-array
                if (x < A.get(mid) && x >= A.get(0))
                    r = mid - 1;
                    // else search in left
                else
                    l = mid + 1;
            }
            // right sub-array is strictly increasing
            else {
                // x lies in right sub-array
                if (x > A.get(mid) && x <= A.get(n - 1))
                    l = mid + 1;
                    // else search in right
                else
                    r = mid - 1;
            }
        }

        return -1;
    }
}