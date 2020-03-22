package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

public interface HttpController {
    <T> Observable<String> getResponse(HttpServerRequest<T> request);
}
