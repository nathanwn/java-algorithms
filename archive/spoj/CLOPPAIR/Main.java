import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            Point[] ps = new Point[n];
            for (int i = 0; i < n; i++) {
                ps[i] = new Point(in.nextInt(), in.nextInt(), i);
            }

            // Sort by x
            Arrays.sort(ps, (Point a, Point b) -> {
                int cmpX = Integer.compare(a.x, b.x);
                if (cmpX == 0) {
                    return Integer.compare(a.y, b.y);
                }
                return cmpX;
            });

            TreeSet<Point> s = new TreeSet<>((Point a, Point b) -> {
                int cmpY = Integer.compare(a.y, b.y);
                if (cmpY == 0) {
                    return Integer.compare(a.x, b.x);
                }
                return cmpY;
            });

            int id1 = 0;
            int id2 = 1;
            long d2 = dist2(ps[id1], ps[id2]);

            for (Point p : ps) {
                int d = (int) Math.sqrt(d2);
                int minY = p.y - d;
                int maxY = p.y + d;
                // Find all points q with q.y in the range [minY, maxY].
                // It can be proven that there are at most a constant number
                // (8) of such points.
                for (Point q = s.higher(new Point(Integer.MIN_VALUE, minY, -1));
                     q != null && q.y <= p.y + d;
                     q = s.higher(q)) {
                    if (q.x < p.x - d) {
                        s.remove(q);
                        continue;
                    }
                    if (dist2(p, q) < d2) {
                        d2 = dist2(p, q);
                        id1 = p.id;
                        id2 = q.id;
                    }
                }
                s.add(p);
            }

            if (id1 > id2) {
                int tmp = id1;
                id1 = id2;
                id2 = tmp;
            }

            out.println(id1 + " " + id2);
            out.println(String.format("%.6f", Math.sqrt(d2)));
        }
    }

    static long dist2(Point a, Point b) {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        return 1L * dx * dx + 1L * dy * dy;
    }

    static class Point {
        int x;
        int y;
        int id;

        Point(int x, int y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[65536];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int get() {
            if (numChars == -1) {
                throw new RuntimeException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int peek() {
            if (numChars == -1) {
                return -1;
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    return -1;
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar];
        }

        public boolean hasNext() {
            int c = peek();
            while (c != -1 && isWhitespace(c)) {
                get();
                c = peek();
            }
            return c != -1;
        }

        public String next() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = get();
            } while (!isWhitespace(c));
            return res.toString();
        }

        public int nextInt() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = get();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new RuntimeException();
                }
                res *= 10;
                res += c - '0';
                c = get();
            } while (!isWhitespace(c));
            return res * sgn;
        }

        public long nextLong() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = get();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new RuntimeException();
                }
                res *= 10;
                res += c - '0';
                c = get();
            } while (!isWhitespace(c));
            return res * sgn;
        }

        public double readDouble() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = get();
            }
            double res = 0;
            while (!isWhitespace(c) && c != '.') {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, nextInt());
                }
                if (c < '0' || c > '9') {
                    throw new RuntimeException();
                }
                res *= 10;
                res += c - '0';
                c = get();
            }
            if (c == '.') {
                c = get();
                double m = 1;
                while (!isWhitespace(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    if (c < '0' || c > '9') {
                        throw new RuntimeException();
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = get();
                }
            }
            return res * sgn;
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
