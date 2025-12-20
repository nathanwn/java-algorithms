import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.PriorityQueue;
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
                int w = in.nextInt();
                g.addEdge(u, v, w);
                g.addEdge(v, u, w);
            }
            ArrayList<Integer> path = g.shortestPath(0, n - 1);
            if (path == null) {
                out.println(-1);
            } else {
                for (int i = 0; i < path.size(); i++) {
                    if (i != 0) out.print(' ');
                    out.print(path.get(i) + 1);
                }
                out.println();
            }
        }
    }

    static class Graph {
        int n;
        ArrayList<ArrayList<Edge>> adj;

        Graph(int n) {
            this.n = n;
            adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v, int w) {
            adj.get(u).add(new Edge(v, w));
        }

        ArrayList<Integer> shortestPath(int s, int t) {
            final long INF = (long) 1e18;
            long[] dist = new long[n];
            int[] parent = new int[n];
            Arrays.fill(dist, INF);
            PriorityQueue<State> q = new PriorityQueue<>(new Comparator<State>() {
                @Override
                public int compare(State a, State b) {
                    return Long.compare(a.d, b.d);
                }
            });
            dist[s] = 0;
            parent[s] = -1;
            q.add(new State(s, dist[s], parent[s]));
            while (!q.isEmpty()) {
                State state = q.poll();
                int u = state.u;

                if (state.d > dist[u]) continue;
                parent[u] = state.p;

                if (u == t) break;

                for (Edge e : adj.get(u)) {
                    int v = e.v;
                    long w = e.w;
                    if (dist[v] > dist[u] + w) {
                        dist[v] = dist[u] + w;
                        q.add(new State(v, dist[v], u));
                    }
                }
            }

            if (dist[t] == INF) {
                return null;
            }

            ArrayList<Integer> res = new ArrayList<>();
            int u = t;
            while (u != -1) {
                res.add(u);
                u = parent[u];
            }
            Collections.reverse(res);
            return res;
        }
    }

    static class Edge {
        int v;
        int w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    static class State {
        int u;
        long d;
        int p;

        State(int u, long d, int p) {
            this.u = u;
            this.d = d;
            this.p = p;
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
