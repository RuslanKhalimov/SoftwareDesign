package graph;

import drawing.Point;

public class PointsFactory {
    private double nextAlpha;
    private final double dAlpha;
    private final Point center;
    private final double radius;

    public PointsFactory(int pointsCount, Point center, double radius) {
        if (pointsCount == 0) {
            throw new IllegalArgumentException("Empty graph");
        }
        this.center = center;
        this.radius = radius;
        nextAlpha = 0;
        dAlpha = 2 * Math.PI / pointsCount;
    }

    public Point nextPoint() {
        double x = radius * Math.cos(nextAlpha) + center.getX();
        double y = radius * Math.sin(nextAlpha) + center.getY();
        Point point = new Point(x, y);
        nextAlpha += dAlpha;
        return point;
    }

}
