import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        Integer[] a1 = {1, 1, 1, 2, 2};
        ArrayList<Integer> A = Array.toIntArrayList(a1);

        Array.printArrayList(A);
        System.out.printf("no of triangles = %d\n", TwoPointers.nTriang(A));
    }
}