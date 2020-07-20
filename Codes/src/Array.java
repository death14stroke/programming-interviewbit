import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;

public class Array {
    // https://www.interviewbit.com/problems/spiral-order-matrix-i/
    static int[] spiralOrder(final int[][] a) {
        int n = a.length, m = a[0].length;
        // top, left, bottom, right
        int t = 0, l = 0, b = n - 1, r = m - 1, dir = 0;

        int[] out = new int[m * n];
        int k = 0;

        // keep crossing off row(t++ and then b--) and column(l++ and then r--)
        while (t <= b && l <= r) {
            switch (dir) {
                // move left to right top row
                case 0:
                    for (int i = l; i <= r; i++) {
                        out[k] = a[t][i];
                        k++;
                    }
                    t++;
                    dir = 1;
                    break;
                // move top to bottom rightmost row
                case 1:
                    for (int i = t; i <= b; i++) {
                        out[k] = a[i][r];
                        k++;
                    }
                    r--;
                    dir = 2;
                    break;
                // move right to left bottom row
                case 2:
                    for (int i = r; i >= l; i--) {
                        out[k] = a[b][i];
                        k++;
                    }
                    b--;
                    dir = 3;
                    break;
                // move bottom to top leftmost row
                case 3:
                    for (int i = b; i >= t; i--) {
                        out[k] = a[i][l];
                        k++;
                    }
                    l++;
                    dir = 0;
            }
        }
        return out;
    }

