public class Solution {
    public static void main(String[] args) {
        int[] A = {1, 3, 2, 4};
        int[] B = {1, 4, 2, 3};
        int[][] C = {
                {2, 4}
        };

        System.out.println(Graphs.permutationSwaps(A, B, C));
    }
}