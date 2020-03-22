package dao;

import com.mongodb.rx.client.MongoCollection;
import model.Currency;
import model.Product;
import model.User;
import org.bson.Document;
import rx.Observable;

public class MongoReactiveDao implements ReactiveDao {
    private final MongoCollection<Document> users;
    private final MongoCollection<Document> products;

    public MongoReactiveDao(MongoCollection<Document> users, MongoCollection<Document> products) {
        this.users = users;
        this.products = products;
    }

    @Override
    public Observable<Boolean> registerUser(User user) {
        return insertToCollection(user.toDocument(), users);
    }

    @Override
    public Observable<Boolean> addProduct(Product product) {
        return insertToCollection(product.toDocument(), products);
    }

    @Override
    public Observable<Product> getProductsForUser(long userId) {
        return users
                .find()
                .toObservable()
                .filter(doc -> doc.getLong("id") == userId)
                .map(doc -> Currency.valueOf(doc.getString("currency")))
                .flatMap(userCurrency -> products
                        .find()
                        .toObservable()
                        .map(doc -> new Product(doc).convertCurrency(userCurrency)));
    }

    @Override
    public rx.Observable<User> getUsers() {
        users.find().toObservable().subscribe(System.out::println);
        return users
                .find()
                .toObservable()
                .map(User::new);
    }

    private Observable<Boolean> insertToCollection(Document document, MongoCollection<Document> collection) {
        return collection
                .find()
                .toObservable()
                .filter(foundDoc -> document.getLong("id").equals(foundDoc.getLong("id")))
                .singleOrDefault(null)
                .flatMap(foundDoc -> {
                    if (foundDoc == null) {
                        return collection
                                .insertOne(document)
                                .asObservable()
                                .isEmpty()
                                .map(f -> !f);
                    } else {
                        return Observable.just(false);
                    }
                });
    }
}
