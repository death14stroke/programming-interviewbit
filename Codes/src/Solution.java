import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int[] A = {2, 4, 1, 3, 5};

        System.out.println(Arrays.toString(A));
        System.out.println(Trees.countInversions(A));
    }
}