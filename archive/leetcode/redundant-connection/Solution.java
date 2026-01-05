class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        DSU dsu = new DSU(n + 1);
        for (int[] edge : edges) {
            if (!dsu.merge(edge[0], edge[1])) {
                return edge;
            }
        }
        throw new AssertionError("unreachable");
    }
}

class DSU {
    int n;
    int[] parent;
    int[] depth;

    DSU(int n) {
        this.n = n;
        this.parent = new int[n];
        this.depth = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    int find(int u) {
        while (u != parent[u]) {
            u = parent[u];
        }
        return u;
    }

    boolean merge(int u, int v) {
        u = find(u);
        v = find(v);
        if (u == v) {
            return false;
        }
        if (depth[u] == depth[v]) {
            parent[u] = v;
            depth[v]++;
        } else if (depth[u] < depth[v]) {
            parent[u] = v;
        } else {
            parent[v] = u;
        }
        return true;
    }

    int countComponents() {
        int res = 0;
        for (int u = 0; u < n; u++) {
            if (u == parent[u]) res++;
        }
        return res;
    }
}
