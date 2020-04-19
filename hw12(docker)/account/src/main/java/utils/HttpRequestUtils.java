package utils;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;

import java.util.List;
import java.util.Optional;

public class HttpRequestUtils {
    public static <T> String getQueryParam(HttpServerRequest<T> request, String param) {
        return request.getQueryParameters().get(param).get(0);
    }

    public static <T> int getIntParam(HttpServerRequest<T> request, String param) {
        return Integer.parseInt(getQueryParam(request, param));
    }

    public static <T> long getLongParam(HttpServerRequest<T> request, String param) {
        return Long.parseLong(getQueryParam(request, param));
    }

    public static <T> Optional<String> checkRequestParameters(HttpServerRequest<T> request, List<String> requiredParameters) {
        return requiredParameters.stream()
                .filter(key -> !request.getQueryParameters().containsKey(key))
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(s -> s + " parameters not found");
    }
}
