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

    static class Solver {
        void solve() {
            // Idea:
            // There are x1 ways to choose character 1,
            //           x2 ways to choose character 2,
            //           x3 ways to choose character 3, etc.
            // Given that sum(x) == n <= k where n is a constant.
            // We want to maximize prod(x).
            // Applying the AM/GM inequality: need to distribute values in x evenly.
            long k = in.nextLong();
            int p = -1;
            // x = 40 is the first x where x^10 >= 10^16.
            for (int i = 1; i <= 40; i++) {
                long pp = pow10(i);
                if (pp >= k) {
                    p = i;
                    break;
                }
            }
            if (p == -1) throw new AssertionError();
            long pp = pow10(p);
            int reduce = 0;
            while (pp >= k) {
                pp /= p;
                pp *= (p - 1);
                reduce++;
            }
            reduce--;
            char[] s = "codeforces".toCharArray();
            StringBuilder buf = new StringBuilder();
            for (int j = 0; j < 10 - reduce; j++) {
                for (int i = 0; i < p; i++) {
                    buf.append(s[j]);
                }
            }
            for (int j = 10 - reduce; j < 10; j++) {
                for (int i = 0; i < p - 1; i++) {
                    buf.append(s[j]);
                }
            }
            String ans = buf.toString();
            out.println(ans);
        }

        long pow10(int n) {
            long p = 1;
            for (int i = 0; i < 10; i++) {
                p *= n;
            }
            return p;
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
