class Solution {
    public int evalRPN(String[] tokens) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            if (token.length() == 1 && "+-/*".indexOf(token.charAt(0)) != -1) {
                int rhs = stack.removeLast();
                int lhs = stack.removeLast();
                char op = token.charAt(0);
                int res = 0;
                if (op == '+') {
                    res = lhs + rhs;
                } else if (op == '-') {
                    res = lhs - rhs;
                } else if (op == '*') {
                    res = lhs * rhs;
                } else if (op == '/') {
                    res = lhs / rhs;
                } else {
                    throw new AssertionError("op is not +, -, *, or /");
                }
                stack.addLast(res);
            } else {
                stack.addLast(Integer.parseInt(token));
            }
        }
        return stack.removeLast();
    }
}
