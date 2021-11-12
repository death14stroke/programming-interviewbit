import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int[] A = {1, 2, 1, 2, 3};
        int B = 2;

        System.out.println(Arrays.toString(A));
        System.out.println(TwoPointers.subArraysWithDistinct(A, B));
    }
}