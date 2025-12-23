class Solution {
    public boolean isPalindrome(String s) {
        int n = s.length();
        char[] t = new char[n];
        int m = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if ('A' <= c && c <= 'Z') {
                t[m] = (char) (c - 'A' + 'a');
                m++;
            } else if ('a' <= c && c <= 'z' ||
                       '0' <= c && c <= '9') {
                t[m] = c;
                m++;
            }
        }
        for (int i = 0; i < m / 2; i++) {
            if (t[i] != t[m - i - 1]) {
                return false;
            }
        }
        return true;
    }
}
