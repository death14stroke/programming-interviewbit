import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        int n = 3;

        ArrayList<String> res = Backtracking.generateParenthesis2(n);
        Array.printArrayList(res);
    }
}