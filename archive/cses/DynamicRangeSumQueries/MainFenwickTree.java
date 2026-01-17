import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int q = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        FenwickTree ft = new FenwickTree(a);
        for (int j = 0; j < q; j++) {
            int ty = in.nextInt();
            if (ty == 1) {
                int k = in.nextInt() - 1;
                int x = in.nextInt();
                int y = a[k];
                int d = x - y;
                a[k] = x;
                ft.add(k, d);
            } else if (ty == 2) {
                int l = in.nextInt() - 1;
                int r = in.nextInt() - 1;
                long res = ft.sum(r) - ft.sum(l - 1);
                out.println(res);
            } else {
                throw new AssertionError();
            }
        }
    }

    static class FenwickTree {
        int n;
        long[] t;

        FenwickTree(int[] a) {
            n = a.length;
            t = new long[n];
            for (int i = 0; i < n; i++) {
                add(i, a[i]);
            }
        }

        void add(int i, int d) {
            for (int j = i; j < n; j = j | (j + 1)) {
                t[j] += d;
            }
        }

        long sum(int i) {
            long res = 0;
            for (int j = i; j >= 0; j = (j & (j + 1)) - 1) {
                res += t[j];
            }
            return res;
        }
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
