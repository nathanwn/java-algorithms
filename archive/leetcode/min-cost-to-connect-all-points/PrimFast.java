class Solution {
    public int minCostConnectPoints(int[][] points) {
        // Prim with O(n^2) time complexity
        // https://cp-algorithms.com/graph/mst_prim.html#dense-graphs-on2
        final int INF = Integer.MAX_VALUE;
        int n = points.length;
        Edge[] minEdges = new Edge[n];
        for (int u = 0; u < n; u++) {
            minEdges[u] = new Edge(-1, INF);
        }
        boolean[] selected = new boolean[n];
        minEdges[0].w = 0;
        int cost = 0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!selected[v] && (u == -1 || minEdges[v].w < minEdges[u].w)) {
                    u = v;
                }
            }
            if (minEdges[u].w == INF) throw new AssertionError();
            selected[u] = true;
            cost += minEdges[u].w;
            for (int v = 0; v < n; v++) {
                int w = dist(points[u], points[v]);
                if (w < minEdges[v].w) {
                    minEdges[v].v = u;
                    minEdges[v].w = w;
                }
            }
        }
        return cost;
    }

    int dist(int[] x, int[] y) {
        return Math.abs(x[0] - y[0]) + Math.abs(x[1] - y[1]);
    }

    class Edge {
        int v;
        int w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
}
