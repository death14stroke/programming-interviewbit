import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        Integer[] a1 = {-4, 3};
        Integer[] b1 = {-2, -2};

        ArrayList<Integer> A = Array.toIntArrayList(a1), B = Array.toIntArrayList(b1);

        Array.printArrayList(A);
        Array.printArrayList(B);

        TwoPointers.merge(A, B);

        Array.printArrayList(A);
    }
}