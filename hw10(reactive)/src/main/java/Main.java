import dao.DaoUtils;
import http.HttpController;
import http.RxNettyHttpController;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

public class Main {
    public static void main(String[] args) {
        HttpController controller = new RxNettyHttpController(DaoUtils.createReactiveDao());

        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response = controller.getResponse(req);
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }
}
