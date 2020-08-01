import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        int n = 2;

        ArrayList<Integer> res = Backtracking.grayCode(n);
        Array.printArrayList(res);
    }
}