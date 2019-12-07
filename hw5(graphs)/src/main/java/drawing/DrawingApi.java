package drawing;

import static drawing.DrawingUtils.HEIGHT;
import static drawing.DrawingUtils.WIDTH;

public interface DrawingApi {
    default int getDrawingAreaWidth() {
        return WIDTH;
    }

    default int getDrawingAreaHeight() {
        return HEIGHT;
    }

    void drawCircle(Point center, double radius);

    void drawLine(Point a, Point b);

    void visualize();

}
