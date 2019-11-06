package vk;

import com.google.gson.JsonObject;
import http.URLBuilder;
import http.URLReader;
import parser.ResponseParser;
import time.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class VkClient {
    private final URLReader urlReader;
    private final ResponseParser responseParser;

    public VkClient(URLReader urlReader, ResponseParser responseParser) {
        this.urlReader = urlReader;
        this.responseParser = responseParser;
    }

    public List<VkNewsFeed> getNewsFeeds(String hashTagText, long timeInterval) {
        List<VkNewsFeed> newsFeeds = new ArrayList<>();
        String startFrom = null;
        while (startFrom == null || !startFrom.equals("")) {
            String url = new URLBuilder()
                    .setHashTagText(hashTagText)
                    .setStartTime(TimeUtils.getPastTimeInSeconds(timeInterval))
                    .setStartFrom(startFrom)
                    .build();

            String response = urlReader.readAsText(url);

            JsonObject responseJson = responseParser.parseResponse(response);
            startFrom = responseParser.getStartFrom(responseJson);

            newsFeeds.addAll(responseParser.parseNewsFeeds(responseJson));
        }
        return newsFeeds;
    }

}
