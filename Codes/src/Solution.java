import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        int a = 1, b = 1000000;
        Integer[] c1 = {1000000, 1000000};
        ArrayList<Integer> c = Array.toIntArrayList(c1);

        Array.printArrayList(c);
        System.out.printf("min cost for %d painters = %d", a, BinarySearch.paint(a, b, c));
    }
}