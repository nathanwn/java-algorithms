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

    void solve() {
        int caseCount = 1;
        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            HashMap<String, Integer> ids = new HashMap<>();
            for (int i = 0; i < n; i++) {
                String currency = in.next();
                ids.put(currency, i);
            }
            int m = in.nextInt();
            double[][] directRate = new double[n][n];
            for (int i = 0; i < m; i++) {
                String fromCurrency = in.next();
                double rate = in.nextDouble();
                String toCurrency = in.next();
                int from = ids.get(fromCurrency);
                int to = ids.get(toCurrency);
                directRate[from][to] = rate;
            }
            out.print("Case " + caseCount++ + ": ");
            out.println(hasArbitrage(n, directRate) ? "Yes" : "No");
        }
    }

    boolean hasArbitrage(int n, double[][] directRate) {
        double[] exchangeRate = new double[n];

        for (int s = 0; s < n; s++) {
            Arrays.fill(exchangeRate, 0.0);
            exchangeRate[s] = 1.0;

            for (int i = 0; i < n - 1; i++) {
                for (int u = 0; u < n; u++) {
                    for (int v = 0; v < n; v++) {
                        double newExchangeRate = exchangeRate[u] * directRate[u][v];
                        if (exchangeRate[v] < newExchangeRate) {
                            exchangeRate[v] = newExchangeRate;
                        }
                    }
                }
            }

            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (exchangeRate[v] < exchangeRate[u] * directRate[u][v]) {
                        return true;
                    }
                }
            }
        }

        return false;
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
                new Main().solve();
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
