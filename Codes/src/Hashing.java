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