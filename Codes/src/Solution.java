import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        String A = "{\"a\": \"This is a space\"}";

        ArrayList<String> json = Strings.prettyJson(A);
        for (String s : json)
            System.out.println(s);
    }
}