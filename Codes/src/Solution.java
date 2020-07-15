import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        Integer[] a1 = {4, 5, 7, 5};
        ArrayList<Integer> A = Array.toIntArrayList(a1);

        System.out.printf("xor subarray = %d\n", BitManipulation.xoringSubarrays(A));
    }
}