class Solution {
    public boolean isValid(String s) {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        List<Character> opening = List.of('(', '[', '{');
        List<Character> closing = List.of(')', ']', '}');

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int closingIndex = closing.indexOf(c);
            if (closingIndex == -1) {  // is opening
                stack.addLast(c);
            } else {                   // is closing
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.removeLast();
                int openingIndex = opening.indexOf(top);
                if (openingIndex != closingIndex) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
