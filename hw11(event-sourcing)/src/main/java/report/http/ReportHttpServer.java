package report.http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

public interface ReportHttpServer {
    <T> Observable<String> getResponse(HttpServerRequest<T> request);
}
