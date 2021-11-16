import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int[][] A = {
                {1, 0, 0, 1}
        };
        int[][] B = {
                {1, 2},
                {1, 3}
        };

        System.out.println(Arrays.toString(StackQueue.nearestHotel(A, B)));
    }
}