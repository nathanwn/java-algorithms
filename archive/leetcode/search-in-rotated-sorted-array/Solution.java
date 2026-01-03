class Solution {
    public int search(int[] nums, int target) {
        int pivot = findPivot(nums);
        int n = nums.length;
        if (target > nums[n - 1]) {
            return search(nums, 0, pivot - 1, target);
        } else {
            return search(nums, pivot, n - 1, target);
        }
    }

    int search(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    int findPivot(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        int pivot = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= nums[n - 1]) {
                pivot = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (pivot == -1) {
            return 0;
        } else {
            return pivot;
        }
    }
}
