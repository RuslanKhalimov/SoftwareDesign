package vk;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

import http.URLReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parser.ResponseParser;
import utils.TestWithResources;

import java.util.List;

public class VkClientIntegrationTest extends TestWithResources {
    private final String fileExtension = ".json";

    @Mock
    private URLReader urlReader;

    private VkClient client;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        client = new VkClient(urlReader, new ResponseParser());
    }

    @Test
    public void testZeroTimeInterval() throws Exception {
        when(urlReader.readAsText(anyString()))
                .thenReturn(readFromResources("zero_interval", fileExtension));

        List<VkNewsFeed> newsFeeds = client.getNewsFeeds("hashTag", 0);
        assertThat(newsFeeds).isEmpty();
    }

    @Test
    public void testOneJson() throws Exception {
        when(urlReader.readAsText(anyString()))
                .thenReturn(readFromResources("one_json", fileExtension));

        List<VkNewsFeed> newsFeeds = client.getNewsFeeds("messi", 24);
        assertThat(newsFeeds).containsExactlyInAnyOrder(
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
    }

    @Test
    public void testTwoJsons() throws Exception {
        when(urlReader.readAsText(not(contains("&start_from="))))
                .thenReturn(readFromResources("two_jsons1", fileExtension));
        when(urlReader.readAsText(contains("&start_from=30/-184783437_1583")))
                .thenReturn(readFromResources("two_jsons2", fileExtension));

        List<VkNewsFeed> newsFeeds = client.getNewsFeeds("messi", 24);
        assertThat(newsFeeds).containsExactlyInAnyOrder(
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
    }

}
