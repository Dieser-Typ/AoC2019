package util;

import java.util.List;

public class Point {
    private int x, y, steps;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        steps = 0;
    }

    public Point(int x, int y, int steps) {
        this(x, y);
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point) o;
        return p.x == x && p.y == y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y +")";
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(x).hashCode() + Integer.valueOf(y).hashCode();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getManhattanDistance(Point p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    public int getSteps() {
        return steps;
    }

    public double getAngle(Point p) {
        double angle = Math.atan2(p.y - y, p.x - x) - Math.atan2(-1, 0);
        if (angle < 0) angle += 2 * Math.PI;
        return angle;
    }

    public int getVisiblePoints(List<Point> points) {
        return (int) points
                .stream()
                .mapToDouble(this::getAngle)
                .distinct().count();
    }
}
