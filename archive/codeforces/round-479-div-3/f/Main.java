import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    static final boolean MULTITEST = false;

    static void solve() {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        // key: num
        // value: length of longest subsequent ending with num
        HashMap<Integer, Integer> lengths = new HashMap<>();
        // key: num
        // value: position of the end of the longest subsequence ending with num
        HashMap<Integer, Integer> positions = new HashMap<>();
        // dp[i]: length of subsequence ending at i
        int[] dp = new int[n];
        int[] prev = new int[n];
        int bestEnd = 0;
        for (int i = 0; i < n; i++) {
            int prevLen = lengths.getOrDefault(a[i] - 1, 0);
            int prevPos = positions.getOrDefault(a[i] - 1, -1);
            dp[i] = prevLen + 1;
            prev[i] = prevPos;
            int len = lengths.getOrDefault(a[i], -1);
            if (dp[i] > len) {
                lengths.put(a[i], dp[i]);
                positions.put(a[i], i);
            }
            if (dp[i] > dp[bestEnd]) {
                bestEnd = i;
            }
        }
        out.println(dp[bestEnd]);
        ArrayList<Integer> ans = new ArrayList<>();
        for (int j = bestEnd; j != -1; j = prev[j]) {
            ans.add(j + 1);
        }
        Collections.reverse(ans);
        for (int i = 0; i < ans.size(); i++) {
            if (i != 0) out.print(' ');
            out.print(ans.get(i));
        }
        out.println();
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
