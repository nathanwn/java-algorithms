class Solution {
    public int findMin(int[] nums) {
        // Equivalent to finding the first element smaller than or
        // equal to the last element in the array.
        // Only case this cannot be found is when the array is the same
        // as the original.
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        int pos = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= nums[n - 1]) {
                pos = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (pos == -1) {
            return nums[0];
        } else {
            return nums[pos];
        }
    }
}
