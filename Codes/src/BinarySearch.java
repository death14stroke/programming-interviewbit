import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BinarySearch {
    // https://www.interviewbit.com/problems/count-element-occurence/
    static int findCount(final int[] A, int B) {
        // find first occurrence of B
        int start = findFirstOccurrence(A, B);
        // if not found B in list A
        if (start == -1)
            return 0;

        // find last occurrence of B
        int end = findLastOccurrence(A, B);

        return end - start + 1;
    }

    // util to find first occurrence of x in array
    private static int findFirstOccurrence(int[] A, int x) {
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

    // util to find last occurrence of x in array
    private static int findLastOccurrence(int[] A, int x) {
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
    static int findMin(final List<Integer> A) {
        int n = A.size();
        // if sorted array 0th element is minimum
        if (A.get(0) <= A.get(n - 1))
            return A.get(0);

        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // next and previous in circular array
            int prev = (mid - 1) % n, next = (mid + 1) % n;

            // mid is the minimum
            if (A.get(mid) < A.get(prev) && A.get(mid) < A.get(next))
                return A.get(mid);
            // if left half is sorted search in right half
            if (A.get(0) <= A.get(mid))
                l = mid + 1;
                // else right half is sorted search in left half
            else
                r = mid - 1;
        }

        return -1;
    }

    // https://www.interviewbit.com/problems/square-root-of-integer/
    static int sqrt(int A) {
        // sqrt(0) = 0, sqrt(1) = 1
        if (A <= 1)
            return A;

        int l = 2, r = A / 2;
        int sqrt = 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            long prod = (long) mid * mid;
            // perfect square
            if (prod == A)
                return mid;
            // mid might be floor(sqrt(A)). Keep looking for larger numbers still
            if (prod < A) {
                sqrt = mid;
                l = mid + 1;
            }
            // check for smaller numbers
            else
                r = mid - 1;
        }

        return sqrt;
    }

    // https://www.interviewbit.com/problems/search-in-bitonic-array/
    static int bitonicSearch(int[] A, int B) {
        int n = A.length;
        int l = 0, r = n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found target
            if (A[mid] == B)
                return mid;
            // if current left half is sorted increasing
            if (A[l] <= A[mid]) {
                // if target falls in range of left half, search in left half
                if (A[l] <= B && B < A[mid])
                    r = mid - 1;
                    // else search in right half
                else
                    l = mid + 1;
            }
            // else current right half is sorted decreasing
            else {
                // if target falls in range of right half, search in right half
                if (A[mid] > B && B >= A[n - 1])
                    l = mid + 1;
                    // else search in left half
                else
                    r = mid - 1;
            }
        }

        return -1;
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
        int maxHeight = A[0];
        for (int i = 1; i < A.length; i++)
            maxHeight = Math.max(maxHeight, A[i]);
        // try for all values from min saw height (all wood cut) to max saw height (0 wood cut)
        int l = 0, r = maxHeight;
        // optimum saw height
        int res = 0;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // increase saw height to check for a larger possible value
            if (isEnoughWood(A, B, mid)) {
                res = mid;
                l = mid + 1;
            }
            // enough wood not acquired, decrease saw height
            else {
                r = mid - 1;
            }
        }

        return res;
    }

    // util to get wood cut by saw of height sawHeight
    private static boolean isEnoughWood(int[] A, int B, int sawHeight) {
        int woodCnt = 0;
        // add upper part of each tree to wood count
        for (int tree : A) {
            woodCnt += Math.max(tree - sawHeight, 0);
            // enough wood acquired
            if (woodCnt >= B)
                return true;
        }
        // could not cut enough wood
        return false;
    }

    // https://www.interviewbit.com/problems/matrix-search/
    static int searchMatrix(int[][] A, int B) {
        int m = A.length, n = A[0].length;
        // since matrix is sorted such, we can consider it as a sorted array of m*n
        int l = 0, r = m * n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // mapping to row and col in matrix
            int x = mid / n, y = mid % n;

            // found B
            if (A[x][y] == B)
                return 1;
            if (A[x][y] < B)
                l = mid + 1;
            else
                r = mid - 1;
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/search-for-a-range/
    static int[] searchRange(final int[] A, int B) {
        // find first occurrence
        int firstOccurrence = findFirstOccurrence(A, B);
        if (firstOccurrence == -1)
            return new int[]{-1, -1};

        // find last occurrence
        int lastOccurrence = findLastOccurrence(A, B);
        return new int[]{firstOccurrence, lastOccurrence};
    }

    // https://www.interviewbit.com/problems/sorted-insert-position/
    static int searchInsert(ArrayList<Integer> A, int B) {
        int n = A.size();
        // found or insert B at beginning
        if (B <= A.get(0))
            return 0;
        // insert B at end
        if (B > A.get(n - 1))
            return n;

        int l = 0, r = n - 1, res = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found B
            if (A.get(mid) == B)
                return mid;
            // search for smaller pos in left sub-array
            if (A.get(mid) < B)
                l = mid + 1;
                // search for larger pos in right sub-array
            else {
                res = mid;
                r = mid - 1;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/matrix-median/
    static int findMedian(int[][] A) {
        int m = A.length, n = A[0].length;
        // global minimum and maximum in matrix
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        // matrix is sorted by rows. Hence, check top for each row for min and bottom for each row for max
        for (int[] row : A) {
            min = Math.min(min, row[0]);
            max = Math.max(max, row[n - 1]);
        }
        // all entries in matrix are same
        if (min == max)
            return min;

        // #elements <= median in odd size array is (n+1)/2
        int req = (m * n + 1) / 2, res = -1;
        // check all values from min to max as median
        while (min <= max) {
            int mid = min + (max - min) / 2;
            int totalCnt = 0;
            // count for numbers <= mid in each row
            for (int[] row : A)
                totalCnt += lowerBoundPos(row, mid) + 1;
            // if mid has fewer elements than required, try higher value as median
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

    // https://www.interviewbit.com/problems/allocate-books/
    static int books(int[] A, int B) {
        int n = A.length;
        // #students > books
        if (B > n)
            return -1;

        int sum = 0, max = Integer.MIN_VALUE;
        for (int pages : A) {
            sum += pages;
            max = Math.max(max, pages);
        }

        // min for range will be case when each student gets one book
        // max for range will be when one student gets all the books
        int l = max, r = sum, res = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // if arrangement is possible try for lower value
            if (isPagesPossible(A, B, mid)) {
                res = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return res;
    }

    // util to check if arrangement is possible with maxLimit for #pages for each student
    private static boolean isPagesPossible(int[] pages, int students, int maxLimit) {
        int currSum = 0;

        for (int page : pages) {
            // add more pages to curr student
            currSum += page;
            // curr student cannot read more. Bring new student
            if (currSum > maxLimit) {
                currSum = page;
                students--;
                // if #students exceeded
                if (students == 0)
                    return false;
            }
        }

        return true;
    }

    // https://www.interviewbit.com/problems/painters-partition-problem/
    static int paint(int A, int B, int[] C) {
        int p = 10000003, sum = 0, max = 0;
        // find maximum board length and total board length
        for (int board : C) {
            sum = (sum + board) % p;
            max = Math.max(max, board);
        }

        // min for range will be case when each painter paints one. Hence, time will be length of max board
        // max for range will be when one painter paints all. Hence, time will be sum of all lengths
        int l = max, r = sum, res = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found a solution. Look for a lower time cost
            if (canPaint(C, A, mid, p)) {
                res = mid;
                r = mid - 1;
            }
            // else increase the time limit for a possible solution
            else
                l = mid + 1;
        }
        // multiply length by time and take mod
        return (int) ((res * (long) B) % p);
    }

    // util to check if it is possible to paint all the boards with current max time per board
    private static boolean canPaint(int[] boards, int painters, int maxTime, int p) {
        int currSum = 0;

        for (int cost : boards) {
            // allocate board to same painter
            currSum = (currSum + cost) % p;
            // current painter cannot paint more. Add new painter
            if (currSum > maxTime) {
                currSum = cost;
                painters--;
                // if #painters exceeded
                if (painters == 0)
                    return false;
            }
        }

        return true;
    }

    // https://www.interviewbit.com/problems/implement-power-function/
    static int pow(int x, int n, int d) {
        // pow(0, n) = 0
        if (x == 0)
            return 0;
        // x1 to avoid overflow
        long x1 = x, res = 1;

        while (n > 0) {
            // pow(x, 2k+1) = pow(x, 2k) * x
            if (n % 2 == 1)
                res = (res * x1) % d;
            // pow(x, 2k) = pow (x^2, k)
            x1 = (x1 * x1) % d;
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
        int l = 0, r = n - 1, res = -1;

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
                res = mid;
                r = mid - 1;
            }
        }

        return res;
    }

    // util to find previous greater positions for all elements
    private static int[] findPreviousGreater(int[] A) {
        int n = A.length;
        int[] res = new int[n];
        res[n - 1] = -1;
        Stack<Integer> s = new Stack<>();
        s.push(n - 1);

        for (int i = n - 2; i >= 0; i--) {
            res[i] = -1;
            // pop elements from stack and mark previous greater as the current element
            while (!s.empty() && A[s.peek()] <= A[i])
                res[s.pop()] = i;

            s.push(i);
        }

        return res;
    }

    // util to find next greater positions for all elements
    private static int[] findNextGreater(int[] A) {
        int n = A.length;
        int[] res = new int[n];
        res[0] = n;
        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            res[i] = n;
            // pop elements from stack and mark next greater as the current element (use < to avoid double counting of subarrays)
            while (!s.empty() && A[s.peek()] < A[i])
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
                // if same factor (sqrt(x)) add one else both
                cnt += (x / i == i) ? 1 : 2;
            }
        }

        return cnt;
    }

    // util to get product of all factors
    private static int productOfFactors(int x) {
        // get count of factors
        int cnt = countFactors(x), p = 1000000007;
        // each factor will come in pair except for perfect squares. Hence, product will be pow(x, n/2) if even
        // or pow(x, n/2) * sqrt(x) if odd
        int res = pow(x, cnt / 2, p);
        if (cnt % 2 == 1)
            res = (int) ((res * (long) Math.sqrt(x)) % p);

        return res;
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

    // https://www.interviewbit.com/problems/median-of-array/
    static double findMedianSortedArrays(final List<Integer> A, final List<Integer> B) {
        // A must be the smaller array
        if (A.size() > B.size())
            return findMedianSortedArrays(B, A);

        int x = A.size(), y = B.size();
        int l = 0, r = x;
        // binary search for partition over x
        while (l <= r) {
            int partitionX = l + (r - l) / 2;
            // partitionX + partitionY = (x - partitionX) + (y - partitionY) + 1
            // added 1 to make sure partitionX has the extra element if x + y is odd
            int partitionY = (x + y + 1) / 2 - partitionX;
            // rightmost in left partition from array A
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : A.get(partitionX - 1);
            // leftmost in right partition from array A
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : A.get(partitionX);
            // rightmost in left partition from array B
            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : B.get(partitionY - 1);
            // leftmost in right partition from array B
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : B.get(partitionY);

            // correct partition. Found interested 4 elements of the middle
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                // result size is even. n/2 pos will be taken by max of left partitions and
                // (n/2 + 1) pos will be taken by min of right partitions
                if ((x + y) % 2 == 0)
                    return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
                // result size is odd. n/2 pos will be taken by max of left partitions
                // (because left partition has one element extra)
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

    // https://www.interviewbit.com/problems/rotated-sorted-array-search/
    static int search(final int[] A, int B) {
        int n = A.length;
        int l = 0, r = n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // found B
            if (A[mid] == B)
                return mid;
            // if left sub-array is strictly increasing
            if (A[0] <= A[mid]) {
                // B lies in left sub-array
                if (A[0] <= B && B < A[mid])
                    r = mid - 1;
                    // else search in right
                else
                    l = mid + 1;
            }
            // right sub-array is strictly increasing
            else {
                // B lies in right sub-array
                if (A[mid] < B && B <= A[n - 1])
                    l = mid + 1;
                    // else search in left
                else
                    r = mid - 1;
            }
        }

        return -1;
    }

    // https://www.interviewbit.com/problems/capacity-to-ship-packages-within-b-days/
    static int solve(int[] A, int B) {
        // min capacity will be the weight of the largest item and max capacity will be the total sum
        long max = A[0], sum = A[0];
        for (int i = 1; i < A.length; i++) {
            max = Math.max(max, A[i]);
            sum += A[i];
        }

        long l = max, r = sum, res = -1;
        // binary search on capacity
        while (l <= r) {
            long mid = l + (r - l) / 2;
            // update answer and search for better answer
            if (canShip(A, B, mid)) {
                res = mid;
                r = mid - 1;
            }
            // try increasing the capacity
            else {
                l = mid + 1;
            }
        }

        return (int) res;
    }

    // util to check if all the weights in A can be shipped in B days with capacity
    private static boolean canShip(int[] A, int B, long capacity) {
        long curr = 0;

        for (int val : A) {
            curr += val;
            // exceeded capacity, ship on the next day
            if (curr > capacity) {
                B--;
                // no more days left
                if (B <= 0)
                    return false;

                curr = val;
            }
        }

        return true;
    }
}