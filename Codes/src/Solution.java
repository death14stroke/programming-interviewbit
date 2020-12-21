import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        char[][] A = {
                {'X', 'O', 'X'},
                {'X', 'O', 'X'},
                {'X', 'O', 'X'}
        };

        ArrayList<ArrayList<Character>> A1 = new ArrayList<>();
        for (char[] c : A) {
            ArrayList<Character> list = new ArrayList<>();
            for (char c1 : c)
                list.add(c1);
            A1.add(list);
        }

        Graphs.captureRegions(A1);

        System.out.println(A1);
    }
}