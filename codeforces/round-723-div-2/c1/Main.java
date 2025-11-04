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
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            // dp[i + 1][c]: max value incurred after reaching position
            //               i having drunk c times
            long INF = 2 * 1_000_000_000 + 7;
            long[][] dp = new long[n + 1][n + 1];
            for (int i = 0; i <= n; i++) {
                for (int c = 1; c <= n; c++) {
                    dp[i][c] = -INF;
                }
            }
            dp[0][0] = 0;
            for (int i = 0; i < n; i++) {
                dp[i + 1][0] = 0;
                for (int c = 1; c <= i + 1; c++) {
                    long leave = dp[i][c];
                    long take = dp[i][c - 1] + a[i];
                    if (take < 0) take = -INF;
                    dp[i + 1][c] = Math.max(leave, take);
                }
            }
            long ans = 0;
            for (int c = n; c > 0; c--) {
                if (dp[n][c] >= 0) {
                    ans = c;
                    break;
                }
            }
            out.println(ans);
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
        Solver solver = new Solver();
        solver.solve(in, out);
        out.close();
    }
}
