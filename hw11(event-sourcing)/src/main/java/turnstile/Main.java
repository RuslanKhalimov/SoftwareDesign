package turnstile;

import dao.DaoUtils;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;
import turnstile.http.RxNettyHttpTurnstileServer;
import turnstile.http.TurnstileHttpServer;

public class Main {
    public static void main(String[] args) {
        TurnstileHttpServer server = new RxNettyHttpTurnstileServer(DaoUtils.createDao());

        HttpServer
                .newServer(8082)
                .start((req, resp) -> {
                    Observable<String> response = server.getResponse(req);
                    return resp.writeString(response.map(r -> r + System.lineSeparator()));
                })
                .awaitShutdown();
    }
}
