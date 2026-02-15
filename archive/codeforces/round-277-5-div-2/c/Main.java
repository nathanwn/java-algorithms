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

    static void solve() {
        int n = in.nextInt();
        int s = in.nextInt();
        out.println(toString(getMin(n, s)) + " " + toString(getMax(n, s)));
    }

    static int[] getMin(int n, int s) {
        if (s == 0) {
            if (n == 1) {
                return new int[]{0};
            }
            return null;
        }
        int[] d = new int[n];
        d[0] = 1;
        s--;
        int i = n - 1;
        while (s >= 9 && i > 0) {
            s -= 9;
            d[i] = 9;
            i--;
        }
        if (i == 0) {
            if (s > 8) {
                return null;
            }
            d[0] += s;
        } else {
            if (s > 9) {
                throw new AssertionError();
            }
            d[i] = s;
        }
        return d;
    }

    static int[] getMax(int n, int s) {
        if (s == 0) {
            if (n == 1) {
                return new int[]{0};
            }
            return null;
        }
        int[] d = new int[n];
        int i = 0;
        while (s >= 9 && i < n) {
            s -= 9;
            d[i] = 9;
            i++;
        }
        if (i == n) {
            if (s > 0) {
                return null;
            }
        } else {
            if (s > 9) {
                return null;
            }
            d[i] = s;
        }
        return d;
    }

    static String toString(int[] a) {
        StringBuilder s = new StringBuilder();
        if (a == null) {
            s.append(-1);
        } else {
            for (int i = 0; i < a.length; i++) {
                s.append(a[i]);
            }
        }
        return s.toString();
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

    static void stressTest() {}

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
