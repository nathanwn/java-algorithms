import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

public class Main {
    static void solveCase(int n0, int n1, int n2) {
        int en0 = n0;
        int en1 = n1;
        int en2 = n2;
        if (invalid(en0, en1, en2)) {
            throw new AssertionError();
        }
        int r1 = (n1 > 0 && n1 % 2 == 0) ? 1 : 0;
        n1 -= r1;
        int bit = 0;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        if (n1 > 0) {
            for (int i = 0; i < n1 + 1; i++) {
                q.addLast(bit);
                bit ^= 1;
            }
        }
        if (n1 == 0 && n0 > 0) {
            q.addFirst(0);
        }
        for (int i = 0; i < n0; i++) {
            q.addFirst(0);
        }
        if (n1 == 0 && n2 > 0) {
            q.addLast(1);
        }
        for (int i = 0; i < n2; i++) {
            q.addLast(1);
        }
        if (r1 > 0) {
            q.addLast(0);
        }
        
        StringBuilder buf = new StringBuilder();
        while (!q.isEmpty()) buf.append(q.removeFirst());
        String ans = buf.toString();

        if (!verify(ans, en0, en1, en2)) {
            throw new AssertionError();
        }
        out.println(ans);
    }

    static boolean invalid(int n0, int n1, int n2) {
        if (n0 > 0 && n1 == 0 && n2 > 0) return true;
        return false;
    }

    static boolean verify(String s, int en0, int en1, int en2) {
        int n0 = 0;
        int n1 = 0;
        int n2 = 0;
        for (int i = 1; i < s.length(); i++) {
            int ones = 0;
            if (s.charAt(i - 1) == '1') ones++;
            if (s.charAt(i) == '1') ones++;
            if (ones == 0) {
                n0++;
            } else if (ones == 1) {
                n1++;
            } else if (ones == 2) {
                n2++;
            }
        }
        boolean res = true;
        if (n0 != en0) {
            res = false;
        }
        if (n1 != en1) {
            res = false;
        }
        if (n2 != en2) {
            res = false;
        }
        return res;
    }

    static void solve(String[] args) {
        if (args.length > 0 && args[0].equals("stress")) {
            for (int t = 0; t < 10000; t++) {
                while (true) {
                    int n0 = random.nextInt(5);
                    int n1 = random.nextInt(5);
                    int n2 = random.nextInt(5);
                    if (!invalid(n0, n1, n2)) {
                        solveCase(n0, n1, n2);
                        break;
                    }
                }
            }
            out.println("AC!");
        } else {
            int t = in.nextInt();
            while (t-- > 0) {
                int n0 = in.nextInt();
                int n1 = in.nextInt();
                int n2 = in.nextInt();
                solveCase(n0, n1, n2);
            }
        }
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

    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    static Random random = new Random(58);

    public static void main(String[] args) {
        solve(args);
        out.close();
    }
}
