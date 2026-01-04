class Solution {
    public int missingNumber(int[] nums) {
        int actualSum = 0;
        for (int x : nums) {
            actualSum += x;
        }
        int n = nums.length;
        int sum = n * (n + 1) / 2;
        int missing = sum - actualSum;
        return missing;
    }
}
