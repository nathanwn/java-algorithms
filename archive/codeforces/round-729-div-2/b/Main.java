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
        // Can prove that
        // n = a^x + by where x, y are non-negative integers
        // -> (n - a^x) % b == 0
        // Just need to check if x exists.
        // If x exists, then there's m = a^x.
        // From m, we can always reach n by adding b y times,
        // i.e. n = m + by
        // Just need to be careful with the edge case a = 1.
        int t = in.nextInt();
        while (t-- > 0) {
            solveCase(in, out);
        }
    }

    static void solveCase(InputReader in, PrintWriter out) {
        long n = in.nextLong();
        long a = in.nextLong();
        long b = in.nextLong();
        if (a == 1) {
            if ((n - 1) % b == 0) {
                out.println("Yes");
                return;
            } else {
                out.println("No");
                return;
            }
        } else {
            for (long pa = 1; pa <= n; pa *= a) {
                if ((n - pa) % b == 0) {
                    out.println("Yes");
                    return;
                }
            }
            out.println("No");
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
