import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int numToFill = in.nextInt();
        int numEmpty = 0;
        char[][] g = new char[n][];
        for (int i = 0; i < n; i++) {
            g[i] = in.next().toCharArray();
            for (int j = 0; j < m; j++) {
                if (g[i][j] == '.') {
                    numEmpty++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            boolean done = false;
            for (int j = 0; j < m; j++) {
                if (g[i][j] == '.') {
                    fill(g, new int[]{i, j}, numEmpty - numToFill);
                    done = true;
                    break;
                }
            }
            if (done) break;
        }
        replace(g, '.', 'X');
        replace(g, '*', '.');
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out.print(g[i][j]);
            }
            out.println();
        }
    }

    static final int[][] d = new int[][]{
        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    static void replace(char[][] g, char c1, char c2) {
        int n = g.length;
        int m = g[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (g[i][j] == c1) {
                    g[i][j] = c2;
                }
            }
        }
    }

    static void fill(char[][] g, int[] s, int k) {
        int n = g.length;
        int m = g[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.addLast(s);
        int count = 1;
        g[s[0]][s[1]] = '*';
        if (k == 1) return;
        while (!q.isEmpty()) {
            int[] cur = q.removeFirst();
            int r = cur[0];
            int c = cur[1];
            for (int j = 0; j < d.length; j++) {
                int nr = d[j][0] + r;
                int nc = d[j][1] + c;
                if (!(0 <= nr && nr < n)) continue;
                if (!(0 <= nc && nc < m)) continue;
                if (g[nr][nc] == '.') {
                    g[nr][nc] = '*';
                    q.addLast(new int[]{nr, nc});
                    count++;
                    if (count == k) return;
                }
            }
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
        solve(in, out);
        out.close();
    }
}
