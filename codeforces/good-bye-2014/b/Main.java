import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            boolean[][] canSwap = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                String s = in.next();
                for (int j = 0; j < n; j++) {
                    canSwap[i][j] = (s.charAt(j) == '1');
                }
            }
            UnionFindSet ufs = new UnionFindSet(n);
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (canSwap[i][j]) {
                        ufs.union(i, j);
                    }
                }
            }
            TreeMap<Integer, List<Element>> groups = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                int p = ufs.find(i);
                if (!groups.containsKey(p)) {
                    groups.put(p, new ArrayList<>());
                }
                groups.get(p).add(new Element(a[i], i));
            }
            int[] b = new int[n];
            for (Map.Entry<Integer, List<Element>> e : groups.entrySet()) {
                List<Element> elements = e.getValue();
                int m = elements.size();
                int[] ids = new int[m];
                int[] vals = new int[m];
                for (int i = 0; i < m; i++) {
                    ids[i] = elements.get(i).id;
                    vals[i] = elements.get(i).v;
                }
                Arrays.sort(ids);
                Arrays.sort(vals);
                for (int i = 0; i < m; i++) {
                    b[ids[i]] = vals[i];
                }
            }
            for (int i = 0; i < n; i++) {
                if (i != 0) out.print(' ');
                out.print(b[i]);
            }
            out.println();
        }
    }

    static class Element {
        int v;
        int id;

        Element(int v, int id) {
            this.v = v;
            this.id = id;
        }
    }

    static class UnionFindSet {
        int n;
        int[] p;
        int[] d;

        UnionFindSet(int n) {
            this.n = n;
            p = new int[n];
            d = new int[n];
            for (int i = 0; i < n; i++) {
                p[i] = i;
                d[i] = 0;
            }
        }

        int find(int u) {
            while (u != p[u]) u = p[u];
            return u;
        }

        boolean union(int u, int v) {
            u = find(u);
            v = find(v);
            if (u == v) return false;
            if (d[u] == d[v]) {
                p[v] = u;
                d[u]++;
            } else if (d[u] > d[v]) {
                p[v] = u;
            } else {
                p[u] = v;
            }
            return true;
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
