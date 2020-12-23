import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    public static void main(String[] args) {
        String A = "hit";
        String B = "cog";
        String[] C1 = {"hot", "dot", "dog", "lot", "log"};

        ArrayList<String> C = new ArrayList<>();
        Collections.addAll(C, C1);

        System.out.println(Graphs.wordLadder2(A, B, C));
    }
}