import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BinarySearch {
    // https://www.interviewbit.com/problems/count-element-occurence/
    static int findCount(final int[] A, int B) {
        // find first occurence of B
        int start = findFirstOccurence(A, B);
        // if not found B in list A
        if (start == -1)
            return 0;

        // find last occurrence of B
        int end = findLastOccurence(A, B);

        return end - start + 1;
    }

    // util to find first occurence of x in array
    private static int findFirstOccurence(int[] A, int x) {
        int l = 0, r = A.length - 1, res = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found an instance. Mark it and keep looking in left half
            if (A[mid] == x) {
                res = mid;
                r = mid - 1;
            }
            // found smaller number. Search in right half
            else if (A[mid] < x) {
                l = mid + 1;
            }
            // else search in left half
            else {
                r = mid - 1;
            }
        }

        return res;
    }

    // util to find last occurence of x in array
    private static int findLastOccurence(int[] A, int x) {
        int l = 0, r = A.length - 1, res = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found an instance. Mark it and keep looking in right half
            if (A[mid] == x) {
                res = mid;
                l = mid + 1;
            }
            // found smaller number. Search in right half
            else if (A[mid] < x) {
                l = mid + 1;
            }
            // else search in left half
            else {
                r = mid - 1;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/rotated-array/
    static int findMin(final List<Integer> a) {
        // pivot is the element which is greater than both next and prev
        int n = a.size(), pivotPos = findPivotPos(a, n);
        return a.get((pivotPos + 1) % n);
    }

    // util to find pivot element position
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
            if (prod == n)
                return mid;
            // mid might be floor(sqrt(n)). Keep looking for larger numbers still
            if (prod < n) {
                sqrt = mid;
                l = mid + 1;
            }
            // check for smaller numbers
            else
                r = mid - 1;
        }

        return sqrt;
    }

    // https://www.interviewbit.com/problems/smaller-or-equal-elements/
    static int smallerOrEqual(int[] A, int B) {
        // find last position of equal number or just smaller number
        int pos = lowerBoundPos(A, B);
        return pos + 1;
    }

    // util to find last position of equal or just smaller number than x (lower bound)
    private static int lowerBoundPos(int[] A, int x) {
        int l = 0, r = A.length - 1, res = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // possible result. Search in right half for further position
            if (A[mid] <= x) {
                res = mid;
                l = mid + 1;
            }
            // search in left half
            else
                r = mid - 1;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/woodcutting-made-easy/
    static int woodCutting(int[] A, int B) {
        // highest tree
        int maxHeight = Integer.MIN_VALUE;
        for (int tree : A)
            maxHeight = Math.max(maxHeight, tree);

        // try for all values from min saw height (all wood cut) to max saw height (0 wood cut)
        int l = 0, r = maxHeight;
        // optimum saw height
        int ans = 0;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // if enough wood not acquired, decrease saw height
            if (countWood(A, mid) < B)
                r = mid - 1;
                // increase saw height to check for a larger possible value
            else {
                ans = mid;
                l = mid + 1;
            }
        }

        return ans;
    }

    // util to get wood cut by saw of height sawHeight
    private static long countWood(int[] A, int sawHeight) {
        long woodCnt = 0;
        // add upper part of each tree to wood count
        for (int tree : A)
            woodCnt += Math.max(tree - sawHeight, 0);

        return woodCnt;
    }

    // https://www.interviewbit.com/problems/matrix-search/
    static int searchMatrix(int[][] A, int B) {
        int m = A.length, n = A[0].length;
        // since matrix is sorted such, we can consider it as a sorted array of m*n
        int l = 0, r = m * n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // mapping to row and col in matrix
            int row = mid / n, col = mid % n;

            // found B
            if (A[row][col] == B)
                return 1;
            if (A[row][col] < B)
                l = mid + 1;
            else
                r = mid - 1;
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/search-for-a-range/
    static int[] searchRange(final int[] A, int B) {
        // find first occurrence
        int firstOccurence = findFirstOccurence(A, B);
        if (firstOccurence == -1)
            return new int[]{-1, -1};

        // find last occurrence
        int lastOccurrence = findLastOccurence(A, B);

        return new int[]{firstOccurence, lastOccurrence};
    }

    // https://www.interviewbit.com/problems/sorted-insert-position/
    static int searchInsert(ArrayList<Integer> a, int b) {
        int n = a.size();
        // found or insert b at beginning
        if (b <= a.get(0))
            return 0;
        // insert b at end
        if (b > a.get(n - 1))
            return n;

        int l = 0, r = n - 1, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found b
            if (a.get(mid) == b)
                return mid;
            // search for smaller pos in left sub-array
            if (a.get(mid) < b)
                l = mid + 1;
                // search for larger pos in right sub-array
            else {
                ans = mid;
                r = mid - 1;
            }
        }

        return ans;
    }

    // https://www.interviewbit.com/problems/matrix-median/
    static int findMedian(int[][] A) {
        int m = A.length, n = A[0].length;
        // global minimum and maximum in matrix
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        // matrix is sorted by rows
        // hence check top for each row for min and bottom for each row for max
        for (int[] row : A) {
            min = Math.min(min, row[0]);
            max = Math.max(max, row[n - 1]);
        }

        // all entries in matrix are same
        if (min == max)
            return min;

        // # elements <= median in odd size array is (n+1)/2
        int req = (m * n + 1) / 2, res = -1;
        // check all values from min to max as median
        while (min <= max) {
            int mid = min + (max - min) / 2;
            int totalCnt = 0;
            // count for numbers <= mid in each row
            for (int[] row : A) {
                int cnt = lowerBoundPos(row, mid) + 1;
                totalCnt += cnt;
            }

            // if mid has less elements < required, try higher value as median
            if (totalCnt < req)
                min = mid + 1;
                // mid can be median if no duplicates for mid. Also try lower value as median
            else {
                res = mid;
                max = mid - 1;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/painters-partition-problem/
    static int paint(int A, int B, int[] C) {
        int p = 10000003, sum = 0, max = 0;
        // find maximum board length and total board length
        for (int board : C) {
            sum = (sum + board) % p;
            max = Math.max(max, board);
        }

        // min for range will be case when each painter paints one. Hence time will be length of max board
        // max for range will be when one painter paints all. Hence time will be sum of all lengths
        int l = max, r = sum, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found a solution. Look for a lower time cost
            if (canPaint(C, A, mid, p)) {
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

    // util to check if it is possible to paint all the boards with current max time per board
    private static boolean canPaint(int[] boards, int painters, int maxTime, int p) {
        int curr_sum = 0, cnt = 1;

        for (int cost : boards) {
            // if board is longer than max length decided
            if (cost > maxTime)
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

    // https://www.interviewbit.com/problems/allocate-books/
    static int books(int[] A, int B) {
        int n = A.length;
        // number of students > books
        if (B > n)
            return -1;

        int sum = 0, max = Integer.MIN_VALUE;
        for (int pages : A) {
            sum += pages;
            max = Math.max(max, pages);
        }

        // min for range will be case when each student gets one book
        // max for range will be when one student gets all the books
        int l = max, r = sum, ans = -1;
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

    // util to check if arrangement is possible with maxLimit for #pages for each student
    private static boolean isPagesPossible(int[] pages, int students, int maxLimit) {
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

    // https://www.interviewbit.com/problems/implement-power-function/
    static int pow(int x, int n, int d) {
        // pow(0, n) = 0
        if (x == 0)
            return 0;

        long res = 1;

        while (n > 0) {
            // pow(x, 2k+1) = pow(x, 2k) * x
            if (n % 2 == 1)
                res = (int) ((res * (long) x) % d);

            // pow(x, 2k) = pow (x^2, k)
            x = (int) (((long) x * (long) x) % d);
            n /= 2;
        }

        // result cannot be negative
        res = (res + d) % d;

        return (int) res;
    }

    // https://www.interviewbit.com/problems/simple-queries/
    static int[] simpleQueries(int[] A, int[] B) {
        int n = A.length;
        // find next and previous greater positions for all elements
        int[] next = findNextGreater(A);
        int[] prev = findPreviousGreater(A);
        // G array from which queries will be made
        ProductNode[] G = new ProductNode[n];

        for (int i = 0; i < n; i++) {
            // #subarrays in which A[i] will be maximum = #elements on left * #elements on right
            int leftCount = i - prev[i];
            int rightCount = next[i] - i;
            G[i] = new ProductNode(productOfFactors(A[i]), (long) leftCount * rightCount);
        }

        // sort in descending order of product of factors
        Arrays.sort(G, (p1, p2) -> p2.val - p1.val);
        // cumulative frequency array
        long[] freqSum = new long[n];
        freqSum[0] = G[0].freq;

        for (int i = 1; i < n; i++)
            freqSum[i] = freqSum[i - 1] + G[i].freq;

        // query results
        int[] res = new int[B.length];

        for (int i = 0; i < B.length; i++) {
            // find position by querying frequencies array for rank
            int pos = queryFrequencies(freqSum, B[i]);
            res[i] = G[pos].val;
        }

        return res;
    }

    // util to query rank of x in the cumulative frequency array
    private static int queryFrequencies(long[] freqSum, int x) {
        int n = freqSum.length;
        int l = 0, r = n - 1, ans = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found rank
            if (freqSum[mid] == x)
                return mid;
            // x will be in higher bucket
            if (freqSum[mid] < x)
                l = mid + 1;
                // mark as result. X can be in lower bucket
            else {
                ans = mid;
                r = mid - 1;
            }
        }

        return ans;
    }

    // util to find previous greater positions for all elements
    private static int[] findPreviousGreater(int[] A) {
        int n = A.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);

        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            // pop elements from stack while stack is not empty and top of stack is smaller than arr[i]
            // we always have elements in decreasing order in a stack.
            while (!s.empty() && A[s.peek()] <= A[i])
                s.pop();
            // if stack becomes empty, then no element is greater on left side
            // else top of stack is previous greater
            if (!s.empty())
                res[i] = s.peek();

            s.push(i);
        }

        return res;
    }

    // util to find next greater positions for all elements
    private static int[] findNextGreater(int[] A) {
        int n = A.length;
        int[] res = new int[n];
        Arrays.fill(res, n);

        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            // pop elements from stack and mark next greater as the current element
            while (!s.empty() && A[s.peek()] <= A[i])
                res[s.pop()] = i;

            s.push(i);
        }

        return res;
    }

    // util to get #factors for an integer
    private static int countFactors(int x) {
        int cnt = 0;

        for (int i = 1; i * i <= x; i++) {
            // if factor
            if (x % i == 0) {
                // if same factor (sqrt(x))
                if (i == x / i)
                    cnt++;
                else
                    cnt += 2;
            }
        }

        return cnt;
    }

    // util to get product of all factors
    private static int productOfFactors(int x) {
        // get count of factors
        int cnt = countFactors(x), p = 1000000007;
        // each factor will come in pair except for perfect squares. Hence product will be pow(x, n/2) if even
        // or pow(x, n/2) * sqrt(x) if odd
        int ans = pow(x, cnt / 2, p);
        if (cnt % 2 == 1)
            ans = (int) ((ans * (long) Math.sqrt(x)) % p);

        return ans;
    }

    // data class to store value and its frequency
    private static class ProductNode {
        int val;
        long freq;

        ProductNode(int val, long freq) {
            this.val = val;
            this.freq = freq;
        }
    }

    // https://www.interviewbit.com/problems/rotated-sorted-array-search/
    static int search(final int[] A, int x) {
        int n = A.length;
        int l = 0, r = n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found x
            if (x == A[mid])
                return mid;
            // if left sub-array is strictly increasing
            if (A[0] <= A[mid]) {
                // x lies in left sub-array
                if (x >= A[0] && x < A[mid])
                    r = mid - 1;
                    // else search in right
                else
                    l = mid + 1;
            }
            // right sub-array is strictly increasing
            else {
                // x lies in right sub-array
                if (x > A[mid] && x < A[n - 1])
                    l = mid + 1;
                    // else search in left
                else
                    r = mid - 1;
            }
        }

        return -1;
    }

    // https://www.interviewbit.com/problems/median-of-array/
    static double findMedianSortedArrays(final List<Integer> a, final List<Integer> b) {
        // a must be the smaller array
        if (a.size() > b.size())
            return findMedianSortedArrays(b, a);

        int x = a.size(), y = b.size();
        int l = 0, r = x;

        // binary search for partition over x
        while (l <= r) {
            int partitionX = l + (r - l) / 2;
            int partitionY = (x + y + 1) / 2 - partitionX;

            // rightmost in left partition from array A
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : a.get(partitionX - 1);
            // leftmost in right partition from array A
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : a.get(partitionX);

            // rightmost in left partition from array B
            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : b.get(partitionY - 1);
            // leftmost in right partition from array B
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : b.get(partitionY);

            // correct partition. Found interested 4 elements of the middle
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                // result size is even. n/2 pos will be taken by max of left partitions and
                // (n/2 + 1) pos will be taken by min of right partitions
                if ((x + y) % 2 == 0)
                    return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
                    // result size is odd. n/2 pos will be taken by max of left partitions
                    // (because left partition has one element extra)
                else
                    return Math.max(maxLeftX, maxLeftY);
            }
            // left partition for x is too large. Move to left
            else if (maxLeftX > minRightY)
                r = partitionX - 1;
                // left partition for x is too small. Move to right
            else
                l = partitionX + 1;
        }

        return -1;
    }

    // https://www.interviewbit.com/problems/search-in-bitonic-array/
    static int bitonicSearch(int[] A, int B) {
        int n = A.length;
        // find the peak in array
        int peakPos = findPeak(A);

        // binary search in increasing order left half
        int leftSearch = binarySearchLeft(A, 0, peakPos, B);
        if (leftSearch != -1)
            return leftSearch;
        // not found in left half. Binary search in decreasing order right half
        return binarySearchRight(A, peakPos + 1, n - 1, B);
    }

    // find peak element in bitonic array
    private static int findPeak(int[] A) {
        int n = A.length;
        int l = 0, r = n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // base case - 1
            if (mid == 0) {
                if (A[0] > A[1])
                    return 0;
                return 1;
            }
            // base case - 2
            if (mid == n - 1) {
                if (A[n - 2] > A[n - 1])
                    return n - 2;
                return n - 1;
            }

            int next = mid + 1, prev = mid - 1;
            // found peak
            if (A[mid] > A[prev] && A[mid] > A[next])
                return mid;
            // left half is increasing
            if (A[mid] > A[prev])
                l = mid + 1;
                // peak point already passed
            else if (A[mid] < A[prev])
                r = mid - 1;
        }

        return -1;
    }

    // binary search ascending
    @SuppressWarnings("SameParameterValue")
    private static int binarySearchLeft(int[] A, int l, int r, int x) {
        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (A[mid] == x)
                return mid;
            if (A[mid] < x)
                l = mid + 1;
            else
                r = mid - 1;
        }

        return -1;
    }

    // binary search descending
    private static int binarySearchRight(int[] A, int l, int r, int x) {
        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (A[mid] == x)
                return mid;
            if (A[mid] < x)
                r = mid - 1;
            else
                l = mid + 1;
        }

        return -1;
    }
}