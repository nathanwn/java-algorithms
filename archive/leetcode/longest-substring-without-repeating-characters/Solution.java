class Solution {
    public int lengthOfLongestSubstring(String s) {
        int[] count = new int[(1 << 7) + 1];
        int dups = 0;
        int n = s.length();
        int l = 0;
        int ans = 0;
        for (int r = 0; r < n; r++) {
            char rc = s.charAt(r);
            count[rc]++;
            if (count[rc] == 2) {
                dups++;
            }
            while (dups > 0) {
                char lc = s.charAt(l);
                l++;
                count[lc]--;
                if (count[lc] == 1) {
                    dups--;
                }
            }
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }
}
