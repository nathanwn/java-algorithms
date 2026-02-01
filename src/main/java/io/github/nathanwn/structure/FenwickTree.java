package io.github.nathanwn.structure;

public class FenwickTree {
    int n;
    long[] t;

    public FenwickTree(int n) {
        this.n = n;
        t = new long[n];
    }

    public FenwickTree(int[] a) {
        n = a.length;
        t = new long[n];
        for (int i = 0; i < n; i++) {
            add(i, a[i]);
        }
    }

    public void add(int i, int d) {
        for (int j = i; j < n; j = j | (j + 1)) {
            t[j] += d;
        }
    }

    public long sum(int i) {
        long res = 0;
        for (int j = i; j >= 0; j = (j & (j + 1)) - 1) {
            res += t[j];
        }
        return res;
    }
}