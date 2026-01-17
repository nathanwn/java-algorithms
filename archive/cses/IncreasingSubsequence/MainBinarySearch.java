import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        int[] minEndVal = new int[n + 1];
        final int INF = (int) 2e9;
        Arrays.fill(minEndVal, INF);
        minEndVal[0] = -INF;
        for (int i = 0; i < n; i++) {
            int x = a[i];
            // Find smallest len such that minEndVal[len] > a[i]
            int lo = 0;
            int hi = n;
            int len = -1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (minEndVal[mid] > x) {
                    len = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            if (minEndVal[len - 1] < x && x < minEndVal[len]) {
                minEndVal[len] = x;
            }
        }
        int ans = 0;
        for (int i = n; i > 0; i--) {
            if (minEndVal[i] != INF) {
                ans = i;
                break;
            }
        }
        out.println(ans);
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
