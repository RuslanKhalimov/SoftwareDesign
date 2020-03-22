package dao;

import model.Product;
import model.User;
import rx.Observable;

public interface ReactiveDao {
    Observable<Boolean> registerUser(User user);

    Observable<Boolean> addProduct(Product product);

    Observable<Product> getProductsForUser(long userId);

    Observable<User> getUsers();
}
