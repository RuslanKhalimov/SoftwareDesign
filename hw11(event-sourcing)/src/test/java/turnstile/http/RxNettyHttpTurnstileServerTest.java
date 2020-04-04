package turnstile.http;

import com.mongodb.rx.client.Success;
import dao.FitnessCenterDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import utils.LocalDateTimeUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RxNettyHttpTurnstileServerTest {
    @Mock
    private FitnessCenterDao dao;

    private RxNettyHttpTurnstileServer turnstileServer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(dao.addEvent(any()))
                .thenReturn(Observable.just(Success.SUCCESS));
        turnstileServer = new RxNettyHttpTurnstileServer(dao);
    }

    @Test
    public void testEnter() {
        LocalDateTime now = LocalDateTime.now();

        Map<String, List<String>> params = new HashMap<>();
        params.put("id", Collections.singletonList("0"));
        params.put("timestamp", Collections.singletonList(now.format(LocalDateTimeUtils.FORMATTER)));
        assertThat(turnstileServer.enter(params).toBlocking().single())
                .isEqualTo("SUCCESS");
    }

    @Test
    public void testDoubleEnter() {
        LocalDateTime now = LocalDateTime.now();

        Map<String, List<String>> params = new HashMap<>();
        params.put("id", Collections.singletonList("0"));
        params.put("timestamp", Collections.singletonList(now.format(LocalDateTimeUtils.FORMATTER)));
        turnstileServer.enter(params);

        params.put("timestamp", Collections.singletonList(now.plusMinutes(1).format(LocalDateTimeUtils.FORMATTER)));
        assertThat(turnstileServer.enter(params).toBlocking().single())
                .isEqualTo("You already in fitness center");

    }

    @Test
    public void testExit() {
        LocalDateTime now = LocalDateTime.now();

        Map<String, List<String>> params = new HashMap<>();
        params.put("id", Collections.singletonList("0"));
        params.put("timestamp", Collections.singletonList(now.format(LocalDateTimeUtils.FORMATTER)));
        turnstileServer.enter(params);

        params.put("timestamp", Collections.singletonList(now.plusMinutes(1).format(LocalDateTimeUtils.FORMATTER)));
        assertThat(turnstileServer.exit(params).toBlocking().single())
                .isEqualTo("SUCCESS");
    }

    @Test
    public void testExitWithoutEnter() {
        LocalDateTime now = LocalDateTime.now();

        Map<String, List<String>> params = new HashMap<>();
        params.put("id", Collections.singletonList("0"));
        params.put("timestamp", Collections.singletonList(now.format(LocalDateTimeUtils.FORMATTER)));
        assertThat(turnstileServer.exit(params).toBlocking().single())
                .isEqualTo("You are not in fitness center");
    }

    @Test
    public void testExitBeforeEnter() {
        LocalDateTime now = LocalDateTime.now();

        Map<String, List<String>> params = new HashMap<>();
        params.put("id", Collections.singletonList("0"));
        params.put("timestamp", Collections.singletonList(now.format(LocalDateTimeUtils.FORMATTER)));
        turnstileServer.enter(params);

        params.put("timestamp", Collections.singletonList(now.minusMinutes(1).format(LocalDateTimeUtils.FORMATTER)));
        assertThat(turnstileServer.exit(params).toBlocking().single())
                .isEqualTo("Invalid timestamp (exit before enter)");
    }

}