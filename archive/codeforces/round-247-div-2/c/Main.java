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
    static final int MOD = 1_000_000_007;

    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        int d = in.nextInt();
        // dp[n][b]: number of ways to get to sum n
        //           with/without an edge weighted least d
        int[][] dp = new int[n + 1][2];
        dp[0][0] = 1;
        dp[0][1] = 0;  // No edge so far, so no edge weighted at least d.
        for (int cur = 1; cur <= n; cur++) {
            for (int j = 1; j <= k; j++) {
                int prev = cur - j;
                if (prev < 0) continue;
                if (j >= d) {
                    dp[cur][1] = modAdd(dp[cur][1], dp[prev][0]);
                    dp[cur][1] = modAdd(dp[cur][1], dp[prev][1]);
                } else {
                    dp[cur][0] = modAdd(dp[cur][0], dp[prev][0]);
                    dp[cur][1] = modAdd(dp[cur][1], dp[prev][1]);
                }
            }
        }
        out.println(dp[n][1]);
    }

    static int modAdd(int a, int b) {
        a += b;
        if (a >= MOD) a -= MOD;
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
