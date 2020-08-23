import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        Trees.TreeNode root = new Trees.TreeNode(3);
        root.left = new Trees.TreeNode(9);
        root.right = new Trees.TreeNode(20);
        root.right.left = new Trees.TreeNode(15);
        root.right.right = new Trees.TreeNode(7);

        System.out.print(Trees.zigzagLevelOrder(root));
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