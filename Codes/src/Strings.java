import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

@SuppressWarnings("StringRepeatCanBeUsed")
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
        int n = A.length();
        int start = n - 1, end = n - 1;
        StringBuilder res = new StringBuilder();

        // one-pass algorithm: iterate the string in reverse and keep track of start and end for each word
        // skip extra white spaces at the end or middle if any
        while (start >= 0 && start <= end) {
            // remove extra white spaces
            while (end >= 0 && A.charAt(end) == ' ')
                end--;

            start = end;

            // find starting point of word
            while (start >= 0 && A.charAt(start) != ' ')
                start--;

            // append word with one blank space
            res.append(A, start + 1, end + 1);
            res.append(" ");

            // check for next word from the right
            end = start;
        }

        return res.toString().trim();
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

        // remove leading and ending spaces
        int l = 0, r = n - 1;
        while (l < n && A.charAt(l) == ' ')
            l++;
        // all were spaces
        if (l == n)
            return 0;
        while (r >= l && A.charAt(r) == ' ')
            r--;

        char[] c = A.substring(l, r + 1).toCharArray();
        // if processed string is empty or the only char is not digit
        if (c.length == 1 && !Character.isDigit(c[0]))
            return 0;

        // if first char is not sign, dot or digit
        if (c[0] != '+' && c[0] != '-' && c[0] != '.' && !Character.isDigit(c[0]))
            return 0;

        boolean dot = (c[0] == '.'), exp = false;
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

    // https://www.interviewbit.com/problems/valid-ip-addresses/
    static ArrayList<String> restoreIpAddresses(String A) {
        ArrayList<String> res = new ArrayList<>();

        int n = A.length();
        // length of valid IPV4 address will be min 4 and max 12
        if (n < 4 || n > 12)
            return res;

        // regex pattern for ipv4
        Pattern ipv4 = Pattern.compile("((([0-9])|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\.){3}(([0-9])|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))");

        // try out all combinations for placing dots
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    // create ipv4 string and match with regex
                    String address = A.substring(0, i + 1) + "." + A.substring(i + 1, j + 1) + "." +
                            A.substring(j + 1, k + 1) + "." + A.substring(k + 1);

                    if (ipv4.matcher(address).matches())
                        res.add(address);
                }
            }
        }

        return res;
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

    // https://www.interviewbit.com/problems/integer-to-roman/
    static String intToRoman(int n) {
        // ones = { blank, 1...9 }
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        // tens = { blank, 10...90 }
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        // hundreds = { blank, 100...900 }
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        // thousands = { blank, 1000...3000 }
        String[] thousands = {"", "M", "MM", "MMM"};

        return thousands[n / 1000] + hundreds[(n % 1000) / 100] + tens[(n % 100) / 10] + ones[n % 10];
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

    // https://www.interviewbit.com/problems/multiply-strings/
    static String multiply(String A, String B) {
        int m = A.length(), n = B.length();
        String res = "";

        // school multiplication technique: 123 * 456 =
        // (123 * 400) + (123 * 50) + (123 * 6)
        for (int i = n - 1; i >= 0; i--) {
            StringBuilder tmp = new StringBuilder();
            int carry = 0;

            // multiply string A with each digit of B
            for (int j = m - 1; j >= 0; j--) {
                int prod = (B.charAt(i) - '0') * (A.charAt(j) - '0') + carry;
                int digit = prod % 10;
                carry = prod / 10;

                tmp.append(digit);
            }

            // append carry for multiplication
            if (carry != 0)
                tmp.append(carry);
            // reverse the string as it was appended
            tmp.reverse();

            // add the zeroes as per the decimal position of digit in string b
            //noinspection StringRepeatCanBeUsed
            for (int k = 0; k < n - 1 - i; k++)
                tmp.append(0);

            // add the current product with previous result
            res = add(res, tmp.toString());
        }

        // remove leading zeroes
        int start = 0;
        while (start < res.length() && res.charAt(start) == '0')
            start++;

        return res.substring(start);
    }

    // util for adding two strings
    private static String add(String A, String B) {
        // ensure A is the larger string
        if (A.length() < B.length())
            return add(B, A);

        int m = A.length(), n = B.length();
        StringBuilder res = new StringBuilder();

        int i = m - 1, j = n - 1;
        int carry = 0;

        // add all the digits in B with corresponding digits in A
        while (i >= 0 && j >= 0) {
            int sum = (A.charAt(i) - '0') + (B.charAt(j) - '0') + carry;
            int digit = sum % 10;
            carry = sum / 10;

            res.append(digit);
            i--;
            j--;
        }

        // add the remaining digits in A
        while (i >= 0) {
            int sum = (A.charAt(i) - '0') + carry;
            int digit = sum % 10;
            carry = sum / 10;

            res.append(digit);
            i--;
        }

        // append carry if exists
        if (carry > 0)
            res.append(carry);

        // reverse the result to get the number
        return res.reverse().toString();
    }

    // https://www.interviewbit.com/problems/justified-text/
    static ArrayList<String> fullJustify(ArrayList<String> words, int L) {
        ArrayList<String> res = new ArrayList<>();
        if (words.isEmpty())
            return res;

        // chars = number of non-space characters
        // spaces = minimum number of spaces required
        // start = position of first word of the line in list
        int chars = words.get(0).length(), spaces = 0, start = 0;
        for (int i = 1; i < words.size(); i++) {
            // skip empty words
            if (words.get(i).isEmpty())
                continue;

            // if current word cannot be added in the same line
            if (chars + spaces + words.get(i).length() + 1 > L) {
                // construct and add current line
                String line = constructLineWithSpaces(words, start, i, L, chars, spaces);
                res.add(line);

                // create new line
                start = i;
                chars = words.get(i).length();
                spaces = 0;
            } else {
                // add word to current line
                chars += words.get(i).length();
                spaces++;
            }
        }

        // add the remaining words to last line
        String line = constructLineWithSpaces(words, start, words.size(), L, chars, spaces);
        res.add(line);

        return res;
    }

    // util to construct string with equal spaces in middle as per limit
    private static String constructLineWithSpaces(ArrayList<String> words, int start, int end, int L, int chars, int spaces) {
        StringBuilder line = new StringBuilder();
        // total white spaces in the string
        int totalSpace = L - chars;

        // add the first word
        line.append(words.get(start));

        // if there is only one word on the line
        if (spaces == 0) {
            // add all the white spaces at the end
            for (int i = 0; i < totalSpace; i++)
                line.append(" ");

            return line.toString();
        }

        // blanks = # white spaces per word
        int blanks = totalSpace / spaces;
        for (int i = start + 1; i < end; i++) {
            // if this is the last string, add only one white space after each word
            if (end == words.size()) {
                line.append(" ");
                totalSpace--;
            } else {
                // add the equal amount of white spaces
                for (int k = 0; k < blanks; k++)
                    line.append(" ");

                // if division is not even give the extra white space if left
                if (totalSpace % spaces != 0) {
                    line.append(" ");
                    totalSpace--;
                }
            }

            // append the current word after white space
            line.append(words.get(i));
        }

        // if this is last string add all the remaining white spaces at the end
        if (end == words.size()) {
            for (int k = 0; k < totalSpace; k++)
                line.append(" ");
        }

        return line.toString();
    }

    // https://www.interviewbit.com/problems/zigzag-string/
    static String convert(String A, int rows) {
        // if # required rows is 1 return the string itself
        if (rows == 1)
            return A;

        int n = A.length();
        StringBuilder res = new StringBuilder();

        // first row: always move down
        int pos = 0;
        while (pos < n) {
            res.append(A.charAt(pos));
            pos += 2 * (rows - 1);
        }

        // middle rows: move down and then up
        for (int r = 1; r < rows - 1; r++) {
            // initial direction will be down
            boolean down = true;
            pos = r;

            while (pos < n) {
                res.append(A.charAt(pos));

                // move down and then switch dir to up
                if (down) {
                    pos += 2 * (rows - 1 - r);
                    down = false;
                }
                // move up and then switch dir to down
                else {
                    pos += 2 * r;
                    down = true;
                }
            }
        }

        // last row: always move up
        pos = rows - 1;
        while (pos < n) {
            res.append(A.charAt(pos));
            pos += 2 * (rows - 1);
        }

        return res.toString();
    }

    // https://www.interviewbit.com/problems/pretty-json/
    static ArrayList<String> prettyJson(String A) {
        ArrayList<String> res = new ArrayList<>();

        // empty string or first char is not open bracket (invalid JSON)
        if (A.length() == 0 || (A.charAt(0) != '{' && A.charAt(0) != '['))
            return res;

        int tabs = 0;
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < A.length(); i++) {
            switch (A.charAt(i)) {
                case '{':
                case '[':
                    // if previous line is not blank, add to result
                    if (line.length() != 0 && !Character.isWhitespace(line.charAt(line.length() - 1)))
                        res.add(line.toString());

                    // create new line with tabs for current line,
                    // append bracket and add to result
                    initTabbedLine(line, tabs);
                    line.append(A.charAt(i));
                    res.add(line.toString());

                    // increment tabs and create new line with tabs for next line
                    tabs++;
                    initTabbedLine(line, tabs);
                    break;

                case '}':
                case ']':
                    // if previous line is not blank, add to result
                    if (line.length() != 0 && !Character.isWhitespace(line.charAt(line.length() - 1)))
                        res.add(line.toString());

                    // decrement tab count and create new line with tabs and bracket
                    tabs--;
                    initTabbedLine(line, tabs);
                    line.append(A.charAt(i));

                    // if next char is comma(,) then add it to the same line
                    // skip to char after comma
                    if (i + 1 < A.length() && A.charAt(i + 1) == ',') {
                        line.append(A.charAt(i + 1));
                        i++;
                    }
                    // add line to result
                    res.add(line.toString());

                    // create new line with tabs for next line
                    initTabbedLine(line, tabs);
                    break;

                case ',':
                    // add comma at the end, add line to result
                    line.append(A.charAt(i));
                    res.add(line.toString());

                    // create new line with tabs for next line
                    initTabbedLine(line, tabs);
                    break;

                default:
                    line.append(A.charAt(i));
            }
        }

        return res;
    }

    // util for creating tabs at the beginning
    private static void initTabbedLine(StringBuilder builder, int tabs) {
        builder.setLength(0);
        for (int j = 0; j < tabs; j++)
            builder.append("\t");
    }

    // https://www.interviewbit.com/problems/convert-to-palindrome/
    static int convertToPalindrome(String A) {
        int start = 0, end = A.length() - 1;

        // skip the palindromic characters at the ends
        while (start < end && A.charAt(start) == A.charAt(end)) {
            start++;
            end--;
        }

        // odd length palindrome or substring by removing first non-matching or last-non matching char is palindrome
        if (start == end || isPalindrome(A, start + 1, end) || isPalindrome(A, start, end - 1))
            return 1;

        // not possible to make palindrome by removing exactly one char
        return 0;
    }

    // util to check if string is palindrome or not
    private static boolean isPalindrome(String A, int start, int end) {
        while (start <= end) {
            // not palindrome
            if (A.charAt(start) != A.charAt(end))
                return false;

            // check for next positions
            start++;
            end--;
        }

        return true;
    }

    // https://www.interviewbit.com/problems/minimum-appends-for-palindrome/
    static int minAppendsForPalindrome(String A) {
        // create string rev(A) + "$" + A and compute lps
        String str = new StringBuilder(A)
                .reverse()
                .append('$')
                .append(A)
                .toString();
        int[] lps = computeLpsArray(str);

        // characters needed at end = len(A) - lps(last pos of str)
        return A.length() - lps[str.length() - 1];
    }

    // https://www.interviewbit.com/problems/minimum-parantheses/
    static int minParenthesis(String A) {
        int n = 0, cnt = 0;
        // check each parenthesis and maintain the balance count
        for (char c : A.toCharArray()) {
            if (c == '(')
                n++;
            else
                n--;

            // if number of closing parenthesis is greater than open so far
            // add open parenthesis at beginning to balance
            if (n < 0) {
                cnt += Math.abs(n);
                n = 0;
            }
        }

        // add closing parenthesis at end for extra open ones
        cnt += Math.abs(n);

        return cnt;
    }

    // https://www.interviewbit.com/problems/remove-consecutive-characters/
    static String removeConsecutive(String A, int B) {
        StringBuilder res = new StringBuilder();

        int n = A.length(), i = 0;
        while (i < n) {
            int j = i + 1;
            // skip consecutive duplicate characters
            while (j < n && A.charAt(j) == A.charAt(i))
                j++;

            // if count of consecutive duplicates is not exactly B, add A[i]
            if (j - i != B)
                res.append(A.charAt(i));

            // check for next char after skipping duplicates
            i = j;
        }

        return res.toString();
    }
}