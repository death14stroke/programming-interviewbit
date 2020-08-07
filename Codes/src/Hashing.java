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

    // https://www.interviewbit.com/problems/4-sum/
    static ArrayList<ArrayList<Integer>> fourSum(ArrayList<Integer> A, int target) {
        int n = A.size();
        // sort the list for getting quadruplets in order
        Collections.sort(A);

        Set<ArrayList<Integer>> res = new LinkedHashSet<>();
        Map<Integer, List<Pair<Integer, Integer>>> map = new HashMap<>();

        // compute sum of each pair and store in hashmap with sum as key and list of pairs as values
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = A.get(i) + A.get(j);
                map.putIfAbsent(sum, new ArrayList<>());
                map.get(sum).add(new Pair<>(i, j));
            }
        }

        // for each pair
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = A.get(i) + A.get(j);

                // if remaining pair sum is present
                if (map.containsKey(target - sum)) {
                    // for each pair with sum equal to remaining
                    for (Pair<Integer, Integer> pair : map.get(target - sum)) {
                        // if any element is overlapping
                        if (i == pair.first || i == pair.second || j == pair.first || j == pair.second)
                            continue;
                        // if the other pair is not greater than current pair
                        if (A.get(j) > A.get(pair.first))
                            continue;

                        // add solution to the set
                        ArrayList<Integer> quad = new ArrayList<>();
                        quad.add(A.get(i));
                        quad.add(A.get(j));
                        quad.add(A.get(pair.first));
                        quad.add(A.get(pair.second));

                        res.add(quad);
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

        return new ArrayList<>(res);
    }

    // pair class
    static class Pair<F, S> {
        F first;
        S second;

        Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
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

    // https://www.interviewbit.com/problems/anagrams/
    static ArrayList<ArrayList<Integer>> anagrams(final List<String> A) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();

        // map hash of each string to its position in the list
        for (int i = 0; i < A.size(); i++) {
            int key = hash(A.get(i));
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(i + 1);
        }

        // return all the entries in the map.
        // Anagrams will have the same hash hence will be in common list
        return new ArrayList<>(map.values());
    }

    // util to find hash of a string
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
        Map<Integer, Pair<Integer, Integer>> map = new HashMap<>();

        int[] res = new int[4];
        Arrays.fill(res, Integer.MAX_VALUE);

        // for each pair
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = A[i] + A[j];

                // if already a pair exists, get the first such pair
                if (map.containsKey(sum)) {
                    Pair<Integer, Integer> pair = map.get(sum);
                    // if overlapping indices, skip
                    if (i == pair.first || i == pair.second || j == pair.first || j == pair.second)
                        continue;

                    // update if the new solution is lexicographically smaller than the previous one
                    if (pair.first < res[0]) {
                        res[0] = pair.first;
                        res[1] = pair.second;
                        res[2] = i;
                        res[3] = j;
                    } else if (pair.first == res[0] && pair.second < res[1]) {
                        res[1] = pair.second;
                        res[2] = i;
                        res[3] = j;
                    } else if (pair.first == res[0] && pair.second == res[1] && i < res[2]) {
                        res[2] = i;
                        res[3] = j;
                    } else if (pair.first == res[0] && pair.second == res[1] && i == res[2] && j < res[3])
                        res[3] = j;
                }
                // add current pair to hashmap with its sum as key
                else
                    map.put(sum, new Pair<>(i, j));
            }
        }

        // if no solution found
        if (res[0] == Integer.MAX_VALUE)
            return new int[0];
        return res;
    }

    // https://www.interviewbit.com/problems/longest-substring-without-repeat/
    static int longestSubstringWithoutRepeat(String A) {
        // set to check whether char is repeating or not
        Set<Character> set = new HashSet<>();
        int start = 0, res = 1;

        // for each char
        for (char c : A.toCharArray()) {
            // keep removing all chars from start till the first occurence of current element
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

    // https://www.interviewbit.com/problems/window-string/
    static String windowString(String S, String T) {
        int[] map = new int[256], patMap = new int[256];
        // count frequency of all characters in the pattern string
        for (char c : T.toCharArray())
            patMap[c]++;

        int n = S.length();
        int start = 0, end = 0, cnt = 0, minStart = 0, minLen = Integer.MAX_VALUE;

        // sliding window two pointers
        while (end < n) {
            char c = S.charAt(end);
            // if present in pattern and frequency in current window not fulfilled yet
            if (map[c] < patMap[c])
                cnt++;

            map[c]++;
            end++;

            // if all chars found, keep shrinking window from left till it remains a valid window
            while (cnt == T.length()) {
                // smaller window found
                if (end - start < minLen) {
                    minStart = start;
                    minLen = end - start;
                }

                // remove first char of window if present in pattern and is extra
                c = S.charAt(start);
                if (patMap[c] != 0 && map[c] <= patMap[c])
                    cnt--;

                // shrink window from left side
                map[c]--;
                start++;
            }
        }

        // if no valid window was found
        if (minLen == Integer.MAX_VALUE)
            return "";
        return S.substring(minStart, minStart + minLen);
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
            if (entry.getValue() > 1)
                res.add(entry.getKey());
        }

        Collections.sort(res);

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
}