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
        int[][] pts = new int[n][2];
        for (int i = 0; i < n; i++) {
            pts[i][0] = in.nextInt();
            pts[i][1] = in.nextInt();
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            boolean[] already = new boolean[4];
            for (int j = 0; j < n; j++) {
                if (j == i) continue;
                if (pts[j][0] == pts[i][0]) {
                    if (pts[j][1] < pts[i][1]) {
                        already[0] = true;
                    } else if (pts[j][1] > pts[i][1]) {
                        already[1] = true;
                    }
                } else if (pts[j][1] == pts[i][1]) {
                    if (pts[j][0] < pts[i][0]) {
                        already[2] = true;
                    } else if (pts[j][0] > pts[i][0]) {
                        already[3] = true;
                    }
                }
            }
            int cnt = 0;
            for (int k = 0; k < 4; k++) {
                if (already[k]) {
                    cnt++;
                }
            }
            if (cnt == 4) {
                ans++;
            }
        }
        out.println(ans);
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
