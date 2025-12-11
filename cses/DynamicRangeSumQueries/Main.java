import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int q = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            IntervalTree it = new IntervalTree(a);
            for (int j = 0; j < q; j++) {
                int t = in.nextInt();
                if (t == 1) {
                    int pos = in.nextInt() - 1;
                    int val = in.nextInt();
                    it.update(pos, pos, val);
                } else {
                    int from = in.nextInt() - 1;
                    int to = in.nextInt() - 1;
                    out.println(it.query(from, to));
                }
            }
        }
    }

    static class IntervalTree {
        int n;
        long[] t;

        IntervalTree(int[] a) {
            this(a.length);
            build(a);
        }

        IntervalTree(int len) {
            n = 1;
            while (n < len) n <<= 1;
            t = new long[2 * n];
        }

        int left(int node) {
            return node << 1;
        }

        int right(int node) {
            return (node << 1) + 1;
        }

        void build(int[] a) {
            for (int i = 0; i < a.length; i++) {
                t[n + i] = a[i];
            }
            for (int i = n - 1; i > 0; i--) {
                t[i] = t[left(i)] + t[right(i)];
            }
        }

        long query(int from, int to, int node, int nodeFrom, int nodeTo) {
            if (from <= nodeFrom && nodeTo <= to) {
                return t[node];
            }
            if (to < nodeFrom || nodeTo < from) {
                return 0;
            }
            int mid = (nodeFrom + nodeTo) / 2;
            return query(from, to, left(node), nodeFrom, mid) +
                   query(from, to, right(node), mid + 1, nodeTo);
        }

        long query(int from, int to) {
            return query(from, to, 1, 0, n - 1);
        }

        void update(int from, int to, int d, int node, int nodeFrom, int nodeTo) {
            if (from <= nodeFrom && nodeTo <= to) {
                t[node] = d;
                return;
            }
            if (to < nodeFrom || nodeTo < from) {
                return;
            }
            int mid = (nodeFrom + nodeTo) / 2;
            update(from, to, d, left(node), nodeFrom, mid);
            update(from, to, d, right(node), mid + 1, nodeTo);
            t[node] = t[left(node)] + t[right(node)];
        }
 
        void update(int from, int to, int d) {
            update(from, to, d, 1, 0, n - 1);
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
