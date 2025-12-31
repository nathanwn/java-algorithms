class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((int[] p1, int[] p2) -> {
            return Long.compare(dist(p1), dist(p2));
        });
        for (int[] p : points) {
            pq.add(p);
        }
        int[][] res = new int[k][];
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll();
        }
        return res;
    }

    long dist(int[] p) {
        return p[0] * p[0] + p[1] * p[1];
    }
}
