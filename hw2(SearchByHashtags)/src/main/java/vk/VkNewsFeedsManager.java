package vk;

import time.TimeUtils;

import java.util.List;

public class VkNewsFeedsManager {
    private final VkClient client;

    public VkNewsFeedsManager(VkClient client) {
        this.client = client;
    }

    public int[] getHashTagOccurrence(String hashTagText, int timeInterval) {
        if (timeInterval < 0 || timeInterval > 24) {
            throw new IllegalArgumentException("time interval should be from 0 to 24");
        }

        List<VkNewsFeed> newsFeeds = client.getNewsFeeds(hashTagText, timeInterval);

        long currentTime = TimeUtils.getCurrentTimeInSeconds();
        int result[] = new int[timeInterval];
        for (VkNewsFeed newsFeed : newsFeeds) {
            int index = (int) ((currentTime - newsFeed.getDate().getTime()) / TimeUtils.SECONDS_IN_HOUR);
            result[index]++;
        }
        return result;
    }

}
