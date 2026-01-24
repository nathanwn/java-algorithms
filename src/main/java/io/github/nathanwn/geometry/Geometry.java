package io.github.nathanwn.geometry;

public class Geometry {
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
     * Compare the polar angle of two points.
     * If used for sorting, points are sorted clockwise.
     * This version does not contain the distance-from-origin tie-break.
     * Returns
     *   1 if p1 is on the left of p2 (ccw).
     *  -1 if p1 is on the right of p2.
     *  0 if the two polar angles are the same.
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
