package dao;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;

public class DaoUtils {
    public static MarketDao createDao() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("market");
        MongoCollection<Document> companies = database.getCollection("companies");
        return new MongoMarketDao(companies);
    }
}
