class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] minEnd = new int[n + 1];
        final int INF = (int) 2e9;
        Arrays.fill(minEnd, INF);
        minEnd[0] = -INF;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            // Find smallest len such that minEnd[len] > x
            int lo = 0;
            int hi = n;
            int len = -1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (minEnd[mid] > x) {
                    len = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            if (minEnd[len] > x && x > minEnd[len - 1]) {
                minEnd[len] = x;
            }
        }
        int ans = 0;
        for (int len = n; len > 0; len--) {
            if (minEnd[len] < INF) {
                ans = len;
                break;
            }
        }
        return ans;
    }
}
