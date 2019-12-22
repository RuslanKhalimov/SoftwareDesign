package model;

import java.time.Duration;

public class MethodCallStatistics {
    private Duration callsDuration;
    private int callsCount;

    public MethodCallStatistics() {
        this(Duration.ofMillis(0), 0);
    }

    public MethodCallStatistics(Duration callDuration, int callsCount) {
        this.callsDuration = callDuration;
        this.callsCount = callsCount;
    }

    public int getCallsCount() {
        return callsCount;
    }

    public Duration getTotalCallsDuration() {
        return callsDuration;
    }

    public Duration getAverageCallsDuration() {
        return callsDuration.dividedBy(callsCount);
    }

    public void addNewCall(Duration duration) {
        callsDuration = callsDuration.plus(duration);
        callsCount++;
    }

    @Override
    public String toString() {
        return String.format("Calls count : %d\n", getCallsCount()) +
                String.format("Total calls duration : %d mills\n", getTotalCallsDuration().toMillis()) +
                String.format("Average calls duration : %d mills", getAverageCallsDuration().toMillis());
    }

}
