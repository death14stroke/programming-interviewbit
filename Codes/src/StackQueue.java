import java.util.ArrayList;
import java.util.Stack;

class StackQueue {
    // https://www.interviewbit.com/problems/generate-all-parentheses/
    static int validMixParantheses(String A) {
        Stack<Character> s = new Stack<>();

        for (char c : A.toCharArray()) {
            // if open bracket, push to stack
            if (c == '(' || c == '[' || c == '{')
                s.push(c);
                // if close bracket and stack is empty, string is invalid
            else if (s.empty())
                return 0;
                // if matching closing bracket with stack top, pop from the stack
            else if ((c == ')' && s.peek() == '(') || (c == ']' && s.peek() == '[') || (c == '}' && s.peek() == '{'))
                s.pop();
                // not matching closing bracket with stack top, invalid string
            else
                return 0;
        }

        // if stack is empty, valid string else invalid string
        return s.empty() ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/reverse-string/
    static String reverse(String A) {
        // push all characters of string to stack
        Stack<Character> s = new Stack<>();
        for (char c : A.toCharArray())
            s.push(c);

        // append characters from top of stack to result
        StringBuilder res = new StringBuilder();
        while (!s.empty())
            res.append(s.pop());

        return res.toString();
    }

    // https://www.interviewbit.com/problems/maxspprod/
    static int maxSpecialProduct(ArrayList<Integer> a) {
        int n = a.size();
        long p = 1000000007;
        long ans = -1;

        int[] leftSpecials = new int[n];
        int[] rightSpecials = new int[n];

        // get left and right specials with next greater element logic
        calculateLeftSpecials(a, leftSpecials, n);
        calculateRightSpecials(a, rightSpecials, n);

        // maximize the product first then take modulo
        for (int i = 0; i < n; i++)
            ans = Math.max(ans, (long) leftSpecials[i] * (long) rightSpecials[i]);

        return (int) (ans % p);
    }

    // util to calculate right special index j such that j > i && A[j] > A[i]
    private static void calculateRightSpecials(ArrayList<Integer> a, int[] rightSpecials, int n) {
        // no element on right
        rightSpecials[n - 1] = 0;
        Stack<Integer> s = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!s.empty() && a.get(i) > a.get(s.peek())) {
                int top = s.pop();
                rightSpecials[top] = i;
            }

            s.push(i);
        }
    }

    // util to calculate left special index j such that j < i && A[j] > A[i]
    private static void calculateLeftSpecials(ArrayList<Integer> a, int[] leftSpecials, int n) {
        // no element on left
        leftSpecials[0] = 0;
        Stack<Integer> s = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!s.empty() && a.get(i) > a.get(s.peek())) {
                int top = s.pop();
                leftSpecials[top] = i;
            }

            s.push(i);
        }
    }

    // https://www.interviewbit.com/problems/evaluate-expression/
    static int evaluate(String[] A) {
        // if no operands and operators
        if (A.length == 0)
            return 0;

        Stack<Integer> s = new Stack<>();
        for (String str : A) {
            // if operator, apply operator to top two elements of stack and push new result after popping them
            if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                int b = s.pop(), a = s.pop(), op;

                switch (str) {
                    case "+":
                        op = a + b;
                        break;
                    case "-":
                        op = a - b;
                        break;
                    case "*":
                        op = a * b;
                        break;
                    default:
                        op = a / b;
                        break;
                }

                s.push(op);
            }
            // else if operand, push the operand to the stack
            else
                s.push(Integer.parseInt(str));
        }

        // last number left will be the result of the expression
        return s.pop();
    }

    // https://www.interviewbit.com/problems/balanced-parantheses/
    static int balancedParentheses(String A) {
        // maintain count of open parentheses
        int open = 0;

        for (char c : A.toCharArray()) {
            // increment count of open parentheses
            if (c == '(')
                open++;
                // if at least one open parentheses is present, balance that with current closing one
            else if (open > 0)
                open--;
                // no open parentheses to balance this closed one, invalid string
            else
                return 0;
        }

        // if all open balanced, valid string else invalid string
        return open == 0 ? 1 : 0;
    }
}