import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        Integer[] a1 = {-259, -825, 459, 825, 221, 870, 626, 934, 205, 783, 850, 398};
        ArrayList<Integer> A = Array.toIntArrayList(a1);
        int B = -42;

        Array.printArrayList(A);
        System.out.printf("pair with diff %d : %d\n", B, TwoPointers.solve(A, B));
    }
}