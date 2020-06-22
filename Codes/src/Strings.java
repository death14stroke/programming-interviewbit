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
}