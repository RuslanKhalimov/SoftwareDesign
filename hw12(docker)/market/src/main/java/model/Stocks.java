package model;

import org.bson.Document;

public class Stocks {
    private final String companyName;
    private final int count;
    private final int price;

    public Stocks(Document document) {
        this(document.getString("companyName"), document.getInteger("count"), document.getInteger("price"));
    }

    public Stocks(String companyName, int count, int price) {
        this.companyName = companyName;
        this.count = count;
        this.price = price;
    }

    public Document toDocument() {
        return new Document()
                .append("companyName", companyName)
                .append("count", count)
                .append("price", price);
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public Stocks changePrice(int newStockPrice) {
        return new Stocks(companyName, count, newStockPrice);
    }

    public Stocks add(int stocksCount) {
        return new Stocks(companyName, count + stocksCount, price);
    }

    public Stocks minus(int stocksCount) {
        if (count < stocksCount) {
            return this;
        }
        return new Stocks(companyName, count - stocksCount, price);
    }

    @Override
    public String toString() {
        return "Stocks {\n" +
                "  companyName : " + companyName + ",\n" +
                "  count : " + count + ",\n" +
                "  price : " + price + "\n" +
                "}";
    }
}
