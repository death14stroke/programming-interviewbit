import java.util.*;

public class Array {
    // https://www.interviewbit.com/problems/spiral-order-matrix-i/
    static int[] spiralOrder(final int[][] A) {
        int n = A.length, m = A[0].length;
        int[] res = new int[m * n];
        // top, left, bottom, right
        int t = 0, l = 0, b = n - 1, r = m - 1, dir = 0, k = 0;

        // keep crossing off row(t++ and then b--) and column(l++ and then r--)
        while (k != m * n) {
            //noinspection EnhancedSwitchMigration
            switch (dir) {
                // move left to right top row
                case 0:
                    for (int i = l; i <= r; i++)
                        res[k++] = A[t][i];
                    t++;
                    dir = 1;
                    break;
                // move top to bottom rightmost row
                case 1:
                    for (int i = t; i <= b; i++)
                        res[k++] = A[i][r];
                    r--;
                    dir = 2;
                    break;
                // move right to left bottom row
                case 2:
                    for (int i = r; i >= l; i--)
                        res[k++] = A[b][i];
                    b--;
                    dir = 3;
                    break;
                // move bottom to top leftmost row
                case 3:
                    for (int i = b; i >= t; i--)
                        res[k++] = A[i][l];
                    l++;
                    dir = 0;
            }
        }

        return res;
    }

    // Stable sorting algorithms: except Quick Sort, Heap Sort (can be made stable if positions considered - performance cost)
    // See GFG for Bucket Sort
    // https://www.interviewbit.com/tutorial/insertion-sort-algorithm/
    static void insertionSort(int[] A) {
        for (int i = 1; i < A.length; i++) {
            int j = i - 1, key = A[i];
            // move elements of arr[0...i-1] that are greater than key, to one position ahead of their current position
            while (j >= 0 && A[j] > key) {
                A[j + 1] = A[j];
                j--;
            }
            // insert the element from unsorted list at the hole created
            A[j + 1] = key;
        }
    }

    // https://www.interviewbit.com/tutorial/merge-sort-algorithm/
    static void mergeSort(int[] A, int l, int r) {
        // if size > 1
        if (l < r) {
            int mid = l + (r - l) / 2;
            // recursively sort two sub-arrays
            mergeSort(A, l, mid);
            mergeSort(A, mid + 1, r);
            // merge the sorted sub-arrays
            merge(A, l, mid, r);
        }
    }

    // util to merge two sub-arrays
    private static void merge(int[] A, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;
        // merge the two sub-arrays
        while (i <= m && j <= r) {
            if (A[i] <= A[j])
                temp[k++] = A[i++];
            else
                temp[k++] = A[j++];
        }
        // append remaining of left sub-array
        while (i <= m)
            temp[k++] = A[i++];
        // else append remaining of right sub-array
        while (j <= r)
            temp[k++] = A[j++];

        // copy temp array into original array
        for (i = 0; i < temp.length; i++)
            A[i + l] = temp[i];
    }

    // https://www.interviewbit.com/tutorial/quicksort-algorithm/
    static void quickSort(int[] A, int l, int r) {
        // size > 1
        if (l < r) {
            // pi is partitioning index, arr[pi] is now at right place
            int pi = partition(A, l, r);
            // Recursively sort elements before partition and after partition
            quickSort(A, l, pi - 1);
            quickSort(A, pi + 1, r);
        }
    }

