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
        int n = in.nextInt();
        int m = in.nextInt();
        IntervalTree t = new IntervalTree(1 << n, in);
        for (int j = 0; j < m; j++) {
            int i = in.nextInt() - 1;
            int val = in.nextInt();
            t.update(i, val);
            out.println(t.key[1]);
        }
    }

    static class IntervalTree {
        int n;
        int[] key;
        boolean[] isOr;

        IntervalTree(int n, InputReader in) {
            this.n = n;
            // n is already a power of 2
            key = new int[2 * n];
            isOr = new boolean[2 * n];
            for (int i = 0; i < n; i++) {
                key[n + i] = in.nextInt();
                isOr[n + i] = false;
            }
            for (int i = n - 1; i > 0; i--) {
                if (isOr[left(i)]) {
                    key[i] = key[left(i)] ^ key[right(i)];
                } else {
                    key[i] = key[left(i)] | key[right(i)];
                }
                isOr[i] = !isOr[left(i)];
            }
        }

        void update(int pos, int val, int node, int nodeFrom, int nodeTo) {
            if (nodeFrom == nodeTo) {
                key[node] = val;
                return;
            }
            int mid = (nodeFrom + nodeTo) / 2;
            if (pos <= mid) {
                update(pos, val, left(node), nodeFrom, mid);
            } else {
                update(pos, val, right(node), mid + 1, nodeTo);
            }
            if (isOr[node]) {
                key[node] = key[left(node)] | key[right(node)];
            } else {
                key[node] = key[left(node)] ^ key[right(node)];
            }
        }

        void update(int pos, int val) {
            update(pos, val, 1, 0, n - 1);
        }

        int left(int node) {
            return node << 1;
        }

        int right(int node) {
            return (node << 1) + 1;
        }
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
