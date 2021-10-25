import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int[] A = {5, 6, 7, 8, 9, 10, 3, 2, 1};
        int B = 30;

        System.out.println(Arrays.toString(A));
        System.out.println(BinarySearch.bitonicSearch(A, B));
    }
}