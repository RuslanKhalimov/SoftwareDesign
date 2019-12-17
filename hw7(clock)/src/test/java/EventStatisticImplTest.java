import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EventStatisticImplTest {
    private SetableClock clock;
    private EventStatistic eventStatistic;

    @Before
    public void setUp() {
        clock = new SetableClock(Instant.now());
        eventStatistic = new EventStatisticImpl(clock);
    }

    @Test
    public void testStatisticByNonExistingName() {
        assertThat(eventStatistic.getEventStatisticByName("Event")).isZero();
    }

    @Test
    public void testStatisticByName() {
        eventStatistic.incEvent("Event");
        eventStatistic.incEvent("Event");
        eventStatistic.incEvent("AnotherEvent");

        assertThat(eventStatistic.getEventStatisticByName("Event"))
                .isEqualTo(1.0 / 30);
    }

    @Test
    public void testOldEvent() {
        eventStatistic.incEvent("Event");
        clock.plus(1, ChronoUnit.HOURS);

        assertThat(eventStatistic.getEventStatisticByName("Event")).isZero();
    }

    @Test
    public void testAllStatistic() {
        eventStatistic.incEvent("Event1");
        clock.plus(30, ChronoUnit.MINUTES);

        eventStatistic.incEvent("Event2");
        eventStatistic.incEvent("Event2");
        clock.plus(30, ChronoUnit.MINUTES);

        eventStatistic.incEvent("Event3");

        assertThat(eventStatistic.getAllEventStatistic())
                .containsOnlyKeys("Event2", "Event3");
        assertThat(eventStatistic.getAllEventStatistic())
                .containsEntry("Event2", 1.0 / 30);
        assertThat(eventStatistic.getAllEventStatistic())
                .containsEntry("Event3", 1.0 / 60);
    }

}
