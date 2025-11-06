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
            int[] c = new int[n];
            for (int u = 0; u < n; u++) {
                c[u] = in.nextInt();
            }
            ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
            for (int u = 0; u < n; u++) {
                adj.add(new ArrayList<>());
            }

            for (int i = 0; i < m; i++) {
                int u = in.nextInt() - 1;
                int v = in.nextInt() - 1;
                adj.get(u).add(v);
                adj.get(v).add(u);
            }

            boolean[] visited = new boolean[n];
            int[] queue = new int[n];
            Arrays.fill(visited, false);
            long ans = 0;

            for (int s = 0; s < n; s++) {
                if (visited[s]) continue;
                visited[s] = true;

                int head = 0;
                int tail = 0;
                queue[tail] = s; tail++;

                int val = c[s];

                while (head < tail) {
                    int u = queue[head]; head++;

                    for (int v : adj.get(u)) {
                        if (visited[v]) continue;
                        visited[v] = true;

                        val = Math.min(val, c[v]);
                        queue[tail] = v; tail++;
                    }
                }
                ans += val;
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
