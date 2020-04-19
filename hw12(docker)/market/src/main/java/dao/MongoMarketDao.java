package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import model.Stocks;
import org.bson.Document;
import rx.Observable;
import rx.functions.Func2;

public class MongoMarketDao implements MarketDao {
    private final MongoCollection<Document> companies;

    public MongoMarketDao(MongoCollection<Document> companies) {
        this.companies = companies;
    }

    @Override
    public Observable<Success> addCompany(String name, int stocksCount, int stocksPrice) {
        return companies
                .find(Filters.eq("companyName", name))
                .toObservable()
                .isEmpty()
                .flatMap(isEmpty -> {
                    if (isEmpty) {
                        return companies.insertOne(new Stocks(name, stocksCount, stocksPrice).toDocument());
                    } else {
                        return Observable.error(new IllegalArgumentException("Company with name '" + name + "' already exists"));
                    }
                });
    }

    @Override
    public Observable<Stocks> getCompanies() {
        return companies.find().toObservable().map(Stocks::new);
    }

    @Override
    public Observable<Success> addStocks(String companyName, int stocksCount) {
        return manageStocks(companyName, Stocks::add, stocksCount);
    }

    @Override
    public Observable<Stocks> getStocksInfo(String companyName) {
        return companies
                .find(Filters.eq("companyName", companyName))
                .toObservable()
                .map(Stocks::new);
    }

    @Override
    public Observable<Success> buyStocks(String companyName, int count) {
        return manageStocks(companyName, Stocks::minus, count);
    }

    @Override
    public Observable<Success> changeStocksPrice(String companyName, int newStocksPrice) {
        return manageStocks(companyName, Stocks::changePrice, newStocksPrice);
    }

    private Observable<Success> manageStocks(String companyName, Func2<Stocks, Integer, Stocks> action, int parameter) {
        return companies
                .find(Filters.eq("companyName", companyName))
                .toObservable()
                .map(Stocks::new)
                .defaultIfEmpty(null)
                .flatMap(company -> {
                    if (company == null) {
                        return Observable.error(new IllegalArgumentException("Company with name '" + companyName + "' doesn't exists"));
                    } else {
                        return companies.replaceOne(
                                Filters.eq("companyName", companyName),
                                action.call(company, parameter).toDocument())
                                .map(doc -> Success.SUCCESS);
                    }
                });
    }
}
