package profiler;

import model.MethodCallStatistics;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Profiler {
    private final static Clock clock = Clock.systemDefaultZone();

    private final static Map<String, MethodCallStatistics> statistics = new HashMap<>();
    private final static Stack<Instant> callsInProgress = new Stack<>();

    public static void methodIn(String methodName) {
        callsInProgress.push(clock.instant());
    }

    public static void methodOut(String methodName) {
        Duration callDuration = Duration.between(callsInProgress.pop(), clock.instant());
        statistics.putIfAbsent(methodName, new MethodCallStatistics());
        statistics.get(methodName).addNewCall(callDuration);
    }

    public static void printStatistics() {
        System.out.println("Statistics :");
        for (String methodName : statistics.keySet()) {
            System.out.println();
            System.out.println("Method : " + methodName);
            System.out.println(statistics.get(methodName));
        }
    }

}
