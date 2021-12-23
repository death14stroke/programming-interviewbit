import java.util.*;

class Greedy {
    // https://www.interviewbit.com/problems/highest-product/
    static int highestProduct(int[] A) {
        int n = A.length;
        // sort the array
        Arrays.sort(A);
        // maximum product is the max of product of largest three numbers and 
        // product of the largest number and smallest two numbers (when both are negative)
        return Math.max(A[n - 1] * A[n - 2] * A[n - 3], A[0] * A[1] * A[n - 1]);
    }

    // https://www.interviewbit.com/problems/interview-questions/
    static int bulbs(int[] A) {
        // number of switch flips done till now
        int flips = 0;
        // for each bulb from left to right
        for (int bulb : A) {
            // check the state of the bulbs after 'flips' switch flips
            int state = (bulb + flips) % 2;
            // if bulb is off, flip the switch to turn it on
            if (state == 0)
                flips++;
        }

        return flips;
    }

    // https://www.interviewbit.com/problems/disjoint-intervals/
    static int disjointIntervals(int[][] A) {
        // sort the intervals in increasing order of end points
        Arrays.sort(A, Comparator.comparingInt(i -> i[1]));

        int endTime = A[0][1], cnt = 1;
        // start with taking the first interval
        for (int i = 1; i < A.length; i++) {
            // if current interval is disjoint, add to the result set
            if (A[i][0] > endTime) {
                cnt++;
                endTime = A[i][1];
            }
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/largest-permutation/
    static int[] largestPermutation(int[] A, int B) {
        int n = A.length;
        // position map for each element in array
        int[] map = new int[n + 1];
        for (int i = 0; i < n; i++)
            map[A[i]] = i;

        // check till the end of array is not reached or swaps limit is not reached
        for (int i = 0; i < n && B > 0; i++) {
            int N = n - i;
            // if the next largest is at current position, no need to swap
            if (A[i] == N)
                continue;

            int pos = map[N];
            // swap current element with next largest
            A[pos] = A[i];
            A[i] = N;
            // update positions of the swapped elements in map
            map[N] = i;
            map[A[pos]] = pos;
            // update remaining swaps limit
            B--;
        }

        return A;
    }

    // https://www.interviewbit.com/problems/meeting-rooms/
    static int meetingRooms(int[][] A) {
        int n = A.length;
        // create separate arrays for start and end times of meetings
        int[] start = new int[n], end = new int[n];
        for (int i = 0; i < n; i++) {
            start[i] = A[i][0];
            end[i] = A[i][1];
        }

        // sort both the arrays in increasing order
        Arrays.sort(start);
        Arrays.sort(end);

        // first meeting will always occupy one room
        int i = 1, j = 0;
        int res = 1, curr = 1;
        // process the start times and end times of meetings
        while (i < n && j < n) {
            // new meeting must start - allocate new room
            if (start[i] < end[j]) {
                curr++;
                res = Math.max(res, curr);
                i++;
            }
            // previous meeting over - vacate the room
            else {
                curr--;
                j++;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/distribute-candy/
    static int distributeCandy(int[] A) {
        int n = A.length;
        // #candies received by each child
        int[] candies = new int[n];
        // first child
        candies[0] = 1;

        for (int i = 1; i < n; i++) {
            // if more priority than left neighbour, give one candy more than the neighbour else give only one candy
            if (A[i] > A[i - 1])
                candies[i] = candies[i - 1] + 1;
            else
                candies[i] = 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            // if more priority than right neighbour, give one candy more than the neighbour
            // #candies received = #candies required to have more candy than both left and right low priority neighbours
            if (A[i] > A[i + 1])
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        }

        int total = 0;
        for (int val : candies)
            total += val;

        return total;
    }

    // https://www.interviewbit.com/problems/seats/
    static int seats(String A) {
        int MOD = 10000003, n = A.length();
        // list with positions of people seated
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (A.charAt(i) == 'x')
                positions.add(i);
        }

        int cnt = positions.size();
        // if no people seated or all are seated together without spaces
        if (cnt == 0 || cnt == n)
            return 0;

        // optimal solution is moving all people around the middle seated person
        int mid = cnt / 2, median = positions.get(mid), res = 0;
        // move each person to positions around the median person
        for (int i = 0; i < cnt; i++) {
            // cost = positions[i] - (median position + #positions to left/right of the median)
            res = (res + Math.abs(positions.get(i) - (median + i - mid))) % MOD;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/assign-mice-to-holes/
    static int assignMiceToHoles(int[] A, int[] B) {
        // sort mice and hole positions
        Arrays.sort(A);
        Arrays.sort(B);

        int res = 0;
        // result is the maximum time taken for a mouse to move to the next available hole
        for (int i = 0; i < A.length; i++)
            res = Math.max(res, Math.abs(B[i] - A[i]));

        return res;
    }

    // https://www.interviewbit.com/problems/majority-element/
    static int majorityElement(final int[] A) {
        int n = A.length;
        // select first element as possible candidate (Moore's Voting Algorithm)
        int first = A[0], count = 1;

        for (int i = 1; i < n; i++) {
            // increment count if candidate element found again
            if (A[i] == first)
                count++;
            else {
                count--;
                // candidate element not in majority till now - select a new candidate
                if (count == 0) {
                    count = 1;
                    first = A[i];
                }
            }
        }

        count = 0;
        // count number of occurrences of candidate element
        for (int val : A) {
            if (val == first)
                count++;
        }

        // candidate is the actual majority element
        if (count > n / 2)
            return first;
        // no majority element
        return -1;
    }

    // https://www.interviewbit.com/problems/gas-station/
    static int gasStation(final int[] A, final int[] B) {
        int n = A.length;
        // if only one station
        if (n == 1)
            return A[0] - B[0] >= 0 ? 0 : -1;

        // start from position 0
        int start = 0, end = 1;
        // current petrol
        int curr = A[0] - B[0];

        // loop till not completed full circle
        while (start != end) {
            // sliding window - remove gas stations from the start and update starting point
            while (start != end && curr < 0) {
                curr -= A[start] - B[start];
                start = (start + 1) % n;
                // reached position 0 without a solution
                if (start == 0)
                    return -1;
            }
            // add current gas station to the sliding window
            curr += A[end] - B[end];
            end = (end + 1) % n;
        }

        // solution not found
        if (curr < 0)
            return -1;
        return start;
    }
}