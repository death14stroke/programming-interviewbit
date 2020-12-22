public class Solution {
    public static void main(String[] args) {
        int A = 4;
        int[][] B = {
                {1, 2, 1},
                {2, 3, 2},
                {3, 1, 4}
        };
        int C = 1, D = 4;
        int[][] E = {
                {1, 3, 2}
        };

        System.out.println(Graphs.usefulExtraEdges(A, B, C, D, E));
    }
}