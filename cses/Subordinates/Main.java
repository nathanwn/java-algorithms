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

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            DfsGraph g = new DfsGraph(n);
            for (int u = 1; u < n; u++) {
                g.addEdge(in.nextInt() - 1, u);
            }
            // g.dfs(0);
            g.traverse();
            StringBuilder output = new StringBuilder();
            for (int u = 0; u < n; u++) {
                output.append(g.count[u]);
                output.append(u == n - 1 ? '\n' : ' ');
            }
            out.print(output.toString());
        }
    }

    static class DfsGraph {
        int n;
        IntList[] adj;
        int[] count;

        DfsGraph(int n) {
            this.n = n;
            this.adj = new IntList[n];
            for (int u = 0; u < n; u++) {
                this.adj[u] = new IntList();
            }
            this.count = new int[n];
        }

        void addEdge(int u, int v) {
            adj[u].push(v);
        }

        void dfs(int u) {
            for (int i = 0; i < adj[u].n; i++) {
                int v = adj[u].a[i];
                dfs(v);
                count[u] += count[v] + 1;
            }
        }

        void traverse() {
            IntList s1 = new IntList(n);
            IntList s2 = new IntList(n);

            s1.push(0);
            while (!s1.isEmpty()) {
                int u = s1.pop();
                s2.push(u);
                for (int i = 0; i < adj[u].n; i++) {
                    int v = adj[u].a[i];
                    s1.push(v);
                }
            }
            while (!s2.isEmpty()) {
                int u = s2.pop();
                for (int i = 0; i < adj[u].n; i++) {
                    int v = adj[u].a[i];
                    count[u] += count[v] + 1;
                }
            }
        }
    }

    static class IntList {
        int cap;
        int n;
        int[] a;

        IntList() {
            this(16);
        }

        IntList(int cap) {
            this.cap = cap;
            this.n = 0;
            this.a = new int[cap];
        }

        int get(int i) {
            return a[i];
        }

        void push(int x) {
            if (n == cap) {
                cap <<= 1;
                a = Arrays.copyOf(a, cap);
            }
            a[n] = x;
            n++;
        }

        int pop() {
            int x = a[n - 1];
            n--;
            return x;
        }

        boolean isEmpty() {
            return n == 0;
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
