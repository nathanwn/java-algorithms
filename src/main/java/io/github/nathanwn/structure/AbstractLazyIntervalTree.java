package io.github.nathanwn.structure;

import java.util.Arrays;

public abstract class AbstractLazyIntervalTree {
    int n;
    public long[] t;
    public long[] z;
    boolean[] lazy;
    long defaultValue;

    public abstract long merge(long leftVal, long rightVal);

    public abstract void updateNode(int node, long val);

    void pushDown(int node) {
        if (lazy[node]) {
            updateNode(left(node), z[node]);
            updateNode(right(node), z[node]);
            z[node] = defaultValue;
            lazy[node] = false;
            lazy[left(node)] = true;
            lazy[right(node)] = true;
        }
    }

    public AbstractLazyIntervalTree(int[] a, long defaultValue) {
        this(a.length, defaultValue);
        build(a);
    }

    public AbstractLazyIntervalTree(int len, long defaultValue) {
        this.defaultValue = defaultValue;
        n = 1;
        while (n < len) n <<= 1;
        t = new long[2 * n];
        z = new long[2 * n];
        lazy = new boolean[2 * n];
        if (defaultValue != 0) {
            Arrays.fill(t, defaultValue);
        }
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
            t[i] = merge(t[left(i)], t[right(i)]);
        }
    }

    long query(int from, int to, int node, int nodeFrom, int nodeTo) {
        if (from <= nodeFrom && nodeTo <= to) {
            return t[node];
        }
        if (to < nodeFrom || nodeTo < from) {
            return defaultValue;
        }
        pushDown(node);
        int mid = nodeFrom + ((nodeTo - nodeFrom) >> 1);
        long leftVal = query(from, to, left(node), nodeFrom, mid);
        long rightVal = query(from, to, right(node), mid + 1, nodeTo);
        return merge(leftVal, rightVal);
    }

    public long query(int from, int to) {
        return query(from, to, 1, 0, n - 1);
    }

    void update(int from, int to, int val, int node, int nodeFrom, int nodeTo) {
        if (from <= nodeFrom && nodeTo <= to) {
            updateNode(node, val);
            lazy[node] = true;
            return;
        }
        if (to < nodeFrom || nodeTo < from) {
            return;
        }
        pushDown(node);
        int mid = nodeFrom + ((nodeTo - nodeFrom) >> 1);
        update(from, to, val, left(node), nodeFrom, mid);
        update(from, to, val, right(node), mid + 1, nodeTo);
        t[node] = merge(t[left(node)], t[right(node)]);
    }

    public void update(int from, int to, int val) {
        update(from, to, val, 1, 0, n - 1);
    }
}
