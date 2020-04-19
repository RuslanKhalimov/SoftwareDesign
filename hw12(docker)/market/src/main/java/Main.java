import io.reactivex.netty.protocol.http.server.HttpServer;
import http.RxNettyMarketHttpServer;
import rx.Observable;

import static dao.DaoUtils.createDao;

public class Main {
    public static void main(String[] args) {
        RxNettyMarketHttpServer server = new RxNettyMarketHttpServer(createDao());

        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response = server.getResponse(req);
                    return resp.writeString(response.map(r -> r + System.lineSeparator()));
                })
                .awaitShutdown();
    }
}
