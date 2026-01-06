class Solution {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> all = new HashSet<>();
        // Keep track of the starts of chains of consecutive numbers
        HashSet<Integer> starts = new HashSet<>();
        for (int x : nums) {
            all.add(x);
        }
        for (int x : nums) {
            if (!all.contains(x - 1)) {
                starts.add(x);
            }
        }
        int res = 0;
        for (int start : starts) {
            int end = start;
            while (all.contains(end + 1)) {
                end++;
            }
            res = Math.max(res, end - start + 1);
        }
        return res;
    }
}
