package vk;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import time.TimeUtils;

public class VkNewsFeedsManagerTest {
    @Mock
    private VkClient client;

    private VkNewsFeedsManager manager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        manager = new VkNewsFeedsManager(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeTimeInterval() {
        manager.getHashTagOccurrence("messi", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBigTimeInterval() {
        manager.getHashTagOccurrence("messi", 25);
    }

    @Test
    public void testEmpty() {
        when(client.getNewsFeeds("messi", 0))
                .thenReturn(Collections.emptyList());

        int occurrence[] = manager.getHashTagOccurrence("messi", 0);
        assertThat(occurrence).isEmpty();
    }

    @Test
    public void testNoPosts() {
        when(client.getNewsFeeds("messi", 24))
                .thenReturn(Collections.emptyList());

        int occurrence[] = manager.getHashTagOccurrence("messi", 24);

        assertThat(occurrence).hasSize(24);
        assertThat(occurrence).containsOnly(0);
    }

    @Test
    public void testWithPosts() {
        List<VkNewsFeed> newsFeeds = Arrays.asList(
                new VkNewsFeed(
                        0,
                        "some content",
                        TimeUtils.getCurrentTimeInSeconds() - 10
                ),
                new VkNewsFeed(
                        1,
                        "another content",
                        TimeUtils.getPastTimeInSeconds(1) - 10
                )
        );

        when(client.getNewsFeeds("messi", 2))
                .thenReturn(newsFeeds);

        int occurrence[] = manager.getHashTagOccurrence("messi", 2);

        assertThat(occurrence).hasSize(2);
        assertThat(occurrence).containsOnly(1);
    }

}
