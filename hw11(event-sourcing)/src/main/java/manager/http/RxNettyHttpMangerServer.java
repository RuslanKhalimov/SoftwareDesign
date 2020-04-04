package manager.http;

import dao.FitnessCenterDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
            return createSubscription(request.getQueryParameters());
        }
        if (path.equals("get_subscription")) {
            return getSubscription(request.getQueryParameters());
        }
        if (path.equals("renew_subscription")) {
            return renewSubscription(request.getQueryParameters());
        }
        return Observable.just("Unsupported request : " + path);
    }

    <T> Observable<String> createSubscription(Map<String, List<String>> params) {
        long id = getLongParam(params, "id");
        LocalDateTime subscriptionEnd = getLocalDateTimeParam(params, "subscription_end");

        return dao
                .createSubscription(id, subscriptionEnd)
                .map(Objects::toString)
                .onErrorReturn(Throwable::getMessage);
    }

    <T> Observable<String> getSubscription(Map<String, List<String>> params) {
        long id = Long.parseLong(getQueryParam(params, "id"));

        return dao
                .getLatestVersionSubscription(id)
                .map(Objects::toString);
    }

    <T> Observable<String> renewSubscription(Map<String, List<String>> params) {
        long id = getLongParam(params, "id");
        LocalDateTime subscriptionEnd = getLocalDateTimeParam(params, "subscription_end");

        return dao.renewSubscription(id, subscriptionEnd)
                .map(Objects::toString)
                .onErrorReturn(Throwable::getMessage);
    }
}
