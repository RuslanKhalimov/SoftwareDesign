package model;

import org.bson.Document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final long id;
    private int money;
    private final Map<String, Stocks> stocks = new HashMap<>();

    public User(long id, int money) {
        this.id = id;
        this.money = money;
    }

    public Document toDocument() {
        Map<String, Object> document = new HashMap<>(stocks);
        document.put("id", id);
        document.put("money", money);
        return new Document(document);
    }

    public long getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int addition) {
        money += addition;
    }

    public Collection<Stocks> getStocks() {
        return stocks.values();
    }

    public void buyStocks(String companyName, int price, int count) {
        if (price * count > money) {
            throw new IllegalArgumentException("Not enough money");
        }
        Stocks current = stocks.getOrDefault(companyName, new Stocks(companyName, 0, 0));
        stocks.put(companyName, current.add(count));
        money -= price * count;
    }

    public void sellStocks(String companyName, int price, int count) {
        if (!stocks.containsKey(companyName) || stocks.get(companyName).getCount() < count) {
            throw new IllegalArgumentException("Not enough stocks");
        }
        Stocks current = stocks.get(companyName);
        stocks.put(companyName, current.minus(count));
        money += price * count;
    }
}
