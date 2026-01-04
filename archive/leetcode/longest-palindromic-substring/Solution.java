class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        int maxLength = 1;
        int[] bestSubstring = new int[]{0, 0};
        for (int mid = 0; mid < n; mid++) {
            int left = mid - 1;
            int right = mid + 1;
            while (left > -1 && right < n && s.charAt(left) == s.charAt(right)) {
                int len = right - left + 1;
                if (len > maxLength) {
                    bestSubstring[0] = left;
                    bestSubstring[1] = right;
                    maxLength = len;
                }
                left--;
                right++;
            }
        }
        for (int mid1 = 0; mid1 < n - 1; mid1++) {
            int mid2 = mid1 + 1;
            if (s.charAt(mid1) != s.charAt(mid2)) continue;
            int left = mid1;
            int right = mid2;
            while (left > -1 && right < n && s.charAt(left) == s.charAt(right)) {
                int len = right - left + 1;
                if (len > maxLength) {
                    bestSubstring[0] = left;
                    bestSubstring[1] = right;
                    maxLength = len;
                }
                left--;
                right++;
            }
        }
        return s.substring(bestSubstring[0], bestSubstring[1] + 1);
    }
}
