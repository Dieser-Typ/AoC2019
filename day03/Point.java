package day03;

public class Point {
    private int x, y, steps;

    Point(int x, int y, int steps) {
        this.x = x;
        this.y = y;
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

    int getManhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    int getSteps() {
        return steps;
    }
}
