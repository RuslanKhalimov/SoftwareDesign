package http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class URLBuilderTest {
    private static final String ACCESS_TOKEN = System.getenv("vk-api-service-token");
    private static final String HASH_TAG_SYMBOL = "%23";
    private static final String URL_TEMPLATE =
            "https://api.vk.com/method/newsfeed.search?q=%s&start_time=%d&access_token=" + ACCESS_TOKEN + "&v=5.102";

    @Test
    public void testNoParameters() {
        String url = new URLBuilder().build();
        assertThat(url).isEqualTo(String.format(URL_TEMPLATE, HASH_TAG_SYMBOL, 0));
    }

    @Test
    public void testHashTag() {
        String HASH_TAG = "hash_tag";
        String url = new URLBuilder().setHashTagText(HASH_TAG).build();
        assertThat(url).isEqualTo(String.format(URL_TEMPLATE, HASH_TAG_SYMBOL + HASH_TAG, 0));
    }

    @Test
    public void testStartTime() {
        long START_TIME = 1572280483;
        String url = new URLBuilder().setStartTime(START_TIME).build();
        assertThat(url).isEqualTo(String.format(URL_TEMPLATE, HASH_TAG_SYMBOL, START_TIME));
    }

    @Test
    public void testStartFrom() {
        String START_FROM = "30/-184783437_1583";
        String url = new URLBuilder().setStartFrom(START_FROM).build();
        assertThat(url).isEqualTo(String.format(URL_TEMPLATE, HASH_TAG_SYMBOL, 0) + "&start_from=" + START_FROM);
    }

    @Test
    public void testFullURL() {
        String HASH_TAG = "hash_tag";
        String START_FROM = "30/-184783437_1583";
        long START_TIME = 1572280483;
        String url = new URLBuilder()
                .setHashTagText(HASH_TAG)
                .setStartFrom(START_FROM)
                .setStartTime(START_TIME)
                .build();

        assertThat(url).isEqualTo(String.format(URL_TEMPLATE, HASH_TAG_SYMBOL + HASH_TAG, START_TIME) + "&start_from=" + START_FROM);
    }

}