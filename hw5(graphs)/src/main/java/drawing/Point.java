package drawing;

import java.util.Objects;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Point)) return false;
        Point point = (Point) other;
        double EPS = 1e-10;
        return Math.abs(x - point.x) < EPS &&
                Math.abs(y - point.y) < EPS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point { x: " + x + ", y: " + y + " }";
    }

}
