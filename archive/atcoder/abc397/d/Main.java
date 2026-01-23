import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static void solve(InputReader in, PrintWriter out) {
        long n = in.nextLong();
        // Let d = (x - y).
        // Can prove that d^3 < n, i.e. d < cbrt(n).
        // n = x^3 - y^3 = (x - y)(x^2 + xy + y^2)
        //               = d(x^2 + xy + y^2)
        //               = d(x^2 - 2xy + y^2) + 3xy
        //               = d^3 + 3xy
        // => d^3 < n
        // n = x^3 - y^3 = (y + d)^3 - y^3
        //               = y^3 + 3y^2d + 3yd^2 + d^3 - y^3
        //               = 3y^2d + 3yd^2 + d^3
        // For each d, solve the equation to find y:
        // 3dy^2 + 3d^2y + d^3 = n
        // => d(3y^2 + 3dy + d^2) = n
        // => n % d = 0 and
        //    3y^2 + 3dy + d^2 - n / d = 0
        // One method for solving the equation is binary search.
        // Can easily prove that y < sqrt(n) <= 1e9.
        long dMax = cbrt(n);
        for (long d = 1; d < dMax; d++) {
            if (n % d != 0) continue;
            long a = 3;
            long b = 3 * d;
            long c = d * d - n / d;
            long y = solveQuadratic(a, b, c);
            if (y != -1) {
                long x = y + d;
                out.println(x + " " + y);
                return;
            }
        }
        out.println("-1");
    }

    static long solveQuadratic(long a, long b, long c) {
        long lo = 1;
        long hi = (long) 1e9;
        long y = -1;
        while (lo <= hi) {
            long mid = lo + (hi - lo) / 2;
            if (a * mid * mid + b * mid + c <= 0) {
                y = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        if (a * y * y + b * y + c != 0) {
            return -1;
        }
        return y;
    }

    static long cbrt(long n) {
        long x = (long) Math.cbrt(n);
        while (x * x * x < n) x++;
        return x;
    }

    static class InputReader {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public InputReader(InputStream inputStream) {
            reader = new BufferedReader(new InputStreamReader(inputStream), 32768);
            tokenizer = new StringTokenizer("");
        }

        public boolean hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                if (line == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(line);
            }
            return true;
        }

        public String next() {
            if (!hasNext()) {
                throw new RuntimeException();
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.close();
    }
}
