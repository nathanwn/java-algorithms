import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        String line = in.next();
        ArrayList<Range> ranges = new ArrayList<>();
        String[] rangesInput = line.split(",");
        for (String rangeInput : rangesInput) {
            String[] parts = rangeInput.split("-");
            if (parts.length != 2) throw new RuntimeException();
            long first = Long.parseLong(parts[0]);
            long last = Long.parseLong(parts[1]);
            ranges.add(new Range(first, last));
        }
        out.println("part 1: " + part1(ranges));
        out.println("part 2: " + part2(ranges));
        out.close();
    }

    static long part1(ArrayList<Range> ranges) {
        return solve(ranges, Main::isValid1);
    }

    static long part2(ArrayList<Range> ranges) {
        return solve(ranges, Main::isValid2);
    }

    static long solve(ArrayList<Range> ranges, Function<Long, Boolean> isValid) {
        long ans = 0;
        for (Range range : ranges) {
            for (long x = range.first; x <= range.last; x++) {
                if (!isValid.apply(x)) {
                    ans += x;
                }
            }
        }
        return ans;
    }

    static boolean isValid1(long x) {
        String s = "" + x;
        if (s.length() % 2 == 1) {
            return true;
        }
        int halfLen = s.length() / 2;
        String u = s.substring(0, halfLen);
        String v = s.substring(halfLen, halfLen + halfLen);
        return !u.equals(v);
    }

    static boolean isValid2(long x) {
        String s = "" + x;
        int maxLen = s.length() / 2;
        for (int len = 1; len <= maxLen; len++) {
            if (s.length() % len != 0) continue;
            boolean valid = false;
            String t = s.substring(0, len);
            for (int start = len; start + len <= s.length(); start += len) {
                String v = s.substring(start, start + len);
                if (!t.equals(v)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                return false;
            }
        }
        return true;
    }

    static class Range {
        long first;
        long last;

        Range(long first, long last) {
            this.first = first;
            this.last = last;
        }

        @Override
        public String toString() {
            return "(" + this.first + ", " + this.last + ")";
        }
    }
}
