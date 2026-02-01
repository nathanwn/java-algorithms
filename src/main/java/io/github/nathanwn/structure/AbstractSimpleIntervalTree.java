package io.github.nathanwn.structure;

import java.util.Arrays;

public abstract class AbstractSimpleIntervalTree {
    int n;
    long[] t;
    long defaultValue;

    public abstract long merge(long leftVal, long rightVal);

    public abstract void updateNode(int node, int val);

    AbstractSimpleIntervalTree(int[] a, long defaultValue) {
        this(a.length, defaultValue);
        build(a);
    }

    AbstractSimpleIntervalTree(int len, long defaultValue) {
        this.defaultValue = defaultValue;
        n = 1;
        while (n < len) n <<= 1;
        t = new long[2 * n];
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
        int mid = (nodeFrom + nodeTo) / 2;
        long leftVal = query(from, to, left(node), nodeFrom, mid);
        long rightVal = query(from, to, right(node), mid + 1, nodeTo);
        return merge(leftVal, rightVal);
    }

    long query(int from, int to) {
        return query(from, to, 1, 0, n - 1);
    }

    void update(int pos, int val, int node, int nodeFrom, int nodeTo) {
        if (nodeFrom == nodeTo) {
            updateNode(node, val);
            return;
        }
        int mid = (nodeFrom + nodeTo) / 2;
        if (pos <= mid) {
            update(pos, val, left(node), nodeFrom, mid);
        } else {
            update(pos, val, right(node), mid + 1, nodeTo);
        }
        t[node] = merge(t[left(node)], t[right(node)]);
    }

    void update(int pos, int val) {
        update(pos, val, 1, 0, n - 1);
    }
}
