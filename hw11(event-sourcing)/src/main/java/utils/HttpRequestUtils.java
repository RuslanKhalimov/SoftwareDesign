package utils;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;

import java.time.Duration;
import java.time.LocalDateTime;

import static utils.LocalDateTimeUtils.fromString;

public class HttpRequestUtils {
    public static <T> String getQueryParam(HttpServerRequest<T> request, String param) {
        return request.getQueryParameters().get(param).get(0);
    }

    public static <T> long getLongParam(HttpServerRequest<T> request, String param) {
        return Long.parseLong(getQueryParam(request, param));
    }

    public static <T> LocalDateTime getLocalDateTimeParam(HttpServerRequest<T> request, String param) {
        return fromString(getQueryParam(request, param));
    }

    public static <T> Duration getDurationParam(HttpServerRequest<T> request, String param) {
        return Duration.parse(getQueryParam(request, param));
    }

}
