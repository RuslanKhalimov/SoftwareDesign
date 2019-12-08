package graph;

import drawing.DrawingApi;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class EdgesListGraph extends Graph {
    private List<Edge> edges;

    public EdgesListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    protected void doDrawGraph() {
        edges.forEach(this::drawEdge);
    }

    @Override
    public void readGraph(BufferedReader reader) {
        edges = reader.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> Arrays.stream(line.split("\\s"))
                        .map(x -> new Vertex(Integer.parseInt(x)))
                        .collect(Collectors.toList()))
                .map(list -> {
                    if (list.size() != 2) {
                        throw new IllegalArgumentException("Incorrect input");
                    }
                    return new Edge(list.get(0), list.get(1));
                })
                .collect(Collectors.toList());
    }

    @Override
    protected int getGraphSize() {
        Set<Integer> ids = new HashSet<>();
        for (Edge edge : edges) {
            ids.add(edge.getFrom().getId());
            ids.add(edge.getTo().getId());
        }
        return ids.size();
    }

}
