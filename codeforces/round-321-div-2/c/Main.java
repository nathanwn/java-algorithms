import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int m = in.nextInt();
            boolean[] hasCat = new boolean[n];
            for (int i = 0; i < n; i++) {
                hasCat[i] = in.nextInt() == 1 ? true : false;
            }
            ArrayList<ArrayList<Integer>> g = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                g.add(new ArrayList<>());
            }
            for (int i = 0; i < n - 1; i++) {
                int u = in.nextInt() - 1;
                int v = in.nextInt() - 1;
                g.get(u).add(v);
                g.get(v).add(u);
            }
            Queue<Integer> q = new LinkedList<>();
            boolean[] visited = new boolean[n];
            int[] c = new int[n];
            q.add(0);
            visited[0] = true;
            if (hasCat[0]) c[0] = 1;
            int ans = 0;
            while (!q.isEmpty()) {
                int u = q.poll();
                boolean isLeaf = true;
                for (int v : g.get(u)) {
                    if (visited[v]) continue;
                    isLeaf = false;
                    visited[v] = true;
                    q.add(v);
                    if (c[u] == m + 1) {
                        c[v] = m + 1;
                    } else if (hasCat[v]) {
                        c[v] = c[u] + 1;
                    } else {
                        c[v] = 0;
                    }
                }
                if (isLeaf) {
                    if (c[u] <= m) {
                        ans++;
                    }
                }
            }
            out.println(ans);
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
