package dao;

import com.mongodb.rx.client.Success;
import model.TurnstileEvent;
import model.Subscription;
import rx.Observable;

import java.time.LocalDateTime;

public interface FitnessCenterDao {
    Observable<Subscription> getSubscriptions(long subscriptionId);

    Observable<Subscription> getLatestVersionSubscription(long subscriptionId);

    Observable<Success> createSubscription(long id, LocalDateTime subscriptionEnd);

    Observable<Success> renewSubscription(long id, LocalDateTime newSubscriptionEnd);

    Observable<Success> addEvent(TurnstileEvent event);
}
