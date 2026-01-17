class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] minEnd = new int[n + 1];
        final int INF = (int) 1e9;
        Arrays.fill(minEnd, INF);
        minEnd[0] = -INF;
        for (int i = 0; i < n; i++) {
            for (int len = n; len > 0; len--) {
                if (minEnd[len] > nums[i] && nums[i] > minEnd[len - 1]) {
                    minEnd[len] = nums[i];
                    break;
                }
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
