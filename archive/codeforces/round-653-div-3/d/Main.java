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
        void solve() {
            int n = in.nextInt();
            int k = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            TreeMap<Integer, Integer> cnts = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                int x = a[i];
                int r = x % k;
                int add = (k - r) % k;
                cnts.put(add, cnts.getOrDefault(add, 0) + 1);
            }
            ArrayList<Integer> adds = new ArrayList<>(cnts.keySet());
            int lastAdd = 0;
            int maxAddCount = 0;
            for (int add : adds) {
                if (add == 0) continue;
                int cnt = cnts.get(add);
                if (cnt >= maxAddCount) {
                    lastAdd = add;
                    maxAddCount = cnt;
                }
            }
            if (maxAddCount == 0) {
                out.println(0);
                return;
            }
            long ans = (long) (maxAddCount - 1) * k + lastAdd + 1;
            out.println(ans);
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
