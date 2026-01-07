class FenwickTree {
    int n;
    long[] t;

    FenwickTree(int n) {
        this.n = n;
        this.t = new long[n];
    }

    FenwickTree(int[] a) {
        this(a.length);
        for (int i = 0; i < n; i++) {
            t[i] += a[i];
            int r = i | (i + 1);
            if (r < n) {
                t[r] += t[i];
            }
        }
    }

    long preSum(int id) {
        int res = 0;
        for (int i = id; i >= 0; i = (i & (i + 1)) - 1) {
            res += t[i];
        }
        return res;
    }

    long sum(int l, int r) {
        return preSum(r) - preSum(l - 1);
    }

    void add(int id, int delta) {
        for (int i = id; i < n; i = i | (i + 1)) {
            t[i] += delta;
        }
    }
}
