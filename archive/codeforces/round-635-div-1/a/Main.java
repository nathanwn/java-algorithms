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

    static class Solver {
        Node[] nodes;
        long ans;

        void solve() {
            int n = in.nextInt();
            int k = in.nextInt();
            nodes = new Node[n];
            for (int u = 0; u < n; u++) {
                nodes[u] = new Node(u);
            }
            for (int i = 0; i < n - 1; i++) {
                int u = in.nextInt() - 1;
                int v = in.nextInt() - 1;
                nodes[u].adj.add(v);
                nodes[v].adj.add(u);
            }
            dfs(0, -1);
            ArrayList<Integer> order = new ArrayList<>(n);
            for (int u = 0; u < n; u++) {
                order.add(u);
            }

            // If a node is chosen to be an industry city:
            // we are
            // - losing depth - 1
            // - gaining treeSize - 1
            // Important to think about this in isolation to
            // the decision on each node.
            Collections.sort(order, (Integer i, Integer j) -> {
                    Node u = nodes[i];
                    Node v = nodes[j];
                    return Integer.compare(u.treeSize - u.depth,
                                           v.treeSize - v.depth);
                });

            for (int i = k; i < n; i++) {
                int u = order.get(i);
                nodes[u].tourism = true;
            }

            ans = 0;
            dfs2(0, -1, 0);
            out.println(ans);
        }

        void dfs(int u, int p) {
            nodes[u].treeSize = 1;
            for (int v : nodes[u].adj) {
                if (v == p) continue;
                nodes[v].depth = nodes[u].depth + 1;
                dfs(v, u);
                nodes[u].treeSize += nodes[v].treeSize;
            }
        }

        void dfs2(int u, int p, int cur) {
            int newCur = cur;
            if (nodes[u].tourism) {
                newCur++;
            } else {
                ans += cur;
            }
            for (int v : nodes[u].adj) {
                if (v == p) continue;
                dfs2(v, u, newCur);
            }
        }
    }

    static class Node {
        int id;
        ArrayList<Integer> adj;
        int depth;
        int treeSize;
        boolean tourism;

        Node(int id) {
            this.id = id;
            this.adj = new ArrayList<>();
            this.depth = 0;
            this.treeSize = 0;
            this.tourism = false;
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
