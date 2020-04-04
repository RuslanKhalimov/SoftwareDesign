package model;

import org.bson.Document;

import java.time.LocalDateTime;

import static utils.LocalDateTimeUtils.FORMATTER;
import static utils.LocalDateTimeUtils.fromString;

public class TurnstileEvent implements Comparable<TurnstileEvent> {
    private final long subscriptionId;
    private final EventType eventType;
    private final LocalDateTime eventTimestamp;

    public TurnstileEvent(Document document) {
        this(
                document.getLong("subscriptionId"),
                EventType.valueOf(document.getString("eventType")),
                fromString(document.getString("eventTimestamp"))
        );
    }

    public TurnstileEvent(long subscriptionId, EventType eventType, LocalDateTime eventTimestamp) {
        this.subscriptionId = subscriptionId;
        this.eventType = eventType;
        this.eventTimestamp = eventTimestamp;
    }

    public Document toDocument() {
        return new Document()
                .append("subscriptionId", subscriptionId)
                .append("eventType", eventType.toString())
                .append("eventTimestamp", eventTimestamp.format(FORMATTER));
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    @Override
    public int compareTo(TurnstileEvent other) {
        int idComparision = Long.compare(subscriptionId, other.subscriptionId);
        if (idComparision != 0) {
            return idComparision;
        }
        return eventTimestamp.compareTo(other.eventTimestamp);
    }

    @Override
    public String toString() {
        return "TurnstileEvent {\n" +
                "  subscriptionId : " + subscriptionId + ",\n" +
                "  eventType : " + eventType + ",\n" +
                "  eventTimestamp : " + eventTimestamp.format(FORMATTER) + "\n" +
                "}";
    }
}
