class Solution {
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        int sgn = (n > 0) ? 1 : -1;
        double res = 1;
        long e = Math.abs((long) n);  // careful with n == -2^31
        double cur = x;
        while (e > 0) {
            if ((e & 1) != 0) {
                res *= cur;
            }
            cur *= cur;
            e >>= 1;
        }
        if (sgn < 0) {
            res = 1.0 / res;
        }
        return res;
    }
}
