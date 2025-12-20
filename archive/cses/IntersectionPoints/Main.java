import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[][] segments = new int[n][4];
            ArrayList<Event> events = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 4; j++) {
                    segments[i][j] = in.nextInt();
                }
            }

            int[] ys = new int[2 * n];
            for (int i = 0; i < n; i++) {
                ys[(i << 1)] = segments[i][1];
                ys[(i << 1) + 1] = segments[i][3];
            }

            CoordinateCompressor yCompressor = new CoordinateCompressor(ys, random);

            for (int i = 0; i < n; i++) {
                int x1 = segments[i][0];
                int y1 = segments[i][1];
                int x2 = segments[i][2];
                int y2 = segments[i][3];
                if (x1 == x2) {  // vertical
                    int yy1 = yCompressor.getIndex(y1);
                    int yy2 = yCompressor.getIndex(y2);
                    events.add(new Event(x1, yy1, yy2, EventType.VER));
                } else if (y1 == y2) {  // horizontal
                    int yy = yCompressor.getIndex(y1);
                    events.add(new Event(x1, yy, yy, EventType.HOR_START));
                    events.add(new Event(x2, yy, yy, EventType.HOR_END));
                } else throw new RuntimeException();
            }

            Collections.sort(events, (e1, e2) -> {
                return Integer.compare(e1.x, e2.x);
            });

            /// FenwickTree t = new FenwickTree(yCompressor.length);
            IntervalTree t = new IntervalTree(yCompressor.length, 0);

            long ans = 0;
            for (Event e : events) {
                if (e.ty == EventType.VER) {
                    /// ans += t.sum(e.y1, e.y2);
                    ans += t.query(e.y1, e.y2);
                } else if (e.ty == EventType.HOR_START) {
                    /// t.add(e.y1, 1);
                    t.update(e.y1, 1);
                } else if (e.ty == EventType.HOR_END) {
                    /// t.add(e.y1, -1);
                    t.update(e.y1, -1);
                } else throw new RuntimeException();
            }
            out.println(ans);
        }
    }

    static class CoordinateCompressor {
        int length;
        int[] b;

        CoordinateCompressor(int[] a, Random random) {
            int n = a.length;
            b = Arrays.copyOf(a, n);
            sort(b, random);
            length = unique(b);
        }

        int getIndex(int x) {
            return search(b, length, x);
        }
    }

    static int search(int[] a, int n, int x) {
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (x == a[mid]) {
                return mid;
            } else if (x < a[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        throw new RuntimeException();
    }

    static int unique(int[] a) {
        if (a.length == 0) return 0;
        int j = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[i - 1]) {
                a[j] = a[i];
                j++;
            }
        }
        return j;
    }

    static void sort(int[] a, Random random) {
        shuffle(a, random);
        Arrays.sort(a);
    }

    static void shuffle(int[] a, Random random) {
        // Fisher-Yates shuffle
        for (int i = a.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    static final Random random = new Random();

    enum EventType {
        VER,
        HOR_START,
        HOR_END
    }

    static class Event {
        int x;
        int y1;
        int y2;
        EventType ty;

        Event(int x, int y1, int y2, EventType ty) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
            this.ty = ty;
        }
    }

    static class IntervalTree {
        int n;
        long[] t;
        long defaultValue;

        long merge(long leftVal, long rightVal) {
            return leftVal + rightVal;
        }

        void updateNode(int node, int val) {
            t[node] += val;
        }

        IntervalTree(int[] a, long defaultValue) {
            this(a.length, defaultValue);
            build(a);
        }

        IntervalTree(int len, long defaultValue) {
            this.defaultValue = defaultValue;
            n = 1;
            while (n < len) n <<= 1;
            t = new long[2 * n];
            if (defaultValue != 0) {
                Arrays.fill(t, defaultValue);
            }
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
                t[i] = merge(t[left(i)], t[right(i)]);
            }
        }

        long query(int from, int to, int node, int nodeFrom, int nodeTo) {
            if (from <= nodeFrom && nodeTo <= to) {
                return t[node];
            }
            if (to < nodeFrom || nodeTo < from) {
                return defaultValue;
            }
            int mid = (nodeFrom + nodeTo) / 2;
            long leftVal = query(from, to, left(node), nodeFrom, mid);
            long rightVal = query(from, to, right(node), mid + 1, nodeTo);
            return merge(leftVal, rightVal);
        }

        long query(int from, int to) {
            return query(from, to, 1, 0, n - 1);
        }

        void update(int pos, int val, int node, int nodeFrom, int nodeTo) {
            if (nodeFrom == nodeTo) {
                updateNode(node, val);
                return;
            }
            int mid = (nodeFrom + nodeTo) / 2;
            if (pos <= mid) {
                update(pos, val, left(node), nodeFrom, mid);
            } else {
                update(pos, val, right(node), mid + 1, nodeTo);
            }
            t[node] = merge(t[left(node)], t[right(node)]);
        }

        void update(int pos, int val) {
            update(pos, val, 1, 0, n - 1);
        }
    }

    static class FenwickTree {
        int n;
        int[] t;

        FenwickTree(int n) {
            this.n = n;
            this.t = new int[n];
        }

        FenwickTree(int[] a) {
            int n = a.length;
            this.n = n;
            t = new int[n];
            for (int i = 0; i < n; i++) {
                t[i] += a[i];
                int r = i | (i + 1);
                if (r < n) {
                    t[r] += t[i];
                }
            }
        }

        int preSum(int id) {
            int res = 0;
            for (int i = id; i >= 0; i = (i & (i + 1)) - 1) {
                res += t[i];
            }
            return res;
        }

        int sum(int l, int r) {
            return preSum(r) - preSum(l - 1);
        }

        void add(int id, int delta) {
            for (int i = id; i < n; i = i | (i + 1)) {
                t[i] += delta;
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
        Solver solver = new Solver();
        solver.solve(in, out);
        out.close();
    }
}
