package http;

import dao.ReactiveDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.Currency;
import model.Product;
import model.User;
import rx.Observable;

public class RxNettyHttpController implements HttpController {
    private final ReactiveDao db;

    public RxNettyHttpController(ReactiveDao db) {
        this.db = db;
    }

    @Override
    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);
        if (path.equals("register_user")) {
            return registerUser(request);
        }
        if (path.equals("add_product")) {
            return addProduct(request);
        }
        if (path.equals("get_products_for_user")) {
            return getProductsForUser(request);
        }
        if (path.equals("get_users")) {
            return getUsers(request);
        }
        return Observable.just("Incorrect request : " + path);
    }

    private <T> String getQueryParam(HttpServerRequest<T> request, String param) {
        return request.getQueryParameters().get(param).get(0);
    }

    private <T> Observable<String> registerUser(HttpServerRequest<T> request) {
        long id = Long.parseLong(getQueryParam(request, "id"));
        String login = getQueryParam(request, "login");
        Currency currency = Currency.valueOf(getQueryParam(request, "currency"));

        User user = new User(id, login, currency);
        return db.registerUser(user).map(Object::toString);
    }

    private <T> Observable<String> addProduct(HttpServerRequest<T> request) {
        long id = Long.parseLong(getQueryParam(request, "id"));
        String name = getQueryParam(request, "name");
        Currency currency = Currency.valueOf(getQueryParam(request, "currency"));
        double price = Double.parseDouble(getQueryParam(request, "price"));

        Product product = new Product(id, name, currency, price);
        return db.addProduct(product).map(Object::toString);
    }

    private <T> Observable<String> getProductsForUser(HttpServerRequest<T> request) {
        long id = Long.parseLong(getQueryParam(request, "id"));
        return db.getProductsForUser(id).map(Object::toString);
    }

    private <T> Observable<String> getUsers(HttpServerRequest<T> request) {
        return db.getUsers().map(Object::toString);
    }

}
