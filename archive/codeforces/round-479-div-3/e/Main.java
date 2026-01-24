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
        int m = in.nextInt();
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>(n);
        for (int u = 0; u < n; u++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        Solver solver = new Solver(adj);
        out.println(solver.solve());
    }

    static class Solver {
        int n;
        ArrayList<ArrayList<Integer>> adj;
        int[] visited;
        int[] leader;
        boolean hasBackEdge;
        boolean allHaveCorrectDegree;

        Solver(ArrayList<ArrayList<Integer>> adj) {
            this.n = adj.size();
            this.adj = adj;
            this.visited = new int[n];
        }

        int solve() {
            int ans = 0;
            for (int u = 0; u < n; u++) {
                if (visited[u] == 0) {
                    hasBackEdge = false;
                    allHaveCorrectDegree = true;
                    dfs(u, u);
                    if (hasBackEdge && allHaveCorrectDegree) {
                        ans++;
                    }
                }
            }
            return ans;
        }

        void dfs(int u, int root) {
            visited[u] = 1;
            if (adj.get(u).size() != 2) {
                allHaveCorrectDegree = false;
            }
            for (int v : adj.get(u)) {
                if (visited[v] == 0) {
                    dfs(v, root);
                } else if (visited[v] == 1) {
                    hasBackEdge = true;
                }
            }
            visited[u] = 2;
        }
    }

    static void solve(InputReader in, PrintWriter out) {
        // int t = in.nextInt();
        int t = 1;
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
