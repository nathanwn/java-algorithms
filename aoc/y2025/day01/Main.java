import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<Rot> rots = new ArrayList<>();
        while (in.hasNext()) {
            String line = in.next();
            Dir d = line.charAt(0) == 'L' ? Dir.L : Dir.R;
            int n = Integer.parseInt(line.substring(1));
            rots.add(new Rot(d, n));
        }
        out.println("part 1: " + part1(rots));
        out.println("part 2: " + part2(rots));
        out.close();
    }

    static int part1(ArrayList<Rot> rots) {
        int cur = 50;
        int ans = 0;
        for (Rot r : rots) {
            int n = r.n % 100;
            if (r.d == Dir.L) {
                cur -= n;
                if (cur < 0) cur += 100;
            } else {
                cur += n;
                if (cur >= 100) cur -= 100;
            }
            if (cur == 0) ans++;
        }
        return ans;
    }

    static int part2(ArrayList<Rot> rots) {
        int cur = 50;  // [-100, 99]
        int ans = 0;
        for (Rot r : rots) {
            ans += r.n / 100;
            int n = r.n % 100;
            if (n == 0) continue;
            boolean overflow = false;
            if (r.d == Dir.L) {
                int prev = cur;
                cur -= n;
                if (cur < 0) {
                    cur += 100;
                    overflow = true;
                    if (prev > 0) ans++;
                }
            } else {
                cur += n;
                if (cur >= 100) {
                    cur -= 100;
                    overflow = true;
                    ans++;
                }
            }
            if (cur == 0 && !overflow) {
                ans++;
            }
        }
        return ans;
    }

    enum Dir { L, R }

    static class Rot {
        Dir d;
        int n;

        Rot(Dir d, int n) {
            this.d = d;
            this.n = n;
        }

        @Override
        public String toString() {
            return (d == Dir.L ? "L" : "R") + this.n;
        }
    }
}
