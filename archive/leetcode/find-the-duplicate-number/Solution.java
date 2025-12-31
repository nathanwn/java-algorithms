class Solution {
    public int findDuplicate(int[] nums) {
        // See each value as the index to the next node
        // Use Floyd's tortoise and hare algorithm to find the cycle
        // and the starting point of the cycle.
        // https://cp-algorithms.com/others/tortoise_and_hare.html
        int slow = 0;
        int fast = 0;
        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];
            if (slow == fast) {
                break;
            }
        }
        int p1 = slow;
        int p2 = 0;
        while (p1 != p2) {
            p1 = nums[p1];
            p2 = nums[p2];
        }
        return p1;
    }
}
