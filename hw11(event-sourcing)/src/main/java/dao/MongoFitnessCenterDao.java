package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import model.EventType;
import model.TurnstileEvent;
import model.Subscription;
import org.bson.Document;
import rx.Observable;

import java.time.LocalDateTime;

public class MongoFitnessCenterDao implements FitnessCenterDao {
    protected final MongoCollection<Document> subscriptions;
    protected final MongoCollection<Document> events;

    public MongoFitnessCenterDao(MongoCollection<Document> subscription, MongoCollection<Document> events) {
        this.subscriptions = subscription;
        this.events = events;
    }

    @Override
    public Observable<Subscription> getSubscriptions(long subscriptionId) {
        return subscriptions
                .find(Filters.eq("id", subscriptionId))
                .toObservable()
                .map(Subscription::new)
                .sorted();
    }

    @Override
    public Observable<Subscription> getLatestVersionSubscription(long subscriptionId) {
        return getSubscriptions(subscriptionId).last();
    }

    @Override
    public Observable<Success> createSubscription(long id, LocalDateTime subscriptionEnd) {
        return subscriptions
                .insertOne(new Subscription(id, subscriptionEnd).toDocument());
    }

    @Override
    public Observable<Success> renewSubscription(long id, LocalDateTime newSubscriptionEnd) {
        return getSubscriptions(id)
                .isEmpty()
                .flatMap(isEmpty -> {
                    if (isEmpty) {
                        return Observable.error(new IllegalArgumentException("Subscription with id=" + id + " not exists"));
                    } else {
                        return createSubscription(id, newSubscriptionEnd);
                    }
                });
    }

    @Override
    public Observable<Success> addEvent(TurnstileEvent event) {
        return subscriptions
                .find(Filters.eq("id", event.getSubscriptionId()))
                .toObservable()
                .map(Subscription::new)
                .defaultIfEmpty(null)
                .flatMap(subscription -> {
                    if (subscription == null) {
                        return Observable.error(new IllegalArgumentException("Subscription with id=" + event.getSubscriptionId() + " not exists"));
                    }
                    if (event.getEventType() == EventType.EXIT ||
                            subscription.getSubscriptionEnd().isAfter(event.getEventTimestamp())) {
                        return events.insertOne(event.toDocument());
                    } else {
                        return Observable.error(new IllegalArgumentException("Subscription expired"));
                    }
                });
    }

    @Override
    public Observable<TurnstileEvent> getEvents() {
        return events.find().toObservable().map(TurnstileEvent::new);
    }
}
