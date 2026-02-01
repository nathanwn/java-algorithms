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

    static void solve() {
        long l = in.nextInt();
        long r = in.nextInt();
        out.println(countOpsFromZero(r) - countOpsFromZero(l));
    }

    static long countOpsFromZero(long x) {
        // 0 -> 1: 1
        // 0 -> 10: 10 + 1 = 11
        // 0 -> 100: 11 * 10 + 1 = 111
        // 0 -> 1000: 111 * 10 + 1 = 1111
        long ops = 1;
        long res = 0;
        while (x > 0) {
            long d = x % 10;
            x /= 10;
            res += d * ops;
            ops = ops * 10 + 1;
        }
        return res;
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
