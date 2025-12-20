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
            long[][] ps = new long[n][2];
            for (int i = 0; i < n; i++) {
                ps[i][0] = in.nextInt();
                ps[i][1] = in.nextInt();
            }

            // Sort by x
            Arrays.sort(ps, (long[] a, long[] b) -> {
                int cmpX = Long.compare(a[0], b[0]);
                if (cmpX == 0) {
                    return Long.compare(a[1], b[1]);
                }
                return cmpX;
            });

            TreeSet<long[]> s = new TreeSet<>((long[] a, long[] b) -> {
                int cmpY = Long.compare(a[1], b[1]);
                if (cmpY == 0) {
                    return Long.compare(a[0], b[0]);
                }
                return cmpY;
            });

            long d2 = dist2(ps[0], ps[1]);
            // Sentinel point to find all points with y in range.
            long[] st = new long[]{Long.MIN_VALUE, Long.MIN_VALUE};

            for (long[] p : ps) {
                long d = (long) Math.sqrt(d2);
                long minY = p[1] - d;
                long maxY = p[1] + d;
                // Find all points q with q.y in the range [minY, maxY].
                // It can be proven that there are at most a constant number
                // (8) of such points.
                st[1] = minY;
                for (long[] q = s.higher(st);
                     q != null && q[1] <= p[1] + d;
                     q = s.higher(q)) {
                    if (q[0] < p[0] - d) {
                        s.remove(q);
                        continue;
                    }
                    long curD2 = dist2(p, q);
                    if (curD2 < d2) {
                        d2 = curD2;
                    }
                }
                s.add(p);
            }

            out.println(d2);
        }
    }

    static long dist2(long[] a, long[] b) {
        long dx = a[0] - b[0];
        long dy = a[1] - b[1];
        return 1L * dx * dx + 1L * dy * dy;
    }

    static class Point {
        long x;
        long y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
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
