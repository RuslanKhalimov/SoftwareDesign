package dao;

import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import model.EventType;
import model.Subscription;
import model.TurnstileEvent;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.LocalDateTimeUtils.fromString;

public class MongoFitnessCenterDaoTest {
    private final MongoDatabase database;
    private FitnessCenterDao dao;

    public MongoFitnessCenterDaoTest() {
        database = MongoClients.create("mongodb://localhost:27017").getDatabase("test-database");
    }

    @Before
    public void setUp() {
        MongoCollection<Document> subscriptions = database.getCollection("subscriptions");
        MongoCollection<Document> events = database.getCollection("events");
        dao = new MongoFitnessCenterDao(subscriptions, events);
    }

    @After
    public void tearDown() {
        database.getCollection("subscriptions").drop().toBlocking().single();
        database.getCollection("events").drop().toBlocking().single();
    }

    @Test
    public void renewSubscription() {
        LocalDateTime now = fromString("2020-04-05 01:15");
        LocalDateTime later = now.plusMinutes(10);
        dao.createSubscription(0, now).toBlocking().single();
        dao.renewSubscription(0, later).toBlocking().single();

        Subscription actualSubscription = dao.getLatestVersionSubscription(0).toBlocking().single();
        assertThat(actualSubscription.getId()).isEqualTo(0);
        assertThat(actualSubscription.getSubscriptionEnd()).isEqualTo(later);
    }

    @Test
    public void renewNonExistingSubscription() {
        LocalDateTime now = fromString("2020-04-05 01:15");
        LocalDateTime later = now.plusMinutes(10);
        assertThat(dao
                .renewSubscription(0, later)
                .map(Objects::toString)
                .onErrorReturn(Throwable::getMessage)
                .toBlocking()
                .single()
        )
                .isEqualTo("Subscription with id=0 not exists");
    }

    @Test
    public void addEvent() {
        LocalDateTime now = fromString("2020-04-05 01:15");
        LocalDateTime later = now.plusMinutes(10);
        dao.createSubscription(0, later).toBlocking().single();
        TurnstileEvent event = new TurnstileEvent(0, EventType.ENTER, now);
        dao.addEvent(event).toBlocking().single();
        assertThat(dao
                .getEvents()
                .map(Objects::toString)
                .toBlocking()
                .single()
        )
                .isEqualTo(event.toString());
    }

    @Test
    public void addEventWithoutSubscription() {
        LocalDateTime now = fromString("2020-04-05 01:15");
        TurnstileEvent event = new TurnstileEvent(0, EventType.ENTER, now);
        assertThat(dao
                .addEvent(event)
                .map(Objects::toString)
                .onErrorReturn(Throwable::getMessage)
                .toBlocking()
                .single()
        )
                .isEqualTo("Subscription with id=0 not exists");
    }

    @Test
    public void addEventToExpiredSubscription() {
        LocalDateTime now = fromString("2020-04-05 01:15");
        LocalDateTime later = now.plusMinutes(10);
        dao.createSubscription(0, now).toBlocking().single();
        TurnstileEvent event = new TurnstileEvent(0, EventType.ENTER, later);
        assertThat(dao
                .addEvent(event)
                .map(Objects::toString)
                .onErrorReturn(Throwable::getMessage)
                .toBlocking()
                .single()
        )
                .isEqualTo("Subscription expired");
    }
}
