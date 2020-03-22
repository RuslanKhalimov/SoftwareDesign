package model;

import org.bson.Document;

public class User {
    private final long id;
    private final String login;
    private final Currency currency;

    public User(Document document) {
        this(
                document.getLong("id"),
                document.getString("login"),
                Currency.valueOf(document.getString("currency"))
        );
    }

    public User(long id, String login, Currency currency) {
        this.id = id;
        this.login = login;
        this.currency = currency;
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("login", login)
                .append("currency", currency.toString());
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "User {\n" +
                "  id : " + id + ",\n" +
                "  login : " + login + ",\n" +
                "  currency : " + currency + "\n" +
                "}";
    }
}
