import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {
    static int[] di = {0, 1, 0, -1};
    static int[] dj = {1, 0, -1, 0};
    //                 R  D  L   U

    static class Dir {
        static final int R = 0;
        static final int D = 1;
        static final int L = 2;
        static final int U = 3;
    }

    static class Solver {
        static final int A = 2 * (int) 1e5;
        int[][] cost = new int[A][4];
        String[] g = new String[A];

        void solve(InputReader in, PrintWriter out) {
            int t = in.nextInt();
            for (int i = 0; i < t; i++) {
                solveCase(in, out);
            }
        }

        void solveCase(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int m = in.nextInt();
            for (int i = 0; i < n; i++) {
                g[i] = in.next();
            }
            final int INF = (int) 1e9;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    Arrays.fill(cost[i * m + j], INF);
                }
            }
            ArrayDeque<State> q = new ArrayDeque<>();
            q.add(new State(0, -1, Dir.R, 0));
            while (!q.isEmpty()) {
                State prev = q.poll();
                int i = prev.i + di[prev.d];
                int j = prev.j + dj[prev.d];

                if (i == n - 1 && j == m && prev.d == Dir.R) {
                    break;
                }

                if (i < 0 || i >= n || j < 0 || j >= m) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {
                    if (Math.abs(d - prev.d) == 2) {
                        // Opposite directions
                        continue;
                    }
                    int addedCost = 0;
                    char cell = g[i].charAt(j);
                    if (cell == 'A') {
                        if (d != prev.d) {
                            addedCost = 1;
                        }
                    } else if (cell == 'B') { // \
                        if ((prev.d == Dir.R && d == Dir.D)
                            || (prev.d == Dir.D && d == Dir.R)
                            || (prev.d == Dir.L && d == Dir.U)
                            || (prev.d == Dir.U && d == Dir.L)) {
                            // pass
                        } else {
                            addedCost = 1;
                        }
                    } else if (cell == 'C') { // /
                        if ((prev.d == Dir.R && d == Dir.U)
                            || (prev.d == Dir.U && d == Dir.R)
                            || (prev.d == Dir.L && d == Dir.D)
                            || (prev.d == Dir.D && d == Dir.L)) {
                            // pass
                        } else {
                            addedCost++;
                        }
                    }

                    int c = prev.cost + addedCost;
                    if (c < cost[i * m + j][d]) {
                        cost[i * m + j][d] = c;
                        if (addedCost == 0) {
                            q.addFirst(new State(i, j, d, c));
                        } else {
                            q.addLast(new State(i, j, d, c));
                        }
                    }
                }
            }
            out.println(cost[(n - 1) * m + m - 1][Dir.R]);
        }
    }

    static class State {
        int i;
        int j;
        int d;
        int cost;

        State(int i, int j, int d, int cost) {
            this.i = i;
            this.j = j;
            this.d = d;
            this.cost = cost;
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

