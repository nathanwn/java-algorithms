class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        HashSet<List<Integer>> triplets = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int target = -x;
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                int y = nums[j];
                int z = nums[k];
                int sum2 = y + z;
                if (sum2 == target) {
                    triplets.add(Arrays.asList(x, y, z));
                    while (j < n && nums[j] == y) j++;
                    while (k > -1 && nums[k] == z) k--;
                } else if (sum2 < target) {
                    j++;
                } else {
                    k--;
                }
            }
        }
        return new ArrayList<>(triplets);
    }
}
