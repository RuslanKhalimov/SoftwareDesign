package drawing;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingAwt implements DrawingApi {
    private final List<Ellipse2D> circles = new ArrayList<>();
    private final List<Line2D> lines = new ArrayList<>();

    @Override
    public void drawCircle(Point center, double radius) {
        center = new Point(center.getX() - radius, center.getY() - radius);
        circles.add(new Ellipse2D.Double(center.getX(), center.getY(), 2 * radius, 2 * radius));
    }

    @Override
    public void drawLine(Point a, Point b) {
        lines.add(new Line2D.Double(a.getX(), a.getY(), b.getX(), b.getY()));
    }

    @Override
    public void visualize() {
        Frame frame = new DrawingGraphFrame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        frame.setSize(getDrawingAreaWidth(), getDrawingAreaHeight());
        frame.setVisible(true);
    }

    private class DrawingGraphFrame extends Frame {
        public DrawingGraphFrame() {
            super("JavaAWT Graph visualization");
        }

        @Override
        public void paint(Graphics graphics) {
            Graphics2D graphics2D = (Graphics2D) graphics;
            circles.forEach(graphics2D::fill);
            lines.forEach(graphics2D::draw);
        }

    }

}
