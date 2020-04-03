package dao;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;

public class DaoUtils {
    public static FitnessCenterDao createDao() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("fitness-center");
        MongoCollection<Document> subscriptions = database.getCollection("subscriptions");
        MongoCollection<Document> events = database.getCollection("events");
        return new MongoFitnessCenterDao(subscriptions, events);
    }
}
