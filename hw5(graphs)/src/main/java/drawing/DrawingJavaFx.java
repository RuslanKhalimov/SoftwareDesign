package drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static drawing.DrawingUtils.HEIGHT;
import static drawing.DrawingUtils.WIDTH;

public class DrawingJavaFx implements DrawingApi {
    private static final List<Shape> shapes = new ArrayList<>();

    @Override
    public void drawCircle(Point center, double radius) {
        shapes.add(new Circle(center.getX(), center.getY(), radius));
    }

    @Override
    public void drawLine(Point a, Point b) {
        shapes.add(new Line(a.getX(), a.getY(), b.getX(), b.getY()));
    }

    @Override
    public void visualize() {
        Application.launch(DrawingGraphApplication.class);
    }

    public static class DrawingGraphApplication extends Application {

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("JavaFX graph visualization");
            Group root = new Group();

            shapes.forEach(shape -> root.getChildren().add(shape));

            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
            primaryStage.show();
        }

    }

}
