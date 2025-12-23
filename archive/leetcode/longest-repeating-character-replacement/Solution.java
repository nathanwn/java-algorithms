class Solution {
    public final int ALPHA = 26;

    public int characterReplacement(String s, int k) {
        // Given current segment [l, r]
        // Extend r until length - freq[mostFrequentChar] > k
        int n = s.length();
        int[] freq = new int[ALPHA];
        int l = 0;
        int r = 0;
        int ans = 0;
        while (l < n && r < n) {
            int rc = s.charAt(r) - 'A';
            freq[rc]++;
            int length = r - l + 1;
            int mostFrequentChar = getMostFrequentChar(freq);
            r++;
            if (length - freq[mostFrequentChar] <= k) {
                ans = Math.max(ans, length);
            } else {
                int lc = s.charAt(l) - 'A';
                freq[lc]--;
                l++;
            }
        }
        return ans;
    }

    int getMostFrequentChar(int[] freq) {
        int c = 0;
        for (int i = 1; i < ALPHA; i++) {
            if (freq[i] > freq[c]) {
                c = i;
            }
        }
        return c;
    }
}
