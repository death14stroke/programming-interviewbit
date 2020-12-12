public class Solution {
    public static void main(String[] args) {
        int A = 5;
        int[][] B = {
                {1, 2},
                {2, 3},
                {3, 4},
                {4, 5}
        };

        System.out.print(Graphs.isPath(A, B));
    }
}