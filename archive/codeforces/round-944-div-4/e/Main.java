import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = true;

    static void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        int[] x = new int[m + 1];
        int[] t = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            x[i] = in.nextInt();
        }
        for (int i = 1; i <= m; i++) {
            t[i] = in.nextInt();
        }
        for (int j = 0; j < q; j++) {
            if (j > 0) out.print(' ');
            int d = in.nextInt();
            // Find last i where x[i] <= d
            int low = 0;
            int high = m;
            int k = -1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (x[mid] <= d) {
                    k = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            if (k == -1) throw new AssertionError();
            if (d == x[k]) {
                out.print(t[k]);
            } else {
                if (k == m) throw new AssertionError();
                int x0 = x[k];
                int t0 = t[k];
                int dx = x[k + 1] - x[k];
                int dt = t[k + 1] - t[k];
                // v = dx / dt;
                // t = t0 + (d - x0) / v
                //   = t0 + (d - x0) / (dx / dt)
                //   = t0 + (d - x0) * dt / dx
                long tz = t0 + (long) (d - x0) * dt / dx;
                out.print(tz);
            }
        }
        out.println();
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("stress")) {
            stressTest();
            return;
        } else {
            int t = 1;
            if (MULTITEST) {
                t = in.nextInt();
            }
            while (t-- > 0) {
                solve();
            }
        }
        out.close();
    }

    static void stressTest() {
        out.println("AC!");
    }

    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    static Random random = new Random(58);

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
}
