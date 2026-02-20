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
    static final boolean MULTITEST = false;
    static HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> mem = new HashMap<>();

    static void solve() {
        int[] a = new int[3];
        for (int i = 0; i < 3; i++) {
            a[i] = in.nextInt();
        }
        out.println(calc(a));
    }

    static int calc(int[] a) {
        Arrays.sort(a);
        return calc(a[0], a[1], a[2]);
    }

    static int calc(int a0, int a1, int a2) {
        if (a0 < 0 || a1 < 0 || a2 < 0) {
            throw new AssertionError();
        }
        if (!(a0 <= a1 && a1 <= a2)) {
            throw new AssertionError();
        }
        if (a0 == 0 && a1 == 0) {
            return 0;
        }
        if (a0 == a1 && a1 == a2) {
            return a0;
        }
        int d1 = a1 - a0;
        int d2 = a2 - a1;
        if (d1 <= 10 && d2 <= 10) {
            int ans = 0;
            if (a0 >= 5) {
                int d = a0 - 5;
                a0 -= d;
                a1 -= d;
                a2 -= d;
                ans = d;
            }
            return ans + scalc(a0, a1, a2);
        }
        if (d1 == 0) {
            int x = Math.min(a0, d2 / 4);
            return 2 * x + calc(a0 - x, a1 - x, a2 - x * 4);
        } else if (d2 >= d1) {
            return d1 + calc(a0, a0, a2 - d1 * 2);
        } else {  // d2 < d1
            int x = d2;
            int ans = x;
            a1 -= x;
            a2 -= 2 * x;
            if (a1 != a2) {
                throw new AssertionError();
            }
            int d = a1 - a0;
            a1 -= d / 3 * 3;
            a2 -= d / 3 * 3;
            ans += d / 3 * 2;
            return ans + calc(a0, a1, a2);
        }
    }

    static int scalc(int a0, int a1, int a2) {
        if (a0 < 0 || a1 < 0 || a2 < 0) return Integer.MIN_VALUE;
        if (a0 == 0 && a1 == 0 && a2 == 0) return 0;
        if (a0 == a1 && a1 == a2) return a0;
        if (mem.containsKey(a0)
                && mem.get(a0).containsKey(a1)
                && mem.get(a0).get(a1).containsKey(a2)) {
            return mem.get(a0).get(a1).get(a2);
        }
        int res = 0;
        res = Math.max(res, scalc(a0 - 1, a1 - 1, a2 - 1) + 1);
        res = Math.max(res, scalc(a0, a1 - 1, a2 - 2) + 1);
        res = Math.max(res, scalc(a0, a1 - 2, a2 - 1) + 1);
        res = Math.max(res, scalc(a0 - 1, a1, a2 - 2) + 1);
        res = Math.max(res, scalc(a0 - 2, a1, a2 - 1) + 1);
        res = Math.max(res, scalc(a0 - 1, a1 - 2, a2) + 1);
        res = Math.max(res, scalc(a0 - 2, a1 - 1, a2) + 1);
        if (!mem.containsKey(a0)) {
            mem.put(a0, new HashMap<>());
        }
        if (!mem.get(a0).containsKey(a1)) {
            mem.get(a0).put(a1, new HashMap<>());
        }
        mem.get(a0).get(a1).put(a2, res);
        return res;
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
                solve();
            }
        }
        out.close();
    }

    static void stressTest() {
        for (int tt = 0; tt < 1000; tt++) {
            System.err.println("Test #" + tt);
            long n = random.nextInt(500000) + 1;
            int[] a = new int[3];
            for (int i = 0; i < n; i++) {
                int[] b = new int[3];
                int cnt = 0;
                while (true) {
                    int choice = random.nextInt(3);
                    if (b[choice] == 2) {
                        continue;
                    }
                    b[choice]++;
                    cnt++;
                    if (cnt == 3) break;
                }
                for (int j = 0; j < 3; j++) {
                    a[j] += b[j];
                }
            }
            System.err.println("a = " + a[0] + " " + a[1] + " " + a[2]);
            int m = calc(a);
            if (m != n) {
                System.err.println(a[0] + " " + a[1] + " " + a[2]);
                System.err.println("Expect " + n + "; Got " + m);
                throw new AssertionError();
            }
        }
        System.err.println("AC!");
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
