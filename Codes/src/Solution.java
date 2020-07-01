import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        Integer[] a1 = {1, 2, 3};
        ArrayList<Integer> A = Array.toIntArrayList(a1);

        Array.printArrayList(A);
        System.out.printf("remove duplicates length = %d\n", TwoPointers.removeDuplicates(A));
        Array.printArrayList(A);
    }
}