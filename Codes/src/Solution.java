import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        String S = "abbaccaaabcabbbccbabbccabbacabcacbbaabbbbbaaabaccaacbccabcbababbbabccabacbbcabbaacaccccbaabcabaabaaaabcaabcacabaa";
        String[] l = {"cac", "aaa", "aba", "aab", "abc"};

        ArrayList<String> L = new ArrayList<>(Arrays.asList(l));
        System.out.println(S);
        Array.printArrayList(L);
        Array.printArrayList(Hashing.substrConcat(S, L));
    }
}