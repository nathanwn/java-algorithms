import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.List;
import java.util.HashSet;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int ans1 = 0;
        int ans2 = 0;
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] parts = line.split(" ");
            int nParts = parts.length;
            char[] lights = parts[0]
                .substring(1, parts[0].length() - 1)
                .toCharArray();
            List<Integer> voltages = Arrays.asList(parts[nParts - 1]
                .substring(1, parts[nParts - 1].length() - 1)
                .split(","))
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

            int nLights = lights.length;
            int[] onVal = new int[nLights];
            int[] voltageVal = new int[nLights];
            for (int i = 0; i < nLights; i++) {
                onVal[i] = (lights[i] == '#' ? 1 : 0);
                voltageVal[i] = voltages.get(i);
            }
            ArrayList<ArrayList<Integer>> buttons = new ArrayList<>();
            for (int i = 1; i < nParts - 1; i++) {
                ArrayList<Integer> button = Arrays.asList(parts[i]
                    .substring(1, parts[i].length() - 1)
                    .split(","))
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
                buttons.add(button);
            }
            ans1 += solve1(onVal, buttons);
            ans2 += solve2(voltageVal, buttons);
        }

        out.println("part 1: " + ans1);
        out.println("part 2: " + ans2);

        out.close();
    }

    static int solve1(int[] val, ArrayList<ArrayList<Integer>> buttons) {
        State start = new OnState(new int[val.length], 0);
        State end = new OnState(val, 0);
        ArrayDeque<State> q = new ArrayDeque<>();
        q.addLast(start);
        HashSet<State> already = new HashSet<>();
        already.add(start);
        while (!q.isEmpty()) {
            State u = q.pollFirst();
            if (u.equals(end)) {
                return u.dist;
            }
            for (ArrayList<Integer> button : buttons) {
                State v = u.next(button);
                if (already.contains(v)) continue;
                already.add(v);
                q.addLast(v);
            }
        }
        throw new RuntimeException();
    }

    /**
     * This is too slow.
    **/
    static int solve2Slow(int[] val, ArrayList<ArrayList<Integer>> buttons) {
        State start = new VoltageState(new int[val.length], 0);
        State end = new VoltageState(val, 0);
        ArrayDeque<State> q = new ArrayDeque<>();
        q.addLast(start);
        HashSet<State> already = new HashSet<>();
        already.add(start);
        while (!q.isEmpty()) {
            State u = q.pollFirst();
            if (u.equals(end)) {
                return u.dist;
            }
            boolean skip = false;
            for (int i = 0; i < end.val.length; i++) {
                if (u.val[i] > end.val[i]) {
                    skip = true;
                    break;
                }
            }
            if (skip) continue;
            for (ArrayList<Integer> button : buttons) {
                State v = u.next(button);
                if (already.contains(v)) continue;
                already.add(v);
                q.addLast(v);
            }
        }
        throw new RuntimeException();
    }

    /**
     * This is incorrect as the linear systems given in the input may have
     * infinitely many solutions, and Gauss does not guarantee to find the
     * optimal one.
    **/
    static int solve2(int[] val, ArrayList<ArrayList<Integer>> buttons) {
        int nRows = val.length;
        int nCols = buttons.size();
        double[][] a = new double[nRows][nCols + 1];
        for (int i = 0; i < nRows; i++) {
            a[i][nCols] = val[i];
        }
        for (int j = 0; j < buttons.size(); j++) {
            ArrayList<Integer> button = buttons.get(j);
            for (int i : button) {
                a[i][j] = 1;
            }
        }
        Gauss.Result res = Gauss.solve(a);
        int ans = 0;
        for (int j = 0; j < nCols; j++) {
            ans += res.sol[j];
        }
        return ans;
    }

    static abstract class State {
        int[] val;
        int dist;

        State(int[] val, int dist) {
            this.val = val;
            this.dist = dist;
        }

        abstract State next(ArrayList<Integer> button);

        @Override
        public int hashCode() {
            return Arrays.hashCode(val);
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof State) {
                return Arrays.equals(val, ((State) other).val);
            }
            return false;
        }

        @Override
        public String toString() {
            return Arrays.toString(val);
        }
    }

    static class OnState extends State {
        OnState(int[] val, int dist) {
            super(val, dist);
        }

        @Override
        State next(ArrayList<Integer> button) {
            int[] nextVal = Arrays.copyOf(val, val.length);
            for (int i : button) {
                nextVal[i] = val[i] ^ 1;
            }
            return new OnState(nextVal, dist + 1);
        }
    }

    static class VoltageState extends State {
        VoltageState(int[] val, int dist) {
            super(val, dist);
        }

        @Override
        State next(ArrayList<Integer> button) {
            int[] nextVal = Arrays.copyOf(val, val.length);
            for (int i : button) {
                nextVal[i]++;
            }
            return new VoltageState(nextVal, dist + 1);
        }
    }

    static class Gauss {
        enum ResultType {
            ZERO,
            ONE,
            INF,
        }

        static class Result {
            ResultType ty;
            double[] sol;

            Result(ResultType ty, double[] sol) {
                this.ty = ty;
                this.sol = sol;
            }
        }

        /**
         * If there are infinitely many solutions, this returns a
         * particular solution where all free variables are set to zero.
         * @param a  First m columns are matrix A, last column is vector b.
        **/
        static Result solve(double[][] a) {
            final double EPS = 1e-9;
            final int INF = 2;

            int nRows = a.length;
            int nCols = a[0].length - 1;

            // Tracks which col is used to eliminate variable x_j
            // A value of -1 indicates a free variable.
            int[] pivotRowForCol = new int[nCols];
            Arrays.fill(pivotRowForCol, -1);

            for (int col = 0, row = 0; col < nCols && row < nRows; col++) {
                // Select row for elimination.
                // Choose the one with the largest absolute coefficient value.
                int pivotRow = row;
                for (int i = pivotRow; i < nRows; i++) {
                    if (Math.abs(a[i][col]) > Math.abs(a[pivotRow][col])) {
                        pivotRow = i;
                    }
                }
                // Skip this column.
                // This indicates a redundant equation or a free variable.
                if (Math.abs(a[pivotRow][col]) < EPS) {
                    continue;
                }
                // Swaps the current row with the pivotRow so the largest value is
                // on the diagonal.
                for (int j = col; j <= nCols; j++) {
                    double tmp = a[pivotRow][j];
                    a[pivotRow][j] = a[row][j];
                    a[row][j] = tmp;
                }
                pivotRowForCol[col] = row;
                // Elimination
                for (int i = 0; i < nRows; i++) {
                    if (i != row) {
                        double c = a[i][col] / a[row][col];
                        for (int j = col; j <= nCols; j++) {
                            a[i][j] -= a[row][j] * c;
                        }
                    }
                }
                row++;
            }
            double[] sol = new double[nCols];
            for (int j = 0; j < nCols; j++) {
                if (pivotRowForCol[j] != -1) {
                    int i = pivotRowForCol[j];
                    sol[j] = a[i][nCols] / a[i][j];
                }
            }
            for (int i = 0; i < nRows; i++) {
                double sum = 0;
                for (int j = 0; j < nCols; j++) {
                    sum += sol[j] * a[i][j];
                }
                if (Math.abs(sum - a[i][nCols]) > EPS) {
                    return new Result(ResultType.ZERO, sol);
                }
            }

            for (int j = 0; j < nCols; j++) {
                if (pivotRowForCol[j] == -1) {
                    return new Result(ResultType.INF, sol);
                }
            }
            return new Result(ResultType.ONE, sol);
        }
    }
}
