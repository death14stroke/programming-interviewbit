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
}