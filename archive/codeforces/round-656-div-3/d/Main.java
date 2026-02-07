import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = true;

    static class Solver {
        int[] a;
        int[][] p;
        HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> mem;

        void solve() {
            int n = in.nextInt();
            String s = in.next();
            a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = s.charAt(i) - 'a';
            }
            int[] d = new int[n];
            p = new int[26][n + 1];
            for (int c = 0; c < 26; c++) {
                for (int i = 0; i < n; i++) {
                    d[i] = (a[i] == c) ? 0 : 1;
                }
                for (int i = 0; i < n; i++) {
                    p[c][i + 1] = p[c][i] + d[i];
                }
            }
            mem = new HashMap<>();
            out.println(solve(0, 0, n - 1));
        }

        int solve(int c, int low, int high) {
            if (mem.containsKey(c)
                    && mem.get(c).containsKey(low)
                    && mem.get(c).get(low).containsKey(high)) {
                return mem.get(c).get(low).get(high);
            }
            if (low == high) {
                return (c == a[low]) ? 0 : 1;
            }
            int mid = low + (high - low) / 2;
            int first = p[c][mid + 1] - p[c][low] + solve(c + 1, mid + 1, high);
            int second = p[c][high + 1] - p[c][mid + 1] + solve(c + 1, low, mid);
            int res = Math.min(first, second);
            if (!mem.containsKey(c)) {
                mem.put(c, new HashMap<>());
            }
            if (!mem.get(c).containsKey(low)) {
                mem.get(c).put(low, new HashMap<>());
            }
            mem.get(c).get(low).put(high, res);
            return res;
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("stress")) {
            stressTest();
            return;
        } else {
            int t = 1;
            if (MULTITEST) {
                t = in.nextInt();
            }
            while (t-- > 0) {
                new Solver().solve();
            }
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
