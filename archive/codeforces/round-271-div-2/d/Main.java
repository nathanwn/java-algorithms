import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = false;

    static void solve() {
        int n = (int) 1e5 + 7;
        int t = in.nextInt();
        int k = in.nextInt();
        // dp[i][c]: number of sequences length i ending with c
        int[][] dp = new int[n][2];
        dp[0][0] = 1;
        dp[0][1] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = modAdd(dp[i][0], dp[i - 1][0]);
            dp[i][0] = modAdd(dp[i][0], dp[i - 1][1]);
            if (i - k >= 0) {
                dp[i][1] = modAdd(dp[i][1], dp[i - k][0]);
                dp[i][1] = modAdd(dp[i][1], dp[i - k][1]);
            }
        }
        int[] p = new int[n];
        for (int i = 1; i < n; i++) {
            p[i] = p[i - 1];
            p[i] = modAdd(p[i], dp[i][0]);
            p[i] = modAdd(p[i], dp[i][1]);
        }
        for (int j = 0; j < t; j++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int sum = modSub(p[b], p[a - 1]);
            out.println(sum);
        }
    }

    static final int MOD = (int) 1e9 + 7;

    static int modAdd(int a, int b) {
        a += b;
        if (a >= MOD) a -= MOD;
        return a;
    }

    static int modSub(int a, int b) {
        a -= b;
        if (a < 0) a += MOD;
        return a;
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
