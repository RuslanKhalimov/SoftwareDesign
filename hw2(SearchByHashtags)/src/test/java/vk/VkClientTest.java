package vk;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import http.URLReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parser.ResponseParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VkClientTest {
    @Mock
    private URLReader urlReader;

    @Mock
    private ResponseParser responseParser;

    private VkClient client;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        client = new VkClient(urlReader, responseParser);
    }

    private void mocks(String url, String response, JsonObject responseJson, List<VkNewsFeed> newsFeeds, String startFrom) {
        when(urlReader.readAsText(url))
                .thenReturn(response);
        when(responseParser.parseResponse(response))
                .thenReturn(responseJson);
        when(responseParser.parseNewsFeeds(responseJson))
                .thenReturn(newsFeeds);
        when(responseParser.getStartFrom(responseJson))
                .thenReturn(startFrom);
    }

    private void mocks(List<VkNewsFeed> newsFeeds) {
        mocks(anyString(), "response", new JsonObject(), newsFeeds, "");
    }

    private void mocks(String url, List<VkNewsFeed> newsFeeds, String startFrom, int mockNumber) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("id", mockNumber);
        mocks(url, Integer.toString(mockNumber), responseJson, newsFeeds, startFrom);
    }

    @Test
    public void testZeroTimeInterval() {
        mocks(Collections.emptyList());

        List<VkNewsFeed> newsFeeds = client.getNewsFeeds("hashTag", 0);
        assertThat(newsFeeds).isEmpty();
    }

    @Test
    public void testOneJson() {
        List<VkNewsFeed> expectedNewsFeeds = Arrays.asList(
                new VkNewsFeed(
                        1708,
                        "Luar biasa king leo, kami salut, bangga dan bahagia, sukses selalu , from indonesia",
                        1572449412
                ),
                new VkNewsFeed(
                        1706,
                        "#Repost @foxsportsbrasil with [club87821973|@make_repost]",
                        1572449409
                )
        );

        mocks(expectedNewsFeeds);

        List<VkNewsFeed> newsFeeds = client.getNewsFeeds("messi", 24);
        assertThat(newsFeeds).containsExactlyInAnyOrderElementsOf(expectedNewsFeeds);
    }

    @Test
    public void testTwoJsons() {
        VkNewsFeed firstNewsFeed = new VkNewsFeed(
                1708,
                "Luar biasa king leo, kami salut, bangga dan bahagia, sukses selalu , from indonesia",
                1572449412
        );
        VkNewsFeed secondNewsFeed = new VkNewsFeed(
                1706,
                "#Repost @foxsportsbrasil with [club87821973|@make_repost]",
                1572449409
        );

        mocks(
                not(contains("&start_from=")),
                Collections.singletonList(firstNewsFeed),
                "30/-184783437_1583",
                1
        );
        mocks(
                contains("&start_from=30/-184783437_1583"),
                Collections.singletonList(secondNewsFeed),
                "",
                2
        );

        List<VkNewsFeed> newsFeeds = client.getNewsFeeds("messi", 24);
        assertThat(newsFeeds).containsExactlyInAnyOrder(firstNewsFeed, secondNewsFeed);
    }

}
