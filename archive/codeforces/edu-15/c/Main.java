import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = false;

    static void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = new int[n];
        int[] b = new int[m];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        for (int j = 0; j < m; j++) {
            b[j] = in.nextInt();
        }
        int[] c = new int[n + m];
        boolean[] isCity = new boolean[n + m];
        {
            int i = 0;
            int j = 0;
            for (int k = 0; k < n + m; k++) {
                if (i == n) {
                    c[k] = b[j++];
                    isCity[k] = false;
                } else if (j == m) {
                    c[k] = a[i++];
                    isCity[k] = true;
                } else if (a[i] < b[j]) {
                    c[k] = a[i++];
                    isCity[k] = true;
                } else {
                    c[k] = b[j++];
                    isCity[k] = false;
                }
            }
        }
        // d[k]: closest distance from a point to a tower
        int[] d = new int[n + m];
        Arrays.fill(d, Integer.MAX_VALUE);
        int last = -1;
        for (int k = 0; k < n + m; k++) {
            if (!isCity[k]) {
                last = k;
                d[k] = 0;
            } else {
                if (last == -1) {
                    continue;
                }
                d[k] = c[k] - c[last];
            }
        }
        last = -1;
        for (int k = n + m - 1; k > -1; k--) {
            if (!isCity[k]) {
                last = k;
                d[k] = 0;
            } else {
                if (last == -1) {
                    continue;
                }
                d[k] = Math.min(d[k], c[last] - c[k]);
            }
        }
        int r = 0;
        for (int k = 0; k < n + m; k++) {
            r = Math.max(r, d[k]);
        }
        out.println(r);
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
