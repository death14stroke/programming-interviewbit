import java.util.ArrayList;

public class Strings {
    // https://www.interviewbit.com/problems/palindrome-string/
    static int isPalindrome(String A) {
        int n = A.length();
        int l = 0, r = n - 1;

        while (l <= r) {
            // convert left and right char to lowerCase
            // if any of them is not alphanumeric, skip it
            char c1 = Character.toLowerCase(A.charAt(l));
            if (!Character.isLetterOrDigit(c1)) {
                l++;
                continue;
            }

            char c2 = Character.toLowerCase(A.charAt(r));
            if (!Character.isLetterOrDigit(c2)) {
                r--;
                continue;
            }

            // if both left and right char are alphanumeric and equal
            // check next pair of left and right
            if (c1 == c2) {
                l++;
                r--;
            } else
                return 0;
        }

        return 1;
    }

    // https://www.interviewbit.com/problems/longest-common-prefix/
    static String longestCommonPrefix(ArrayList<String> A) {
        // find the length of shortest string
        int minLength = Integer.MAX_VALUE;
        for (String s : A)
            minLength = Math.min(minLength, s.length());

        // position till which prefix characters match for all
        int endIndex = 0;

        // check for all character positions till minLength
        for (int i = 0; i < minLength; i++) {
            char c = A.get(0).charAt(i);
            int j;

            // compare the character at each position till j for all strings
            for (j = 1; j < A.size(); j++) {
                if (c != A.get(j).charAt(i))
                    break;
            }

            // if all strings match at pos, prefix length will be incremented
            if (j == A.size())
                endIndex++;
                // else mismatch
            else
                break;
        }

        // prefix of length endIndex
        return A.get(0).substring(0, endIndex);
    }

    // https://www.interviewbit.com/problems/amazing-subarrays/
    static int solve(String A) {
        int n = A.length(), p = 10003;
        int cnt = 0;

        // if a vowel is found at pos i, then
        // substrings = strings each of length 1 to n - i starting at i
        // hence cnt = cnt + (n - i)
        for (int i = 0; i < n; i++) {
            char c = Character.toLowerCase(A.charAt(i));

            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
                cnt = (cnt + (n - i)) % p;
        }

        return cnt;
    }
}