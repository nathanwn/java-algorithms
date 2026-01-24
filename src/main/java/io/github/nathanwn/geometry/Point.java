package io.github.nathanwn.geometry;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    long dist2() {
        return (long) x * x + (long) y * y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
