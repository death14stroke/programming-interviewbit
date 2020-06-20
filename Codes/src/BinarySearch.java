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

    // https://www.interviewbit.com/problems/allocate-books/
    static int books(ArrayList<Integer> A, int B) {
        int n = A.size();
        // number of students > books
        if (B > n)
            return -1;

        int sum = 0;
        for (int pages : A) {
            sum += pages;
        }

        // ans will be in range of 0 to sum of all pages (case: 1 student gets all books)
        int l = 0, r = sum, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;

            // if arrangement is possible try for lower value
            if (isPagesPossible(A, B, mid)) {
                ans = mid;
                r = mid - 1;
            } else
                l = mid + 1;
        }

        return ans;
    }

    private static boolean isPagesPossible(ArrayList<Integer> pages, int students, int maxLimit) {
        int curr_sum = 0, cnt = 1;

        for (int page : pages) {
            // if one book has more pages than page limit of a student
            if (page > maxLimit)
                return false;

            // curr student cannot read more. Bring new student
            if (curr_sum + page > maxLimit) {
                curr_sum = page;
                cnt++;

                // if number of students exceeded
                if (cnt > students)
                    return false;
            }
            // add more pages to curr student
            else
                curr_sum += page;
        }

        return true;
    }

    // https://www.interviewbit.com/problems/painters-partition-problem/
    static int paint(int A, int B, ArrayList<Integer> C) {
        int p = 10000003, sum = 0, max = 0;

        for (int board : C) {
            sum = (sum + board) % p;
            max = Math.max(max, board);
        }

        // min for range will be case when each painter paints one.
        // Hence time will be length of max board
        // max for range will be when one painter paints all.
        // Hence time will be sum of all lengths
        int l = max, r = sum, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;

            // found a solution. Look for a lower time cost
            if (canPaint(C, A, B, mid, p)) {
                ans = mid;
                r = mid - 1;
            }
            // else increase the time limit for a possible solution
            else
                l = mid + 1;
        }

        // multiply length by time and take mod
        return (int) (((long) ans * (long) B) % p);
    }

    private static boolean canPaint(ArrayList<Integer> boards, int painters, int time, int maxTime, int p) {
        int curr_sum = 0, cnt = 1;

        for (int cost : boards) {
            // if board is longer than max length decided
            if (cost % p > maxTime)
                return false;

            // if board is longer than painter's max length capacity add new painter
            if ((curr_sum + cost) % p > maxTime) {
                curr_sum = cost;
                cnt++;

                // if count of painters is reached
                if (cnt > painters)
                    return false;
            }
            // allocate board to same painter
            else
                curr_sum = (curr_sum + cost) % p;
        }

        return true;
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