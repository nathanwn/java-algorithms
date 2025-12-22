class Solution {
    public long maxPoints(int[] technique1, int[] technique2, int k) {
        int n = technique1.length;
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tasks.add(new Task(technique1[i], technique2[i]));
        }
        Collections.sort(tasks, (Task a, Task b) -> {
            return -Integer.compare(a.t1 - a.t2, b.t1 - b.t2);
        });
        long ans = 0;
        for (int i = 0; i < k; i++) {
            ans += tasks.get(i).t1;
        }
        for (int i = k; i < n; i++) {
            ans += Math.max(tasks.get(i).t1, tasks.get(i).t2);
        }
        return ans;
    }
}

class Task {
    int t1;
    int t2;

    Task(int t1, int t2) {
        this.t1 = t1;
        this.t2 = t2;
    }
}
