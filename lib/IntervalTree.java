class IntervalTree {
    int n;
    long[] t;

    void merge(int node) {
        t[node] = t[left(node)] + t[right(node)];
    }

    void updateNode(int node, int d) {
        t[node] += d;
    }

    IntervalTree(int[] a) {
        this(a.length);
        build(a);
    }

    IntervalTree(int len) {
        n = 1;
        while (n < len) n <<= 1;
        t = new long[2 * n];
    }

    int left(int node) {
        return node << 1;
    }

    int right(int node) {
        return (node << 1) + 1;
    }

    void build(int[] a) {
        for (int i = 0; i < a.length; i++) {
            t[n + i] = a[i];
        }
        for (int i = n - 1; i > 0; i--) {
            t[i] = t[left(i)] + t[right(i)];
        }
    }

    long query(int from, int to, int node, int nodeFrom, int nodeTo) {
        if (from <= nodeFrom && nodeTo <= to) {
            return t[node];
        }
        if (to < nodeFrom || nodeTo < from) {
            return 0;
        }
        int mid = (nodeFrom + nodeTo) / 2;
        return query(from, to, left(node), nodeFrom, mid) +
               query(from, to, right(node), mid + 1, nodeTo);
    }

    long query(int from, int to) {
        return query(from, to, 1, 0, n - 1);
    }

    void update(int from, int to, int d, int node, int nodeFrom, int nodeTo) {
        if (from <= nodeFrom && nodeTo <= to) {
            updateNode(node, d);
            return;
        }
        if (to < nodeFrom || nodeTo < from) {
            return;
        }
        int mid = (nodeFrom + nodeTo) / 2;
        update(from, to, d, left(node), nodeFrom, mid);
        update(from, to, d, right(node), mid + 1, nodeTo);
        merge(node);
    }

    void update(int from, int to, int d) {
        update(from, to, d, 1, 0, n - 1);
    }
}
