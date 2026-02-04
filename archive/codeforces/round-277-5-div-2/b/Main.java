import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = false;

    static void solve() {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        int m = in.nextInt();
        int[] b = new int[m];
        for (int j = 0; j < m; j++) {
            b[j] = in.nextInt();
        }
        int s = 0;
        int t = n + m + 1;
        Dinic dinic = new Dinic(n + m + 2, s, t);
        for (int i = 0; i < n; i++) {
            int u = i + 1;
            dinic.addEdge(s, u, 1);
        }
        for (int j = 0; j < m; j++) {
            int v = n + j + 1;
            dinic.addEdge(v, t, 1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (Math.abs(a[i] - b[j]) <= 1) {
                    int u = i + 1;
                    int v = n + j + 1;
                    dinic.addEdge(u, v, 1);
                }
            }
        }
        out.println(dinic.maxFlow());
    }

    static class FlowEdge {
        int u, v;
        int c, f;

        FlowEdge(int u, int v, int c, int f) {
            this.u = u;
            this.v = v;
            this.c = c;
            this.f = f;
        }
    };

    static class Dinic {
        static final int inf = Integer.MAX_VALUE;
        int n;
        int s, t;
        ArrayList<ArrayList<Integer>> adj; // stores indices of edges
        int[] level;       // shortest distance from source
        int[] ptr;         // points to the next edge which can be used
        ArrayList<FlowEdge> edges;

        Dinic(int n, int s, int t) {
            this.n = n;
            this.s = s;
            this.t = t;
            this.adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                this.adj.add(new ArrayList<>());
            }
            this.level = new int[n];
            this.ptr = new int[n];
            this.edges = new ArrayList<>();
        }

        void addEdge(int u, int v, int c) {
            addEdge(u, v, c, 0);
        }

        void addEdge(int u, int v, int c, int rc) {
            int eid = edges.size();
            adj.get(u).add(eid);
            adj.get(v).add(eid + 1);
            edges.add(new FlowEdge(u, v, c, 0));
            edges.add(new FlowEdge(v, u, rc, 0));
        }

        boolean bfs() {
            Arrays.fill(level, -1);
            level[s] = 0;
            ArrayDeque<Integer> q = new ArrayDeque<>();
            q.addLast(s);

            while (!q.isEmpty()) {
                int u = q.removeFirst();
                for (int eid : adj.get(u)) {
                    FlowEdge e = edges.get(eid);
                    if (e.c - e.f == 0 || level[e.v] != -1) continue;
                    level[e.v] = level[u] + 1;
                    q.addLast(e.v);
                }
            }

            return level[t] != -1;
        }

        int dfs(int u, int flow) {
            if (u == t) return flow;

            for (int j = ptr[u]; j < adj.get(u).size(); j++) {
                int eid = adj.get(u).get(j);
                FlowEdge e = edges.get(eid);
                if (e.c - e.f > 0 && level[e.v] == level[u] + 1) {
                    int df = dfs(e.v, Math.min(e.c - e.f, flow));
                    if (df > 0) {
                        edges.get(eid).f += df;
                        edges.get(eid ^ 1).f -= df;
                        return df;
                    }
                }
            }

            return 0;
        }

        int maxFlow() {
            int f = 0;

            while (bfs()) {
                Arrays.fill(ptr, 0);
                int total_df = 0;
                while (true) {
                    int df = dfs(s, inf);
                    if (df == 0) break;
                    total_df += df;
                }
                if (total_df == 0) break;
                f += total_df;
            }

            return f;
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
