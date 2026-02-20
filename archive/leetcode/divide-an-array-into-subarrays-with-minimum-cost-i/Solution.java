class Solution {
    public int minimumCost(int[] nums) {
        int a1 = nums[1];
        int a2 = nums[2];
        if (a1 > a2) {
            a1 ^= a2; // a1 = (a1 ^ a2)
            a2 ^= a1; // a2 = a2 ^ (a1 ^ a2) = a1
            a1 ^= a2; // a1 = (a1 ^ a2) ^ a1 = a2
        }
        for (int i = 3; i < nums.length; i++) {
            if (nums[i] < a1) {
                a2 = a1;
                a1 = nums[i];
            } else if (nums[i] < a2) {
                a2 = nums[i];
            }
        }
        return nums[0] + a1 + a2;
    }
}
