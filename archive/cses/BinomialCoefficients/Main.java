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
    static class Solver {
        static final int MAX = (int) 1e6;
        int[] fact;
        int[] ifact;

        Solver() {
            fact = new int[MAX + 1];
            ifact = new int[MAX + 1];
            fact[0] = 1;
            for (int i = 1; i <= MAX; i++) {
                fact[i] = modMul(fact[i - 1], i);
            }
            ifact[MAX] = modInverse(fact[MAX]);
            for (int i = MAX - 1; i >= 0; i--) {
                ifact[i] = modMul(ifact[i + 1], i + 1);
            }
        }

        int nCk(int n, int k) {
            return modMul(modMul(fact[n], ifact[k]), ifact[n - k]);
        }

        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            for (int i = 0; i < n; i++) {
                int a = in.nextInt();
                int b = in.nextInt();
                out.println(nCk(a, b));
            }
        }
    }

    static final int MOD = (int) 1e9 + 7;

    static int modMul(int a, int b) {
        return (int) ((long) a * b % MOD);
    }

    static int modInverse(int a) {
        return power(a, MOD - 2);
    }

    static int power(int a, int x) {
        int res = 1;
        while (x > 0) {
            if ((x & 1) > 0) {
                res = modMul(res, a);
            }
            a = modMul(a, a);
            x >>= 1;
        }
        return res;
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
        Solver solver = new Solver();
        solver.solve(in, out);
        out.close();
    }
}
