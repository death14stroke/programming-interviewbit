public class Solution {
    public static void main(String[] args) {
        int[][] A = {
                {1, 0, 0, 0},
                {1, 1, 1, 1},
                {1, 0, 0, 0},
                {1, 0, 1, 0},
                {1, 1, 0, 0},
                {0, 1, 1, 1},
                {1, 0, 0, 0}
        };

        System.out.print(Graphs.largestRegion(A));
    }
}