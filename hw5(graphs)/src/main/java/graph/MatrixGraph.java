package graph;

import drawing.DrawingApi;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixGraph extends Graph {
    private int vertexCount;
    private List<List<Boolean>> matrix;

    public MatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    protected void doDrawGraph() {
        for (int v = 0; v < matrix.size(); v++) {
            for (int u = 0; u < matrix.get(v).size(); u++) {
                if (matrix.get(v).get(u)) {
                    drawEdge(new Edge(new Vertex(v), new Vertex(u)));
                }
            }
        }
    }

    @Override
    protected void readGraph(BufferedReader reader) {
        matrix = reader.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> line.split(" "))
                .map(line -> Arrays.stream(line)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(x -> x > 0)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        vertexCount = matrix.size();
        for (List<Boolean> row : matrix) {
            if (row.size() != vertexCount) {
                throw new RuntimeException("Incorrect matrix");
            }
        }
    }

    @Override
    protected int getGraphSize() {
        return vertexCount;
    }

}
