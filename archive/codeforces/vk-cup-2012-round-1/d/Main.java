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

    static class Solver {
        ArrayList<ArrayList<Integer>> adj;
        int[][] cnt;
        long[] cntPairsDistK;
        int n;
        int k;
        int ans = 0;

        void solve() {
            n = in.nextInt();
            k = in.nextInt();
            cnt = new int[n][k + 1];
            cntPairsDistK = new long[n];
            adj = new ArrayList<>(n);
            for (int u = 0; u < n; u++) {
                adj.add(new ArrayList<>());
            }
            for (int i = 0; i < n - 1; i++) {
                int u = in.nextInt() - 1;
                int v = in.nextInt() - 1;
                adj.get(u).add(v);
                adj.get(v).add(u);
            }
            dfs(0, -1);
            dfs2(0, -1);
            out.println(ans);
        }

        void dfs(int u, int p) {
            cnt[u][0] = 1;
            for (int v : adj.get(u)) {
                if (v == p) continue;
                dfs(v, u);
                cnt[u][1]++;
                for (int j = 2; j <= k; j++) {
                    cnt[u][j] += cnt[v][j - 1];
                }
            }
        }

        void dfs2(int u, int p) {
            for (int v : adj.get(u)) {
                if (v == p) continue;
                dfs2(v, u);
                for (int j = 1; j < k; j++) {  // j is length of first path
                    int firstPath = cnt[v][j - 1];
                    int secondPath = cnt[u][k - j] - cnt[v][k - j - 1];
                    cntPairsDistK[u] += (long) firstPath * secondPath;
                }
            }
            cntPairsDistK[u] /= 2;
            cntPairsDistK[u] += cnt[u][k];
            ans += cntPairsDistK[u];
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
