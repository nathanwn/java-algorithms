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
        int n = in.nextInt();
        int m = in.nextInt();
        long[] a = new long[n];
        long[] b = new long[m];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
        }
        for (int j = 0; j < m; j++) {
            b[j] = in.nextLong();
        }
        // Important property: gcd(x, y) = gcd(x - y, y)
        // Therefore:
        // G = gcd(a0 + x, a1 + x, a2 + x, ...)
        // = gcd(a0 + x, a1 + x - (a0 + x), a2 + x - (a0 + x), ...)
        // = gcd(a0 + x, a1 - a0, a2 - a0, ...)
        // Let g = gcd(a1 - a0, a2 - a0, ...)
        // => G = gcd(a0 + x, g)
        // Note: Be careful to avoid negative values.
        // g = gcd(a1 - a0, a2 - a0, ...)
        //   = gcd(|a1 - a0|, |a2 - a0|, ...)
        long g = 0;
        for (int i = 1; i < n; i++) {
            g = gcd(g, Math.abs(a[i] - a[0]));
        }
        for (int j = 0; j < m; j++) {
            if (j != 0) out.print(' ');
            long ans = gcd(a[0] + b[j], g);
            out.print(ans);
        }
        out.println();
    }

    static long gcd(long a, long b) {
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
