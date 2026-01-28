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

    static class Solver {
        static final int ALPHA = 26;
        int n;
        ArrayList<ArrayList<Integer>> adj;
        int[] visited;
        boolean hasCycle;
        ArrayList<Integer> order;

        void solve() {
            n = in.nextInt();
            int[][] a = new int[n][];
            for (int i = 0; i < n; i++) {
                String name = in.next();
                int m = name.length();
                a[i] = new int[m];
                for (int j = 0; j < m; j++) {
                    a[i][j] = name.charAt(j) - 'a';
                }
            }
            adj = new ArrayList<>();
            for (int i = 0; i < ALPHA; i++) {
                adj.add(new ArrayList<>());
            }
            for (int i = 1; i < n; i++) {
                int[] s = a[i - 1];
                int[] t = a[i];
                int m = Math.min(s.length, t.length);
                int d = -1;
                for (int j = 0; j < m; j++) {
                    if (s[j] != t[j]) {
                        d = j;
                        break;
                    }
                }
                if (d == -1) {
                    if (s.length > t.length) {
                        out.println("Impossible");
                        return;
                    } else {
                        continue;
                    }
                }
                adj.get(s[d]).add(t[d]);
            }
            visited = new int[ALPHA];
            hasCycle = false;
            order = new ArrayList<>();
            for (int u = 0; u < ALPHA; u++) {
                if (visited[u] == 0) {
                    dfs(u);
                }
            }
            if (hasCycle) {
                out.println("Impossible");
                return;
            }
            Collections.reverse(order);
            for (int u = 0; u < ALPHA; u++) {
                char c = (char) (order.get(u) + 'a');
                out.print(c);
            }
            out.println();
        }

        void dfs(int u) {
            visited[u] = 1;
            for (int v : adj.get(u)) {
                if (visited[v] == 1) {
                    hasCycle = true;
                    return;
                } else if (visited[v] == 0) {
                    dfs(v);
                }
            }
            order.add(u);
            visited[u] = 2;
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
                new Solver().solve();
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
