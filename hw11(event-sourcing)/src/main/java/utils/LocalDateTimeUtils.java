package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtils {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDateTime fromString(String localDateTime) {
        return LocalDateTime.parse(localDateTime, LocalDateTimeUtils.FORMATTER);
    }
}
