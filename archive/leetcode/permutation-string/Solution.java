class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int[] f1 = new int[26];
        int n1 = s1.length();
        for (int i = 0; i < n1; i++) {
            int c = s1.charAt(i) - 'a';
            f1[c]++;
        }
        int[] f2 = new int[26];
        int n2 = s2.length();
        int l = 0;
        int r = 0;
        while (l < n2 && r < n2) {
            int rc = s2.charAt(r) - 'a';
            r++;
            f2[rc]++;
            while (l <= r && f2[rc] > f1[rc]) {
                int lc = s2.charAt(l) - 'a';
                f2[lc]--;
                l++;
            }
            if (Arrays.equals(f1, f2)) {
                return true;
            }
        }
        return false;
    }
}
