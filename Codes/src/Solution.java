import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        Integer[] A = {1, 1, 1, 1, 1};
        int target = 5;

        ArrayList<ArrayList<Integer>> res = Backtracking.combinationSum(new ArrayList<>(Arrays.asList(A)), target);

        for (ArrayList<Integer> subset : res)
            Array.printArrayList(subset);
    }
}