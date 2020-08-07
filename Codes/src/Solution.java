import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        Integer[] A = {1, 2};
        Integer[] B = {1, 3};
        Integer[] C = {2, 3};

        Array.printArrayList(Hashing.twoOutOfThree(new ArrayList<>(Arrays.asList(A)), new ArrayList<>(Arrays.asList(B)), new ArrayList<>(Arrays.asList(C))));
    }
}