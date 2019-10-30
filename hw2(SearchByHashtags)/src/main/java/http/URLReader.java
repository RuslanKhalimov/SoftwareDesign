package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

public class URLReader {

    public String readAsText(String sourceUrl) {
        URL url = toUrl(sourceUrl);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed url: " + url);
        }
    }

}
