package manager;

import dao.DaoUtils;
import io.reactivex.netty.protocol.http.server.HttpServer;
import manager.http.ManagerHttpServer;
import manager.http.RxNettyHttpMangerServer;
import rx.Observable;

public class Main {
    public static void main(String[] args) {
        ManagerHttpServer server = new RxNettyHttpMangerServer(DaoUtils.createDao());

        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response = server.getResponse(req);
                    return resp.writeString(response.map(r -> r + System.lineSeparator()));
                })
                .awaitShutdown();
    }
}
