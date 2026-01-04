class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        Solver solver = new Solver(nums);
        solver.backtrack(0);
        return solver.res;
    }
}

class Solver {
    ArrayList<Integer> a;
    int n;
    List<Integer> cur;
    List<List<Integer>> res;

    Solver(int[] nums) {
        this.a = new ArrayList<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            this.a.add(nums[i]);
        }
        this.n = a.size();
        Collections.sort(this.a);
        this.cur = new ArrayList<>();
        res = new ArrayList<>();
    }

    void backtrack(int id) {
        if (id == n) {
            res.add(new ArrayList<>(cur));
            return;
        }
        // Case 1: Include element at a[id]
        cur.add(a.get(id));
        backtrack(id + 1);
        cur.remove(cur.size() - 1);
        // Case 2: Exclude element at a[id]
        int j = id + 1;
        // The following part is not needed for this problem, but is necessary for
        // deduplicating subsets if there are duplicates in the original array.
        // while (j < n && a.get(id) == a.get(j)) {
        //     // Important: Also exclude elements with the same value.
        //     // By doing this, we avoid duplicates.
        //     j++;
        // }
        backtrack(j);
    }
}
