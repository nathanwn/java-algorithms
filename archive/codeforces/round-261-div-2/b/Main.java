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
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = false;

    static void solve() {
        int n = in.nextInt();
        ArrayList<Integer> b = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            b.add(in.nextInt());
        }
        Collections.sort(b);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = b.get(i);
        }
        int countMin = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] != a[0]) {
                break;
            }
            countMin++;
        }
        if (a[0] == a[n - 1]) {
            long count = (long) countMin * (countMin - 1) / 2;
            int diff = 0;
            out.println(diff + " " + count);
            return;
        }
        int countMax = 0;
        for (int i = n - 1; i > -1; i--) {
            if (a[i] != a[n - 1]) {
                break;
            }
            countMax++;
        }
        int diff = a[n - 1] - a[0];
        long count = (long) countMin * countMax;
        out.println(diff + " " + count);
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
