package report.http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.SubscriptionReport;
import rx.Observable;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static utils.HttpRequestUtils.*;

public class RxNettyHttpReportServer implements ReportHttpServer {
    private final Map<Long, SubscriptionReport> reports = new HashMap<>();

    @Override
    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        System.out.println(request.toString());
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
        System.out.println(request.toString());
        long id = getLongParam(request, "id");
        Duration visitDuration = getDurationParam(request, "duration");

        SubscriptionReport report = reports.getOrDefault(id, new SubscriptionReport());
        reports.put(id, report.addVisit(visitDuration));

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
