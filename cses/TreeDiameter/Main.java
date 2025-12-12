import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        int[] a1 = bfs(adj, 0);
        int v = a1[0];
        int[] a2 = bfs(adj, v);
        int t = a2[0];
        int ans = a2[1];
        out.println(ans);
    }

    // Return the furthest node + distance from source
    static int[] bfs(ArrayList<ArrayList<Integer>> adj, int s) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        int[] dist = new int[n];
        int tail = 1;
        int head = 0;
        visited[s] = true;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(s);
        int t = -1;
        while (!q.isEmpty()) {
            int u = q.pollFirst();
            t = u;
            for (int v : adj.get(u)) {
                if (visited[v]) continue;
                visited[v] = true;
                dist[v] = dist[u] + 1;
                q.addLast(v);
            }
        }
        return new int[]{t, dist[t]};
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
