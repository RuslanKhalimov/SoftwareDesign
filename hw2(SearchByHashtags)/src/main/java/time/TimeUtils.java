package time;

import java.util.Date;

public class TimeUtils {
    public static long SECONDS_IN_HOUR = 3600;
    private static long MILLISECONDS_IN_SECOND = 1000;

    public static long getCurrentTimeInSeconds() {
        return getCurrentTime() / MILLISECONDS_IN_SECOND;
    }

    public static long getPastTimeInSeconds(long hoursBefore) {
        return getPastTime(hoursBefore) / MILLISECONDS_IN_SECOND;
    }

    public static long getCurrentTime() {
        return new Date().getTime();
    }

    public static long getPastTime(long hoursBefore) {
        return getCurrentTime() - hoursBefore * SECONDS_IN_HOUR * MILLISECONDS_IN_SECOND;
    }
}
