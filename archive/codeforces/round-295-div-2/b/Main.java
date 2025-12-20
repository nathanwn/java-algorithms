import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int m = in.nextInt();
            out.println(solve(n, m));
        }

        int solve(int n, int m) {
            TreeMap<Integer, Integer> clicks = new TreeMap<>();
            Queue<Integer> q = new LinkedList<>();
            q.add(n);
            clicks.put(n, 0);
            int ans = 0;
            while (!q.isEmpty()) {
                int u = q.poll();
                int c = clicks.get(u);
                if (u == m) {
                    return c;
                }
                if (u < m && clicks.get(u * 2) == null) {
                    clicks.put(u * 2, c + 1);
                    q.add(u * 2);
                }
                if (u > 1 && clicks.get(u - 1) == null) {
                    clicks.put(u - 1, c + 1);
                    q.add(u - 1);
                }
            }
            throw new RuntimeException();
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
