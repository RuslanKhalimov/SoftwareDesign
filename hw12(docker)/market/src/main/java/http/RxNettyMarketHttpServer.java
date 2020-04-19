package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import dao.MarketDao;
import model.Stocks;
import rx.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static utils.HttpRequestUtils.*;

public class RxNettyMarketHttpServer {
    private final MarketDao dao;

    public RxNettyMarketHttpServer(MarketDao dao) {
        this.dao = dao;
    }

    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);
        if (path.equals("add_company")) {
            return addCompany(request);
        }
        if (path.equals("get_companies")) {
            return getCompanies(request);
        }
        if (path.equals("add_stocks")) {
            return addStocks(request);
        }
        if (path.equals("get_stocks_price")) {
            return getStocksPrice(request);
        }
        if (path.equals("get_stocks_count")) {
            return getStocksCount(request);
        }
        if (path.equals("buy_stocks")) {
            return buyStocks(request);
        }
        if (path.equals("change_stocks_price")) {
            return changeStocksPrice(request);
        }
        return Observable.just("Unsupported request : " + path);
    }

    private <T> Observable<String> addCompany(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Arrays.asList("name", "stocks_count", "stocks_price"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        String name = getQueryParam(request, "name");
        int stocksCount = getIntParam(request, "stocks_count");
        int stocksPrice = getIntParam(request, "stocks_price");
        return dao.addCompany(name, stocksCount, stocksPrice).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getCompanies(HttpServerRequest<T> request) {
        return dao.getCompanies().map(Objects::toString).reduce("", (s1, s2) -> s1 + ",\n" + s2);
    }

    private <T> Observable<String> addStocks(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Arrays.asList("company_name", "count"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        String companyName = getQueryParam(request, "company_name");
        int stocksCount = getIntParam(request, "count");
        return dao.addStocks(companyName, stocksCount).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> buyStocks(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Arrays.asList("company_name", "count"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        String companyName = getQueryParam(request, "company_name");
        int count = getIntParam(request, "count");
        return dao.buyStocks(companyName, count).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getStocksPrice(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Collections.singletonList("company_name"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        String companyName = getQueryParam(request, "company_name");
        return dao.getStocksInfo(companyName).map(Stocks::getPrice).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getStocksCount(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Collections.singletonList("company_name"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        String companyName = getQueryParam(request, "company_name");
        return dao.getStocksInfo(companyName).map(Stocks::getCount).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> changeStocksPrice(HttpServerRequest<T> request) {
        Optional<String> error = checkRequestParameters(request, Arrays.asList("company_name", "new_stocks_price"));
        if (error.isPresent()) {
            return Observable.just(error.get());
        }

        String companyName = getQueryParam(request, "company_name");
        int newStocksPrice = getIntParam(request, "new_stocks_price");
        return dao.changeStocksPrice(companyName, newStocksPrice).map(Objects::toString).onErrorReturn(Throwable::getMessage);
    }
}
