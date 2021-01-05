import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Strings {
    // https://www.interviewbit.com/problems/palindrome-string/
    static int isPalindrome(String A) {
        int n = A.length();
        int l = 0, r = n - 1;

        while (l < r) {
            // convert left and right char to lowerCase. If any of them is not alphanumeric, skip it
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

            // if both left and right char are not equal
            if (c1 != c2)
                return 0;

            // check next pair of characters
            l++;
            r--;
        }

        return 1;
    }

    // https://www.interviewbit.com/problems/vowel-and-consonant-substrings/
    static int vowelAndConsonantSubstr(String A) {
        int n = A.length(), p = 1000000007;
        // #vowels and consonants in the string
        int vo = 0, co = 0;

        for (char c : A.toCharArray()) {
            if (isVowelLowerCase(c))
                vo++;
            else
                co++;
        }

        // #total substrings that can be formed
        long totalSubstr = (n * (n - 1L) / 2) % p;
        // #substrings starting and ending with vowels
        long voSubstr = (vo * (vo - 1L) / 2) % p;
        // #substrings starting and ending with consonants
        long coSubstr = (co * (co - 1L) / 2) % p;

        // total substrings - substrings not valid (starting and ending with both vowels or consonants)
        return (int) ((totalSubstr - voSubstr - coSubstr) % p);
    }

    private static boolean isVowelLowerCase(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
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

            // if count of consecutive duplicates is not exactly B, add all duplicate characters
            if (j - i != B)
                res.append(A, i, j);

            // check for next char after skipping duplicates
            i = j;
        }

        return res.toString();
    }

    // https://www.interviewbit.com/problems/longest-common-prefix/
    static String longestCommonPrefix(String[] A) {
        int n = A.length;
        // find the length of shortest string
        int minLength = Integer.MAX_VALUE;
        for (String s : A)
            minLength = Math.min(minLength, s.length());

        // position till which prefix characters match for all
        int endIndex = 0;
        // check for all character positions till minLength
        for (int i = 0; i < minLength; i++) {
            char c = A[0].charAt(i);
            int j = 1;
            // compare the character at each position till j for all strings
            while (j < n && c == A[j].charAt(i))
                j++;
            // if all strings do not match at pos, break
            if (j != n)
                break;
            // prefix length will be incremented
            endIndex++;
        }

        // prefix of length endIndex
        return A[0].substring(0, endIndex);
    }

    // https://www.interviewbit.com/problems/count-and-say/
    static String countAndSay(int n) {
        // for n = 1 sequence is 1
        if (n == 1)
            return "1";

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
    static int amazingSubarrays(String A) {
        int n = A.length(), p = 10003;
        int cnt = 0;

        // if a vowel is found at pos i, then # substrings = strings each of length 1 to n - i starting at i
        // hence cnt = cnt + (n - i)
        for (int i = 0; i < n; i++) {
            if (isVowel(A.charAt(i)))
                cnt = (cnt + (n - i)) % p;
        }

        return cnt;
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

    // https://www.interviewbit.com/problems/implement-strstr/
    static int strStr(final String str, final String pat) {
        int n = str.length(), m = pat.length();
        // if haystack (str) or needle(pat) is empty
        if (n == 0 || m == 0)
            return -1;

        int[] lps = computeLpsArray(pat);
        int i = 0, j = 0;

        while (i < n) {
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
                if (j == 0)
                    i++;
                    // lps[j-1] must be matching, compare chars after that
                else
                    j = lps[j - 1];
            }
        }

        return lps;
    }

    // https://www.interviewbit.com/problems/stringoholics/
    static int stringoholics(String[] A) {
        final int p = 1000000007;
        // minimum time for each string to get back to original value after rotations
        ArrayList<Integer> times = new ArrayList<>();

        // for each string
        for (String str : A) {
            // find maximum lps length
            int maxLen = computeMaxLps(str);
            int n = str.length();

            // can use this lps length as string will return to original string before n*(n+1)/2 time
            if (n % (n - maxLen) == 0)
                n -= maxLen;

            long sum = 1;
            int i = 2;
            // find i*(i+1)/2 which will be divisible by length of original string (or lps removed string)
            // a string will always return back to original at n * x rotations
            while (sum % n != 0) {
                sum += i;
                i++;
            }

            // add minimum time found to list
            times.add(i - 1);
        }

        // return lcm of all minimum times
        return (int) lcm(times, p);
    }

    // util to compute maximum lps length of a pattern
    private static int computeMaxLps(String pat) {
        int n = pat.length();
        int[] lps = new int[n];
        int i = 1, j = 0, max = 0;

        while (i < n) {
            // pat[i] forms a part of previous lps (or new lps)
            if (pat.charAt(i) == pat.charAt(j)) {
                lps[i] = lps[j + 1];
                max = Math.max(max, lps[i]);

                i++;
                j++;
            } else {
                // search for lps with next char after pat[i]
                if (j == 0)
                    i++;
                    // lps[j-1] must be matching, compare chars after that
                else
                    j = lps[j - 1];
            }
        }

        return max;
    }

    // util to update map of all factors and its powers in a num
    private static void updatePowersMap(int num, Map<Integer, Integer> powersMap) {
        // for all factors from 2 to num
        for (int i = 2; i <= num; i++) {
            int cnt = 0;
            // compute the power of i in factorization
            while (num % i == 0) {
                cnt++;
                num /= i;
            }

            // if i is a factor, update max(prevCount, cnt) in the map
            if (cnt != 0)
                powersMap.put(i, Math.max(cnt, powersMap.getOrDefault(i, 0)));
        }
    }

    // util to calculate lcm of multiple numbers
    @SuppressWarnings("SameParameterValue")
    private static long lcm(ArrayList<Integer> A, int p) {
        // map for each factor and its maximum power in factorization of all numbers
        Map<Integer, Integer> powersMap = new HashMap<>();

        // update powersMap for each number in A
        for (int num : A)
            updatePowersMap(num, powersMap);

        long lcm = 1;
        // multiply each factor raised to its maximum power to get LCM (school-method)
        for (Map.Entry<Integer, Integer> e : powersMap.entrySet()) {
            int fact = e.getKey(), cnt = e.getValue();
            long prod = BinarySearch.pow(fact, cnt, p);
            lcm = (lcm * prod) % p;
        }

        return lcm;
    }

    // https://www.interviewbit.com/problems/minimum-characters-required-to-make-a-string-palindromic/
    static int minInsertions(String A) {
        // compute lps for A + "$" + rev(A)
        String str = A + "$" + new StringBuilder(A).reverse().toString();
        int[] lps = computeLpsArray(str);

        // min insertions req = strlen(A) - lps[appended str last pos]
        return A.length() - lps[str.length() - 1];
    }

    // https://www.interviewbit.com/problems/minimum-parantheses/
    static int minParenthesis(String A) {
        int open = 0, cnt = 0;
        // check each parenthesis and maintain the balance count
        for (char c : A.toCharArray()) {
            if (c == '(')
                open++;
            else
                open--;
            // if number of closing parenthesis is greater than open so far
            // add open parenthesis at beginning to balance
            if (open < 0) {
                cnt++;
                open = 0;
            }
        }

        // add closing parenthesis at end for extra open ones
        cnt += open;

        return cnt;
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
        while (start >= 0) {
            // remove extra white spaces
            while (end >= 0 && A.charAt(end) == ' ')
                end--;

            start = end;
            // find starting point of word
            while (start >= 0 && A.charAt(start) != ' ')
                start--;

            // append word with one blank space
            res.append(A, start + 1, end + 1);
            res.append(' ');
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
        while (s1.size() < s2.size())
            s1.add("");
        while (s2.size() < s1.size())
            s2.add("");

        // loop for all the version parts
        for (int i = 0; i < s1.size(); i++) {
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
                while (x < a.length()) {
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
        }

        // both versions are equal
        return 0;
    }

    // https://www.interviewbit.com/problems/atoi/
    static int atoi(final String A) {
        int n = A.length();
        // empty string
        if (n == 0)
            return 0;

        int i = 0;
        // remove leading white spaces
        while (i < n && A.charAt(i) == ' ')
            i++;

        boolean neg = false;
        // check for + or - sign at beginning
        if (A.charAt(i) == '+') {
            i++;
        } else if (A.charAt(i) == '-') {
            neg = true;
            i++;
        }

        long ans = 0;

        for (; i < n; i++) {
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
        }

        // if - sign encountered at beginning
        if (neg)
            return (int) -ans;
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
        while (r > l && A.charAt(r) == ' ')
            r--;

        char[] c = A.substring(l, r + 1).toCharArray();
        // if the only char is not digit
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
            int a = A.charAt(i) - '0', b = B.charAt(j) - '0';
            int sum = a + b + carry;
            // add A, B and carry at pos i (=j)
            res.append(sum % 2);
            carry = sum / 2;

            i--;
            j--;
        }

        // loop for remaining bits in string A
        while (i >= 0) {
            int a = A.charAt(i) - '0';
            int sum = a + carry;

            res.append(sum % 2);
            carry = sum / 2;

            i--;
        }

        // else loop for remaining bits in string B
        while (j >= 0) {
            int b = B.charAt(j) - '0';
            int sum = b + carry;

            res.append(sum % 2);
            carry = sum / 2;

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
        // remove leading zeroes
        A = removeLeadingZeroes(A);
        // if A is 0 or 1 - not power of 2
        if (A.equals("") || A.equals("1"))
            return 0;

        // keep dividing A by 2 till it is divisible
        while ((A.charAt(A.length() - 1) - '0') % 2 == 0) {
            A = divideBy2(A);
            A = removeLeadingZeroes(A);
        }

        // A is power of 2
        if (A.equals("1"))
            return 1;
        return 0;
    }

    // util to divide string number A by 2
    private static String divideBy2(String A) {
        StringBuilder res = new StringBuilder();
        int n = A.length(), rem = 0;

        // school division - take down digits one by one and update remainder
        for (int i = 0; i < n; i++) {
            rem = rem * 10 + (A.charAt(i) - '0');

            if (rem < 2 && i + 1 < n) {
                res.append(0);
                continue;
            }

            res.append(rem / 2);
            rem = rem % 2;
        }

        return res.toString();
    }

    // util to remove leading zeroes
    private static String removeLeadingZeroes(String A) {
        int i = 0;
        while (i < A.length() && A.charAt(i) == '0')
            i++;

        return A.substring(i);
    }

    // https://www.interviewbit.com/problems/multiply-strings/
    static String multiply(String A, String B) {
        A = removeLeadingZeroes(A);
        B = removeLeadingZeroes(B);

        if (A.equals("") || B.equals(""))
            return "0";

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

        return res;
    }

    // util for adding two strings
    private static String add(String A, String B) {
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

        // add the remaining digits in B
        while (j >= 0) {
            int sum = (B.charAt(j) - '0') + carry;
            int digit = sum % 10;
            carry = sum / 10;

            res.append(digit);
            j--;
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
    private static String constructLineWithSpaces(ArrayList<String> words, int start, int end, int L,
                                                  int chars, int spaces) {
        StringBuilder line = new StringBuilder();
        // total white spaces in the string
        int totalSpace = L - chars;

        // add the first word
        line.append(words.get(start));

        // if there is only one word on the line
        if (spaces == 0) {
            // add all the white spaces at the end
            return line.append(" ".repeat(totalSpace))
                    .toString();
        }

        // if this is the last string
        if (end == words.size()) {
            for (int i = start + 1; i < end; i++) {
                // add only one white space after each word
                line.append(" ");
                totalSpace--;
                // append the current word after white space
                line.append(words.get(i));
            }

            // add all the remaining white spaces at the end
            return line.append(" ".repeat(totalSpace))
                    .toString();
        }

        // blanks = # white spaces per word
        int blanks = totalSpace / spaces;
        for (int i = start + 1; i < end; i++) {
            // add the equal amount of white spaces
            line.append(" ".repeat(blanks));

            // if division is not even give the extra white space if left
            if (totalSpace % spaces != 0) {
                line.append(" ");
                totalSpace--;
            }

            // append the current word after white space
            line.append(words.get(i));
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
        int n = A.length();
        ArrayList<String> res = new ArrayList<>();
        // empty string or first char is not open bracket (invalid JSON)
        if (n == 0 || (A.charAt(0) != '{' && A.charAt(0) != '['))
            return res;

        int tabs = 0;
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < n; i++) {
            switch (A.charAt(i)) {
                case '{':
                case '[':
                    // if previous line is not blank, add to result
                    if (line.length() != 0 && !Character.isWhitespace(line.charAt(line.length() - 1)))
                        res.add(line.toString());

                    // create new line with tabs for current line,
                    // append bracket and add to result
                    line = initTabbedLine(tabs);
                    line.append(A.charAt(i));
                    res.add(line.toString());

                    // increment tabs and create new line with tabs for next line
                    tabs++;
                    line = initTabbedLine(tabs);
                    break;

                case '}':
                case ']':
                    // if previous line is not blank, add to result
                    if (line.length() != 0 && !Character.isWhitespace(line.charAt(line.length() - 1)))
                        res.add(line.toString());

                    // decrement tab count and create new line with tabs and bracket
                    tabs--;
                    line = initTabbedLine(tabs);
                    line.append(A.charAt(i));

                    // if next char is comma(,) then add it to the same line
                    // skip to char after comma
                    if (i + 1 < n && A.charAt(i + 1) == ',') {
                        line.append(A.charAt(i + 1));
                        i++;
                    }
                    // add line to result
                    res.add(line.toString());

                    // create new line with tabs for next line
                    line = initTabbedLine(tabs);
                    break;

                case ',':
                    // add comma at the end, add line to result
                    line.append(A.charAt(i));
                    res.add(line.toString());

                    // create new line with tabs for next line
                    line = initTabbedLine(tabs);
                    break;

                default:
                    line.append(A.charAt(i));
            }
        }

        return res;
    }

    // util for creating tabs at the beginning
    private static StringBuilder initTabbedLine(int tabs) {
        return new StringBuilder()
                .append("\t".repeat(tabs));
    }
}