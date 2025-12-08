import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<String> banks = new ArrayList<>();
        while (in.hasNext()) {
            banks.add(in.next());
        }
        out.println("part 1: " + part1(banks));
        out.println("part 2: " + part2(banks));
        out.close();
    }

    static long part1(ArrayList<String> banks) {
        long ans = 0;
        for (String bank : banks) {
            int firstId = -1;
            char first = '0';
            for (int i = 0; i < bank.length() - 1; i++) {
                if (bank.charAt(i) > first) {
                    firstId = i;
                    first = bank.charAt(i);
                }
            }
            int secondId = firstId + 1;
            for (int i = firstId + 1; i < bank.length(); i++) {
                if (bank.charAt(i) > bank.charAt(secondId)) {
                    secondId = i;
                }
            }
            char second = bank.charAt(secondId);
            int val = Integer.parseInt(first + "" + second);
            ans += val;
        }
        return ans;
    }

    static long part2(ArrayList<String> banks) {
        final int D = 12;
        // final int D = 2;
        // dp[end + 1][d]: largest value obtained after taking d digits
        //               in the range [0..end]
        long ans = 0;
        for (String bank : banks) {
            int n = bank.length();
            long[][] dp = new long[n + 1][D + 1];
            for (int d = 1; d <= D; d++) {
                for (int i = 0; i < n; i++) {
                    long take = dp[i][d - 1] * 10 + bank.charAt(i) - '0';
                    long leave = dp[i][d];
                    dp[i + 1][d] = Math.max(take, leave);
                }
            }
            ans += dp[n][D];
        }
        return ans;
    }
}
