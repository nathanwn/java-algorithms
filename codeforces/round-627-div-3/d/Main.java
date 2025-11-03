import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static class Solver {

        // We need to find the number of pairs i, j where
        // a[i] + a[j] > b[i] + b[j]
        // i.e. (a[i] - b[i]) + (a[j] - b[j]) > 0.
        // Let d[i] = a[i] - b[i]. Therefore, we need to find the number
        // of pairs i, j where d[i] > -d[j]

        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            Topic[] t = new Topic[n];
            int[] a = new int[n];
            int[] b = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            for (int i = 0; i < n; i++) {
                b[i] = in.nextInt();
            }
            for (int i = 0; i < n; i++) {
                t[i] = new Topic(a[i], b[i]);
            }
            Arrays.sort(t);
            long ans = 0;
            for (int i = 0; i < n; i++) {
                int firstJ = n;
                int lo = i + 1;
                int hi = n - 1;
                while (lo <= hi) {
                    int mid = lo + (hi - lo) / 2;
                    if (t[i].d > -t[mid].d) {
                        firstJ = mid;
                        hi = mid - 1;
                    } else {
                        lo = mid + 1;
                    }
                }
                ans += n - firstJ;
            }
            out.println(ans);
        }
    }

    static class Topic implements Comparable<Topic> {
        int a;
        int b;
        int d;

        Topic(int a, int b) {
            this.a = a;
            this.b = b;
            this.d = a - b;
        }

        @Override
        public int compareTo(Topic other) {
            return Integer.compare(d, other.d);
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
