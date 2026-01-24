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
    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int q = in.nextInt();
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(in.nextInt(), in.nextInt());
        }
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            order.add(i);
        }
        Collections.sort(order, (Integer i, Integer j) -> {
            Point p1 = pts[i];
            Point p2 = pts[j];
            return Geometry.comparePolar(p1, p2);
        });
        int[] orderToId = new int[n];
        for (int i = 0; i < n; i++) {
            orderToId[order.get(i)] = i;
        }
        // leftMost[i]: left-most point with the same polar angle
        // rightMost[i]: right-most point with the same polar angle
        int[] leftMost = new int[n];
        leftMost[0] = 0;
        for (int i = 1; i < n; i++) {
            Point ptPorderToId = pts[order.get(i - 1)];
            Point ptCur = pts[order.get(i)];
            if (Geometry.comparePolar(ptPorderToId, ptCur) == 0) {
                leftMost[i] = leftMost[i - 1];
            } else {
                leftMost[i] = i;
            }
        }
        int[] rightMost = new int[n];
        rightMost[n - 1] = n - 1;
        for (int i = n - 2; i > -1; i--) {
            Point pNext = pts[order.get(i + 1)];
            Point ptCur = pts[order.get(i)];
            if (Geometry.comparePolar(pNext, ptCur) == 0) {
                rightMost[i] = rightMost[i + 1];
            } else {
                rightMost[i] = i;
            }
        }
        for (int j = 0; j < q; j++) {
            int u = leftMost[orderToId[in.nextInt() - 1]];
            int v = rightMost[orderToId[in.nextInt() - 1]];
            if (u < v) {
                out.println(v - u + 1);
            } else {
                out.println(n - u + v + 1);
            }
        }
    }

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        long dist2() {
            return (long) x * x + (long) y * y;
        }
    }

    static class Geometry {
        /**
        * Cross product.
        */
        public static long cross(int x1, int y1, int x2, int y2) {
            return (long) x1 * y2 - (long) x2 * y1;
        }

        /**
        * Cross product.
        */
        public static long cross(Point p1, Point p2) {
            return (long) p1.x * p2.y - (long) p2.x * p1.y;
        }

        /**
        * Orientation of three points p1, p2, p3.
        * Returns:
        *   -1 if p3 is on the left of p1p2.
        *   1 if p3 is on the right of p1p2.
        *   0 if the three points are co-linear.
        */
        public static int orient(Point p1, Point p2, Point p3) {
            // Calculate the cross product of (p2 - p1, p3 - p1)
            long crossProduct = cross(p2.x - p1.x, p2.y - p1.y, p3.x - p1.x, p3.y - p1.y);
            if (crossProduct > 0) return 1;
            if (crossProduct < 0) return -1;
            return 0;
        }

        /**
        * Returns true if the point is in the upper half-plane.
        * A point is considered in the upper half-plane if
        * y > 0 or if it is on the positive x-axis (y == 0, x > 0).
        */
        public static boolean inUpperHalf(Point p) {
            if (p.x == 0 && p.y == 0) {
                throw new AssertionError("Cannot get the upper half-plane of the origin.");
            }
            return p.y > 0 || (p.y == 0 && p.x < 0);
        }

        /**
        * Points are sorted clockwise.
        */
        public static int comparePolar(Point p1, Point p2) {
            // Compare half-planes the points are in. Upper-half comes first.
            int half1 = inUpperHalf(p1) ? 0 : 1;
            int half2 = inUpperHalf(p2) ? 0 : 1;
            if (half1 < half2) {
                return -1;
            } else if (half1 > half2) {
                return 1;
            }
            // If cross(p1, p2) > 0, then p1 is to the left of p2 (ccw).
            long crossProduct = cross(p1, p2);
            if (crossProduct > 0) {
                return 1;
            } else if (crossProduct < 0) {
                return -1;
            }
            return 0;
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
        solve(in, out);
        out.close();
    }
}
