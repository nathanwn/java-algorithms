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
            int m = in.nextInt();
            Graph g = new Graph(n);
            for (int i = 0; i < m; i++) {
                int u = in.nextInt() - 1;
                int v = in.nextInt() - 1;
                g.addEdge(u, v);
            }
            g.topoSort();
            long[] dp = new long[n];
            long ans = 0;
            Arrays.fill(dp, 0);
            dp[0] = 1;
            for (int u : g.order) {
                for (int v : g.adj.get(u)) {
                    dp[v] += dp[u];
                    if (dp[v] >= MOD) dp[v] -= MOD;
                }
            }
            out.println(dp[n - 1]);
        }
    }

    static final int MOD = (int) 1e9 + 7;

    static int modMul(int a, int b) {
        return (int) ((long) a * b % MOD);
    }

    static class Graph {
        int n;
        ArrayList<ArrayList<Integer>> adj;
        int[] visited;
        ArrayList<Integer> order;

        Graph(int n) {
            this.n = n;
            adj = new ArrayList<>(n);
            for (int u = 0; u < n; u++) {
                adj.add(new ArrayList<>());
            }
            visited = new int[n];
            order = new ArrayList<>(n);
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
        }

        void dfs(int u) {
            visited[u] = 1;
            for (int v : adj.get(u)) {
                if (visited[v] == 1) throw new RuntimeException();
                if (visited[v] == 0) dfs(v);
            }
            order.add(u);
            visited[u] = 2;
        }

        void topoSort() {
            for (int u = 0; u < n; u++) {
                if (visited[u] == 0) {
                    dfs(u);
                }
            }
            Collections.reverse(order);
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[65536];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int get() {
            if (numChars == -1) {
                throw new RuntimeException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public String next() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = get();
            } while (!isWhitespace(c));
            return res.toString();
        }

        public int nextInt() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = get();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new RuntimeException();
                }
                res *= 10;
                res += c - '0';
                c = get();
            } while (!isWhitespace(c));
            return res * sgn;
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
