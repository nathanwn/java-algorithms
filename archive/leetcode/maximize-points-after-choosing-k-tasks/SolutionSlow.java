class SolutionSlow {
    public long maxPoints(int[] technique1, int[] technique2, int k) {
        // f(n, k): maximum points completing n tasks with k tasks using technique 1
        // f(n, k) = max(
        //     f(n - 1, k) + technique2[n - 1],
        //     f(n - 1, k - 1) + technique1[n - 1]
        // )
        int n = technique1.length;
        long[] f = new long[n + 1];
        long[] pf = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    f[j] = pf[j] + technique2[i - 1];
                } else if (j == i) {
                    f[j] = pf[j - 1] + technique1[i - 1];
                } else {
                    long use1 = pf[j - 1] + technique1[i - 1];
                    long use2 = pf[j] + technique2[i - 1];
                    f[j] = Math.max(use1, use2);
                }
            }
            long[] tmp = pf;
            pf = f;
            f = tmp;
        }
        long ans = 0;
        for (int i = k; i <= n; i++) {
            ans = Math.max(ans, pf[i]);
        }
        return ans;
    }
}
