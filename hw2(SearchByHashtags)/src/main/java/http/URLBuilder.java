package http;

public class URLBuilder {
    private static final String BASE_URL = "https://api.vk.com/method/";
    private static final String METHOD_NAME = "newsfeed.search";
    private static final String ACCESS_TOKEN = System.getenv("vk-api-service-token");
    private static final String API_VERSION = "5.102";
    private static final String HASH_TAG_SYMBOL = "%23";

    private String hashTagText = "";
    private long startTime = 0;
    private String startFrom;

    public URLBuilder setHashTagText(String hashTagText) {
        this.hashTagText = hashTagText;
        return this;
    }

    public URLBuilder setStartFrom(String startFrom) {
        this.startFrom = startFrom;
        return this;
    }

    public URLBuilder setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public String build() {
        return BASE_URL + METHOD_NAME + "?" +
                String.format("q=%s%s&", HASH_TAG_SYMBOL, hashTagText) +
                String.format("start_time=%d&", startTime) +
                String.format("access_token=%s&", ACCESS_TOKEN) +
                String.format("v=%s", API_VERSION) +
                (startFrom == null ? "" : String.format("&start_from=%s", startFrom));
    }

}
