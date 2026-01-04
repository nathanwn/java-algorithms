class Solution {
    public int missingNumber(int[] nums) {
        int actualSum = 0;
        for (int x : nums) {
            actualSum ^= x;
        }
        int n = nums.length;
        int sum = 0;
        if (n % 4 == 0) {
            sum = n;
        } else if (n % 4 == 1) {
            sum = 1;
        } else if (n % 4 == 2) {
            sum = n + 1;
        } else {
            sum = 0;
        }
        int missing = sum ^ actualSum;
        return missing;
    }
}
