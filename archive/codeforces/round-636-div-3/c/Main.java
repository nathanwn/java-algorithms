import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static void solve(InputReader in, PrintWriter out) {
        int t = in.nextInt();
        while (t-- > 0) solveCase(in, out);
    }

    static void solveCase(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        int curSign = -getSign(a[0]);
        ArrayList<ArrayList<Integer>> groups = new ArrayList<>();
        ArrayList<Integer> curGroup = null;
        for (int i = 0; i < n; i++) {
            int sign = getSign(a[i]);
            if (curSign != sign) {
                if (curGroup != null) groups.add(curGroup);
                curGroup = new ArrayList<>();
                curSign = sign;
            }
            curGroup.add(a[i]);
        }
        if (!curGroup.isEmpty()) groups.add(curGroup);
        long ans = 0;
        for (ArrayList<Integer> group : groups) {
            ans += getGroupValue(group);
        }
        out.println(ans);
    }

    static int getGroupValue(ArrayList<Integer> group) {
        int sign = getSign(group.get(0));
        if (sign == -1) {
            int minAbs = Integer.MAX_VALUE;
            for (int x : group) {
                minAbs = Math.min(minAbs, Math.abs(x));
            }
            return -minAbs;
        } else {
            int max = Integer.MIN_VALUE;
            for (int x : group) {
                max = Math.max(max, x);
            }
            return max;
        }
    }

    static int getSign(int x) {
        if (x > 0) return 1;
        return -1;
    }

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

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.close();
    }
}
