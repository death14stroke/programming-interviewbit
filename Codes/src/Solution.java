import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        int[][] a1 = {
                {2, 9, 12, 13, 16, 18, 18, 19, 20, 22},
                {29, 59, 62, 66, 71, 75, 77, 79, 97, 99}
        };

        ArrayList<ArrayList<Integer>> a = Array.toIntMatrix(a1);
        int b = 45;

        Array.printMatrix(a);

        System.out.printf("found %d in matrix : %d", b, BinarySearch.searchMatrix(a, b));
    }
}