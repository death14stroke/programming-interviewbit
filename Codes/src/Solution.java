import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        Integer[] a1 = {0, 4, 7, 9};
        ArrayList<Integer> A = Array.toIntArrayList(a1);

        Array.printArrayList(A);

        System.out.printf("min xor value = %d\n", BitManipulation.minXor(A));
    }
}