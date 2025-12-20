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
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            long n = in.nextLong();

            // Given both
            // (1) a^2 * b^2 * c^2 <= n
            // (2) a < b < c
            // => c <= sqrt(N / a^2 / b)
            //      <= sqrt(10^12 / 2^2 / 3)
            //      < 3 * 10^5
            PrimeSieve sieve = new PrimeSieve(3 * 100_000);
            int numPrimes = sieve.primes.length;

            long ans = 0;

            for (int ai = 0; ai < numPrimes; ai++) {
                int a = sieve.primes[ai];

                int bi = ai + 1;
                int ci = numPrimes - 1;

                while (bi < ci) {
                    int b = sieve.primes[bi];
                    long res = 1L * a * a * b;  // <= 10^6 * 10^6 * 10^6
                    // out.println("b = " + b);
                    if (res > n) {
                        ci--;
                        continue;
                    }
                    int c = sieve.primes[ci];
                    // out.println("c = " + c);
                    res *= c;  // <= 10^12 * 10^6 since res <= n <= 10^12
                    if (res > n) {
                        ci--;
                        continue;
                    }
                    res *= c;  // <= 10^12 * 10^6 since res <= n <= 10^12
                    if (res > n) {
                        ci--;
                        continue;
                    }
                    // out.println("Here " + b + " " + c);
                    ans += ci - bi;
                    bi++;
                }
            }
            out.println(ans);
        }
    }

    static class PrimeSieve {
        int[] primes;

        PrimeSieve(int n) {
            boolean[] isPrime = new boolean[n + 1];
            Arrays.fill(isPrime, true);
            int numPrimes = n - 1;
            for (int i = 2; i * i <= n; i++) {
                if (!isPrime[i]) continue;
                for (int j = i * i; j <= n; j += i) {
                    if (isPrime[j]) {
                        isPrime[j] = false;
                        numPrimes--;
                    }
                }
            }
            primes = new int[numPrimes];
            int j = 0;
            for (int i = 2; i <= n; i++) {
                if (isPrime[i]) {
                    primes[j] = i;
                    j++;
                }
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

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        Solver solver = new Solver();
        solver.solve(in, out);
        out.close();
    }
}
