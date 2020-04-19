package http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class MarketHttpClient implements MarketClient {
    @Override
    public void buyStocks(String companyName, int count) {
        String response = sendRequest("buy_stocks", Map.ofEntries(
                Map.entry("company_name", companyName),
                Map.entry("count", count)
        ));
        if (!response.equals("SUCCESS")) {
            throw new IllegalArgumentException(response);
        }
    }

    @Override
    public void sellStocks(String companyName, int count) {
        String response = sendRequest("add_stocks", Map.ofEntries(
                Map.entry("company_name", companyName),
                Map.entry("count", count)
        ));
        if (!response.equals("SUCCESS")) {
            throw new IllegalArgumentException(response);
        }
    }

    @Override
    public int getStocksPrice(String companyName) {
        String response = sendRequest("get_stocks_price", Map.ofEntries(Map.entry("company_name", companyName)));
        try {
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(response);
        }
    }

    @Override
    public int getStocksCount(String companyName) {
        String response = sendRequest("get_stocks_count", Map.ofEntries(Map.entry("company_name", companyName)));
        try {
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(response);
        }
    }

    private String sendRequest(String path, Map<String, Object> parameters) {
        String requestString = "http://localhost:8080/" + path + "?" + parameters.keySet().stream()
                .map(param -> param + "=" + parameters.get(param))
                .reduce((s1, s2) -> s1 + "&" + s2)
                .get();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(requestString))
                    .GET()
                    .build();
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body().trim();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
