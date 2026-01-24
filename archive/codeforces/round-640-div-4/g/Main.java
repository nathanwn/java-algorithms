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
    static void solveCase(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        if (n < 4) {
            out.println(-1);
            return;
        }
        int k = 0;
        int[] a = new int[n];
        int maxOdd = (n % 2 == 0) ? (n - 1) : n;
        for (int i = maxOdd; i > 0; i -= 2) {
            a[k++] = i;
        }
        a[k++] = 4;
        a[k++] = 2;
        for (int i = 6; i <= n; i += 2) {
            a[k++] = i;
        }
        if (k != n) {
            throw new AssertionError();
        }
        for (int i = 0; i < n; i++) {
            if (i > 0) out.print(' ');
            out.print(a[i]);
        }
        out.println();
    }

    static void solve(InputReader in, PrintWriter out) {
        int t = in.nextInt();
        while (t-- > 0) {
            solveCase(in, out);
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
