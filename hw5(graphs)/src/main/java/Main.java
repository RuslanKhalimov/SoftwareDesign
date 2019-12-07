import drawing.DrawingApi;
import drawing.DrawingAwt;
import drawing.DrawingJavaFx;
import graph.EdgesListGraph;
import graph.Graph;
import graph.MatrixGraph;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid arguments");
            System.out.println("Usage : javafx/awt matrix/edges <fileName>");
            return;
        }
        DrawingApi drawingApi = getDrawingApi(args[0]);
        Graph graph = getGraph(drawingApi, args[1]);
        graph.readGraphFromFile(args[2]);
        graph.drawGraph();
    }

    private static DrawingApi getDrawingApi(String apiType) {
        if (apiType.equals("javafx")) {
            return new DrawingJavaFx();
        }
        if (apiType.equals("awt")) {
            return new DrawingAwt();
        }
        throw new IllegalArgumentException("Unsupported drawing api type : " + apiType);
    }

    private static Graph getGraph(DrawingApi drawingApi, String graphType) {
        if (graphType.equals("matrix")) {
            return new MatrixGraph(drawingApi);
        }
        if (graphType.equals("edges")) {
            return new EdgesListGraph(drawingApi);
        }
        throw new IllegalArgumentException("Unsupported drawing graph type : " + graphType);
    }

}
