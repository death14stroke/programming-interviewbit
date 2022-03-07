import java.util.*;

public class TwoPointers {
    // https://www.interviewbit.com/problems/pair-with-given-difference/
    static int solve(int[] A, int B) {
        Set<Integer> set = new HashSet<>();
        // for each element val, check if there is an element with diff +B or -B with val
        for (int val : A) {
            if (set.contains(val - B) || set.contains(val + B))
                return 1;

            set.add(val);
        }
        // no such pair exists
        return 0;
    }

    // https://www.interviewbit.com/problems/3-sum/
    static int threeSumClosest(int[] A, int B) {
        Arrays.sort(A);

        int n = A.length;
        // initialize with sum of first three numbers
        int res = A[0] + A[1] + A[2];
        // take first pointer at start, second at one pos after start and third at the end
        for (int i = 0; i < n - 2; i++) {
            int j = i + 1, k = n - 1;
            // keep adding the three numbers and update the sum based on difference
            while (j < k) {
                int sum = A[i] + A[j] + A[k];
                // found exact sum
                if (sum == B)
                    return sum;
                // move second pointer right if sum is lesser
                if (sum < B)
                    j++;
                    // move third pointer left
                else
                    k--;
                // if this sum is closer to target than previous sum
                if (Math.abs(B - sum) < Math.abs(B - res))
                    res = sum;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/3-sum-zero/
    static ArrayList<ArrayList<Integer>> threeSum(ArrayList<Integer> A) {
        Collections.sort(A);

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int n = A.size();
        // check for all triplets with the smallest number as A[i]
        for (int i = 0; i < n - 2; i++) {
            int j = i + 1, k = n - 1;

            while (j < k) {
                // long to avoid overflow
                long sum = (long) A.get(i) + A.get(j) + A.get(k);
                // found triplet
                if (sum == 0) {
                    ArrayList<Integer> triplet = new ArrayList<>();
                    triplet.add(A.get(i));
                    triplet.add(A.get(j));
                    triplet.add(A.get(k));
                    res.add(triplet);

                    j++;
                    // skip duplicate 2nd element of triplet
                    while (j < k && A.get(j).equals(A.get(j - 1)))
                        j++;

                    k--;
                    // skip duplicate 3rd element of triplet
                    while (k > j && A.get(k).equals(A.get(k + 1)))
                        k--;
                }
                // check for larger 2nd element
                else if (sum < 0)
                    j++;
                    // check for smaller 3rd element
                else
                    k--;
            }
            // skip duplicate 1st element of triplet
            while (i + 1 < n && A.get(i).equals(A.get(i + 1)))
                i++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/counting-triangles/
    static int nTriangle(int[] A) {
        int p = 1000000007, n = A.length;
        int res = 0;

        Arrays.sort(A);
        // choose the largest side
        for (int k = 2; k < n; k++) {
            int i = 0, j = k - 1;
            // keep checking for first and second sides
            while (i < j) {
                // if sum is less, increase the smallest side
                if (A[i] + A[j] <= A[k])
                    i++;
                    // found a triangle, all triangles with the smallest side greater than A[i] will also form a triangle
                    // decrement the second side and check again
                else {
                    res = (res + (j - i)) % p;
                    j--;
                }
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/diffk/
    static int diffK(int[] A, int B) {
        int n = A.length, i = 0, j = 1;
        // check from the first pair
        while (i < n && j < n) {
            int diff = A[j] - A[i];
            // found the difference
            if (diff == B && i != j)
                return 1;
            // difference is higher, move left pointer
            if (diff > B)
                i++;
                // diff is lower or both elements are same, move right pointer
            else
                j++;
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/maximum-ones-after-modification/
    static int maxOnesLength(int[] A, int B) {
        // current window
        int start = 0, end = 0;
        // best window size and #0's in current window
        int bestWindow = 0, cnt = 0;
        // keep sliding window till end
        while (end < A.length) {
            // if window can contain more zeroes, expand on right
            if (cnt <= B) {
                if (A[end] == 0)
                    cnt++;
                end++;
                // if #0's in current window is below limit and this window is larger than the previous best window
                if (cnt <= B && bestWindow < end - start)
                    bestWindow = end - start;
            }
            // shrink window from the left to remove extra zeroes
            else {
                if (A[start] == 0)
                    cnt--;
                start++;
            }
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
        // #subarrays with exactly B distinct = #subarrays with at most B distinct - #subarrays with at most (B - 1) distinct
        return atMostB(A, B) - atMostB(A, B - 1);
    }

    // util to get sub-array count with at most B distinct elements
    private static int atMostB(int[] A, int B) {
        int n = A.length;
        int start = 0, end = 0, res = 0;
        Map<Integer, Integer> map = new HashMap<>();

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
            res += end - start + 1;
            // move right
            end++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/max-continuous-series-of-1s/
    static int[] maxOne(int[] A, int B) {
        // current window
        int start = 0, end = 0;
        // best window and #0s in current window
        int bestStart = 0, bestWindow = 0, cnt = 0;
        // keep sliding window till end
        while (end < A.length) {
            // if window can contain more zeroes, expand on right
            if (cnt <= B) {
                if (A[end] == 0)
                    cnt++;
                end++;
                // if number of zeroes in current window is below limit and this window is larger than the previous best window
                if (cnt <= B && bestWindow < end - start) {
                    bestWindow = end - start;
                    bestStart = start;
                }
            }
            // shrink window from the left to remove extra zeroes
            else {
                if (A[start] == 0)
                    cnt--;
                start++;
            }
        }

        // result array with all indices of the best window
        int[] res = new int[bestWindow];
        for (int i = 0; i < bestWindow; i++)
            res[i] = bestStart + i;

        return res;
    }

    // https://www.interviewbit.com/problems/array-3-pointers/
    static int minimize(final int[] A, final int[] B, final int[] C) {
        int i = 0, j = 0, k = 0;
        // max(abs(A[i] - B[j]), abs(B[j] - C[k]), abs(C[k] - A[i])) = difference between max and min of the three
        int diff = Integer.MAX_VALUE;

        while (i < A.length && j < B.length && k < C.length) {
            int max = Math.max(A[i], Math.max(B[j], C[k]));
            int min = Math.min(A[i], Math.min(B[j], C[k]));
            // update the minimum difference
            diff = Math.min(diff, max - min);
            // increase the index of minimum element among all to get lower difference next time
            if (min == A[i])
                i++;
            else if (min == B[j])
                j++;
            else
                k++;
        }

        return diff;
    }

    // https://www.interviewbit.com/problems/container-with-most-water/
    static int maxArea(int[] A) {
        int l = 0, r = A.length - 1;
        int maxArea = 0;
        // take the widest container and then keep shrinking it
        while (l < r) {
            maxArea = Math.max(maxArea, Math.min(A[l], A[r]) * (r - l));
            // if left side is smaller, shrink on left side
            if (A[l] < A[r])
                l++;
                // shrink on right side
            else
                r--;
        }

        return maxArea;
    }

    // https://www.interviewbit.com/problems/merge-two-sorted-lists-ii/
    static void merge(ArrayList<Integer> A, ArrayList<Integer> B) {
        int m = A.size(), n = B.size();
        // increase size of A
        for (int k = 0; k < n; k++)
            A.add(0);

        int i = m - 1, j = n - 1;
        // traverse both list in reverse. Set the greater element at end of list A
        while (i >= 0 && j >= 0) {
            if (A.get(i) > B.get(j))
                A.set(i + j + 1, A.get(i--));
            else
                A.set(i + j + 1, B.get(j--));
        }
        // add remaining elements of list B
        while (j >= 0)
            A.set(j, B.get(j--));
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

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-array/
    static int removeDuplicates(ArrayList<Integer> A) {
        int pos = 1;
        // 0th element is always unique. Start from 1st position
        for (int i = 1; i < A.size(); i++) {
            // if not duplicate add at first pointer and update
            if (!A.get(i).equals(A.get(pos - 1)))
                A.set(pos++, A.get(i));
        }
        // return the length of new list
        return pos;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-array-ii/
    static int removeDuplicates2(ArrayList<Integer> A) {
        int n = A.size(), pos = 2;
        // if list is smaller than the least requirement
        if (pos >= n)
            return n;

        // start from (atMost = 2)th position and loop till end
        for (int i = 2; i < n; i++) {
            // if not duplicate add at the end of pos pointer. Compare it with (pos - 2) and not (i - 2) as list has changed
            if (!A.get(i).equals(A.get(pos - 2)))
                A.set(pos++, A.get(i));
        }
        // return the length of new list
        return pos;
    }

    // https://www.interviewbit.com/problems/remove-element-from-array/
    static int removeElement(ArrayList<Integer> A, int B) {
        int pos = 0;
        // keep replacing values at positions to be removed
        for (int i = 0; i < A.size(); i++) {
            // if not target, add value at pos pointer and update
            if (A.get(i) != B)
                A.set(pos++, A.get(i));
        }
        // length of new array
        return pos;
    }

    // https://www.interviewbit.com/problems/sort-by-color/
    static void sortByColor(ArrayList<Integer> A) {
        int n = A.size();
        int p0 = 0, p2 = n - 1, i = 0;
        // keep traversing till 2's are arranged at end
        while (i <= p2) {
            // swap i with the known 0-range
            if (A.get(i) == 0)
                swap(A, i++, p0++);
                // swap i with the known 2-range
            else if (A.get(i) == 2)
                swap(A, i, p2--);
                // skip current position
            else
                i++;
        }
    }

    private static void swap(ArrayList<Integer> A, int i, int j) {
        int temp = A.get(i);
        A.set(i, A.get(j));
        A.set(j, temp);
    }

    // https://www.interviewbit.com/problems/kth-smallest-element-in-the-array/
    static int kthSmallest(final int[] A, int B) {
        // binary search on answer from min to max
        int max = A[0], min = A[0];
        for (int i = 1; i < A.length; i++) {
            max = Math.max(max, A[i]);
            min = Math.min(min, A[i]);
        }

        int l = min, r = max;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // count #elements < mid and <= mid
            int lt = 0, le = 0;
            for (int val : A) {
                if (val < mid)
                    lt++;
                if (val <= mid)
                    le++;
            }
            // if 3elements < mid is less than B and <= mid is greater than equal B, then mid is the Bth smallest
            if (lt < B && le >= B)
                return mid;
            // else look for higher value as answer
            if (le < B)
                l = mid + 1;
                // else look for smaller value as answer
            else
                r = mid - 1;
        }

        return -1;
    }

    // https://www.interviewbit.com/problems/numrange/
    static int numRange(int[] A, int B, int C) {
        // #subarrays with sum in [B, C] = #subarrays with sum <= C - #subarrays with sum <= B - 1
        return sumAtMost(A, C) - sumAtMost(A, B - 1);
    }

    // util to calculate #subarrays with sum less than equal to B
    private static int sumAtMost(int[] A, int B) {
        int start = 0, end = 0;
        int sum = 0, cnt = 0;
        // sliding window
        while (end < A.length) {
            // if window can be expanded, expand and add the count of new subarrays possible
            if (sum + A[end] <= B) {
                sum += A[end];
                cnt += end - start + 1;
                end++;
            }
            // shrink window from the left
            else {
                // window is empty
                if (start == end) {
                    start++;
                    end++;
                } else {
                    sum -= A[start];
                    start++;
                }
            }
        }

        return cnt;
    }
}