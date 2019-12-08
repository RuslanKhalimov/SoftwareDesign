package graph;

import drawing.DrawingApi;
import drawing.Point;

import static drawing.DrawingUtils.MARGIN;

public class PointsFactory {
    private double nextAlpha;
    private final double dAlpha;
    private final Point center;
    private final double radius;

    public PointsFactory(int pointsCount, DrawingApi drawingApi) {
        nextAlpha = 0;

        dAlpha = 2 * Math.PI / pointsCount;
        center = new Point(drawingApi.getDrawingAreaWidth() / 2., drawingApi.getDrawingAreaHeight() / 2.);
        radius = Math.min(
                drawingApi.getDrawingAreaWidth(),
                drawingApi.getDrawingAreaHeight()
        ) / 2.0 - MARGIN;
    }

    public Point nextPoint() {
        double x = radius * Math.cos(nextAlpha) + center.getX();
        double y = radius * Math.sin(nextAlpha) + center.getY();
        Point point = new Point(x, y);
        nextAlpha += dAlpha;
        return point;
    }

}
