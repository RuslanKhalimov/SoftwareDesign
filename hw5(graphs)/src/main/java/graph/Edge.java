package graph;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

public class Edge {
    @NotNull
    private final Vertex from;
    @NotNull
    private final Vertex to;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Edge)) return false;
        Edge edge = (Edge) other;
        return Objects.equals(from, edge.from) &&
                Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(),
                "Edge {",
                "  from: " + from.toString() + ",",
                "  to: " + to.toString(),
                "}"
        );
    }

}
