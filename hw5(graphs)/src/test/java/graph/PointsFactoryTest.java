package graph;

import drawing.Point;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointsFactoryTest {
    @Test
    public void testPointsFactory() {
        PointsFactory pointsFactory = new PointsFactory(4, new Point(0, 0), 1);

        assertThat(pointsFactory.nextPoint()).isEqualTo(new Point(1, 0));
        assertThat(pointsFactory.nextPoint()).isEqualTo(new Point(0, 1));
        assertThat(pointsFactory.nextPoint()).isEqualTo(new Point(-1, 0));
        assertThat(pointsFactory.nextPoint()).isEqualTo(new Point(0, -1));
    }

    @Test
    public void testNonZeroCenter() {
        PointsFactory pointsFactory = new PointsFactory(4, new Point(10, 20), 30);

        assertThat(pointsFactory.nextPoint()).isEqualTo(new Point(40, 20));
        assertThat(pointsFactory.nextPoint()).isEqualTo(new Point(10, 50));
        assertThat(pointsFactory.nextPoint()).isEqualTo(new Point(-20, 20));
        assertThat(pointsFactory.nextPoint()).isEqualTo(new Point(10, -10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyGraph() {
        PointsFactory pointsFactory = new PointsFactory(0, new Point(0, 0), 1);
    }

}
