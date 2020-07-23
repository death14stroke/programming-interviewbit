import java.util.*;

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

    // https://www.interviewbit.com/problems/intersection-of-sorted-arrays/
    static ArrayList<Integer> intersect(final List<Integer> A, final List<Integer> B) {
        ArrayList<Integer> res = new ArrayList<>();
        int m = A.size(), n = B.size();
        int i = 0, j = 0;

        while (i < m && j < n) {
            // compare B with larger element in A
            if (A.get(i) < B.get(j))
                i++;
                // compare A with larger element in B
            else if (A.get(i) > B.get(j))
                j++;
                // intersection point. Check next elements in both lists
            else {
                res.add(A.get(i));
                i++;
                j++;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/3-sum/
    static int threeSumClosest(ArrayList<Integer> A, int target) {
        int n = A.size();
        Collections.sort(A);

        // initialize with sum of first three numbers
        int closestSum = A.get(0) + A.get(1) + A.get(2);
        // take first pointer at start, second at one pos after start and third at the end
        for (int i = 0; i < n - 2; i++) {
            int j = i + 1, k = n - 1;

            // keep adding the three numbers and update the sum based on difference
            while (j < k) {
                int sum = A.get(i) + A.get(j) + A.get(k);

                // move second pointer right if sum is lesser
                if (sum < target)
                    j++;
                    // move third pointer left if sum is greater
                else if (sum > target)
                    k--;
                    // found exact sum
                else
                    return sum;

                // if this sum is closer to target than previous sum
                if (Math.abs(target - sum) <= Math.abs(target - closestSum))
                    closestSum = sum;
            }
        }

        return closestSum;
    }

    // https://www.interviewbit.com/problems/3-sum-zero/
    static ArrayList<ArrayList<Integer>> threeSum(ArrayList<Integer> A) {
        Collections.sort(A);

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        int n = A.size();
        // check for all triplets with smallest number as A[i]
        for (int i = 0; i < n - 2; i++) {
            int j = i + 1, k = n - 1;

            while (j < k) {
                // long to avoid overflow
                long sum = (long) A.get(i) + (long) A.get(j) + (long) A.get(k);

                // check for larger 2nd element
                if (sum < 0)
                    j++;
                    // check for smaller 3rd element
                else if (sum > 0)
                    k--;
                    // found triplet
                else {
                    ArrayList<Integer> triplet = new ArrayList<>();
                    triplet.add(A.get(i));
                    triplet.add(A.get(j));
                    triplet.add(A.get(k));
                    ans.add(triplet);

                    j++;
                    k--;

                    // skip duplicate 2nd element of triplet
                    while (j < n && A.get(j).equals(A.get(j - 1)))
                        j++;

                    // skip duplicate 3rd element of triplet
                    while (k >= 0 && A.get(k).equals(A.get(k + 1)))
                        k--;
                }
            }

            // skip duplicate 1st element of triplet
            while (i + 1 < n && A.get(i).equals(A.get(i + 1)))
                i++;
        }

        return ans;
    }

    // https://www.interviewbit.com/problems/counting-triangles/
    static int nTriang(ArrayList<Integer> A) {
        int p = 1000000007, n = A.size();
        long ans = 0;

        Collections.sort(A);

        // choose the largest side
        for (int k = 2; k < n; k++) {
            int i = 0, j = k - 1;

            // keep checking for first and second sides
            while (i < j) {
                // if sum is less, increase the smallest side
                if (A.get(i) + A.get(j) <= A.get(k))
                    i++;
                    // found a triangle, all triangles with smallest side greater than A[i] will also form a triangle
                    // decrement the second side and check again
                else {
                    ans = (ans + (j - i)) % p;
                    j--;
                }
            }
        }

        return (int) ans;
    }

    // https://www.interviewbit.com/problems/diffk/
    static int diffK(ArrayList<Integer> A, int k) {
        int n = A.size(), i = 0, j = 1;
        // check from the first pair
        while (i <= j && j < n) {
            // found the difference
            if (A.get(j) - A.get(i) == k && i != j)
                return 1;
            // difference is higher, move left pointer
            if (A.get(j) - A.get(i) > k)
                i++;
                // diff is lower or both elements are same, move right pointer
            else
                j++;
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-array/
    static int removeDuplicates(ArrayList<Integer> A) {
        int n = A.size(), i = 1;

        // find the first repeating element (position which to remove)
        while (i < n && !A.get(i).equals(A.get(i - 1)))
            i++;

        // check for all elements after that
        for (int j = i + 1; j < n; j++) {
            // if A[j] is unique, replace it with the position to be removed and update the position
            // (i - 1) is the first occurence of duplicate/next element
            if (!A.get(j).equals(A.get(i - 1))) {
                A.set(i, A.get(j));
                i++;
            }
        }

        // remove the end of the list
        A.subList(i, n).clear();
        // return the length of new list
        return i;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-array-ii/
    static int removeDuplicates2(ArrayList<Integer> A) {
        int n = A.size(), atMost = 2;
        // if list is smaller than least requirement
        if (n <= atMost)
            return n;

        // find first position which is to be deleted (repeating more than atMost times)
        int i = atMost;
        while (i < n && !A.get(i).equals(A.get(i - atMost)))
            i++;

        // check for all elements after that
        for (int j = i + 1; j < n; j++) {
            // if A[j] is not repeating more than atMost times, replace it with the position to be removed
            // and update the position
            // (i - atMost) is the first occurence of duplicate/next element
            if (!A.get(j).equals(A.get(i - atMost))) {
                A.set(i, A.get(j));
                i++;
            }
        }

        // remove the end of the list
        A.subList(i, n).clear();
        // return the length of new list
        return i;
    }

    // https://www.interviewbit.com/problems/remove-element-from-array/
    static int removeElement(ArrayList<Integer> A, int k) {
        int n = A.size(), i = 0;
        // find first occurence of k
        while (i < n && A.get(i) != k)
            i++;

        // keep replacing values at positions to be removed
        for (int j = i + 1; j < n; j++) {
            // if not target, shift it to the left
            if (A.get(j) != k) {
                A.set(i, A.get(j));
                i++;
            }
        }

        // length of new array
        return i;
    }

    // https://www.interviewbit.com/problems/sort-by-color/
    static void sortByColor(ArrayList<Integer> A) {
        int n = A.size();
        int c0 = 0, c1, c2 = n - 1;

        // skip all zeroes at the beginning
        while (c0 < n && A.get(c0) == 0)
            c0++;
        c1 = c0;

        // skip all twos at the end
        while (c2 >= 0 && A.get(c2) == 2)
            c2--;

        // shrink the unknown range from c1 to c2
        while (c1 <= c2) {
            // swap c1 with the known 0-range
            if (A.get(c1) == 0) {
                swap(A, c1, c0);
                c0++;
                c1++;
            }
            // swap c1 with the known 2-range
            else if (A.get(c1) == 2) {
                swap(A, c1, c2);
                c2--;
            }
            // shrink unknown range with 1s fixed at the position c1
            else
                c1++;
        }
    }

    private static void swap(ArrayList<Integer> A, int i, int j) {
        int temp = A.get(i);
        A.set(i, A.get(j));
        A.set(j, temp);
    }

    // https://www.interviewbit.com/problems/max-continuous-series-of-1s/
    static ArrayList<Integer> maxone(ArrayList<Integer> A, int M) {
        // current window
        int wL = 0, wR = 0;
        // best window
        int bestL = 0, bestWindow = 0;
        // number of zeroes in current window
        int cnt = 0;

        // keep sliding window till end
        while (wR < A.size()) {
            // if window can contain more zeroes, expand on right
            if (cnt <= M) {
                if (A.get(wR) == 0)
                    cnt++;
                wR++;
            }
            // shrink window from the left to remove extra zeroes
            else {
                if (A.get(wL) == 0)
                    cnt--;
                wL++;
            }

            // if number of zeroes in current window is below limit and
            // this window is larger than the previous best window
            if (cnt <= M && bestWindow < wR - wL) {
                bestWindow = wR - wL;
                bestL = wL;
            }
        }

        // result list with all indices of the best window
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = bestL; i < bestL + bestWindow; i++)
            res.add(i);

        return res;
    }

    // https://www.interviewbit.com/problems/array-3-pointers/
    static int minimize(final List<Integer> A, final List<Integer> B, final List<Integer> C) {
        int i = 0, j = 0, k = 0;
        int diff = Integer.MAX_VALUE;

        while (i < A.size() && j < B.size() && k < C.size()) {
            int max = Math.max(A.get(i), Math.max(B.get(j), C.get(k)));
            int min = Math.min(A.get(i), Math.min(B.get(j), C.get(k)));

            // if difference of max and min from all three is lower, this is new result
            if (max - min < diff)
                diff = max - min;

            // increase the index of minimum element among all to get lower difference next time
            if (min == A.get(i))
                i++;
            else if (min == B.get(j))
                j++;
            else
                k++;
        }

        return diff;
    }

    // https://www.interviewbit.com/problems/container-with-most-water/
    static int maxArea(ArrayList<Integer> A) {
        int n = A.size();
        int l = 0, r = n - 1;
        int maxArea = 0;

        // take the widest container and then keep shrinking it
        while (l < r) {
            maxArea = Math.max(maxArea, Math.min(A.get(r), A.get(l)) * (r - l));

            // if left side is smaller, shrink on left side
            if (A.get(l) < A.get(r))
                l++;
                // shrink on right side
            else
                r--;
        }

        return maxArea;
    }

    // https://www.interviewbit.com/problems/pair-with-given-difference/
    static int solve(ArrayList<Integer> A, int B) {
        HashSet<Integer> set = new HashSet<>();

        // for each element val, check if there is an element with diff +B or -B with val
        for (int val : A) {
            if (set.contains(val - B) || set.contains(val + B))
                return 1;

            set.add(val);
        }

        // no such pair exists
        return 0;
    }

    // https://www.interviewbit.com/problems/maximum-ones-after-modification/
    static int maxOnesLength(ArrayList<Integer> A, int B) {
        // current window
        int wL = 0, wR = 0;
        // best window
        int bestWindow = 0;
        // number of zeroes in current window
        int cnt = 0;

        // keep sliding window till end
        while (wR < A.size()) {
            // if window can contain more zeroes, expand on right
            if (cnt <= B) {
                if (A.get(wR) == 0)
                    cnt++;
                wR++;
            }
            // shrink window from the left to remove extra zeroes
            else {
                if (A.get(wL) == 0)
                    cnt--;
                wL++;
            }

            // if number of zeroes in current window is below limit and
            // this window is larger than the previous best window
            if (cnt <= B && bestWindow < wR - wL)
                bestWindow = wR - wL;
        }

        return bestWindow;
    }

    // https://www.interviewbit.com/problems/counting-subarrays/
    static int countingSubArrays(int[] A, int B) {
        int n = A.length;
        int start = 0, end = 0;
        int sum = 0, cnt = 0;

        // sliding window approach
        while (end < n) {
            // if new sum is less than B count new subarrays
            if (sum + A[end] < B) {
                sum += A[end];
                cnt += end - start + 1;
                end++;
            }
            // else keep removing sum from the start
            else {
                sum -= A[start];
                start++;
            }
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/subarrays-with-distinct-integers/
    static int subArraysWithDistinct(int[] A, int B) {
        // # subarrays with exactly B distinct =
        // # subarrays with at most B distinct - # subarrays with at most (B - 1) distinct
        return atMostB(A, B) - atMostB(A, B - 1);
    }

    // util to get subarray count with at most B distinct elements
    private static int atMostB(int[] A, int B) {
        int n = A.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        int start = 0, end = 0;
        int cnt = 0;

        while (end < n) {
            // increment frequency of end pointer value
            map.put(A[end], map.getOrDefault(A[end], 0) + 1);

            // while there are more than B distinct elements keep removing from the start
            while (map.size() > B) {
                int freq = map.get(A[start]) - 1;
                if (freq == 0)
                    map.remove(A[start]);
                else
                    map.put(A[start], freq);

                start++;
            }

            // add subarrays formed by current range to answer
            cnt += end - start + 1;
            // move right
            end++;
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/kth-smallest-element-in-the-array/
    static int kthSmallest(final int[] A, int k) {
        // binary search on answer from min to max
        int max = A[0], min = A[0];
        for (int i = 1; i < A.length; i++) {
            max = Math.max(max, A[i]);
            min = Math.min(min, A[i]);
        }

        int l = min, r = max;
        while (l <= r) {
            int mid = l + (r - l) / 2;

            // count number of elements less than and less than equal to mid
            int lt = 0, le = 0;
            for (int val : A) {
                if (val < mid)
                    lt++;
                if (val <= mid)
                    le++;
            }

            // if number of elements less than mid is less than k and
            // less than equal to mid is greater than equal k, mid is the kth smallest
            if (lt < k && le >= k)
                return mid;
            // else look for higher value as answer
            if (le < k)
                l = mid + 1;
                // else look for smaller value as answer
            else
                r = mid - 1;
        }

        return -1;
    }

    // https://www.interviewbit.com/problems/numrange/
    static int numRange(int[] A, int B, int C) {
        // #subarrays with sum in [B, C] =
        // #subarrays with sum <= C - #subarrays with sum <= B - 1
        return sumAtMost(A, C) - sumAtMost(A, B - 1);
    }

    // util to calculate #subarrays with sum less than equal to k
    private static int sumAtMost(int[] A, int k) {
        int n = A.length;
        int start = 0, end = 0;
        int sum = 0, cnt = 0;

        // sliding window
        while (start < n && end < n) {
            // if window can be expanded, expand and add the count of new subarrays possible
            if (sum + A[end] <= k) {
                sum += A[end];
                cnt += end - start + 1;
                end++;
            }
            // shrink window from the left
            else {
                sum -= A[start];
                start++;
            }
        }

        return cnt;
    }
}