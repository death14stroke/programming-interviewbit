public class Solution {
    public static void main(String[] args) {
        int[][] A = {
                {1, 2, 2, 3, 5},
                {3, 2, 3, 4, 4},
                {2, 4, 5, 3, 1},
                {6, 7, 1, 4, 5},
                {5, 1, 1, 2, 4}
        };

        System.out.println(Graphs.waterFlow(A));
    }
}