package dao;

import com.mongodb.rx.client.Success;
import http.MarketClient;
import model.Stocks;
import model.User;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountDao implements AccountDao {
    private final MarketClient marketClient;
    private final Map<Long, User> users = new HashMap<>();

    public InMemoryAccountDao(MarketClient marketClient) {
        this.marketClient = marketClient;
    }

    @Override
    public Observable<Success> addUser(long id) {
        if (users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " already exists"));
        }
        users.put(id, new User(id, 0));
        return Observable.just(Success.SUCCESS);
    }

    @Override
    public Observable<Success> addMoney(long id, int count) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }
        users.get(id).addMoney(count);
        return Observable.just(Success.SUCCESS);
    }

    @Override
    public Observable<Stocks> getUserStocksInfo(long id) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }
        return Observable.from(users.get(id).getStocks()).map(this::updateStocksPrice);
    }

    @Override
    public Observable<Integer> getAllMoney(long id) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }
        User user = users.get(id);
        return Observable.from(user.getStocks())
                .map(this::updateStocksPrice)
                .map(Stocks::getPrice)
                .defaultIfEmpty(0)
                .reduce(Integer::sum)
                .map(x -> x + user.getMoney());
    }

    @Override
    public Observable<Success> buyStocks(long id, String companyName, int count) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }

        try {
            int price = marketClient.getStocksPrice(companyName);
            int availableCount = marketClient.getStocksCount(companyName);
            if (availableCount < count) {
                return Observable.error(new IllegalArgumentException("Not enough stocks in market"));
            }
            users.get(id).buyStocks(companyName, price, count);
            marketClient.buyStocks(companyName, count);
            return Observable.just(Success.SUCCESS);
        } catch (IllegalArgumentException e) {
            return Observable.error(e);
        }
    }

    @Override
    public Observable<Success> sellStocks(long id, String companyName, int count) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }

        try {
            int price = marketClient.getStocksPrice(companyName);
            users.get(id).sellStocks(companyName, price, count);
            marketClient.sellStocks(companyName, count);
            return Observable.just(Success.SUCCESS);
        } catch (IllegalArgumentException e) {
            return Observable.error(e);
        }
    }

    private Stocks updateStocksPrice(Stocks stocks) {
        return stocks.changePrice(marketClient.getStocksPrice(stocks.getCompanyName()));
    }
}
