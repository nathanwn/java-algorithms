import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static final Pattern p1 = Pattern.compile("R(\\d+)C(\\d+)");
    static final Pattern p2 = Pattern.compile("([A-Z]+)(\\d+)");

    static void solveCase(InputReader in, PrintWriter out) {
        String s = in.next();
        Matcher m1 = p1.matcher(s);
        Matcher m2 = p2.matcher(s);
        if (m1.find()) {
            int r = Integer.parseInt(m1.group(1));
            int c = Integer.parseInt(m1.group(2));
            ArrayList<Character> cs = new ArrayList<>();
            int carry = 0;
            while (c > 0) {
                if (carry > 0) {
                    c--;
                    if (c == 0) break;
                }
                int ord = c % 26 - 1;
                if (ord < 0) {
                    carry = 1;
                    ord += 26;
                } else {
                    carry = 0;
                }
                char chr = (char) (ord + 'A');
                cs.add(chr);
                c /= 26;
            }
            Collections.reverse(cs);
            StringBuilder cbuf = new StringBuilder();
            for (char chr : cs) {
                cbuf.append(chr);
            }
            out.println(cbuf + "" + r);
        } else if (m2.find()) {
            String cs = m2.group(1);
            int c = 0;
            for (int i = 0; i < cs.length(); i++) {
                c *= 26;
                c += cs.charAt(i) - 'A' + 1;
            }
            int r = Integer.parseInt(m2.group(2));
            out.println("R" + r + "C" + c);
        } else throw new AssertionError();
    }

    static void solve(InputReader in, PrintWriter out) {
        int t = in.nextInt();
        while (t-- > 0) {
            solveCase(in, out);
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

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.close();
    }
}
