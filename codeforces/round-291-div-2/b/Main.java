import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int x0 = in.nextInt();
            int y0 = in.nextInt();
            TreeSet<Vec> slopes = new TreeSet<>();
            for (int i = 0; i < n; i++) {
                int x = in.nextInt() - x0;
                int y = in.nextInt() - y0;
                slopes.add(new Vec(x, y));
            }
            out.println(slopes.size());
        }
    }

    static class Vec implements Comparable<Vec> {
        int x;
        int y;

        Vec(int x, int y) {
            this.x = x;
            this.y = y;
            normalize();
        }

        void normalize() {
            int xa = Math.abs(x);
            int ya = Math.abs(y);
            int g = gcd(xa, ya);
            x /= g;
            y /= g;
            if (y < 0) {
                x = -x;
                y = -y;
            }
            if (x == 0) {
                y = 1;
            }
            if (y == 0) {
                x = 1;
            }
        }

        public int compareTo(Vec other) {
            if (x == other.x) {
                return Integer.compare(y, other.y);
            }
            return Integer.compare(x, other.x);
        }
    }

    static int gcd(int a, int b) {
        if (a < b) {
            int x = a;
            a = b;
            b = x;
        }
        if (b == 0) {
            return 1;
        }
        while (b > 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
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
