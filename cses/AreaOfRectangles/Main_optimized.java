// NOTE: This also passed with Java 8.
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
    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int[][] events = new int[n * 2][4];
        final int START_EVENT = 1;
        final int END_EVENT = -1;
        for (int i = 0; i < n; i++) {
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            int x2 = in.nextInt();
            int y2 = in.nextInt();
            events[(i << 1)][0] = START_EVENT;
            events[(i << 1)][1] = x1;
            events[(i << 1)][2] = y1;
            events[(i << 1)][3] = y2;

            events[(i << 1)+ 1][0] = END_EVENT;
            events[(i << 1)+ 1][1] = x2;
            events[(i << 1)+ 1][2] = y1;
            events[(i << 1)+ 1][3] = y2;
        }
        Arrays.sort(events, (e1, e2) -> {
            return Integer.compare(e1[1], e2[1]);
        });
        final int MAX = 30000;
        IntervalTree it = new IntervalTree(MAX + 1);
        int ans = 0;
        for (int i = 0; i < events.length - 1; i++) {
            int[] cur = events[i];
            int y1 = cur[2];
            int y2 = cur[3];
            int[] next = events[i + 1];
            if (cur[0] == START_EVENT) {
                it.update(y1, y2 - 1, 1);
            } else {
                it.update(y1, y2 - 1, -1);
            }
            ans += (next[1] - cur[1]) * it.yLength[1];
        }
        out.println(ans);
    }

    static class Event {
        enum Type { START, END }

        int x;
        int y1;
        int y2;
        Type ty;

        Event(int x, int y1, int y2, Type ty) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
            this.ty = ty;
        }
    }

    static class IntervalTree {
        int n;
        int[] yLength;
        int[] cntIncluding;

        IntervalTree(int len) {
            n = 1;
            while (n < len) n <<= 1;
            yLength = new int[2 * n];
            cntIncluding = new int[2 * n];
        }

        int left(int node) {
            return node << 1;
        }

        int right(int node) {
            return (node << 1) + 1;
        }

        void update(int from, int to, int val) {
            update(from, to, val, 1, 0, n - 1);
        }

        void update(int from, int to, int val, int node, int nodeFrom, int nodeTo) {
            // Case 1: [nodeFrom, nodeTo] does not intersect with [from, to].
            // Nothing to be done
            if (nodeTo < from || nodeFrom > to) return;

            // Case 2: [nodeFrom, nodeTo] is a subinterval of [from, to].
            if (from <= nodeFrom && nodeTo <= to) {
                cntIncluding[node] += val;
                if (cntIncluding[node] != 0) {
                    yLength[node] = nodeTo - nodeFrom + 1;
                } else {
                    if (nodeFrom == nodeTo) {  // leaf
                        yLength[node] = 0;
                    } else {
                        yLength[node] = yLength[left(node)] + yLength[right(node)];
                    }
                }
                return;
            }

            // Case 3: [nodeFrom, nodeTo] intersects with but
            // is not a subinterval of [from, to].
            int mid = nodeFrom + ((nodeTo - nodeFrom) >> 1);
            update(from, to, val, left(node), nodeFrom, mid);
            update(from, to, val, right(node), mid + 1, nodeTo);

            if (cntIncluding[node] != 0) {
                yLength[node] = nodeTo - nodeFrom + 1;
            } else {
                yLength[node] = yLength[left(node)] + yLength[right(node)];
            }
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
        solve(in, out);
        out.close();
    }
}

