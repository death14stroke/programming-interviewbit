import java.util.Arrays;

class Greedy {
    // https://www.interviewbit.com/problems/highest-product/
    static int highestProduct(int[] A) {
        int n = A.length;
        // sort the array
        Arrays.sort(A);
        // maximum product is the max of product of largest three numbers and 
        // product of largest number and smallest two numbers (when both are negative) 
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
                i++;
            }
            // previous meeting over - vacate the room
            else {
                curr--;
                j++;
            }

            res = Math.max(res, curr);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/distribute-candy/
    static int distributeCandy(int[] A) {
        int n = A.length;
        // #candies received when considering only left or right neighbour
        int[] left = new int[n], right = new int[n];
        // each child will get at least one candy
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);

        for (int i = 1; i < n; i++) {
            // if more priority than left neighbour, give one candy more than the neighbour
            // else give only one candy
            if (A[i] > A[i - 1])
                left[i] = left[i - 1] + 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            // if more priority than right neighbour, give one candy more than the neighbour
            // else give only one candy
            if (A[i] > A[i + 1])
                right[i] = right[i + 1] + 1;
        }

        int total = 0;
        // for each child, #candies received =
        // #candies required to have more candy than both left and right low priority neighbours =
        // max(left[i], right[i])
        for (int i = 0; i < n; i++)
            total += Math.max(left[i], right[i]);

        return total;
    }
}