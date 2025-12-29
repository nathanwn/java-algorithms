class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> s = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            int y = target - x;
            if (s.containsKey(y)) {
                return new int[]{s.get(y), i};
            }
            s.put(x, i);
        }
        throw new RuntimeException();
    }
}
