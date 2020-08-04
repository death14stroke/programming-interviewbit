import java.util.*;

class Hashing {
    // https://www.interviewbit.com/problems/colorful-number/
    static int colorfulNumber(int N) {
        Set<Integer> set = new HashSet<>();
        // convert N to string to loop through each digit
        String s = String.valueOf(N);

        // check for all sub sequences
        for (int i = 0; i < s.length(); i++) {
            int prod = 1;

            for (int j = i; j < s.length(); j++) {
                // update product for s.substring(i, j + 1)
                prod *= (s.charAt(j) - '0');

                // if this product is already found, not a colorful number
                if (set.contains(prod))
                    return 0;

                set.add(prod);
            }
        }

        // all subsequence products unique, colorful number
        return 1;
    }

    // https://www.interviewbit.com/problems/largest-continuous-sequence-zero-sum/
    static int[] largestSequenceZeroSum(int[] A) {
        // hashmap to store sum value with its starting ending position
        Map<Integer, Integer> map = new HashMap<>();
        // sum of no elements is 0
        map.put(0, -1);

        int sum = 0, maxLeft = 0, maxSize = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];

            // if this sum is already encountered
            if (map.containsKey(sum)) {
                int left = map.get(sum) + 1, size = i - left + 1;
                // update result if #elements between this sum encounter is larger
                if (size > maxSize) {
                    maxSize = size;
                    maxLeft = left;
                }
            }
            // else add this sum to map as key with its end position as value
            else
                map.put(sum, i);
        }

        // return the subarray with sum zero
        return Arrays.copyOfRange(A, maxLeft, maxLeft + maxSize);
    }

    // https://www.interviewbit.com/problems/2-sum/
    static int[] twoSum(final int[] A, int B) {
        // hashmap to store value with its index
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = A.length;

        // try all values for the second element
        for (int i = 0; i < n; i++) {
            // if there is an element before current that satisfies target,
            // return indices with 1-based indexing
            if (map.containsKey(B - A[i]))
                return new int[]{map.get(B - A[i]) + 1, i + 1};

            // add current element to map only if it is first occurence
            map.putIfAbsent(A[i], i);
        }

        // no such pair found
        return new int[0];
    }

    // https://www.interviewbit.com/problems/valid-sudoku/
    static int validSudoku(final String[] board) {
        Set<String> set = new HashSet<>();

        // mark each entry's presence in its resp row, column and box & check for duplication
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i].charAt(j);
                // if empty, ignore
                if (c == '.')
                    continue;

                // base string to mark the number in cell
                String temp = "(" + c + ")";

                // string construct for row i
                String row = i + temp;
                // if row i already contains c
                if (set.contains(row))
                    return 0;
                set.add(row);

                // string construct for col j
                String col = temp + j;
                // if col j already contains c
                if (set.contains(col))
                    return 0;
                set.add(col);

                // string construct for box starting at (i / 3, j / 3)
                String box = i / 3 + temp + j / 3;
                // if the box already contains c
                if (set.contains(box))
                    return 0;
                set.add(box);
            }
        }

        // all rows, columns and boxes are valid
        return 1;
    }

    // https://www.interviewbit.com/problems/diffk-ii/
    static int diffk2(final int[] A, int k) {
        Set<Integer> set = new HashSet<>();

        for (int val : A) {
            if (set.contains(val - k) || set.contains(val + k))
                return 1;

            set.add(val);
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/pairs-with-given-xor/
    static int pairsWithGivenXOR(int[] A, int B) {
        Set<Integer> set = new HashSet<>();
        int cnt = 0;

        // for each element
        for (int val : A) {
            // if val ^ B has been seen before, increment res
            if (set.contains(val ^ B))
                cnt++;
                // else add current val to set
            else
                set.add(val);
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/an-increment-problem/
    static int[] incrementProblem(int[] A) {
        Map<Integer, Integer> map = new HashMap<>();

        // move ahead in sequence
        for (int i = 0; i < A.length; i++) {
            // if first occurence is present
            if (map.containsKey(A[i])) {
                // increment value at first occurence position
                int pos = map.get(A[i]);
                A[pos]++;

                // if the incremented value is a first occurence
                if (!map.containsKey(A[pos]) || pos < map.get(A[pos]))
                    map.put(A[pos], pos);
            }

            // mark first occurence for the element
            map.put(A[i], i);
        }

        return A;
    }

    // https://www.interviewbit.com/problems/first-repeating-element/
    static int firstRepeating(int[] A) {
        Set<Integer> set = new HashSet<>();
        int res = -1;

        // traverse array from the end
        for (int i = A.length - 1; i >= 0; i--) {
            // if element has already been seen, update result
            if (set.contains(A[i]))
                res = A[i];
                // else mark current element as seen
            else
                set.add(A[i]);
        }

        return res;
    }
}