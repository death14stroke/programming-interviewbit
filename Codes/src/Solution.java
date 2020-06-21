import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        Integer[] a1 = {};
        Integer[] b1 = {20};

        ArrayList<Integer> a = Array.toIntArrayList(a1), b = Array.toIntArrayList(b1);

        System.out.printf("median in arrays = %f\n", BinarySearch.findMedianSortedArrays(a, b));
    }
}