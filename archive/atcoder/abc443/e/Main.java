import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = true;

    static class Solver {
        final int N = 3001;
        final int[] dr = {-1, -1, -1};
        final int[] dc = {-1, 0, 1};
        char[][] g = new char[N][];
        boolean[] canBreak = new boolean[N];
        int[] res = new int[N];
        boolean[][] vis = new boolean[N][N];

        void solve() {
            int n = in.nextInt();
            int sc = in.nextInt();
            for (int i = 0; i < n; i++) {
                g[i] = in.next().toCharArray();
            }
            for (int c = 0; c < n; c++) {
                canBreak[c] = (g[n - 1][c] == '.');
            }
            int lastChecked = n - 1;
            for (int i = 0; i < n; i++) {
                res[i] = 0;
                for (int j = 0; j < n; j++) {
                    vis[i][j] = false;
                }
            }
            Cell start = new Cell(n - 1, sc - 1);
            Queue<Cell> q = new ArrayDeque<>();
            q.add(start);
            vis[start.r][start.c] = true;
            while (!q.isEmpty()) {
                Cell cur = q.poll();
                int r = cur.r;
                int c = cur.c;
                if (r < lastChecked - 1) {
                    lastChecked--;
                    for (int j = 0; j < n; j++) {
                        if (g[lastChecked][j] == '#') {
                            canBreak[j] = false;
                        }
                    }
                }
                if (r == 0) {
                    res[c] = 1;
                    continue;
                }
                for (int k = 0; k < 3; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];
                    if (nr < 0 || nr >= n) continue;
                    if (nc < 0 || nc >= n) continue;
                    if (vis[nr][nc]) continue;

                    if (g[nr][nc] == '.') {
                        q.add(new Cell(nr, nc));
                        vis[nr][nc] = true;
                    } else {
                        if (canBreak[nc] && g[r][nc] != '#') {
                            g[nr][nc] = '.';
                            q.add(new Cell(nr, nc));
                            vis[nr][nc] = true;
                        }
                    }
                }
            }
            for (int c = 0; c < n; c++) {
                out.print(res[c]);
            }
            out.println();
        }
    }

    static class Cell {
        int r;
        int c;

        public Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) {
        int t = 1;
        if (MULTITEST) {
            t = in.nextInt();
        }
        Solver solver = new Solver();
        while (t-- > 0) {
            solver.solve();
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
