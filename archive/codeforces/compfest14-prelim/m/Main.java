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
        int m = in.nextInt();
        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            int w = in.nextInt();
            g.addEdge(u, v, w);
        }
        g.dijkstra();
        for (int u = 1; u < n; u++) {
            if (u > 1) out.print(' ');
            long d = g.dist[u + n];
            if (d == Graph.INF) d = -1;
            out.print(d);
        }
        out.println();
    }

    static class Graph {
        static final long INF = (long) 1e18;
        int n;
        ArrayList<ArrayList<Edge>> adj;
        long[] dist;

        Graph(int n) {
            this.n = n;
            adj = new ArrayList<>(n * 2);
            for (int i = 0; i < n * 2; i++) {
                adj.add(new ArrayList<>());
            }
            for (int u = 0; u < n; u++) {
                adj.get(u).add(new Edge(u, u + n, 0));
            }
            dist = new long[n * 2];
        }

        void addEdge(int u, int v, int w) {
            adj.get(u).add(new Edge(u, v, w));
            adj.get(v + n).add(new Edge(v + n, u + n, w));
        }

        void dijkstra() {
            Arrays.fill(dist, INF);
            dist[0] = 0L;
            PriorityQueue<Entry> pq = new PriorityQueue<>((Entry e1, Entry e2) -> {
                return Long.compare(e1.d, e2.d);
            });
            pq.add(new Entry(0, dist[0]));
            while (!pq.isEmpty()) {
                Entry top = pq.poll();
                int u = top.u;
                long d = top.d;
                if (d > dist[u]) {
                    continue;
                }
                for (Edge e : adj.get(u)) {
                    int v = e.v;
                    int w = e.w;
                    if (dist[v] > dist[u] + w) {
                        dist[v] = dist[u] + w;
                        pq.add(new Entry(v, dist[v]));
                    }
                }
            }
        }
    }

    static class Edge {
        int u;
        int v;
        int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static class Entry {
        int u;
        long d;

        Entry(int u, long d) {
            this.u = u;
            this.d = d;
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

    static void stressTest() {}

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
