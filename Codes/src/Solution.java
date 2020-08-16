import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        int[] A = {1, 1, 2, 2};
        int B = 1;

        printArray(A);
        printArray(HeapsMaps.distinctNumbersInWindow(A, B));
    }

    static void printArray(int[] a) {
        for (int val : a)
            System.out.print(val + " ");
        System.out.println();
    }

    static <T> void printArray(T[] a) {
        for (T val : a)
            System.out.print(val + " ");
        System.out.println();
    }

    static void printMatrix(int[][] a) {
        for (int[] arr : a) {
            for (int val : arr)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }

    static <T> void printMatrix(T[][] a) {
        for (T[] arr : a) {
            for (T val : arr)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }

    static <T> void printMatrix(ArrayList<ArrayList<T>> a) {
        for (List<T> arr : a) {
            for (T val : arr)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }

    static <T> void printArrayList(ArrayList<T> a) {
        for (T val : a)
            System.out.print(val + " ");
        System.out.println();
    }
}