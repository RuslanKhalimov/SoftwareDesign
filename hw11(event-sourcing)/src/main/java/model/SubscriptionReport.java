package model;

import java.time.Duration;

public class SubscriptionReport {
    private final int totalVisits;
    private final Duration totalDuration;

    public SubscriptionReport() {
        this(0, Duration.ZERO);
    }

    public SubscriptionReport(int totalVisits, Duration totalDuration) {
        this.totalVisits = totalVisits;
        this.totalDuration = totalDuration;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public SubscriptionReport addVisit(Duration duration) {
        return new SubscriptionReport(totalVisits + 1, totalDuration.plus(duration));
    }

    public SubscriptionReport mergeReports(SubscriptionReport report) {
        return new SubscriptionReport(totalVisits + report.totalVisits, totalDuration.plus(report.totalDuration));
    }

    @Override
    public String toString() {
        return "SubscriptionReport {\n" +
                "  Total visits : " + totalVisits + ",\n" +
                "  Total duration : " + totalDuration + ",\n" +
                "  Average visit time : " + totalDuration.dividedBy(totalVisits) + "\n" +
                "}\n";
    }
}
