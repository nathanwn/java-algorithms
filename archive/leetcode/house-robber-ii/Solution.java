class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        // dp[i][doRobFirst][doRob]: max money obtains after going through houses 0..i,
        // rob (or not rob) first house, and rob (or not rob) house i
        int[][][] dp = new int[n + 1][2][2];
        dp[1][0][0] = 0;
        dp[1][1][1] = nums[0];
        for (int i = 2; i <= n; i++) {
            for (int r = 0; r <= 1; r++) {
                dp[i][r][0] = Math.max(dp[i - 1][r][0], dp[i - 1][r][1]);
                dp[i][r][1] = dp[i - 1][r][0] + nums[i - 1];
            }
        }
        return Math.max(dp[n][0][1], dp[n][1][0]);
    }
}
