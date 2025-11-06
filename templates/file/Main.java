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
    static final String PROBLEM_NAME = "breedflip";

    static class Solver {
        void solve(InputReader in, PrintWriter out) {

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
        InputStream inStream = System.in;
        OutputStream outStream = System.out;
        if (System.getProperty("LOCAL") == null) {
            try {
                inStream = new FileInputStream(PROBLEM_NAME + ".in");
                outStream = new FileOutputStream(PROBLEM_NAME + ".out");
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
