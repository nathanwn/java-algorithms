class Solution {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        HashMap<Integer, Integer> countPrefixWithSum = new HashMap<>();
        // Important: Consider empty prefix.
        countPrefixWithSum.put(0, 1);
        //    sum([l..r]) == k
        // -> preSum[r + 1] - preSum[l] = k
        // -> preSum[r + 1] - k = preSum[l]
        int ans = 0;
        int curSum = 0;
        for (int r = 0; r < n; r++) {
            curSum += nums[r];
            int leftPrefixSum = curSum - k;
            int countLeft = countPrefixWithSum.getOrDefault(leftPrefixSum, 0);
            ans += countLeft;
            countPrefixWithSum.put(
                curSum,
                countPrefixWithSum.getOrDefault(curSum, 0) + 1
            );
        }
        return ans;
    }
}