    /* This function takes last element as pivot, places the pivot element at its correct position in sorted array,
     and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot */
    private static int partition(int[] A, int l, int r) {
        int pivot = A[r], pi = l;
        for (int i = l; i < r; i++) {
            // if current element is smaller than or equal to pivot
            if (A[i] <= pivot)
                swap(A, i, pi++);
        }

        // swap arr[pi] and arr[high] (or pivot)
        swap(A, pi, r);
        // position of pivot
        return pi;
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    // https://www.interviewbit.com/tutorial/selection-sort/
    static void selectionSort(int[] A) {
        for (int i = 0; i < A.length; i++) {
            int pos = findMinIndex(A, i);
            // bring the minimum position to start
            swap(A, i, pos);
        }
    }

    // find index of minimum element in the array
    private static int findMinIndex(int[] A, int start) {
        int minPos = start;
        for (int i = start + 1; i < A.length; i++) {
            if (A[i] < A[minPos])
                minPos = i;
        }

        return minPos;
    }

    // https://www.interviewbit.com/tutorial/bubble-sort/
    static void bubbleSort(int[] A) {
        int n = A.length;
        boolean swap = true;
        // loop till no swap was performed in any iteration
        while (swap) {
            swap = false;
            // push larger element to the end of the array
            for (int i = 0; i < n - 1; i++) {
                if (A[i] > A[i + 1]) {
                    swap(A, i, i + 1);
                    swap = true;
                }
            }

            // last element is maximum now so not needed to check it for swap
            n--;
        }
    }

    // https://www.geeksforgeeks.org/counting-sort/
    static void countingSort(int[] A) {
        int n = A.length;
        // find the range
        int min = A[0], max = A[0];
        for (int i = 1; i < n; i++) {
            min = Math.min(min, A[i]);
            max = Math.max(max, A[i]);
        }

        // create count array and update frequency of each element
        int[] count = new int[max - min + 1];
        for (int val : A)
            count[val - min]++;
        // take cumulative sum to get the position
        for (int i = 1; i < count.length; i++)
            count[i] += count[i - 1];

        int[] temp = new int[n];
        // begin from end for stable sorting
        for (int i = n - 1; i >= 0; i--) {
            // get position of current element and put in temp array
            int pos = A[i] - min;
            temp[count[pos] - 1] = A[i];
            // update position for next element with same value
            count[pos]--;
        }
        // copy temp array to input
        System.arraycopy(temp, 0, A, 0, n);
    }

    // https://www.geeksforgeeks.org/radix-sort/
    static void radixSort(int[] A) {
        int max = A[0];
        for (int i = 1; i < A.length; i++)
            max = Math.max(max, A[i]);
        // start counting sort from LSB position of the maximum element
        for (int exp = 1; max / exp > 0; exp *= 10)
            countingSort(A, exp);
    }

    private static void countingSort(int[] A, int exp) {
        // create count array and update frequency of each element for current digit
        int[] count = new int[10];
        for (int val : A)
            count[(val / exp) % 10]++;
        // take cumulative sum to get the position
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        int n = A.length;
        int[] temp = new int[n];
        // begin from end for stable sorting
        for (int i = n - 1; i >= 0; i--) {
            // get position of current element and put in temp array
            int pos = (A[i] / exp) % 10;
            temp[count[pos] - 1] = A[i];
            // update position for next element with same value
            count[pos]--;
        }
        // copy temp array to input
        System.arraycopy(temp, 0, A, 0, n);
    }

    // https://www.geeksforgeeks.org/bucket-sort-2/
    @SuppressWarnings("unchecked")
    static void bucketSort(int[] A, int B) {
        int n = A.length;
        // find max and min
        int min = A[0], max = A[0];
        for (int i = 1; i < n; i++) {
            min = Math.min(min, A[i]);
            max = Math.max(max, A[i]);
        }
        // range of each bucket = ceil(total / #buckets)
        float delta = (max - min) / (B - 1f);
        // init empty buckets
        List<Integer>[] buckets = new List[B];
        for (int i = 0; i < B; i++)
            buckets[i] = new ArrayList<>();

        // put each element in corresponding bucket
        for (int val : A) {
            int index = Math.round((val - min) / delta);
            buckets[index].add(val);
        }
        // sort each bucket
        for (List<Integer> bucket : buckets)
            Collections.sort(bucket);

        int k = 0;
        // merge all buckets
        for (List<Integer> bucket : buckets) {
            for (int val : bucket)
                A[k++] = val;
        }
    }

    // https://www.interviewbit.com/problems/max-non-negative-subarray/
    static int[] maxSet(final int[] A) {
        int n = A.length;
        long sum = 0, maxSum = Long.MIN_VALUE;
        int start = 0, maxStart = 0, maxEnd = 0;

        for (int i = 0; i < n; i++) {
            // if non-negative number, expand current subset
            if (A[i] >= 0)
                sum += A[i];
            else {
                // update maximum sum subset if current sum is higher or current subset is larger
                if (maxSum < sum || (maxSum == sum && i - start > maxEnd - maxStart)) {
                    maxSum = sum;
                    maxStart = start;
                    maxEnd = i;
                }
                // reset current subset
                sum = 0;
                start = i + 1;
            }
        }
        // update maximum sum subset ending at last element (if non-negative)
        if (A[n - 1] >= 0 && (maxSum < sum || (maxSum == sum && n - start > maxEnd - maxStart))) {
            maxStart = start;
            maxEnd = n;
        }

        return Arrays.copyOfRange(A, maxStart, maxEnd);
    }

    // https://www.interviewbit.com/problems/large-factorial/
    static String largeFactorial(int A) {
        // digits of output in reverse order
        int[] res = new int[500];
        // 0! = 1! = 1
        res[0] = 1;
        // no of digits in output
        int resSize = 1;
        // calculate n! = 2 * 3 * ... * n
        for (int x = 2; x <= A; x++)
            resSize = multiply(res, x, resSize);

        // build string with output digits
        StringBuilder builder = new StringBuilder();
        for (int i = resSize - 1; i >= 0; i--)
            builder.append(res[i]);
        return builder.toString();
    }

    // util to multiply digits array with x
    private static int multiply(int[] res, int x, int resSize) {
        int carry = 0;
        // multiply each digit with x and update carry
        for (int i = 0; i < resSize; i++) {
            int prod = res[i] * x + carry;
            res[i] = prod % 10;
            carry = prod / 10;
        }

        // since we are multiply whole x, remaining carry can be >= 10. Hence, while loop
        while (carry != 0) {
            res[resSize++] = carry % 10;
            carry /= 10;
        }

        return resSize;
    }

    // https://www.interviewbit.com/problems/pick-from-both-sides/
    static int pickFromBothSides(int[] A, int B) {
        int n = A.length;
        // calculate and save prefix sum of first B elements
        int[] prefixSum = new int[B + 1];
        for (int i = 1; i <= B; i++)
            prefixSum[i] = A[i - 1] + prefixSum[i - 1];

        int suffixSum = 0, res = prefixSum[B];
        // compute running suffix sum and update the max sum possible with i elements from end and (B - i) from front
        for (int i = 1; i <= B; i++) {
            suffixSum += A[n - i];
            res = Math.max(res, prefixSum[B - i] + suffixSum);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/min-steps-in-infinite-grid/
    static int coverPoints(int[] A, int[] B) {
        int steps = 0;
        // get minimum steps required from previous point to the next and sum up
        for (int i = 1; i < A.length; i++) {
            // minimum steps from (x1, y1) to (x2, y2) is moving diagonally and then towards the coordinate which is not reached yet =
            // max of diagonal distance between both coordinates
            steps += Math.max(Math.abs(A[i] - A[i - 1]), Math.abs(B[i] - B[i - 1]));
        }

        return steps;
    }

    // https://www.interviewbit.com/problems/minimum-lights-to-activate/
    static int minLights(int[] A, int B) {
        int n = A.length;
        // initial start position will be min of first unreached position and the last position
        int i = Math.min(B - 1, n - 1), res = 0;
        // keep jumping to the end
        while (i < n) {
            // keep moving backwards till a bulb is found
            while (i >= 0 && A[i] == 0)
                i--;
            // if reached before start or an already lit bulb, no solution possible
            if (i < 0 || A[i] == -1)
                return -1;

            // else there is bulb not currently turned on
            // turn on current bulb, mark as on and jump to next unreached position
            A[i] = -1;
            res++;
            i += B;

            // if all positions are covered
            if (i >= n)
                return res;
            // jump to minimum of last position of array or position where next light should be
            i = Math.min(i + B - 1, n - 1);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/maximum-sum-triplet/
    static int maxSumTriplet(int[] A) {
        int n = A.length;
        // calculate right maximum array such that rightMax[i] = max(A[i]...A[n - 1])
        int[] rightMax = new int[n];
        rightMax[n - 1] = A[n - 1];
        for (int i = n - 2; i >= 0; i--)
            rightMax[i] = Math.max(rightMax[i + 1], A[i]);
        // use a treeSet to keep track of element just smaller than current
        TreeSet<Integer> leftMax = new TreeSet<>();
        leftMax.add(A[0]);

        int maxSum = Integer.MIN_VALUE;
        // select A[j] one by one and check for corresponding A[i] and A[k]
        for (int j = 1; j < n - 1; j++) {
            // choose optimum A[k]
            int ak = rightMax[j + 1];
            // j < k always so check if A[j] < A[k]
            if (A[j] < ak) {
                // get A[i] for A[j]
                Integer ai = leftMax.lower(A[j]);
                // if there exists an element just smaller than A[j] on its left
                if (ai != null)
                    maxSum = Math.max(maxSum, ai + A[j] + ak);
                // add A[j] to the set for finding just smaller in next iteration
                leftMax.add(A[j]);
            }
        }

        return maxSum;
    }

    // https://www.interviewbit.com/problems/max-sum-contiguous-subarray/
    static int maxSubArray(final int[] A) {
        int maxSoFar = A[0], currMax = A[0];

        for (int i = 1; i < A.length; i++) {
            // check if current element is greater than the previous sum
            currMax = Math.max(A[i], currMax + A[i]);
            // check if sum of the sub-array till now is greater than any other previous sub-arrays
            maxSoFar = Math.max(maxSoFar, currMax);
        }

        return maxSoFar;
    }

    // https://www.interviewbit.com/problems/add-one-to-number/
    static int[] plusOne(int[] A) {
        int n = A.length;
        int pos = 0;
        // skip leading zeroes
        while (pos < n && A[pos] == 0)
            pos++;
        // if the number is zero
        if (pos == n)
            return new int[]{1};

        int carry = 1;
        // add 1 to last digit and keep adding carry till the first non-zero digit
        for (int i = n - 1; i >= pos; i--) {
            int sum = A[i] + carry;
            A[i] = sum % 10;
            carry = sum / 10;
        }

        // #digits remained same - return the same sub-array
        if (carry == 0)
            return Arrays.copyOfRange(A, pos, n);
        // add 1 digit at the beginning for carry
        int[] res = new int[n - pos + 1];
        res[0] = carry;
        System.arraycopy(A, pos, res, 1, n - pos);
        return res;
    }

    // https://www.interviewbit.com/problems/maximum-absolute-difference/
    static int maxArr(int[] A) {
        /*
         |A[i] - A[j]| + |i - j| =
         Case 1: (A[i] - A[j]) + (i - j) = (A[i] + i) - (A[j] + j)
         Case 2: (-A[i] + A[j]) + (-i + j) = -(A[i] + i) + (A[j] + j)
         Case 3: (A[i] - A[j]) + (-i + j) = (A[i] - i) - (A[j] - j)
         Case 4: (-A[i] + A[j]) + (i - j) = -(A[i] - i) + (A[j] - j)

         Hence ans = max([(A[i] - i) - (A[j] - j)], [(A[i] + i) - (A[j] + j)])
        */
        // min and max for A[i] - i
        int min1 = Integer.MAX_VALUE, max1 = Integer.MIN_VALUE;
        // min and max for A[i] + i
        int min2 = Integer.MAX_VALUE, max2 = Integer.MIN_VALUE;

        for (int i = 0; i < A.length; i++) {
            min1 = Math.min(min1, A[i] - i);
            max1 = Math.max(max1, A[i] - i);
            min2 = Math.min(min2, A[i] + i);
            max2 = Math.max(max2, A[i] + i);
        }

        return Math.max(max1 - min1, max2 - min2);
    }

    // https://www.interviewbit.com/problems/partitions/
    static int partitions(int n, int[] A) {
        // calculate total sum
        int s = 0;
        for (int val : A)
            s += val;
        // total sum cannot be divided into 3 parts
        if (s % 3 != 0)
            return 0;

        int s1 = s / 3, s2 = 2 * s1;
        // sum = running sum, cnt = total positions where sum is s1 till now
        int sum = 0, cnt = 0, res = 0;

        for (int i = 0; i < n - 1; i++) {
            sum += A[i];
            // if sum at ith position is s2, it can be paired with all prev positions where sum is s1
            if (sum == s2)
                res += cnt;
            // if sum at ith position is s1, increment the count of positions where sum is s1
            if (sum == s1)
                cnt++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/maximum-area-of-triangle/
    static int maxArea(String[] A) {
        int r = A.length, c = A[0].length();
        // calculate first and last occurrence from top for each color in each column
        int[][] top = new int[3][c], bottom = new int[3][c];
        // calculate first and last occurrence from left for each color among all columns
        int[] left = new int[3], right = new int[3];

        for (int i = 0; i < 3; i++) {
            Arrays.fill(top[i], Integer.MAX_VALUE);
            Arrays.fill(bottom[i], Integer.MIN_VALUE);
        }
        Arrays.fill(left, Integer.MAX_VALUE);
        Arrays.fill(right, Integer.MIN_VALUE);

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int color = mapColor(A[i].charAt(j));

                left[color] = Math.min(left[color], j);
                right[color] = Math.max(right[color], j);
                top[color][j] = Math.min(top[color][j], i);
                bottom[color][j] = Math.max(bottom[color][j], i);
            }
        }

        // area = 0.5 * l * h. Find max for the variable part, (l * h)
        int maxProd = 0;
        // loop for each column
        for (int i = 0; i < c; i++) {
            // select color of top vertex of base
            for (int x = 0; x < 3; x++) {
                // select color of bottom vertex of base
                for (int y = 0; y < 3; y++) {
                    // if color is same or the vertices are not present in the column
                    if (x == y || top[x][i] == Integer.MAX_VALUE || bottom[y][i] == Integer.MIN_VALUE)
                        continue;

                    // calculate base of the triangle
                    int base = bottom[y][i] - top[x][i] + 1;
                    // color of third vertex which forms the height
                    int z = 3 - x - y;
                    // check if the third vertex lies on the left of current column
                    if (left[z] != Integer.MAX_VALUE)
                        maxProd = Math.max(maxProd, base * (i - left[z] + 1));
                    // check if the third vertex lies on the right of current column
                    if (right[z] != Integer.MIN_VALUE)
                        maxProd = Math.max(maxProd, base * (right[z] - i + 1));
                }
            }
        }

        // return actual area
        return (int) Math.ceil(maxProd / 2.0);
    }

    // util to map 'r', 'g' and 'b' to 0, 1 and 2 resp
    private static int mapColor(char c) {
        //noinspection EnhancedSwitchMigration
        switch (c) {
            case 'r':
                return 0;
            case 'g':
                return 1;
            case 'b':
                return 2;
            default:
                return -1;
        }
    }

    // https://www.interviewbit.com/problems/flip/
    static int[] flip(String A) {
        // use Kadane's algorithm to find the largest sequence of 0's
        int[] res = new int[2];
        int start = 0, diff = 0, maxDiff = Integer.MIN_VALUE;

        for (int i = 0; i < A.length(); i++) {
            // 0 is required hence +1, 1 is not required hence -1
            diff += (A.charAt(i) == '0') ? 1 : -1;
            // number of 1's is more in the current sequence. So reset starting point to next possible 0
            if (diff < 0) {
                diff = 0;
                start = i + 1;
            }
            // this sequence has more 0's than all previous ones
            else if (maxDiff < diff) {
                maxDiff = diff;
                res[0] = start + 1;
                res[1] = i + 1;
            }
        }

        // all 1's in the string
        if (res[0] == 0)
            return new int[0];
        return res;
    }

    // https://www.interviewbit.com/problems/max-min-05542f2f-69aa-4253-9cc7-84eb7bf739c4/
    // two methods: this and Tournament method (https://www.geeksforgeeks.org/maximum-and-minimum-in-an-array/)
    static int maxMin(int[] A) {
        // #comparisions = n is odd: 3 * (n - 1) / 2
        // If n is even: 1 initial comparison for initializing min and max, and 3 * (n - 2) / 2 comparisons for rest of the elements
        // = 1 + 3 * (n - 2) / 2 = 3 * n / 2 - 2
        int n = A.length;
        // minimum and maximum for the array
        int min, max;
        // starting index
        int i;
        // if array length is odd, same min-max and start from 1
        if (n % 2 == 1) {
            min = max = A[0];
            i = 1;
        }
        // else one comparison to find initial min-max and start from 2
        else {
            if (A[0] < A[1]) {
                min = A[0];
                max = A[1];
            } else {
                min = A[1];
                max = A[0];
            }

            i = 2;
        }
        // take elements in pairs - compare larger of them with max and smaller with min and update global max-min
        for (; i + 1 < n; i += 2) {
            if (A[i] > A[i + 1]) {
                max = Math.max(max, A[i]);
                min = Math.min(min, A[i + 1]);
            } else {
                max = Math.max(max, A[i + 1]);
                min = Math.min(min, A[i]);
            }
        }

        return min + max;
    }

    // https://www.interviewbit.com/problems/merge-intervals/
    static class Interval {
        int start = 0, end = 0;
    }

    static ArrayList<Interval> insertInterval(ArrayList<Interval> intervals, Interval newInterval) {
        // rearrange new interval
        if (newInterval.start > newInterval.end) {
            int temp = newInterval.start;
            newInterval.start = newInterval.end;
            newInterval.end = temp;
        }

        int n = intervals.size();
        // given list is empty
        if (n == 0) {
            intervals.add(newInterval);
            return intervals;
        }
        // Case-1: new interval is at the first position
        if (newInterval.end < intervals.get(0).start) {
            intervals.add(0, newInterval);
            return intervals;
        }
        // Case-2: new interval is at the last position
        if (newInterval.start > intervals.get(n - 1).end) {
            intervals.add(newInterval);
            return intervals;
        }
        // Case-3: new interval overlaps all intervals
        if (newInterval.start <= intervals.get(0).start && newInterval.end >= intervals.get(n - 1).end) {
            ArrayList<Interval> res = new ArrayList<>();
            res.add(newInterval);
            return res;
        }

        // Case 4-5: new interval fits between other intervals or is overlapping
        ArrayList<Interval> res = new ArrayList<>();
        int i = 0;

        while (i < n) {
            boolean overlap = doesOverlap(newInterval, intervals.get(i));
            if (!overlap) {
                res.add(intervals.get(i));
                // Case-4: new interval lies between two intervals
                if (newInterval.start > intervals.get(i).end && newInterval.end < intervals.get(i + 1).start)
                    res.add(newInterval);

                i++;
                continue;
            }

            // Case-5: merge overlapping intervals
            Interval temp = new Interval();
            temp.start = Math.min(newInterval.start, intervals.get(i).start);
            // traverse until intervals are overlapping
            while (i < n && overlap) {
                temp.end = Math.max(newInterval.end, intervals.get(i).end);
                overlap = i + 1 < n && doesOverlap(newInterval, intervals.get(i + 1));
                i++;
            }

            res.add(temp);
        }

        return res;
    }

    // util to check if two intervals overlap
    private static boolean doesOverlap(Interval a, Interval b) {
        return Math.min(a.end, b.end) >= Math.max(a.start, b.start);
    }

    // https://www.interviewbit.com/problems/merge-overlapping-intervals/
    static ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        // sort all intervals by start time
        intervals.sort(Comparator.comparingInt(a -> a.start));

        ArrayList<Interval> res = new ArrayList<>();
        res.add(intervals.get(0));

        for (int i = 1; i < intervals.size(); i++) {
            Interval top = res.get(res.size() - 1), curr = intervals.get(i);
            // does not overlap
            if (top.end < curr.start)
                res.add(curr);
            else
                top.end = Math.max(top.end, curr.end);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/perfect-peak-of-array/
    static int perfectPeak(int[] A) {
        int n = A.length;
        // leftMax[i] = max(A[0]...A[i])
        int[] leftMax = new int[n];
        leftMax[0] = A[0];
        for (int i = 1; i < n; i++)
            leftMax[i] = Math.max(leftMax[i - 1], A[i]);

        // rightMin = min(A[i]...A[n - 1])
        int rightMin = A[n - 1];
        // check for all middle positions from end
        for (int i = n - 2; i >= 1; i--) {
            // if A[i] is greater than max of all lefts and less than min of all rights
            if (leftMax[i - 1] < A[i] && A[i] < rightMin)
                return 1;

            // add current position in rightMin calculation
            rightMin = Math.min(rightMin, A[i]);
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/kth-row-of-pascals-triangle/
    static int[] kthRowOfPascal(int A) {
        // kth row is C(k, 0) to C(k, k)
        int[] res = new int[A + 1];
        res[0] = 1;
        // compute 1st half of the row
        // C(k, i) = C(k, i - 1) * (k - (i - 1)) / i;
        for (int i = 1; i <= A / 2; i++)
            res[i] = res[i - 1] * (A - i + 1) / i;
        // 2nd half of the row is the mirror of the 1st half
        for (int i = A / 2 + 1; i <= A; i++)
            res[i] = res[A - i];

        return res;
    }

    // https://www.interviewbit.com/problems/spiral-order-matrix-ii/
    static int[][] generateMatrix(int A) {
        int[][] res = new int[A][A];
        int k = 1, l = 0, t = 0, b = A - 1, r = A - 1;

        while (k <= A * A) {
            // move from left to right in topmost row
            for (int i = l; i <= r; i++)
                res[t][i] = k++;
            t++;
            // move from top to bottom in rightmost row
            for (int i = t; i <= b; i++)
                res[i][r] = k++;
            r--;
            // move from right to left in bottommost row
            for (int i = r; i >= l; i--)
                res[b][i] = k++;
            b--;
            // move from bottom to top in leftmost row
            for (int i = b; i >= t; i--)
                res[i][l] = k++;
            l++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/pascal-triangle/
    static int[][] pascalTriangle(int A) {
        int[][] res = new int[A][];
        if (A == 0)
            return res;

        // first row
        res[0] = new int[]{1};
        // compute the remaining rows from previous rows
        for (int k = 1; k < A; k++) {
            res[k] = new int[k + 1];
            // first and last elements of the row are 1
            res[k][0] = res[k][k] = 1;
            // middle ones are the sum of the upper row entries
            for (int i = 1; i < k; i++)
                res[k][i] = res[k - 1][i] + res[k - 1][i - 1];
        }

        return res;
    }

    // https://www.interviewbit.com/problems/anti-diagonals/
    static int[][] antiDiagonals(int[][] A) {
        int n = A.length;
        int[][] res = new int[2 * n - 1][];
        // upper half of the matrix
        for (int k = 0; k < n; k++) {
            res[k] = new int[k + 1];
            int i = 0, j = k, cnt = 0;
            // traversing the row
            while (i < n && j >= 0)
                res[k][cnt++] = A[i++][j--];
        }
        // lower half of the matrix
        for (int k = 1; k < n; k++) {
            res[n + k - 1] = new int[n - k];
            int i = k, j = n - 1, cnt = 0;
            // traversing the row
            while (i < n && j >= 0)
                res[n + k - 1][cnt++] = A[i++][j--];
        }

        return res;
    }

    // https://www.interviewbit.com/problems/triplets-with-sum-between-given-range/
    static int tripletsInRange(String[] A) {
        // take first three numbers
        float x = Float.parseFloat(A[0]), y = Float.parseFloat(A[1]), z = Float.parseFloat(A[2]);

        for (int i = 3; i < A.length; i++) {
            // if satisfy condition, return
            if (x + y + z > 1 && x + y + z < 2)
                return 1;
            // if sum is greater, replace the largest element in the triplet
            if (x + y + z >= 2) {
                if (x > y && x > z)
                    x = Float.parseFloat(A[i]);
                else if (y > x && y > z)
                    y = Float.parseFloat(A[i]);
                else
                    z = Float.parseFloat(A[i]);
            }
            // sum is smaller, replace the smallest element in the triplet
            else {
                if (x < y && x < z)
                    x = Float.parseFloat(A[i]);
                else if (y < x & y < z)
                    y = Float.parseFloat(A[i]);
                else
                    z = Float.parseFloat(A[i]);
            }
        }

        // check for the last triplet
        if (x + y + z > 1 && x + y + z < 2)
            return 1;
        return 0;
    }

    // https://www.interviewbit.com/problems/balance-array/
    static int balanceArray(int[] A) {
        int n = A.length;
        // calculate prefix sum for odd and even positions
        // leftOdd[i] = prefix sum of odd positions till i-1
        int[] leftOdd = new int[n], leftEven = new int[n];
        int odd = 0, even = 0;

        for (int i = 0; i < n; i++) {
            leftOdd[i] = odd;
            leftEven[i] = even;
            // update left prefix sum for odd and even positions
            if (i % 2 == 0)
                even += A[i];
            else
                odd += A[i];
        }

        // calculate running suffix sum for odd and even positions after the current position
        int rightOdd = 0, rightEven = 0, cnt = 0;
        for (int i = n - 1; i >= 0; i--) {
            // if the special property holds
            if (leftOdd[i] + rightEven == leftEven[i] + rightOdd)
                cnt++;
            // update right even and odd suffix sums
            if (i % 2 == 0)
                rightEven += A[i];
            else
                rightOdd += A[i];
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/find-duplicate-in-array/
    // method-2: multiply visited element in array with -1
    static int multiRepeatedNumber(final int[] A) {
        // consider array as linked list. The point of cycle will be the duplicate number
        int slow = A[0], fast = A[A[0]];
        // keep moving fast pointer at double rate till both meet
        while (slow != fast) {
            slow = A[slow];
            fast = A[A[fast]];
        }

        // set one pointer at start
        fast = A[0];
        // move both pointer at same speed
        while (slow != fast) {
            slow = A[slow];
            fast = A[fast];
        }

        // there is no '0' in array - hence did not intersect
        if (slow == 0)
            return -1;
        // return intersection point (duplicate number)
        return slow;
    }

    // https://www.interviewbit.com/problems/maximum-consecutive-gap/
    static int unsortedMaximumGap(final int[] A) {
        int n = A.length;
        // less than 2 elements
        if (n <= 1)
            return 0;

        // maximum and minimum of the array
        int max = A[0], min = A[0];
        for (int i = 1; i < n; i++) {
            max = Math.max(max, A[i]);
            min = Math.min(min, A[i]);
        }

        // max and min values for each bucket
        int[] maxBucket = new int[n], minBucket = new int[n];
        Arrays.fill(maxBucket, Integer.MIN_VALUE);
        Arrays.fill(minBucket, Integer.MAX_VALUE);

        // average gap between buckets
        float delta = (max - min) / (n - 1f);
        // for each value in array
        for (int val : A) {
            // find index of bucket
            int index = Math.round((val - min) / delta);
            // update maximum and minimum for the bucket
            maxBucket[index] = Math.max(maxBucket[index], val);
            minBucket[index] = Math.min(minBucket[index], val);
        }

        int prev = maxBucket[0];
        // maximum gap is the max of difference between minimum value of current bucket and maximum value of previous bucket
        int maxGap = 0;

        for (int i = 1; i < n; i++) {
            if (minBucket[i] != Integer.MAX_VALUE) {
                // update maxGap
                maxGap = Math.max(maxGap, minBucket[i] - prev);
                prev = maxBucket[i];
            }
        }

        return maxGap;
    }

    // https://www.interviewbit.com/problems/sort-array-with-squares/
    static int[] sortArrayWithSquares(int[] A) {
        int n = A.length;
        // index of first positive number in array
        int pi = findFirstPositiveIndex(A);
        // output array
        int[] res = new int[n];
        // i - start of negative half, j - start of positive half
        int i = pi - 1, j = pi, k = 0;
        // traverse both halves
        while (i >= 0 && j < n) {
            // square of negative number will come first
            if (Math.abs(A[i]) < Math.abs(A[j])) {
                res[k++] = A[i] * A[i];
                i--;
            }
            // square of positive number will come first
            else {
                res[k++] = A[j] * A[j];
                j++;
            }
        }

        // traverse remaining of negative half
        while (i >= 0) {
            res[k++] = A[i] * A[i];
            i--;
        }
        // traverse remaining of positive half
        while (j < n) {
            res[k++] = A[j] * A[j];
            j++;
        }

        return res;
    }

    // util to find index of first positive number in array
    private static int findFirstPositiveIndex(int[] A) {
        int n = A.length;
        int l = 0, r = n - 1, pi = -1;
        // binary search for first positive number
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // if negative, search in 2nd half
            if (A[mid] < 0) {
                l = mid + 1;
            }
            // update answer and search in 1st half
            else {
                pi = mid;
                r = mid - 1;
            }
        }

        return pi;
    }

    // https://www.interviewbit.com/problems/largest-number/
    static String largestNumber(final int[] A) {
        int n = A.length;
        // convert to string array for sorting
        String[] arr = new String[n];
        for (int i = 0; i < n; i++)
            arr[i] = String.valueOf(A[i]);
        // sort array with custom comparator
        Arrays.sort(arr, (s1, s2) -> {
            String ab = s1 + s2, ba = s2 + s1;
            // greater string combination should come first
            return ba.compareTo(ab);
        });

        // skip leading zeroes
        int i = 0;
        while (i < n && arr[i].equals("0"))
            i++;
        // if all are zeroes
        if (i == n)
            return "0";
        // append remaining numbers to string
        StringBuilder builder = new StringBuilder();
        for (; i < n; i++)
            builder.append(arr[i]);
        return builder.toString();
    }

    // https://www.interviewbit.com/problems/rotate-matrix/
    static void rotate(ArrayList<ArrayList<Integer>> A) {
        int n = A.size();
        // compute for 4 x 4 matrix on paper to get the idea
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = A.get(i).get(j);

                A.get(i).set(j, A.get(n - 1 - j).get(i));
                A.get(n - 1 - j).set(i, A.get(n - 1 - i).get(n - 1 - j));
                A.get(n - 1 - i).set(n - 1 - j, A.get(j).get(n - 1 - i));
                A.get(j).set(n - 1 - i, temp);
            }
        }
    }

    // https://www.interviewbit.com/problems/next-permutation/
    static int[] nextPermutation(int[] A) {
        int n = A.length, last = n - 2;
        // find the last increasing pair
        while (last >= 0 && A[last] > A[last + 1])
            last--;

        // all elements are in decreasing order (i.e last permutation)
        if (last < 0) {
            reverseArray(A, 0, n - 1);
            return A;
        }

        // find the successor of A[last]
        int nextPos = findRightMostSuccessor(A, last + 1, n - 1, A[last]);
        // swap A[last] with its successor
        swap(A, last, nextPos);
        // reverse the array from last + 1 till end
        reverseArray(A, last + 1, n - 1);

        return A;
    }

    // util to find next greatest element (this half will be decreasingly sorted so can find in O(log n))
    private static int findRightMostSuccessor(int[] A, int l, int r, int x) {
        int pos = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // search in left half
            if (A[mid] < x) {
                r = mid - 1;
            }
            // mark current position as possible successor. Search for smaller number in right half
            else {
                pos = mid;
                l = mid + 1;
            }
        }

        return pos;
    }

    // util to reverse part of array
    private static void reverseArray(int[] A, int start, int end) {
        while (start < end)
            swap(A, start++, end--);
    }

    // https://www.interviewbit.com/problems/find-permutation/
    static ArrayList<Integer> findPerm(final String A, int B) {
        ArrayList<Integer> res = new ArrayList<>();
        // dual end pointers for deque of numbers 1...B
        int min = 1, max = B;
        // if 'I' is encountered then add the smallest element remaining in output else add the largest element remaining in output
        for (char c : A.toCharArray()) {
            if (c == 'I')
                res.add(min++);
            else
                res.add(max--);
        }

        // add the last remaining element
        res.add(max);

        return res;
    }

    // https://www.interviewbit.com/problems/noble-integer/
    static int nobleInteger(int[] A) {
        Arrays.sort(A);

        int n = A.length;
        for (int i = 0; i < n; i++) {
            // skip elements equal to current number
            while (i + 1 < n && A[i + 1] == A[i])
                i++;

            // number of elements greater than this number is equal to the number
            if (n - i - 1 == A[i])
                return 1;
        }

        // noble integer not found
        return -1;
    }

    // https://www.interviewbit.com/problems/wave-array/
    static int[] wave(int[] A) {
        Arrays.sort(A);
        // swap odd indices with their previous
        for (int i = 1; i < A.length; i += 2)
            swap(A, i, i - 1);

        return A;
    }

    // https://www.interviewbit.com/problems/hotel-bookings-possible/
    static boolean hotel(ArrayList<Integer> arrive, ArrayList<Integer> depart, int K) {
        Collections.sort(arrive);
        Collections.sort(depart);

        int n = arrive.size();
        int i = 0, j = 0, cnt = 0;

        while (i < n && j < n) {
            // if another guest arrives before previous ones departure, allot new room, move to next guest
            if (arrive.get(i) < depart.get(j)) {
                cnt++;
                i++;
                // max rooms exceeded
                if (cnt > K)
                    return false;
            }
            // previous guest left, empty his room, wait for next guest to leave
            else {
                cnt--;
                j++;
            }
        }

        return true;
    }

    // https://www.interviewbit.com/problems/max-distance/
    static int maximumGap(final int[] A) {
        int n = A.length;
        // right maximums for each position
        int[] rightMax = new int[n];
        rightMax[n - 1] = A[n - 1];
        // rightMax[i] = max(A[i...n-1])
        for (int i = n - 2; i >= 0; i--)
            rightMax[i] = Math.max(rightMax[i + 1], A[i]);

        int i = 0, j = 0, maxGap = 0;
        // traverse both arrays from left to right and maximize j-i
        while (i < n && j < n) {
            if (A[i] <= rightMax[j]) { // update max gap and check for further j
                maxGap = Math.max(maxGap, j - i);
                j++;
            } else { // try another i which might have a smaller value in array
                i++;
                // try j with gap higher than current maxGap
                j = i + maxGap + 1;
            }
        }

        return maxGap;
    }

    // https://www.interviewbit.com/problems/maximum-unsorted-subarray/
    static int[] subUnSort(int[] A) {
        int n = A.length;
        // running maximum and minimum of array
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        // start and end indices for result
        int start = -1, end = -1;
        // compute running maximum from start
        for (int i = 0; i < n; i++) {
            max = Math.max(max, A[i]);
            // if maximum != current element, update end boundary
            if (max != A[i])
                end = i;
        }

        // if no end boundary computed, array is already sorted
        if (end == -1)
            return new int[]{-1};
        // compute running minimum from the end
        for (int i = n - 1; i >= 0; i--) {
            min = Math.min(min, A[i]);
            // if minimum != current element, update start boundary
            if (min != A[i])
                start = i;
        }
        // found unsorted interval
        return new int[]{start, end};
    }

    // https://www.interviewbit.com/problems/set-matrix-zeros/
    static void setZeroes(ArrayList<ArrayList<Integer>> A) {
        int m = A.size(), n = A.get(0).size();
        // whether first column and first row need to be converted
        boolean firstCol = false, firstRow = false;
        // check if first column contains 0
        for (int i = 0; i < m; i++) {
            if (A.get(i).get(0) == 0) {
                firstCol = true;
                break;
            }
        }
        // check if first row contains 0
        for (int j = 0; j < n; j++) {
            if (A.get(0).get(j) == 0) {
                firstRow = true;
                break;
            }
        }
        // check remaining matrix
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // if A[i][j] is 0, mark its row and column heads (A[i][0] and A[0][j]) as 0
                if (A.get(i).get(j) == 0) {
                    A.get(i).set(0, 0);
                    A.get(0).set(j, 0);
                }
            }
        }

        // for each cell in remaining matrix
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // if row or column header is 0, convert whole row or column to 0
                if (A.get(i).get(0) == 0 || A.get(0).get(j) == 0)
                    A.get(i).set(j, 0);
            }
        }
        // if first column needs to be converted
        if (firstCol) {
            for (int i = 0; i < m; i++)
                A.get(i).set(0, 0);
        }
        // if first row needs to be converted
        if (firstRow) {
            for (int j = 0; j < n; j++)
                A.get(0).set(j, 0);
        }
    }

    // https://www.interviewbit.com/problems/first-missing-integer/
    static int firstMissingPositive(int[] A) {
        // partition the array with positives at left and non-positives at right
        int pi = partitionPositives(A);
        // multiply the number at pos by -1 to mark as visited in the positive sub-array
        // if all numbers are present, whole sub-array array will be negative now
        for (int i = 0; i < pi; i++) {
            int pos = Math.abs(A[i]) - 1;
            if (pos < A.length)
                A[pos] *= -1;
        }

        // if any position is positive after marking, then it is the missing number
        for (int i = 0; i < pi; i++) {
            if (A[i] > 0)
                return i + 1;
        }
        // else the largest positive in the array is missing
        return pi + 1;
    }

    // util to partition array with positives on the left
    private static int partitionPositives(int[] A) {
        int pi = 0;

        for (int i = 0; i < A.length; i++) {
            // move A[i] to the left half and update partition index
            if (A[i] > 0) {
                swap(A, i, pi);
                pi++;
            }
        }

        return pi;
    }

    // https://www.interviewbit.com/problems/maximum-sum-square-submatrix/
    static int maxSumSquareSubMatrix(int[][] A, int B) {
        int n = A.length;
        // pre-calculate sum of sub-matrix ending at A[i][j]
        // first calculate sum of i x 1 and 1 x i sub-matrices
        for (int i = 1; i < n; i++) {
            A[0][i] += A[0][i - 1];
            A[i][0] += A[i - 1][0];
        }
        // sum of sub-matrix ending at A[i][j]
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++)
                A[i][j] += A[i - 1][j] + A[i][j - 1] - A[i - 1][j - 1];
        }

        // max sum of sub-matrix of size B. Initialize with the first sub-matrix possible
        int res = A[B - 1][B - 1];
        // calculate and compare sub-matrix sum ending at (i, j) of B x B for corner cases (i.e i = B - 1 or j = B - 1)
        for (int i = B; i < n; i++) {
            res = Math.max(res, A[B - 1][i] - A[B - 1][i - B]);
            res = Math.max(res, A[i][B - 1] - A[i - B][B - 1]);
        }
        // calculate and compare sub-matrix sum ending at (i, j) of B x B
        for (int i = B; i < n; i++) {
            for (int j = B; j < n; j++) {
                int matrixSum = A[i][j] - A[i - B][j] - A[i][j - B] + A[i - B][j - B];
                res = Math.max(res, matrixSum);
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/repeat-and-missing-number-array/
    static int[] repeatedNumber(final int[] A) {
        long n = A.length;
        // sum of 1st n numbers
        long nSum = n * (n + 1L) / 2L;
        // sum of squares of 1st n numbers
        long n2Sum = n * (n + 1L) * (2L * n + 1L) / 6L;
        // sum and square sum of all numbers in the array
        // sum = nSum + x - y, and
        // sum2 = n2Sum + x^2 - y^2, where x is the repeating number and y is the missing number
        long sum = 0, sum2 = 0;
        for (int val : A) {
            sum += val;
            sum2 += (long) val * val;
        }

        // x - y
        long diffRepeatMiss = sum - nSum;
        // x + y = (x^2 - y^2)/(x - y)
        long sumRepeatMiss = (sum2 - n2Sum) / diffRepeatMiss;
        // x = (x - y + x + y) / 2
        int x = (int) (diffRepeatMiss + sumRepeatMiss) / 2;
        // substitute x in x + y equation
        int y = (int) sumRepeatMiss - x;

        return new int[]{x, y};
    }

    // https://www.interviewbit.com/problems/n3-repeat-number/
    static int repeatedNumber(final List<Integer> A) {
        int n = A.size();
        // empty list, no repeating number
        if (n == 0)
            return -1;
        // single element
        if (n == 1)
            return A.get(0);

        // Moore's voting algorithm variant. Use two candidates
        int first = A.get(0), second = A.get(0), count1 = 0, count2 = 0;

        for (int val : A) {
            if (val == first) { // first candidate repeating
                count1++;
            } else if (val == second) { // second candidate repeating
                count2++;
            } else if (count1 == 0) { // change first candidate
                count1 = 1;
                first = val;
            } else if (count2 == 0) { // change second candidate
                count2 = 1;
                second = val;
            } else { // number is not equal to both first and second
                count1--;
                count2--;
            }
        }

        count1 = count2 = 0;
        // count actual frequencies of first and second
        for (int val : A) {
            if (val == first)
                count1++;
            else if (val == second)
                count2++;
        }

        // first is the answer
        if (count1 > n / 3)
            return first;
        // second is the answer
        if (count2 > n / 3)
            return second;
        // no n/3 repeating found
        return -1;
    }

    // https://www.interviewbit.com/problems/leaders-in-an-array/
    static ArrayList<Integer> leadersInArray(ArrayList<Integer> A) {
        ArrayList<Integer> res = new ArrayList<>();
        int n = A.size();
        if (n == 0)
            return res;

        // keep track of max elements to the right of A[i]
        int rightMax = A.get(n - 1);
        // right most is always leader
        res.add(rightMax);
        // check for each element from the end
        for (int i = n - 2; i >= 0; i--) {
            // A[i] is a leader. Add to result and update rightMax
            if (A.get(i) > rightMax) {
                res.add(A.get(i));
                rightMax = A.get(i);
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/reorder-data-in-log-files/
    static String[] reorderLogs(String[] A) {
        // push all digit logs at the end in their relative order
        int pi = partition(A);
        // sort the letter logs
        Arrays.sort(A, 0, pi, (s1, s2) -> {
            // find the end position of identifiers
            int pos1 = s1.indexOf("-"), pos2 = s2.indexOf("-");
            int comp = s1.substring(pos1 + 1).compareTo(s2.substring(pos2 + 1));
            // if components are equal, sort by identifiers
            if (comp == 0)
                return s1.substring(0, pos1).compareTo(s2.substring(0, pos2));
            return comp;
        });

        return A;
    }

    // util to partition into letter logs and digit logs
    private static int partition(String[] A) {
        int n = A.length, pi = n - 1;
        // start from the end
        for (int i = n - 1; i >= 0; i--) {
            int pos = A[i].indexOf("-");
            // if digit log found, push at the end and update the partitioning index
            if (Character.isDigit(A[i].charAt(pos + 1))) {
                String temp = A[i];
                A[i] = A[pi];
                A[pi--] = temp;
            }
        }

        return pi + 1;
    }

    // https://www.interviewbit.com/problems/move-zeroes/
    static int[] moveZeroes(int[] A) {
        int pi = 0;
        for (int i = 0; i < A.length; i++) {
            // swap non-zero element to the left
            if (A[i] != 0)
                swap(A, i, pi++);
        }

        return A;
    }

    // https://www.interviewbit.com/problems/make-equal-elements-array/
    static int solve(int[] A, int B) {
        int min = A[0];
        for (int i = 1; i < A.length; i++)
            min = Math.min(min, A[i]);

        // minimum element will always be incremented
        // case-1: max - min = 2 * B -> increment min to (min + B)
        // case-2: max - min = B -> increment min to (min + B) or decrement max to (max - B)
        int k = min + B;
        for (int val : A) {
            // if an element is not equal to (min + B) or cannot be transformed to (min + B)
            if ((val < k && val + B != k) || (val > k && val - B != k))
                return 0;
        }

        return 1;
    }

    // https://www.interviewbit.com/problems/segregate-0s-and-1s-in-an-array/
    static int[] segregateZeroesAndOnes(int[] A) {
        int pi = 0;
        for (int i = 0; i < A.length; i++) {
            // swap zeroes to the left
            if (A[i] == 0)
                swap(A, i, pi++);
        }

        return A;
    }

    // https://www.interviewbit.com/problems/array-sum/
    static int[] addArrays(int[] A, int[] B) {
        // make sure A is the larger array
        if (A.length < B.length) {
            int[] temp = A;
            A = B;
            B = temp;
        }

        int m = A.length, n = B.length;
        int i = m - 1, j = n - 1, carry = 0;
        // keep adding the LSBs and carry
        while (i >= 0 && j >= 0) {
            int sum = A[i] + B[j] + carry;
            A[i] = sum % 10;
            carry = sum / 10;
            // update pointers
            i--;
            j--;
        }
        // keep adding carry to the remaining digits of larger number
        while (i >= 0 && carry > 0) {
            int sum = A[i] + carry;
            A[i] = sum % 10;
            carry = sum / 10;
            // update pointer
            i--;
        }

        // #digits is the same as larger number
        if (carry == 0)
            return A;
        // add one extra digit for the carry
        int[] res = new int[m + 1];
        res[0] = carry;
        System.arraycopy(A, 0, res, 1, m);
        return res;
    }

    // https://www.interviewbit.com/problems/set-intersection/
    static int setIntersection(int[][] A) {
        // sort intervals by their end point
        Arrays.sort(A, (a, b) -> a[1] == b[1] ? a[0] - b[0] : a[1] - b[1]);
        // keep track of last 2 points of the set
        int secondLast = A[0][1] - 1, last = A[0][1], res = 2;
        for (int i = 1; i < A.length; i++) {
            int start = A[i][0], end = A[i][1];
            // if no overlap, add 2 points
            if (start > last) {
                secondLast = end - 1;
                last = end;
                res += 2;
            }
            // else only one point in set - add one point
            else if (start == last || start > secondLast) {
                secondLast = last;
                last = end;
                res++;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/occurence-of-each-number/
    static int[] findOccurrences(int[] A) {
        // treemap to store frequency of elements sorted by key
        Map<Integer, Integer> map = new TreeMap<>();
        for (int val : A)
            map.put(val, map.getOrDefault(val, 0) + 1);

        int i = 0;
        // copy map values to result array
        int[] res = new int[map.size()];
        for (int val : map.values())
            res[i++] = val;

        return res;
    }

    // https://www.interviewbit.com/problems/chips-factory/
    static int[] chipsFactory(int[] A) {
        int pi = 0;

        for (int i = 0; i < A.length; i++) {
            // swap non-zeroes to the left
            if (A[i] > 0)
                swap(A, i, pi++);
        }

        return A;
    }

    // https://www.interviewbit.com/problems/greater-than-all/
    static int greaterThanAll(int[] A) {
        int res = 0, max = 0;

        for (int val : A) {
            // if current element is greater than elements so far
            if (val > max) {
                max = val;
                res++;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/diagonal-flip/
    public int[][] diagonalFlip(int[][] A) {
        int n = A.length;
        // swap the nodes except diagonal on the upper half with its counterpart opposite of the diagonal
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = A[i][j];
                A[i][j] = A[j][i];
                A[j][i] = temp;
            }
        }

        return A;
    }

    // https://www.interviewbit.com/problems/positive-negative/
    static int[] positiveNegative(int[] A) {
        int[] res = new int[2];

        for (int val : A) {
            // update positives count
            if (val > 0)
                res[0]++;
                // update negatives count
            else if (val < 0)
                res[1]++;
        }

        return res;
    }
}