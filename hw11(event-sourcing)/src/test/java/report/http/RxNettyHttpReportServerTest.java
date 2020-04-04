package report.http;

import dao.FitnessCenterDao;
import model.SubscriptionReport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import java.time.Duration;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class RxNettyHttpReportServerTest {
    @Mock
    private FitnessCenterDao dao;

    private RxNettyHttpReportServer reportServer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(dao.getEvents())
                .thenReturn(Observable.empty());
        reportServer = new RxNettyHttpReportServer(dao);
    }

    @Test
    public void testGetSubscriptionReport() {
        addVisits();

        Map<String, List<String>> params = new HashMap<>();
        params.put("id", Collections.singletonList("0"));
        assertThat(reportServer.getSubscriptionReport(params).toBlocking().single())
                .isEqualTo(new SubscriptionReport(2, Duration.ofHours(4)).toString());

        params.put("id", Collections.singletonList("1"));
        assertThat(reportServer.getSubscriptionReport(params).toBlocking().single())
                .isEqualTo(new SubscriptionReport(3, Duration.ofHours(6)).toString());

        params.put("id", Collections.singletonList("2"));
        assertThat(reportServer.getSubscriptionReport(params).toBlocking().single())
                .isEqualTo(new SubscriptionReport(3, Duration.ofHours(4)).toString());
    }

    @Test
    public void testGetTotalReport() {
        addVisits();

        Map<String, List<String>> params = new HashMap<>();
        assertThat(reportServer.getTotalReport(params).toBlocking().single())
                .isEqualTo(new SubscriptionReport(8, Duration.ofHours(14)).toString());
    }

    private void addVisits() {
        Map<String, List<String>> params = new HashMap<>();
        addVisit(0, 1);
        addVisit(0, 3);
        addVisit(1, 1);
        addVisit(1, 2);
        addVisit(1, 3);
        addVisit(2, 1);
        addVisit(2, 1);
        addVisit(2, 2);
    }

    private void addVisit(long id, int durationInHours) {
        Map<String, List<String>> params = new HashMap<>();
        params.put("id", Collections.singletonList(Long.toString(id)));
        params.put("duration", Collections.singletonList(Duration.ofHours(durationInHours).toString()));
        reportServer.addVisit(params);
    }
}