package io.github.nathanwn.math;

public class MathUtil {
    public static long gcd(long a, long b) {
        if (a < b) {
            long tmp = a;
            a = b;
            b = tmp;
        }
        while (b > 0) {
            long r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
}
