package report.http;

import dao.FitnessCenterDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.EventType;
import model.SubscriptionReport;
import model.TurnstileEvent;
import rx.Observable;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static utils.HttpRequestUtils.*;

public class RxNettyHttpReportServer implements ReportHttpServer {
    private final Map<Long, SubscriptionReport> reports = new HashMap<>();

    public RxNettyHttpReportServer(FitnessCenterDao dao) {
        loadEvents(dao);
    }

    private void loadEvents(FitnessCenterDao dao) {
        dao.getEvents()
                .toSortedList()
                .flatMap(events -> {
                    TurnstileEvent previousEvent = null;
                    for (TurnstileEvent event : events) {
                        if (event.getEventType() == EventType.ENTER) {
                            previousEvent = event;
                        } else if (previousEvent != null) {
                                addVisit(
                                        event.getSubscriptionId(),
                                        Duration.between(previousEvent.getEventTimestamp(), event.getEventTimestamp())
                                );
                        }
                    }
                    return Observable.empty();
                })
                .subscribe();
    }

    private void addVisit(long id, Duration duration) {
        SubscriptionReport report = reports.getOrDefault(id, new SubscriptionReport());
        reports.put(id, report.addVisit(duration));
    }

    @Override
    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);
        if (path.equals("add_visit")) {
            return addVisit(request);
        }
        if (path.equals("get_subscription_report")) {
            return getSubscriptionReport(request);
        }
        if (path.equals("get_total_report")) {
            return getTotalReport(request);
        }
        return Observable.just("Unsupported request : " + path);
    }

    private <T> Observable<String> addVisit(HttpServerRequest<T> request) {
        long id = getLongParam(request, "id");
        Duration visitDuration = getDurationParam(request, "duration");

        addVisit(id, visitDuration);

        return Observable.just("Visit added by subscription with id=" + id + " added");
    }

    private <T> Observable<String> getSubscriptionReport(HttpServerRequest<T> request) {
        long id = getLongParam(request, "id");

        if (reports.containsKey(id)) {
            return Observable.just(reports.get(id).toString());
        } else {
            return Observable.just("Subscription with id=" + id + " not exists");
        }
    }

    private <T> Observable<String> getTotalReport(HttpServerRequest<T> request) {
        SubscriptionReport totalReport = new SubscriptionReport();
        for (SubscriptionReport report : reports.values()) {
            totalReport = totalReport.mergeReports(report);
        }
        return Observable.just(totalReport.toString());
    }

}
