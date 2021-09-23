public class Solution {
    public static void main(String[] args) {
        int[][] A = {
                {0, 1, 1, 0},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 0, 0}
        };

        System.out.println(DP.maxRectInBinaryMatrix(A));
    }
}