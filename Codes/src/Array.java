import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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

        for (int i = 0; i < a.length; i++) {
            // if negative number comes, reset the sum and sub-array endpoints
            if (a[i] < 0) {
                curr_sum = 0;
                curr_start = i + 1;
                curr_end = i + 1;
            } else {
                curr_sum += a[i];

                // sum of current sub-array is more than previous ones
                if (max_sum < curr_sum) {
                    start = curr_start;
                    end = i;
                    max_sum = curr_sum;
                }
                // sum is equal but current sub-array is larger than the previous one
                else if (max_sum == curr_sum && curr_end - curr_start > end - start) {
                    start = curr_start;
                    end = i;
                    max_sum = curr_sum;
                }

                curr_end++;
            }
        }

        // all numbers are negative
        if (start == -1)
            return new int[0];

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

        mergeSort(a, 0, n - 1);

        for (int i = 0; i < n; i++) {
            int k = i + 1;

            // skip elements equal to current number
            while (k < n && a[k] == a[i])
                k++;
            k--;

            // number of elements greater than this number is equal to the number
            if (n - 1 - k == a[i])
                return 1;
        }
        return -1;
    }

    // https://www.interviewbit.com/problems/triplets-with-sum-between-given-range/
    static int solve(String[] a) {
        float[] arr = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            arr[i] = Float.parseFloat(a[i]);
        }

        // take first three numbers
        float x = arr[0], y = arr[1], z = arr[2];

        for (int i = 3; i < arr.length; i++) {
            // if satisfy condition, return
            if (x + y + z > 1 && x + y + z < 2)
                return 1;

                // if sum is greater, replace the largest element in the triplet
            else if (x + y + z > 2) {
                if (x > y && x > z)
                    x = arr[i];
                else if (y > x && y > z)
                    y = arr[i];
                else
                    z = arr[i];
            }

            // sum is smaller, replace the smallest element in the triplet
            else {
                if (x < y && x < z)
                    x = arr[i];
                else if (y < x & y < z)
                    y = arr[i];
                else
                    z = arr[i];
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

        int i=0;

        // sort and swap alternate numbers
        while (i+1 < a.length) {
            int temp = a[i];
            a[i] = a[i+1];
            a[i+1] = temp;

            i+=2;
        }
        return a;
    }

    static void printArray(int[] a) {
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
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}