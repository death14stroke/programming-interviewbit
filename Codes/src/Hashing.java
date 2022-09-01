import java.util.*;

class Hashing {
    // https://www.interviewbit.com/problems/colorful-number/
    static int colorfulNumber(int A) {
        Set<Integer> set = new HashSet<>();
        // check for all subsequences starting at each digit position in A
        while (A > 0) {
            int temp = A, prod = 1;
            // update product for subsequence starting with last digit of A
            while (temp > 0) {
                prod *= temp % 10;
                // if this product is already found, not a colorful number
                if (set.contains(prod))
                    return 0;
                // add new product to set
                set.add(prod);
                // move to next digit
                temp /= 10;
            }
            // remove last digit for next set of subsequences
            A /= 10;
        }
        // all subsequence products unique, colorful number
        return 1;
    }

    // https://www.interviewbit.com/problems/largest-continuous-sequence-zero-sum/
    static int[] largestSequenceZeroSum(int[] A) {
        // hashmap to store sum value with its ending position
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
        // return the sub-array with sum zero
        return Arrays.copyOfRange(A, maxLeft, maxLeft + maxSize);
    }

    // https://www.interviewbit.com/problems/longest-subarray-length/
    static int longestSubarray(int[] A) {
        // map each sum to its position
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0, res = 0;

        for (int i = 0; i < A.length; i++) {
            // count difference between number of 1s and 0s at this position
            sum += (A[i] == 0 ? -1 : 1);
            // found subarray array with one more 1s than 0s. Update result
            if (map.containsKey(sum - 1))
                res = Math.max(res, i - map.get(sum - 1));
            // mark first occurrence of sum to position
            map.putIfAbsent(sum, i);
        }

        return res;
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

    // https://www.interviewbit.com/problems/2-sum/
    static int[] twoSum(final int[] A, int B) {
        // hashmap to store value with its index
        Map<Integer, Integer> map = new HashMap<>();
        // try all values for the second element
        for (int i = 0; i < A.length; i++) {
            // if there is an element before current that satisfies target, return indices with 1-based indexing
            if (map.containsKey(B - A[i]))
                return new int[]{map.get(B - A[i]) + 1, i + 1};
            // add current element to map only if it is first occurrence
            map.putIfAbsent(A[i], i);
        }
        // no such pair found
        return new int[0];
    }

    // https://www.interviewbit.com/problems/4-sum/
    static ArrayList<ArrayList<Integer>> fourSum(ArrayList<Integer> A, int B) {
        int n = A.size();
        // sort the list for getting quadruplets in order
        Collections.sort(A);

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        Map<Integer, List<int[]>> map = new HashMap<>();
        // compute sum of each pair and store in hashmap with sum as key and list of pairs as values
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = A.get(i) + A.get(j);
                map.computeIfAbsent(sum, k -> new ArrayList<>()).add(new int[]{i, j});
            }
        }
        // for each pair
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = A.get(i) + A.get(j);
                // if remaining pair sum is present
                if (map.containsKey(B - sum)) {
                    // for each pair with sum equal to remaining
                    for (int[] pair : map.get(B - sum)) {
                        // the two pairs are not overlapping and in order
                        if (j < pair[0]) {
                            ArrayList<Integer> quad = new ArrayList<>();
                            quad.add(A.get(i));
                            quad.add(A.get(j));
                            quad.add(A.get(pair[0]));
                            quad.add(A.get(pair[1]));
                            // if output is empty or current quadruplet is unique
                            if (res.isEmpty() || !quad.equals(res.get(res.size() - 1)))
                                res.add(quad);
                        }
                    }
                }
                // skip duplicate second element
                while (j + 1 < n && A.get(j + 1).equals(A.get(j)))
                    j++;
            }
            // skip duplicate first element
            while (i + 1 < n && A.get(i + 1).equals(A.get(i)))
                i++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/valid-sudoku/
    static int validSudoku(final String[] A) {
        // int mask for each row, column, box
        int[] rows = new int[9], cols = new int[9], boxes = new int[9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = A[i].charAt(j);
                if (c == '.')
                    continue;

                int val = c - '0', mask = 1 << (val - 1);
                int box = (i / 3) * 3 + j / 3;
                // if the bit position in any masks is not 0 means the number is repeated
                if ((rows[i] & mask) != 0 || (cols[j] & mask) != 0 || (boxes[box] & mask) != 0)
                    return 0;
                // set the bit pos in all masks
                rows[i] |= mask;
                cols[j] |= mask;
                boxes[box] |= mask;
            }
        }
        // all rows, columns and boxes are valid
        return 1;
    }

    // https://www.interviewbit.com/problems/diffk-ii/
    static int diffk2(final int[] A, int B) {
        Set<Integer> set = new HashSet<>();

        for (int val : A) {
            // if already seen x where x - val = B or val - x = B
            if (set.contains(val - B) || set.contains(val + B))
                return 1;
            set.add(val);
        }
        // no such pair found
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
            // add current val to set
            set.add(val);
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/anagrams/
    static ArrayList<ArrayList<Integer>> anagrams(final List<String> A) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        // map hash of each string to its position in the list
        for (int i = 0; i < A.size(); i++) {
            int key = hash(A.get(i));
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(i + 1);
        }
        // return all the entries in the map. Anagrams will have the same hash hence will be in common list
        return new ArrayList<>(map.values());
    }

    // util to find hash of a string such that anagrams will have same hash
    private static int hash(String s) {
        // count of each character in the string
        int[] map = new int[26];
        for (char c : s.toCharArray())
            map[c - 'a']++;
        // calculate frequency based hash with prime number
        int hash = 0;
        for (int i = 0; i < 26; i++)
            hash = hash * 31 + map[i];

        return hash;
    }

    // https://www.interviewbit.com/problems/equal/
    static int[] equal(int[] A) {
        int n = A.length;
        // map sum to first found pair
        Map<Integer, int[]> map = new HashMap<>();
        int[] res = new int[4];
        Arrays.fill(res, n);
        // for each pair
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = A[i] + A[j];
                // if already a pair exists, get the first such pair
                if (map.containsKey(sum)) {
                    int[] pair = map.get(sum);
                    // if not A1 < C1, B1 != D1 or B1 != C1
                    if (pair[0] >= i || pair[1] == i || pair[1] == j)
                        continue;
                    // update if the new solution is lexicographically smaller than the previous one
                    if (pair[0] < res[0]) {
                        res[0] = pair[0];
                        res[1] = pair[1];
                        res[2] = i;
                        res[3] = j;
                    } else if (pair[0] == res[0] && pair[1] < res[1]) {
                        res[1] = pair[1];
                        res[2] = i;
                        res[3] = j;
                    } else if (pair[0] == res[0] && pair[1] == res[1] && i < res[2]) {
                        res[2] = i;
                        res[3] = j;
                    } else if (pair[0] == res[0] && pair[1] == res[1] && i == res[2] && j < res[3]) {
                        res[3] = j;
                    }
                }
                // add current pair to hashmap with its sum as key
                else {
                    map.put(sum, new int[]{i, j});
                }
            }
        }
        // found solution
        return res[0] != n ? res : new int[0];
    }

    // https://www.interviewbit.com/problems/copy-list/
    static class RandomListNode {
        int label;
        RandomListNode next, random;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    // see leetcode discuss for without hashmap
    static RandomListNode copyList(RandomListNode head) {
        // hashmap to map old nodes to new nodes
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode curr = head;
        // traverse the list and map each node to a new node
        while (curr != null) {
            map.put(curr, new RandomListNode(curr.label));
            curr = curr.next;
        }

        // traverse all the original nodes and map the random and next pointers
        for (Map.Entry<RandomListNode, RandomListNode> e : map.entrySet()) {
            RandomListNode node = e.getKey(), copy = e.getValue();
            copy.next = map.get(node.next);
            copy.random = map.get(node.random);
        }

        return map.get(head);
    }

    // https://www.interviewbit.com/problems/check-palindrome/
    static int checkPalindrome(String A) {
        // count frequency of all chars
        int[] map = new int[26];
        for (char c : A.toCharArray())
            map[c - 'a']++;

        int oddCount = 0;
        // a palindrome string can have at most 1 character with odd frequency
        for (int freq : map) {
            if (freq % 2 == 1) {
                oddCount++;
                // if more than 1 chars have odd frequency, palindrome not possible
                if (oddCount > 1)
                    return 0;
            }
        }

        return 1;
    }

    // https://www.interviewbit.com/problems/fraction/
    static String fraction(int A, int B) {
        // 0 / x = 0
        if (A == 0)
            return "0";

        StringBuilder res = new StringBuilder();
        // check sign for output
        boolean neg = (A < 0) ^ (B < 0);
        if (neg)
            res.append('-');
        // now perform operations on absolute values
        long num = Math.abs((long) A), den = Math.abs((long) B);
        // integer part of the division
        res.append(num / den);
        // remainder. Use long to avoid negative numbers in modulo
        long rem = num % den;
        // if perfect division
        if (rem == 0)
            return res.toString();

        // decimal point
        res.append('.');
        // map to keep track of remainders
        Map<Long, Integer> map = new HashMap<>();
        // perform division till remainder is not 0
        while (rem != 0) {
            // if this remainder is already seen, we have entered recurring sequence. Place parentheses at appropriate positions
            if (map.containsKey(rem)) {
                res.insert(map.get(rem), "(");
                res.append(')');
                break;
            }
            // else mark this remainder as seen with its position
            map.put(rem, res.length());
            // multiply remainder by 10 and perform division
            rem *= 10;
            res.append(rem / den);
            // update remainder
            rem %= den;
        }

        return res.toString();
    }

    // https://www.interviewbit.com/problems/points-on-the-straight-line/
    static int pointsOnStraightLine(ArrayList<Integer> X, ArrayList<Integer> Y) {
        int n = X.size();
        // if there are less than 3 points
        if (n <= 2)
            return n;

        int res = 0;
        // for each point as reference
        for (int i = 0; i < n - 1; i++) {
            // map to store number of points with slope as key
            Map<Slope, Integer> map = new HashMap<>();
            // curMax = max #points with slope equal to current point
            // overlapPoints = #points repeating with current point
            // verticalPoints = #points on the vertical x = X[i] line
            int curMax = 0, overlapPoints = 0, verticalPoints = 0;
            // for each possible pair
            for (int j = i + 1; j < n; j++) {
                // overlapping point
                if (X.get(i).equals(X.get(j)) && Y.get(i).equals(Y.get(j)))
                    overlapPoints++;
                    // vertical point
                else if (X.get(i).equals(X.get(j)))
                    verticalPoints++;
                    // else calculate slope
                else {
                    int xDiff = X.get(j) - X.get(i);
                    int yDiff = Y.get(j) - Y.get(i);
                    // find gcd to store slope in x/y form
                    int g = Maths.gcd(xDiff, yDiff);
                    xDiff /= g;
                    yDiff /= g;

                    Slope slope = new Slope(yDiff, xDiff);
                    map.put(slope, map.getOrDefault(slope, 0) + 1);
                    // slope with maximum points
                    curMax = Math.max(curMax, map.get(slope));
                }
            }
            // max of vertical points and max of definite slope
            curMax = Math.max(curMax, verticalPoints);
            // result max = max of prev points stats and current point max + overlaps for current point
            res = Math.max(res, curMax + overlapPoints + 1);
        }

        return res;
    }

    // util class to store Slope in y/x form
    static class Slope {
        int y, x;

        Slope(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Slope slope = (Slope) o;
            return x == slope.x && y == slope.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // https://www.interviewbit.com/problems/an-increment-problem/
    static int[] incrementProblem(int[] A) {
        Map<Integer, Integer> map = new HashMap<>();
        // move ahead in sequence
        for (int i = 0; i < A.length; i++) {
            // if first occurrence is present
            if (map.containsKey(A[i])) {
                // increment value at first occurrence position
                int pos = map.get(A[i]);
                A[pos]++;
                // if the incremented value is a first occurrence
                if (!map.containsKey(A[pos]) || pos < map.get(A[pos]))
                    map.put(A[pos], pos);
            }
            // mark first occurrence for the element
            map.put(A[i], i);
        }

        return A;
    }

    // https://www.interviewbit.com/problems/subarray-with-given-xor/
    static int subarrayWithXor(int[] A, int B) {
        Map<Integer, Integer> map = new HashMap<>();
        // xor = 0 when no elements taken
        map.put(0, 1);
        int xor = 0, res = 0;
        // calculate running xor from A[0, i]
        for (int val : A) {
            xor ^= val;
            // if there are any subarrays from (0, j) that have xor = currXor ^ B where j < i,
            // then there will be equal subarrays from (j, i) with xor = B
            if (map.containsKey(xor ^ B))
                res += map.get(xor ^ B);
            // update count of current xor value in map
            map.put(xor, map.getOrDefault(xor, 0) + 1);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/two-out-of-three/
    static ArrayList<Integer> twoOutOfThree(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C) {
        // create sets from arrays
        Set<Integer> s1 = new HashSet<>(A), s2 = new HashSet<>(B), s3 = new HashSet<>(C);
        Map<Integer, Integer> map = new HashMap<>();
        // update count of each element from all the sets in the map
        for (int val : s1)
            map.put(val, 1);
        for (int val : s2)
            map.put(val, map.getOrDefault(val, 0) + 1);
        for (int val : s3)
            map.put(val, map.getOrDefault(val, 0) + 1);

        ArrayList<Integer> res = new ArrayList<>();
        // for each entry in map
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // if appeared in at least two sets, add to result
            if (entry.getValue() >= 2)
                res.add(entry.getKey());
        }

        Collections.sort(res);

        return res;
    }

    @SuppressWarnings("unchecked")
    // https://www.interviewbit.com/problems/substring-concatenation/
    // see solution approach for optimization (consider the words as characters and think as hash that is same for anagrams)
    static ArrayList<Integer> substrConcat(String A, final List<String> B) {
        int wordCnt = B.size(), wordLen = B.get(0).length();
        int totalChars = wordCnt * wordLen;

        ArrayList<Integer> res = new ArrayList<>();
        int n = A.length();
        // if string length is smaller than char count of all the words
        if (n < totalChars)
            return res;

        // map each word in list to its frequency
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : B)
            map.put(word, map.getOrDefault(word, 0) + 1);
        // check for all starting points
        for (int i = 0; i < n - totalChars + 1; i++) {
            // copy of original map to mark words visited
            HashMap<String, Integer> temp = (HashMap<String, Integer>) map.clone();
            // count: #words remaining to find
            int count = wordCnt;
            // for all the words of size wordLen starting from i
            for (int j = i; j < i + totalChars; j += wordLen) {
                String word = A.substring(j, j + wordLen);
                int freq = temp.getOrDefault(word, 0);
                // if visited all occurrences or no occurrences in list
                if (freq == 0)
                    break;
                    // else found a word. Update total count of words remaining and count of this word remaining
                else {
                    temp.put(word, freq - 1);
                    count--;
                }
            }
            // if all the words were found, add starting point to result
            if (count == 0)
                res.add(i);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/subarray-with-b-odd-numbers/
    static int subarrayWithOdd(int[] A, int B) {
        int res = 0, odd = 0;
        // maintain count of prefix arrays with i odd numbers excluding current position
        int[] count = new int[A.length + 1];
        count[0] = 1;

        for (int val : A) {
            // found one more odd number
            if (val % 2 == 1)
                odd++;
            // if required count of odd numbers met, there will be count[odd - B] elements which can be used to form a permutation
            if (odd >= B)
                res += count[odd - B];
            // increase count of prefix arrays with i odd numbers
            count[odd]++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/window-string/
    static String windowString(String A, String B) {
        int[] map = new int[256], patMap = new int[256];
        // count frequency of all characters in the pattern string
        for (char c : B.toCharArray())
            patMap[c]++;

        int n = A.length();
        int start = 0, end = 0, cnt = 0, minStart = 0, minLen = Integer.MAX_VALUE;
        // sliding window two pointers
        while (end < n) {
            char c = A.charAt(end++);
            // if present in pattern and frequency in current window not fulfilled yet
            if (map[c] < patMap[c])
                cnt++;
            // update frequency in string map
            map[c]++;

            c = A.charAt(start);
            // keep shrinking window till there are extra characters at the start
            while (start < end - 1 && map[c] > patMap[c]) {
                map[c]--;
                start++;
                c = A.charAt(start);
            }
            // found a valid window which is smaller than current window
            if (cnt == B.length() && end - start < minLen) {
                minLen = end - start;
                minStart = start;
            }
        }
        // return valid window if found
        return minLen != Integer.MAX_VALUE ? A.substring(minStart, minStart + minLen) : "";
    }

    // https://www.interviewbit.com/problems/longest-substring-without-repeat/
    static int longestSubstringWithoutRepeat(String A) {
        // set to check whether char is repeating or not
        Set<Character> set = new HashSet<>();
        int start = 0, res = 1;
        // for each char
        for (char c : A.toCharArray()) {
            // keep removing all chars from start till the first occurrence of current element
            while (set.contains(c)) {
                set.remove(A.charAt(start));
                start++;
            }
            // current character is unique. Add to set and update result
            set.add(c);
            res = Math.max(res, set.size());
        }

        return res;
    }

    // https://www.interviewbit.com/problems/subarray-with-equal-occurences/
    static int subarrayEqualCount(int[] A, int B, int C) {
        // prefix count of occurrences of B and C
        int countB = 0, countC = 0;
        // map to store diff between two prefix counts at each index
        Map<Integer, Integer> map = new HashMap<>();
        for (int val : A) {
            // update prefix counts
            if (val == B)
                countB++;
            else if (val == C)
                countC++;
            // update diff count in map
            int diff = countB - countC;
            map.put(diff, map.getOrDefault(diff, 0) + 1);
        }

        // initialize count with all prefixes having diff = 0
        int res = map.getOrDefault(0, 0);
        // select any two of same zero or non-zero prefix diff so that the subarray formed from their difference will have diff 0
        for (int val : map.values())
            res += val * (val - 1) / 2;

        return res;
    }

    // https://www.interviewbit.com/problems/longest-consecutive-sequence/
    static int longestConsecutiveSequence(final int[] A) {
        // add all numbers to the set
        Set<Integer> set = new HashSet<>();
        for (int val : A)
            set.add(val);

        int res = 1;
        for (int val : A) {
            // if (val - 1) is present, sequence will be starting from val - 1 so check from that
            if (set.contains(val - 1))
                continue;

            int cnt = 0;
            // keep checking for consecutive sequence starting from val
            while (set.contains(val)) {
                cnt++;
                val++;
            }
            // update longest sequence length
            res = Math.max(res, cnt);
        }

        return res;
    }
}