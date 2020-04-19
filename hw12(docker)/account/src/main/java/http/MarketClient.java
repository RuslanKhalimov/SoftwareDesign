package http;

public interface MarketClient {
    void buyStocks(String companyName, int count);

    void sellStocks(String companyName, int count);

    int getStocksPrice(String companyName);

    int getStocksCount(String companyName);
}
