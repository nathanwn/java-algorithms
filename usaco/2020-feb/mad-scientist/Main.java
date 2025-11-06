import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] a = readArray(in);
            int[] b = readArray(in);

            int numDifferentSegments = 0;
            boolean same;

            if (a[0] == b[0]) {
                same = true;
            } else {
                same = false;
                numDifferentSegments++;
            }

            for (int i = 1; i < n; i++) {
                if (a[i] != b[i]) {
                    if (same) {
                        same = false;
                        numDifferentSegments++;
                    }
                } else {  // a[i] == b[i]
                    if (!same) {
                        same = true;
                    }
                }
            }

            out.println(numDifferentSegments);
        }

        int[] readArray(InputReader in) {
            char[] c = in.next().toCharArray();
            int[] a = new int[c.length];
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 'G') {
                    a[i] = 0;
                }
                else {
                    a[i] = 1;
                }
            }
            return a;
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
        final String problemName = "breedflip";
        InputStream inStream = System.in;
        OutputStream outStream = System.out;
        if (System.getProperty("LOCAL") == null) {
            try {
                inStream = new FileInputStream(problemName + ".in");
                outStream = new FileOutputStream(problemName + ".out");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        InputReader in = new InputReader(inStream);
        PrintWriter out = new PrintWriter(outStream);
        Solver solver = new Solver();
        solver.solve(in, out);
        out.close();
    }
}
