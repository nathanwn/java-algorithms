import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = true;

    static void solve() {
        int n = in.nextInt();
        int[] r = new int[n];
        for (int i = 0; i < n; i++) {
            r[i] = in.nextInt();
        }
        PriorityQueue<Entry> heap = new PriorityQueue<>(
            Comparator.comparingInt((Entry e) -> e.r)
        );
        for (int i = 0; i < n; i++) {
            heap.add(new Entry(r[i], i));
        }
        int[] fr = new int[n];
        Arrays.fill(fr, -1);
        while (!heap.isEmpty()) {
            Entry cur = heap.poll();
            if (fr[cur.c] == -1) {
                fr[cur.c] = cur.r;
            } else {
                continue;
            }
            if (cur.c > 0) {
                heap.add(new Entry(cur.r + 1, cur.c - 1));
            }
            if (cur.c < n - 1) {
                heap.add(new Entry(cur.r + 1, cur.c + 1));
            }
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += r[i] - fr[i];
        }
        out.println(ans);
    }

    static class Entry {
        int r;
        int c;

        public Entry(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) {
        int t = 1;
        if (MULTITEST) {
            t = in.nextInt();
        }
        while (t-- > 0) {
            solve();
        }
        out.close();
    }

    static void stressTest() {
        out.println("AC!");
    }

    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    static Random random = new Random(58);

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
}
