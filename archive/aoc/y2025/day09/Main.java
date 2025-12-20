import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        Point[] points = parsePoints(in);
        out.println("part 1: " + part1(points));
        out.println("part 2: " + part2(points));
        out.close();
    }

    static long part1(Point[] points) {
        long ans = 0;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Point pi = points[i];
                Point pj = points[j];
                long a = area(pi, pj);
                ans = Math.max(ans, a);
            }
        }
        return ans;
    }

    static long part2(Point[] points) {
        int n = points.length;
        int[] xs = new int[n];
        int[] ys = new int[n];
        for (int i = 0; i < n; i++) {
            xs[i] = points[i].x;
            ys[i] = points[i].y;
        }
        CoordinateCompressor xC = new CoordinateCompressor(xs, random);
        CoordinateCompressor yC = new CoordinateCompressor(ys, random);
        int xn = xC.length;
        int yn = yC.length;
        Point[] cPoints = new Point[n];
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            int cx = xC.getIndex(p.x);
            int cy = yC.getIndex(p.y);
            cPoints[i] = new Point(cx, cy);
        }
        int[][] pSum = new int[xC.length + 1][yC.length + 1];
        // NOTE: Can use flood-fill to find cells inside the
        // polygon and construct pSum accordingly.
        // The following solution however is much more advanced
        // and arguably more instructive.

        // Propagating values across horizontal segments
        for (int i = 0; i < n; i++) {
            Point p = cPoints[i];
            Point q = cPoints[(i + 1) % n];
            if (p.y == q.y) {
                pSum[p.x][p.y]++;
                pSum[q.x][q.y]--;
            }
        }
        for (int i = xn; i >= 0; i--) {
            for (int j = yn; j >= 0; j--) {
                if (i + 1 <= xn) {
                    pSum[i][j] += pSum[i + 1][j];
                }
                if (j + 1 <= yn) {
                    pSum[i][j] += pSum[i][j + 1];
                }
                if (i + 1 <= xn && j + 1 <= yn) {
                    pSum[i][j] -= pSum[i + 1][j + 1];
                }
            }
        }

        // Propagating values across vertical segments
        for (int i = 0; i <= xn; i++) {
            for (int j = 0; j <= yn; j++) {
                if (i > 0) {
                    pSum[i][j] += pSum[i - 1][j];
                }
                if (j > 0) {
                    pSum[i][j] += pSum[i][j - 1];
                }
                if (i > 0 && j > 0) {
                    pSum[i][j] -= pSum[i - 1][j - 1];
                }
            }
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Point p = cPoints[i];
                Point q = cPoints[j];
                int minX = Math.min(p.x, q.x);
                int maxX = Math.max(p.x, q.x);
                int minY = Math.min(p.y, q.y);
                int maxY = Math.max(p.y, q.y);
                int dX = maxX - minX;
                int dY = maxY - minY;
                // This could be negative if the order of points is
                // the opposite (clockwise or counter-clockwise)
                int sum = pSum[maxX][maxY]
                        - pSum[maxX][minY]
                        - pSum[minX][maxY]
                        + pSum[minX][minY];
                if (sum == dX * dY) {
                    int dx = xC.getValue(maxX) - xC.getValue(minX) + 1;
                    int dy = yC.getValue(maxY) - yC.getValue(minY) + 1;
                    ans = Math.max(ans, 1L * dx * dy);
                }
            }
        }
        return ans;
    }

    static Random random = new Random();

    static int unique(int[] a) {
        if (a.length == 0) return 0;
        int j = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[i - 1]) {
                a[j] = a[i];
                j++;
            }
        }
        return j;
    }

    static void sort(int[] a, Random random) {
        shuffle(a, random);
        Arrays.sort(a);
    }

    static void shuffle(int[] a, Random random) {
        // Fisher-Yates shuffle
        for (int i = a.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    static int search(int[] a, int n, int x) {
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (x == a[mid]) {
                return mid;
            } else if (x < a[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        throw new RuntimeException();
    }

    static class CoordinateCompressor {
        int length;
        int[] b;

        CoordinateCompressor(int[] a, Random random) {
            int n = a.length;
            b = Arrays.copyOf(a, n);
            sort(b, random);
            length = unique(b);
        }

        int getIndex(int x) {
            return search(b, length, x);
        }

        int getValue(int id) {
            return b[id];
        }
    }

    static Point[] parsePoints(Scanner in) {
        ArrayList<Point> points = new ArrayList<>();
        while (in.hasNext()) {
            Integer[] coords = Arrays.asList(in.next().split(","))
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList())
                .toArray(new Integer[0]);
            points.add(new Point(coords[0], coords[1]));
        }
        return points.toArray(new Point[0]);
    }

    static long area(Point p1, Point p2) {
        int dx = Math.abs(p1.x - p2.x) + 1;
        int dy = Math.abs(p1.y - p2.y) + 1;
        return 1L * dx * dy;
    }

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
