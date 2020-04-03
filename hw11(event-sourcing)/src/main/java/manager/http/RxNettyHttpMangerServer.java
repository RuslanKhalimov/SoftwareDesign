package manager.http;

import dao.FitnessCenterDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

import java.time.LocalDateTime;
import java.util.Objects;

import static utils.HttpRequestUtils.*;

public class RxNettyHttpMangerServer implements ManagerHttpServer {
    private final FitnessCenterDao dao;

    public RxNettyHttpMangerServer(FitnessCenterDao dao) {
        this.dao = dao;
    }

    @Override
    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);
        if (path.equals("create_subscription")) {
            return createSubscription(request);
        }
        if (path.equals("get_subscription")) {
            return getSubscription(request);
        }
        if (path.equals("renew_subscription")) {
            return renewSubscription(request);
        }
        return Observable.just("Unsupported request : " + path);
    }

    private <T> Observable<String> createSubscription(HttpServerRequest<T> request) {
        long id = getLongParam(request, "id");
        LocalDateTime subscriptionEnd = getLocalDateTimeParam(request, "subscription_end");

        return dao
                .createSubscription(id, subscriptionEnd)
                .map(Objects::toString)
                .onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getSubscription(HttpServerRequest<T> request) {
        long id = Long.parseLong(getQueryParam(request, "id"));

        return dao
                .getLatestVersionSubscription(id)
                .map(Objects::toString);
    }

    private <T> Observable<String> renewSubscription(HttpServerRequest<T> request) {
        long id = getLongParam(request, "id");
        LocalDateTime subscriptionEnd = getLocalDateTimeParam(request, "subscription_end");

        return dao.renewSubscription(id, subscriptionEnd)
                .map(Objects::toString)
                .onErrorReturn(Throwable::getMessage);
    }
}
