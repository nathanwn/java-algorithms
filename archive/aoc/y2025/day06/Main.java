import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<String> lines = new ArrayList<>();
        while (in.hasNext()) {
            lines.add(in.nextLine());
        }
        out.println("part 1: " + part1(lines));
        out.println("part 2: " + part2(lines));
        out.close();
    }

    static final String ADD = "+";
    static final String MUL = "*";

    static long part1(ArrayList<String> lines) {
        ArrayList<ArrayList<Integer>> probInput = new ArrayList<>();
        String[] signs = null;
        for (String line : lines) {
            String[] parts = safeSplit(line);
            if (parts[0].equals("+") || parts[0].equals("*")) {
                signs = parts;
                break;
            }
            ArrayList<Integer> nums = new ArrayList<>();
            for (String part : parts) {
                if (part.length() == 0) continue;
                nums.add(Integer.parseInt(part));
            }
            probInput.add(nums);
        }
        int n = probInput.size();
        int m = probInput.get(0).size();
        int[][] probs = new int[n][m];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                probs[r][c] = probInput.get(r).get(c);
            }
        }
        long ans = 0;
        for (int c = 0; c < m; c++) {
            if (signs[c].equals(ADD)) {
                long res = 0;
                for (int r = 0; r < n; r++) {
                    res += probs[r][c];
                }
                ans += res;
            } else {
                long res = 1;
                for (int r = 0; r < n; r++) {
                    res *= probs[r][c];
                }
                ans += res;
            }
        }
        return ans;
    }

    static String[] safeSplit(String line) {
        String[] parts = Arrays
            .asList(line.split(" +"))
            .stream()
            .filter(part -> part.length() > 0)
            .collect(Collectors.toList())
            .toArray(new String[0]);
        return parts;
    }

    static long part2(ArrayList<String> lines) {
        ArrayList<ArrayList<Num>> rows = new ArrayList<>();
        for (int i = 0; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            ArrayList<Num> nums = parseLine(line);
            rows.add(nums);
        }
        String[] signs = safeSplit(lines.get(lines.size() - 1));
        int numCols = rows.get(0).size();
        int[] colStart = new int[numCols];
        int[] colEnd = new int[numCols];
        Arrays.fill(colStart, Integer.MAX_VALUE);
        Arrays.fill(colEnd, -1);
        for (ArrayList<Num> nums : rows) {
            for (int i = 0; i < numCols; i++) {
                colStart[i] = Math.min(colStart[i], nums.get(i).start);
                colEnd[i] = Math.max(colEnd[i], nums.get(i).end);
            }
        }
        long ans = 0;
        for (int j = 0; j < numCols; j++) {
            long aggVal = 0;
            if (signs[j].equals(MUL)) {
                aggVal = 1;
            }
            for (int k = colStart[j]; k <= colEnd[j]; k++) {
                long val = 0;
                for (int i = 0; i < rows.size(); i++) {
                    char c = lines.get(i).charAt(k);
                    if (c != ' ') {
                        val *= 10;
                        val += c - '0';
                    }
                }
                if (signs[j].equals(ADD)) {
                    aggVal += val;
                } else {
                    aggVal *= val;
                }
            }
            ans += aggVal;
        }
        return ans;
    }

    static ArrayList<Num> parseLine(String line) {
        int i = 0;
        ArrayList<Num> nums = new ArrayList<>();
        while (i < line.length()) {
            while (i < line.length() && line.charAt(i) == ' ') {
                i++;
            }
            if (i == line.length()) break;
            int start = i;
            int val = 0;
            while (i < line.length() && Character.isDigit(line.charAt(i))) {
                val *= 10;
                val += line.charAt(i) - '0';
                i++;
            }
            int end = i - 1;
            Num num = new Num(val, start, end);
            nums.add(num);
        }
        return nums;
    }

    static class Num {
        int val;
        int start;
        int end;

        Num(int val, int start, int end) {
            this.val = val;
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return this.val + "(" + this.start + ", " + this.end + ")";
        }
    }
}