    // https://www.interviewbit.com/tutorial/insertion-sort-algorithm/
    static void insertionSort(int[] a) {
        int n = a.length;

        for (int i = 1; i < n; i++) {
            int j = i - 1;
            int key = a[i];

            // move elements of arr[0..i-1], that are greater than key,
            // to one position ahead of their current position
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }

            // insert the element from unsorted list at the hole created
            a[j + 1] = key;
        }
    }

    // https://www.interviewbit.com/tutorial/merge-sort-algorithm/
    static void mergeSort(int[] a, int l, int r) {
        if (l != r) {
            int m = (l + r) / 2;

            mergeSort(a, l, m);
            mergeSort(a, m + 1, r);

            merge(a, l, m, r);
        }
    }

    private static void merge(int[] a, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;

        // merge the two sub-arrays
        while (i <= m && j <= r) {
            if (a[i] < a[j]) {
                temp[k] = a[i];
                i++;
            } else {
                temp[k] = a[j];
                j++;
            }
            k++;
        }

        // append remaining of left sub-array
        while (i <= m) {
            temp[k] = a[i];
            i++;
            k++;
        }

        // else append remaining of right sub-array
        while (j <= r) {
            temp[k] = a[j];
            j++;
            k++;
        }

        // copy temp array into original array
        for (i = 0; i < temp.length; i++)
            a[i + l] = temp[i];
    }

    // https://www.interviewbit.com/tutorial/quicksort-algorithm/
    static void quickSort(int[] a, int l, int r) {
        if (l != r) {
            // pi is partitioning index, arr[pi] is now at right place
            int pi = partition(a, l, r);

            // Recursively sort elements before partition and after partition
            quickSort(a, l, pi - 1);
            quickSort(a, pi + 1, r);
        }
    }

    /* This function takes last element as pivot, places the pivot element at its correct position in sorted array,
     and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot */
    private static int partition(int[] a, int l, int r) {
        int pivot = a[r];
        int pi = l;

        for (int i = l; i < r; i++) {
            // if current element is smaller than or equal to pivot
            if (a[i] <= pivot) {
                // swap arr[i] and arr[j]
                int temp = a[i];
                a[i] = a[pi];
                a[pi] = temp;

                pi++;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = a[r];
        a[r] = a[pi];
        a[pi] = temp;

        return pi;
    }

    // https://www.interviewbit.com/tutorial/selection-sort/
    static void selectionSort(int[] a) {
        int n = a.length;

        for (int i = 0; i < n; i++) {
            int pos = findMinIndex(a, i, n);

            if (i != pos) {
                int temp = a[i];
                a[i] = a[pos];
                a[pos] = temp;
            }
        }
    }

    // find index of minimum element in the array
    private static int findMinIndex(int[] a, int start, int end) {
        int min_pos = start;

        for (int i = start + 1; i < end; i++) {
            if (a[i] < a[min_pos])
                min_pos = i;
        }

        return min_pos;
    }

    // https://www.interviewbit.com/tutorial/bubble-sort/
    static void bubbleSort(int[] a) {
        int n = a.length;
        boolean swap = true;

        // loop till no swap was performed in any iteration
        while (swap) {
            swap = false;

            // push larger element to the end of the array
            for (int i = 0; i < n - 1; i++) {
                if (a[i] > a[i + 1]) {
                    int temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;

                    swap = true;
                }
            }

            // last element is maximum now so not needed to check it for swap
            n--;
        }
    }

    // https://www.interviewbit.com/problems/max-sum-contiguous-subarray/
    static int maxSubArray(int[] a) {
        int max_so_far = a[0], curr_max = a[0];

        for (int i = 1; i < a.length; i++) {
            // check if current element is greater than the previous sum
            curr_max = Math.max(a[i], curr_max + a[i]);
            // check if sum of the sub-array till now is greater than any other previous sub-arrays
            max_so_far = Math.max(max_so_far, curr_max);
        }
        return max_so_far;
    }

    // https://www.interviewbit.com/problems/min-steps-in-infinite-grid/
    static int coverPoints(int[] a, int[] b) {
        int steps = 0;
        // get minimum steps required from previous point to the next and sum up
        for (int i = 1; i < a.length; i++) {
            // minimum steps from (x1, y1) to (x2, y2) is moving diagonally
            // and then towards the coordinate which is not reached yet =
            // max of diagonal distance between both coordinates
            steps += Math.max(Math.abs(a[i] - a[i - 1]), Math.abs(b[i] - b[i - 1]));
        }
        return steps;
    }

    // https://www.interviewbit.com/problems/maximum-absolute-difference/
    static int maxArr(int[] a) {
        /*
         |a[i]-a[j]| + |i-j| =
         Case 1: (a[i]-a[j]) + (i-j) = (a[i]+i) - (a[j]+j)
         Case 2: (-a[i]+a[j]) + (-i+j) = -(a[i]+i) + (a[j]+j)
         Case 3: (a[i]-a[j]) + (-i+j) = (a[i]-i) - (a[j]-j)
         Case 4: (-a[i]+a[j]) + (i-j) = -(a[i]-i) + (a[j]-j)

         Hence ans = max([(a[i]-i)-(a[j]-j)], [(a[i]+i)-(a[j]+j)])
        */

        // min and max for a[i]-i
        int min1 = Integer.MAX_VALUE, max1 = Integer.MIN_VALUE;
        // min and max for a[i]+i
        int min2 = Integer.MAX_VALUE, max2 = Integer.MIN_VALUE;

        for (int i = 0; i < a.length; i++) {
            min1 = Math.min(min1, a[i] - i);
            max1 = Math.max(max1, a[i] - i);

            min2 = Math.min(min2, a[i] + i);
            max2 = Math.max(max2, a[i] + i);
        }

        return Math.max(max1 - min1, max2 - min2);
    }

    // https://www.interviewbit.com/problems/add-one-to-number/
    static int[] plusOne(int[] a) {
        int zero_index = 0;
        // find first non-zero digit of the number
        while (zero_index < a.length && a[zero_index] == 0) {
            zero_index++;
        }

        // if the number is zero
        if (zero_index == a.length) {
            return new int[]{1};
        }

        int carry = 1;
        int[] ans = new int[a.length - zero_index + 1];

        // add 1 to last digit and keep adding carry till the first non-zero digit
        for (int i = a.length - 1; i >= zero_index; i--) {
            int j = i + (ans.length - a.length);
            ans[j] = a[i] + carry;
            carry = ans[j] / 10;
            ans[j] %= 10;
        }
        // if there is carry left add at 0th position of answer array
        int start = 0;
        if (carry == 1)
            ans[0] = carry;
            // exclude the first zero in answer array
        else
            start = 1;

        return Arrays.copyOfRange(ans, start, ans.length);
    }

    // https://www.interviewbit.com/problems/repeat-and-missing-number-array/
    static int[] repeatedNumber(final int[] a) {
        int n = a.length;
        long sum = 0, sum2 = 0;

        // sum of 1st n numbers
        long n_sum = (long) n * (long) (n + 1) / (long) 2;
        // sum of squares of 1st n numbers
        long n2_sum = (long) n * (long) (n + 1) * (long) (2 * n + 1) / (long) 6;

        // sum and square sum of all numbers in the array
        // sum = n_sum + x - y
        // sum2 = n2_sum + x^2 - y^2 ,
        // where x is the repeating number and y is the missing number
        for (int value : a) {
            sum += value;
            sum2 += (long) Math.pow(value, 2);
        }

        // x - y
        long diff_repeat_miss = sum - n_sum;
        // x + y = (x^2 - y^2)/(x - y)
        long sum_repeat_miss = (sum2 - n2_sum) / diff_repeat_miss;

        // x = (x - y + x + y) / 2
        int repeat = (int) (diff_repeat_miss + sum_repeat_miss) / 2;
        // substitute x in x + y equation
        int missing = (int) sum_repeat_miss - repeat;

        return new int[]{repeat, missing};
    }

    // https://www.interviewbit.com/problems/flip/
    static int[] flip(String a) {
        // all 1's in the string
        if (!a.contains("0"))
            return new int[0];

        // use Kadane's algorithm to find largest sequence of 0's
        int[] res = new int[2];
        int start = 0, diff = 0, maxDiff = Integer.MIN_VALUE;

        for (int i = 0; i < a.length(); i++) {
            // 0 is required hence +1, 1 is not required hence -1
            diff += (a.charAt(i) == '0') ? 1 : -1;

            // number of 1's is more in the current sequence
            // so reset starting point to next possible 0
            if (diff < 0) {
                diff = 0;
                start = (a.charAt(i) == '0') ? i : i + 1;
            }
            // this sequence has more 0's than all previous ones
            else if (maxDiff < diff) {
                maxDiff = diff;
                res[0] = start + 1;
                res[1] = i + 1;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/max-non-negative-subarray/
    static int[] maxset(final int[] a) {
        // finding the largest positive sum
        long curr_sum = 0, max_sum = Long.MIN_VALUE;
        int curr_start = 0, curr_end = 0, start = -1, end = -1;

        boolean allNeg = true;
        for (int val : a) {
            if (val >= 0) {
                allNeg = false;
                break;
            }
        }

        // all numbers are negative
        if (allNeg)
            return new int[0];

        for (int i = 0; i < a.length; i++) {
            // if negative number comes, reset the sum and sub-array endpoints
            if (a[i] < 0) {
                curr_sum = 0;
                curr_start = i + 1;
                curr_end = i + 1;
            } else {
                curr_sum += a[i];

                // sum of current sub-array is more than previous ones OR
                // sum is equal but current sub-array is larger than the previous one
                if ((max_sum < curr_sum) || (max_sum == curr_sum && curr_end - curr_start > end - start)) {
                    start = curr_start;
                    end = i;
                    max_sum = curr_sum;
                }

                curr_end++;
            }
        }

        return Arrays.copyOfRange(a, start, end + 1);
    }

    // https://www.interviewbit.com/problems/spiral-order-matrix-ii/
    static int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int k = 1;
        int l = 0, t = 0, b = n - 1, r = n - 1;

        while (k <= n * n) {
            // move from left to right in topmost row
            for (int i = l; i <= r; i++) {
                res[t][i] = k;
                k++;
            }
            t++;

            // move from top to bottom in rightmost row
            for (int i = t; i <= b; i++) {
                res[i][r] = k;
                k++;
            }
            r--;

            // move from right to left in bottommost row
            for (int i = r; i >= l; i--) {
                res[b][i] = k;
                k++;
            }
            b--;

            // move from bottom to top in leftmost row
            for (int i = b; i >= t; i--) {
                res[i][l] = k;
                k++;
            }
            l++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/pascal-triangle/
    static int[][] solve(int n) {
        int[][] res = new int[n][];

        for (int i = 1; i <= n; i++) {
            // first row
            if (i == 1)
                res[i - 1] = new int[]{1};
                // second row
            else if (i == 2)
                res[i - 1] = new int[]{1, 1};
            else {
                res[i - 1] = new int[i];

                // first and last elements of the row are 1
                res[i - 1][0] = 1;
                res[i - 1][i - 1] = 1;

                // middle ones are the sum of the upper row entries
                for (int j = 1; j < i - 1; j++)
                    res[i - 1][j] = res[i - 2][j - 1] + res[i - 2][j];
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/kth-row-of-pascals-triangle/
    static int[] getRow(int k) {
        int[] res = new int[k + 1];

        // kth row is C(k, 0) to C(k, k)
        for (int i = 0; i <= k; i++)
            res[i] = (int) C(k, i);

        return res;
    }

    private static long C(int n, int k) {
        long ans, num = 1, den = 1;

        // C(n, k) = C(n, n-k)
        if (k > n / 2)
            k = n - k;

        for (int i = 0; i < k; i++) {
            num *= (n - i);
            den *= (k - i);
        }

        ans = num / den;
        return ans;
    }

    // https://www.interviewbit.com/problems/anti-diagonals/
    static int[][] diagonal(int[][] a) {
        int n = a.length;

        int[][] res = new int[2 * n - 1][];
        int pos = 0;

        // upper half of the matrix
        for (int k = 0; k < n; k++) {
            int i = 0, j = k, cnt = 0;
            res[pos] = new int[k + 1];

            // traversing the row
            while (i < n && j >= 0) {
                res[pos][cnt] = a[i][j];
                i++;
                j--;
                cnt++;
            }

            pos++;
        }

        // lower half of the matrix
        for (int k = 1; k < n; k++) {
            int i = k, j = n - 1, cnt = 0;
            res[pos] = new int[n - k];

            // traversing the row
            while (i < n && j >= 0) {
                res[pos][cnt] = a[i][j];
                i++;
                j--;
                cnt++;
            }

            pos++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/noble-integer/
    static int solve(int[] a) {
        int n = a.length;

        Arrays.sort(a);

        for (int i = 0; i < n; i++) {
            int k = i + 1;

            // skip elements equal to current number
            while (k < n && a[k] == a[i])
                k++;
            k--;

            // number of elements greater than this number is equal to the number
            if (n - 1 - k == a[i])
                return 1;

            i = k;
        }
        return -1;
    }

    // https://www.interviewbit.com/problems/triplets-with-sum-between-given-range/
    static int solve(String[] a) {
        // take first three numbers
        float x = Float.parseFloat(a[0]), y = Float.parseFloat(a[1]), z = Float.parseFloat(a[2]);

        for (int i = 3; i < a.length; i++) {
            // if satisfy condition, return
            if (x + y + z > 1 && x + y + z < 2)
                return 1;

                // if sum is greater, replace the largest element in the triplet
            else if (x + y + z > 2) {
                if (x > y && x > z)
                    x = Float.parseFloat(a[i]);
                else if (y > x && y > z)
                    y = Float.parseFloat(a[i]);
                else
                    z = Float.parseFloat(a[i]);
            }

            // sum is smaller, replace the smallest element in the triplet
            else {
                if (x < y && x < z)
                    x = Float.parseFloat(a[i]);
                else if (y < x & y < z)
                    y = Float.parseFloat(a[i]);
                else
                    z = Float.parseFloat(a[i]);
            }
        }

        // if satisfy condition
        if (x + y + z > 1 && x + y + z < 2)
            return 1;
        return 0;
    }

    // https://www.interviewbit.com/problems/largest-number/
    static String largestNumber(final int[] a) {
        String[] arr = new String[a.length];
        for (int i = 0; i < a.length; i++)
            arr[i] = String.valueOf(a[i]);

        // sort array with custom comparator
        Arrays.sort(arr, new Comparator<>() {
            @Override
            public int compare(String s1, String s2) {
                String ab = s1 + s2, ba = s2 + s1;
                return ba.compareTo(ab);
            }
        });

        // construct string from array and remove leading zeroes
        boolean isLeadingZero = true;
        StringBuilder builder = new StringBuilder();
        for (String str : arr) {
            if (isLeadingZero && str.charAt(0) == '0')
                isLeadingZero = true;
            else {
                isLeadingZero = false;
                builder.append(str);
            }
        }

        // if all are zeroes
        if (builder.toString().isEmpty())
            return "0";
        return builder.toString();
    }

    // https://www.interviewbit.com/problems/wave-array/
    static int[] wave(int[] a) {
        Arrays.sort(a);

        int i = 0;

        // sort and swap alternate numbers
        while (i + 1 < a.length) {
            int temp = a[i];
            a[i] = a[i + 1];
            a[i + 1] = temp;

            i += 2;
        }

        return a;
    }

    // https://www.interviewbit.com/problems/hotel-bookings-possible/
    static boolean hotel(ArrayList<Integer> arrive, ArrayList<Integer> depart, int k) {
        Collections.sort(arrive);
        Collections.sort(depart);

        int n = arrive.size();
        int i = 0, j = 0, cnt = 0;

        while (i < n && j < n) {
            // if another guest arrives before previous ones departure,
            // allot new room, move to next guest
            if (arrive.get(i) < depart.get(j)) {
                cnt++;
                i++;
            }

            // previous guest left, empty his room, wait for next guest to leave
            else {
                cnt--;
                j++;
            }

            // max rooms exceeded
            if (cnt > k)
                return false;
        }

        return true;
    }

    // https://www.interviewbit.com/problems/max-distance/
    static int maximumGap(final int[] a) {
        int n = a.length;

        // find left minimums and right maximums
        int[] lMins = new int[n], rMaxs = new int[n];

        lMins[0] = a[0];
        rMaxs[n - 1] = a[n - 1];

        for (int i = 1; i < n; i++) {
            // lMins[i] = min(a[0..i])
            lMins[i] = Math.min(a[i], lMins[i - 1]);
            // rMaxs[i] = max(a[i..n-1])
            rMaxs[n - 1 - i] = Math.max(a[n - 1 - i], rMaxs[n - i]);
        }

        int i = 0, j = 0, maxGap = 0;

        // traverse both arrays from left to right and maximize j-i
        while (i < n && j < n) {
            if (lMins[i] <= rMaxs[j]) {
                maxGap = Math.max(maxGap, j - i);
                j++;
            } else
                i++;
        }

        return maxGap;
    }

    // https://www.interviewbit.com/problems/maximum-unsorted-subarray/
    static ArrayList<Integer> subUnsort(ArrayList<Integer> a) {
        int n = a.size();
        int[] mins = new int[n], maxs = new int[n];

        mins[n - 1] = a.get(n - 1);
        maxs[0] = a.get(0);

        for (int i = 1; i < n; i++) {
            // maxs[i] = max from 0 to i
            maxs[i] = Math.max(maxs[i - 1], a.get(i));
            // mins[i] = min from n-1 down to i
            mins[n - i - 1] = Math.min(mins[n - i], a.get(n - i - 1));
        }

        int start = 0, end = n - 1;
        ArrayList<Integer> positions = new ArrayList<>();

        // if array is sorted mins = maxs = original array
        while (start < n && mins[start] == a.get(start))
            start++;

        // array is completely sorted
        if (start == n) {
            positions.add(-1);
            return positions;
        }

        while (end >= 0 && maxs[end] == a.get(end))
            end--;

        positions.add(start);
        positions.add(end);

        return positions;
    }

    // https://www.interviewbit.com/problems/find-duplicate-in-array/
    static int multiRepeatedNumber(final int[] a) {
        int n = a.length;

        for (int i = 0; i < n; i++) {
            int pos = Math.abs(a[i]);

            // encountered this number before
            if (a[pos] < 0)
                return pos;

            // mark as visited (negative)
            a[pos] = -a[pos];
        }

        // no duplicates
        return -1;
    }

    // https://www.interviewbit.com/problems/maximum-consecutive-gap/
    static int unsortedMaximumGap(final int[] a) {
        if (a.length < 2)
            return 0;

        Arrays.sort(a);

        int max_diff = 0;

        for (int i = 1; i < a.length; i++) {
            max_diff = Math.max(max_diff, a[i] - a[i - 1]);
        }

        return max_diff;
    }

    // https://www.interviewbit.com/problems/next-permutation/
    static int[] nextPermutation(int[] a) {
        int n = a.length, last = n - 2;

        // find the last increasing pair
        while (last >= 0 && a[last] > a[last + 1])
            last--;

        // all elements are in decreasing order (i.e last permutation)
        if (last < 0) {
            reverseArray(a, 0, n - 1);
            return a;
        }

        // find the successor of a[last]
        int nextPos = findRightMostSuccessor(a, last + 1, n, a[last]);

        // swap a[last] with its successor
        int temp = a[last];
        a[last] = a[nextPos];
        a[nextPos] = temp;

        // reverse the array from last + 1 till end
        reverseArray(a, last + 1, n - 1);

        return a;
    }

    private static int findRightMostSuccessor(int[] a, int start, int n, int k) {
        for (int i = n - 1; i >= start; i--) {
            if (a[i] > k)
                return i;
        }

        return n - 1;
    }

    private static void reverseArray(int[] a, int start, int end) {
        while (start < end) {
            int temp = a[start];
            a[start] = a[end];
            a[end] = temp;

            start++;
            end--;
        }
    }

    // https://www.interviewbit.com/problems/set-matrix-zeros/
    static void setZeroes(ArrayList<ArrayList<Integer>> a) {
        int m = a.size(), n = a.get(0).size();
        boolean[] rows = new boolean[m], columns = new boolean[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a.get(i).get(j) == 0) {
                    rows[i] = true;
                    columns[j] = true;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rows[i] || columns[j])
                    a.get(i).set(j, 0);
            }
        }
    }

    // https://www.interviewbit.com/problems/first-missing-integer/
    static int firstMissingPositive(int[] a) {
        // partition the array with positives at left and non-positives at right
        int pi = partitionPositives(a);

        // multiply the number at pos by -1 to mark as visited in the positive subarray
        // if all numbers are present, whole subarray array will be negative now
        for (int i = 0; i < pi; i++) {
            int pos = Math.abs(a[i]) - 1;
            if (pos >= 0 && pos < a.length)
                a[pos] *= -1;
        }

        // if any position is positive after marking, then it is the missing number
        for (int i = 0; i < pi; i++) {
            if (a[i] > 0)
                return i + 1;
        }

        // else the largest positive in the array is missing
        return pi + 1;
    }

    private static int partitionPositives(int[] a) {
        int pi = 0, pivot = 0;

        for (int i = 0; i < a.length; i++) {
            if (a[i] > pivot) {
                int temp = a[i];
                a[i] = a[pi];
                a[pi] = temp;

                pi++;
            }
        }

        return pi;
    }

    // https://www.interviewbit.com/problems/merge-intervals/
    static ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        // rearrange new interval
        if (newInterval.start > newInterval.end) {
            int temp = newInterval.start;
            newInterval.start = newInterval.end;
            newInterval.end = temp;
        }

        int n = intervals.size();

        // given list is empty
        if (n == 0) {
            ArrayList<Interval> ans = new ArrayList<>();
            ans.add(newInterval);

            return ans;
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
        if (newInterval.start <= intervals.get(0).start &&
                newInterval.end >= intervals.get(n - 1).end) {
            ArrayList<Interval> ans = new ArrayList<>();
            ans.add(newInterval);

            return ans;
        }

        // Case 4-5: new interval fits between other intervals or is overlapping
        boolean overlap;
        ArrayList<Interval> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            overlap = doesOverlap(newInterval, intervals.get(i));

            if (!overlap) {
                ans.add(intervals.get(i));

                // Case-4: new interval lies between two intervals
                if (newInterval.start > intervals.get(i).end && newInterval.end < intervals.get(i + 1).start)
                    ans.add(newInterval);

                continue;
            }

            // Case-5: merge overlapping intervals
            Interval temp = new Interval();
            temp.start = Math.min(newInterval.start, intervals.get(i).start);

            // traverse until intervals are overlapping
            while (i < n && overlap) {
                temp.end = Math.max(newInterval.end, intervals.get(i).end);

                if (i == n - 1)
                    overlap = false;
                else
                    overlap = doesOverlap(intervals.get(i + 1), newInterval);

                i++;
            }

            i--;
            ans.add(temp);
        }

        return ans;
    }

    private static boolean doesOverlap(Interval a, Interval b) {
        return Math.min(a.end, b.end) >= Math.max(a.start, b.start);
    }

    // https://www.interviewbit.com/problems/merge-overlapping-intervals/
    static ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        intervals.sort((a, b) -> a.start - b.start);

        Stack<Interval> s = new Stack<>();
        s.push(intervals.get(0));

        int pos = 1;

        while (pos != intervals.size()) {
            Interval top = s.peek(), curr = intervals.get(pos);

            if (top.end < curr.start)
                s.push(curr);
            else
                top.end = Math.max(top.end, curr.end);

            pos++;
        }

        return new ArrayList<>(s);
    }

    public static class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    // TODO: wrong answer
    // https://www.interviewbit.com/problems/simple-queries/
    static ArrayList<Integer> solve(ArrayList<Integer> a, ArrayList<Integer> b) {
        int n = a.size();
        Collections.sort(a);

        for (int i = 0; i < n; i++) {
            a.set(i, productOfFactors(a.get(i)));
        }

        long[] sum = new long[n];
        sum[0] = 1;

        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + (int) Math.pow(2, i);
        }

        for (long val : sum)
            System.out.print(val + " ");
        System.out.println();

        long last = (long) Math.pow(2, n - 1);
        for (int i = 0; i < b.size(); i++) {
            int k = b.get(i);
            b.set(i, a.get(binarySearch(sum, (int) (last - k))));
        }

        return b;
    }

    private static int binarySearch(long[] a, int k) {
        int res = 0;
        int l = 0, r = a.length - 1;

        while (l <= r) {
            int mid = (l + r) / 2;
            if (k < a[mid]) {
                res = mid;
                r = mid - 1;
            } else
                l = mid + 1;
        }

        return res;
    }

    private static int productOfFactors(int x) {
        int cnt = countFactors(x), p = 1000000007;

        int ans = moduloPower(x, cnt / 2, p);
        if (cnt % 2 == 1)
            ans = (ans * (int) Math.sqrt(x)) % p;

        return ans;
    }

    private static int countFactors(int x) {
        int cnt = 0;

        for (int i = 1; i * i <= x; i++) {
            if (x % i == 0) {
                if (i == x / i)
                    cnt++;
                else
                    cnt += 2;
            }
        }

        return cnt;
    }

    private static int moduloPower(int x, int y, int p) {
        int res = 1;

        while (y > 0) {
            if (y % 2 == 1)
                res = (res * x) % p;

            y = (y / 2) % p;
            x = (x * x) % p;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/n3-repeat-number/
    static int repeatedNumber(final List<Integer> a) {
        int n = a.size();

        // empty list, no repeating number
        if (n == 0)
            return -1;

        // single element
        if (n == 1)
            return a.get(0);

        // Moore's voting algorithm variant. Use two candidates
        int first = Integer.MIN_VALUE, second = Integer.MAX_VALUE;
        int count1 = 0, count2 = 0;

        for (int val : a) {
            // first candidate repeating
            if (val == first)
                count1++;

                // second candidate repeating
            else if (val == second)
                count2++;

                // change first candidate
            else if (count1 == 0) {
                count1 = 1;
                first = val;
            }

            // change second candidate
            else if (count2 == 0) {
                count2 = 1;
                second = val;
            }

            // number is not equal to both first and second
            else {
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;

        // count actual frequencies of first and second
        for (int val : a) {
            if (val == first)
                count1++;
            else if (val == second)
                count2++;
        }

        // first is the answer
        if (count1 > n / 3)
            return first;

        // second is the anwser
        if (count2 > n / 3)
            return second;

        // no n/3 repeating found
        return -1;
    }

    // https://www.interviewbit.com/problems/rotate-matrix/
    static void rotate(ArrayList<ArrayList<Integer>> a) {
        int n = a.size();

        for (int level = 0; level < n / 2; level++) {
            for (int y = level; y < n - level - 1; y++) {
                int temp = a.get(level).get(y);

                a.get(level).set(y, a.get(n - y - 1).get(level));
                a.get(n - y - 1).set(level, a.get(n - level - 1).get(n - y - 1));
                a.get(n - level - 1).set(n - y - 1, a.get(y).get(n - level - 1));
                a.get(y).set(n - level - 1, temp);
            }
        }
    }

    // https://www.interviewbit.com/problems/find-permutation/
    static ArrayList<Integer> findPerm(final String s, int n) {
        ArrayList<Integer> out = new ArrayList<>();

        // dual end pointers for deque of numbers 1..n
        int min = 1, max = n;

        // if I is encountered then add the smallest element remaining in output
        // else add the largest element remaining in output
        for (char c : s.toCharArray()) {
            if (c == 'I') {
                out.add(min);
                min++;
            } else {
                out.add(max);
                max--;
            }
        }

        // add the last remaining element
        out.add(max);

        return out;
    }

    // https://www.interviewbit.com/problems/maxspprod/
    static int maxSpecialProduct(ArrayList<Integer> a) {
        int n = a.size();
        long p = 1000000007;
        long ans = -1;

        int[] leftSpecials = new int[n];
        int[] rightSpecials = new int[n];

        // get left and right specials with next greater element logic
        calculateLeftSpecials(a, leftSpecials, n);
        calculateRightSpecials(a, rightSpecials, n);

        // maximize the product first then take modulo
        for (int i = 0; i < n; i++)
            ans = Math.max(ans, (long) leftSpecials[i] * (long) rightSpecials[i]);

        return (int) (ans % p);
    }

    private static void calculateRightSpecials(ArrayList<Integer> a, int[] rightSpecials, int n) {
        // no element on right
        rightSpecials[n - 1] = 0;
        Stack<Integer> s = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!s.empty() && a.get(i) > a.get(s.peek())) {
                int top = s.pop();
                rightSpecials[top] = i;
            }

            s.push(i);
        }
    }

    private static void calculateLeftSpecials(ArrayList<Integer> a, int[] leftSpecials, int n) {
        // no element on left
        leftSpecials[0] = 0;
        Stack<Integer> s = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!s.empty() && a.get(i) > a.get(s.peek())) {
                int top = s.pop();
                leftSpecials[top] = i;
            }

            s.push(i);
        }
    }

    // https://www.interviewbit.com/problems/minimum-lights-to-activate/
    static int minLights(int[] A, int B) {
        int n = A.length;
        // initial start position will be min of range of light and the last position
        int i = Math.min(B - 1, n - 1), cnt = 0;

        // keep jumping to the end
        while (i >= 0) {
            // if there is light
            if (A[i] == 1) {
                // turn on the light, mark as on and jump to next unlighted position
                cnt++;
                A[i] = 2;
                i += B;

                // if all positions are covered
                if (i >= n)
                    return cnt;

                // jump to minimum of last position of array or position where light should be
                i = Math.min(n - 1, i + B - 1);
            }
            // light was already turned on
            else if (A[i] == 2)
                return -1;
                // no light, move backwards
            else
                i--;
        }

        // some positions are left unlighted (i < n - 1)
        return -1;
    }

    // https://www.interviewbit.com/problems/maximum-sum-triplet/
    static int maxSumTriplet(ArrayList<Integer> A) {
        int n = A.size();

        // calculate max suffix array such that maxSuffix[i] = max(A[i]...A[n - 1])
        int[] maxSuffix = new int[n];
        maxSuffix[n - 1] = A.get(n - 1);
        for (int i = n - 2; i >= 0; i--)
            maxSuffix[i] = Math.max(maxSuffix[i + 1], A.get(i));

        // use a treeSet to keep track of element just smaller than current
        TreeSet<Integer> leftMax = new TreeSet<>();
        leftMax.add(A.get(0));

        int maxSum = Integer.MIN_VALUE;
        // select A[j] one by one and check for corresponding A[i] and A[k]
        for (int j = 1; j < n - 1; j++) {
            // choose optimum A[k]
            int ak = maxSuffix[j + 1];
            // j < k always so check if A[j] < A[k]
            if (A.get(j) < ak) {
                // get A[i] for A[j]
                Integer ai = leftMax.lower(A.get(j));
                // if there exists an element just smaller than A[j] on its left
                if (ai != null)
                    maxSum = Math.max(maxSum, ai + A.get(j) + ak);

                // add A[i] to the set for finding just smaller in next iteration
                leftMax.add(A.get(j));
            }
        }

        return maxSum;
    }

    // https://www.interviewbit.com/problems/maximum-sum-square-submatrix/
    static int maxSumSquareSubmatrix(int[][] A, int B) {
        int n = A.length;
        // pre-calculate sum of sub-matrix ending at A[i][j]
        // first calculate sum of i x 1 and 1 x i sub-matrices
        for (int i = 1; i < n; i++) {
            A[0][i] = A[0][i] + A[0][i - 1];
            A[i][0] = A[i][0] + A[i - 1][0];
        }

        // sum of sub-matrix ending at A[i][j]
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++)
                A[i][j] = A[i][j] + A[i - 1][j] + A[i][j - 1] - A[i - 1][j - 1];
        }

        // max sum of sub-matrix of size B. Initialize with the first sub-matrix possible
        int maxSum = A[B - 1][B - 1];
        // calculate and compare sub-matrix sum ending at (i, j) of B x B for corner cases (i.e i = B - 1 or j = B - 1)
        for (int i = B; i < n; i++) {
            maxSum = Math.max(maxSum, A[B - 1][i] - A[B - 1][i - B]);
            maxSum = Math.max(maxSum, A[i][B - 1] - A[i - B][B - 1]);
        }

        // calculate and compare sub-matrix sum ending at (i, j) of B x B
        for (int i = B; i < n; i++) {
            for (int j = B; j < n; j++) {
                int matrixSum = A[i][j] - A[i - B][j] - A[i][j - B] + A[i - B][j - B];
                maxSum = Math.max(maxSum, matrixSum);
            }
        }

        return maxSum;
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

    // https://www.interviewbit.com/problems/leaders-in-an-array/
    static ArrayList<Integer> leadersInArray(ArrayList<Integer> A) {
        ArrayList<Integer> res = new ArrayList<>();
        int n = A.size();
        if (n == 0)
            return res;

        // keep track of max elements to the right of A[i]
        int maxRight = A.get(n - 1);
        // right most is always leader
        res.add(maxRight);

        // check for each element from the end
        for (int i = n - 2; i >= 0; i--) {
            // A[i] is a leader. Add to result and update maxRight
            if (A.get(i) > maxRight) {
                res.add(A.get(i));
                maxRight = A.get(i);
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/pick-from-both-sides/
    static int pickFromBothSides(int[] A, int B) {
        int n = A.length;
        // calculate prefix and suffix sum
        int[] prefixSum = new int[n + 1], suffixSum = new int[n + 1];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = A[i - 1] + prefixSum[i - 1];
            suffixSum[n - i] = A[n - i] + suffixSum[n - i + 1];
        }
        prefixSum[n] = A[n - 1] + prefixSum[n - 1];
        suffixSum[0] = A[0] + suffixSum[1];

        // find max of (prefix[i] + suffix[n-(B - i)])
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i <= B; i++)
            maxSum = Math.max(maxSum, prefixSum[i] + suffixSum[n - (B - i)]);

        return maxSum;
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
        int sum = 0, cnt = 0, ans = 0;

        for (int i = 0; i < n - 1; i++) {
            sum += A[i];

            // if sum at position i is s2, it can be paired with all prev positions where sum is s1
            if (sum == s2)
                ans += cnt;
            // if sum at position i is s1, increment the count of positions where sum is s1
            if (sum == s1)
                cnt++;
        }

        return ans;
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
        int rightOdd = 0, rightEven = 0;
        int cnt = 0;

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

    static void printArray(int[] a) {
        for (int val : a)
            System.out.print(val + " ");
        System.out.println();
    }

    static void printArray(Integer[] a) {
        for (int val : a)
            System.out.print(val + " ");
        System.out.println();
    }

    static void printArray(String[] a) {
        for (String val : a)
            System.out.print(val + " ");
        System.out.println();
    }

    static void printMatrix(int[][] a) {
        for (int[] arr : a) {
            for (int val : arr)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static void printMatrix(ArrayList<ArrayList<Integer>> a) {
        for (List<Integer> arr : a) {
            for (int val : arr)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }

    static void printArrayList(ArrayList<Integer> a) {
        for (int val : a)
            System.out.print(val + " ");
        System.out.println();
    }

    static ArrayList<Integer> toIntArrayList(Integer[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }

    static ArrayList<ArrayList<Integer>> toIntMatrix(int[][] a1) {
        ArrayList<ArrayList<Integer>> a = new ArrayList<>();

        for (int[] row : a1) {
            ArrayList<Integer> rw = new ArrayList<>();
            for (int val : row)
                rw.add(val);
            a.add(rw);
        }

        return a;
    }
}