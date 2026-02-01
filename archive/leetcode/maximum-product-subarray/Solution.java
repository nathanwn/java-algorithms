class Solution {
    public int maxProduct(int[] nums) {
        // dp[i][isPositive]: pos/neg subarray prod with
        //                    largest abs value
        int n = nums.length;
        int[][] dp = new int[n + 1][2];
        dp[0][0] = dp[1][0] = 0;
        int ans = -1;
        for (int i = 0; i < n; i++) {
            int x = Math.abs(nums[i]);
            if (nums[i] < 0) {
                dp[i + 1][0] = Math.max(dp[i + 1][0], x);
                dp[i + 1][0] = Math.max(dp[i + 1][0], x * dp[i][1]);
            } else {
                dp[i + 1][0] = Math.max(dp[i + 1][0], x * dp[i][0]);
            }
            if (nums[i] < 0) {
                dp[i + 1][1] = Math.max(dp[i + 1][1], x * dp[i][0]);
            } else {
                dp[i + 1][1] = Math.max(dp[i + 1][1], x);
                dp[i + 1][1] = Math.max(dp[i + 1][1], x * dp[i][1]);
            }
            ans = Math.max(ans, dp[i + 1][1]);
        }
        if (n == 1 && nums[0] < 0) {
            // Only edge case
            return nums[0];
        }
        return ans;
    }
}
