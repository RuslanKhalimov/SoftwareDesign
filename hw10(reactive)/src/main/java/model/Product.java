package model;

import org.bson.Document;

public class Product {
    private final long id;
    private final String name;
    private final Currency currency;
    private final double price;

    public Product(Document document) {
        this(
                document.getLong("id"),
                document.getString("name"),
                Currency.valueOf(document.getString("currency")),
                document.getDouble("price")
        );
    }

    public Product(long id, String name, Currency currency, double price) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.price = price;
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("name", name)
                .append("currency", currency.toString())
                .append("price", price);
    }

    public Product convertCurrency(Currency otherCurrency) {
        return new Product(id, name, otherCurrency, price * currency.getMultiplier(otherCurrency));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product {\n" +
                "  id : " + id + ",\n" +
                "  name : " + name + ",\n" +
                "  currency : " + currency + ",\n" +
                "  price : " + price + "\n" +
                "}";
    }
}
