import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

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

    // https://www.interviewbit.com/problems/count-and-say/
    static String countAndSay(int n) {
        // for n = 1 sequence is 1
        if (n == 1)
            return String.valueOf(1);

        StringBuilder res = new StringBuilder("1");
        for (int i = 2; i <= n; i++) {
            int cnt = 1;
            // add dummy char at end to process the last char
            res.append("$");
            StringBuilder temp = new StringBuilder();

            for (int j = 1; j < res.length(); j++) {
                // if res[i] matches with res[i-1] increase count
                if (res.charAt(j) == res.charAt(j - 1))
                    cnt++;
                else {
                    // append the count of digit followed by the digit to temp string
                    temp.append(cnt);
                    temp.append(res.charAt(j - 1));

                    // reset count to 1 for next char
                    cnt = 1;
                }
            }

            // assign temp to result
            res = temp;
        }

        return res.toString();
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

    // https://www.interviewbit.com/problems/minimum-characters-required-to-make-a-string-palindromic/
    static int minInsertions(String A) {
        // compute lps for A + "$" + rev(A)
        String str = A + "$" + new StringBuilder(A).reverse().toString();
        int[] lps = computeLpsArray(str);

        // min insertions req = strlen(A) - lps[appended str last pos]
        return A.length() - lps[str.length() - 1];
    }

    // https://www.interviewbit.com/problems/longest-palindromic-substring/
    private static int length, left;

    static String longestPalindrome(String A) {
        int n = A.length();
        if (n <= 1)
            return A;

        length = 1;
        left = 0;

        for (int i = 1; i < n; i++) {
            // check for even length palindromes centered at i
            checkPalindrome(A, i - 1, i);
            // check for odd length palindromes centered at i
            checkPalindrome(A, i - 1, i + 1);
        }

        return A.substring(left, left + length);
    }

    // check if string with given endpoints expands into palindrome or not
    private static void checkPalindrome(String A, int start, int end) {
        // keep moving boundaries till the substring is palindrome
        while (start >= 0 && end < A.length() && A.charAt(start) == A.charAt(end)) {
            start--;
            end++;
        }

        // if this is a longer palindrome substring
        if (end - start - 1 > length) {
            length = end - start - 1;
            left = start + 1;
        }
    }

    // https://www.interviewbit.com/problems/implement-strstr/
    static int strStr(final String str, final String pat) {
        int n = str.length(), m = pat.length();

        // if haystack (str) or needle(pat) is empty
        if (n == 0 || m == 0)
            return -1;

        int[] lps = computeLpsArray(pat);
        int i = 0, j = 0;

        while (i < n && j < m) {
            if (str.charAt(i) == pat.charAt(j)) {
                i++;
                j++;

                // found pattern ending at i-1
                if (j == m)
                    return i - m;
            } else {
                // compare next char in str with pat[0]
                if (j == 0)
                    i++;
                    // compare pat[i] with char after lps[j-1]
                else
                    j = lps[j - 1];
            }
        }

        return -1;
    }

    // lps - longest prefix that is also a suffix (KMP pre-computation)
    private static int[] computeLpsArray(String pat) {
        int m = pat.length();
        int[] lps = new int[m];

        int i = 1, j = 0;

        while (i < m) {
            // pat[i] forms a part of previous lps (or new lps)
            if (pat.charAt(i) == pat.charAt(j)) {
                lps[i] = j + 1;
                i++;
                j++;
            } else {
                // search for lps with next char after pat[i]
                if (j == 0) {
                    lps[i] = 0;
                    i++;
                }
                // lps[j-1] must be matching, compare chars after that
                else
                    j = lps[j - 1];
            }
        }

        return lps;
    }

    // https://www.interviewbit.com/problems/compare-version-numbers/
    static int compareVersion(String A, String B) {
        // split both strings by "."
        Pattern pattern = Pattern.compile("\\.");
        ArrayList<String> s1 = new ArrayList<>(Arrays.asList(pattern.split(A)));
        ArrayList<String> s2 = new ArrayList<>(Arrays.asList(pattern.split(B)));

        // make both list equal. Add empty strings to compensate
        int i = 0;
        while (s1.size() < s2.size())
            s1.add("");
        while (s2.size() < s1.size())
            s2.add("");

        // loop for all the version parts
        while (i < s1.size()) {
            String a = s1.get(i), b = s2.get(i);

            // remove leading zeroes for both strings
            int x = 0, y = 0;
            while (x < a.length() && a.charAt(x) == '0')
                x++;
            while (y < b.length() && b.charAt(y) == '0')
                y++;

            // if s1[i] is smaller after leading zeroes
            if (a.length() - x < b.length() - y)
                return -1;
                // if s2[i] is smaller after leading zeroes
            else if (a.length() - x > b.length() - y)
                return 1;
                // else compare digit by digit
            else {
                while (x < a.length() && y < b.length()) {
                    // s1[i] is smaller
                    if (a.charAt(x) < b.charAt(y))
                        return -1;
                        // s2[i] is smaller
                    else if (a.charAt(x) > b.charAt(y))
                        return 1;
                    else {
                        x++;
                        y++;
                    }
                }
            }

            // check next section
            i++;
        }

        // both versions are equal
        return 0;
    }

    // https://www.interviewbit.com/problems/atoi/
    static int atoi(final String A) {
        // empty string
        if (A.length() == 0)
            return 0;

        long ans = 0;
        int i = 0;
        boolean neg = false;

        // remove leading white spaces
        while (i < A.length() && A.charAt(i) == ' ')
            i++;

        // check for + or - sign at beginning
        if (A.charAt(i) == '+') {
            i++;
        } else if (A.charAt(i) == '-') {
            neg = true;
            i++;
        }

        while (i < A.length()) {
            char c = A.charAt(i);

            // not a digit
            if (c < '0' || c > '9')
                break;

            ans = ans * 10 + (c - '0');

            // overflow
            if (ans > Integer.MAX_VALUE) {
                if (neg)
                    return Integer.MIN_VALUE;
                return Integer.MAX_VALUE;
            }

            i++;
        }

        // if - sign encountered at beginning
        if (neg)
            ans = -ans;

        return (int) ans;
    }

    // https://www.interviewbit.com/problems/valid-number/
    static int isNumber(final String A) {
        int n = A.length();
        // empty string
        if (n == 0)
            return 0;

        // remove leading and ending zeroes
        int l = 0, r = n - 1;
        while (l < n && A.charAt(l) == ' ')
            l++;
        // all were zeroes
        if (l == n)
            return 0;
        while (r >= l && A.charAt(r) == ' ')
            r--;

        char[] c = A.substring(l, r + 1).toCharArray();
        // if processed string is empty or the only char is not digit
        if (c.length == 0 || c.length == 1 && !Character.isDigit(c[0]))
            return 0;

        // if first char is not sign, dot or digit
        if (c[0] != '+' && c[0] != '-' && c[0] != '.' && !Character.isDigit(c[0]))
            return 0;

        boolean dot = false, exp = false;
        for (int i = 1; i < c.length; i++) {
            // if char is not sign, dot, exp or digit
            if (c[i] != '+' && c[i] != '-' && c[i] != 'e' && c[i] != '.' && !Character.isDigit(c[i]))
                return 0;

            if (c[i] == '.') {
                // if exp or dot has already occurred
                if (exp || dot)
                    return 0;

                // if dot is last char or next char is not digit
                if (i + 1 >= n || !Character.isDigit(c[i + 1]))
                    return 0;

                dot = true;
            } else if (c[i] == 'e') {
                // if exp has already occurred
                if (exp)
                    return 0;

                // if prev char is not digit
                if (!Character.isDigit(c[i - 1]))
                    return 0;

                // if exp is last char or next char is not sign or digit
                if (i + 1 >= n || (c[i + 1] != '-' && c[i + 1] != '+' && !Character.isDigit(c[i + 1])))
                    return 0;

                // mark exp and dot as true
                exp = true;
                dot = true;
            } else if (c[i] == '+' || c[i] == '-') {
                // if prev char is not exp or sign is the last char or next char is not digit
                // (since initial sign has already been processed, this sign will always be after exp for valid num)
                if (c[i - 1] != 'e' || i + 1 >= n || !Character.isDigit(c[i + 1]))
                    return 0;
            }
        }

        // all cases passed
        return 1;
    }

    // https://www.interviewbit.com/problems/length-of-last-word/
    static int lengthOfLastWord(final String A) {
        int n = A.length() - 1;
        // remove ending white spaces
        while (n >= 0 && A.charAt(n) == ' ')
            n--;

        int lastWordPos = n;
        // keep traversing till blank space is reached
        while (lastWordPos >= 0 && A.charAt(lastWordPos) != ' ')
            lastWordPos--;

        return n - lastWordPos;
    }

    // https://www.interviewbit.com/problems/reverse-the-string/
    static String reverseWords(String A) {
        StringBuilder res = new StringBuilder();
        // split the words by blank space
        String[] words = A.split(" ");

        for (int i = words.length - 1; i >= 0; i--) {
            // if word is not empty
            if (!words[i].isEmpty()) {
                res.append(words[i]);
                res.append(" ");
            }
        }

        // remove last blank space
        return res.toString().trim();
    }

    // https://www.interviewbit.com/problems/roman-to-integer/
    static int romanToInt(String A) {
        int n = A.length();

        // map each Roman char to its value
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        // process last Roman char
        int res = map.get(A.charAt(n - 1));

        for (int i = n - 2; i >= 0; i--) {
            // if current Roman char is less than its right one, subtract
            // e.g IV, IX
            if (map.get(A.charAt(i)) < map.get(A.charAt(i + 1)))
                res -= map.get(A.charAt(i));
                // else add its value
                // e.g VI, XX, XV
            else
                res += map.get(A.charAt(i));
        }

        return res;
    }

    // TODO: use representation of all nums in array
    // https://www.interviewbit.com/problems/integer-to-roman/
    static String intToRoman(int n) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "I");
        map.put(4, "IV");
        map.put(5, "V");
        map.put(9, "IX");
        map.put(10, "X");
        map.put(40, "XL");
        map.put(50, "L");
        map.put(90, "XC");
        map.put(100, "C");
        map.put(400, "CD");
        map.put(500, "D");
        map.put(900, "CM");
        map.put(1000, "M");

        StringBuilder res = new StringBuilder();
        int power = 1;

        while (n > 0) {
            int num = (n % 10) * power;
            if (map.containsKey(num))
                res.insert(0, map.get(num));
            else {
                if (num / power < 5) {
                    for (int i = 0; i < num / power; i++)
                        res.insert(0, map.get(power));
                } else {
                    res.insert(0, map.get(power * 5));
                    for (int i = 5; i < num / power; i++)
                        res.insert(1, map.get(power));
                }
            }

            n /= 10;
            power *= 10;
        }

        return res.toString();
    }

    // https://www.interviewbit.com/problems/add-binary-strings/
    static String addBinary(String A, String B) {
        StringBuilder res = new StringBuilder();
        int m = A.length(), n = B.length();
        int i = m - 1, j = n - 1, carry = 0;

        // loop from end till both strings have bits
        while (i >= 0 && j >= 0) {
            char a = A.charAt(i), b = B.charAt(j);
            // add A, B and carry at pos i (=j)
            res.append(carry ^ (a - '0') ^ (b - '0'));

            // if any two of the three bits are 1, carry will be 1 again
            if ((a == '1' && b == '1') || (carry == 1 && (a == '1' || b == '1')))
                carry = 1;
            else
                carry = 0;

            i--;
            j--;
        }

        // loop for remaining bits in string A
        while (i >= 0) {
            char a = A.charAt(i);
            res.append(carry ^ (a - '0'));

            carry = carry & (a - '0');
            i--;
        }

        // else loop for remaining bits in string B
        while (j >= 0) {
            char b = B.charAt(j);
            res.append(carry ^ (b - '0'));

            carry = carry & (b - '0');
            j--;
        }

        // if carry at the end is 1, add extra bit
        if (carry == 1)
            res.append(1);

        // reverse the string as we were appending instead of insert at beginning
        return res.reverse().toString();
    }

    // https://www.interviewbit.com/problems/power-of-2/
    static int power(String A) {
        BigInteger n = new BigInteger(A);
        // if n < 2 not power of 2
        if (n.compareTo(BigInteger.valueOf(2)) < 0)
            return 0;

        // if # set bits is 1, then it is power of 2
        if (n.bitCount() == 1)
            return 1;
        return 0;
    }
}