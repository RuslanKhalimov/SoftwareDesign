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
        URLBuilder urlBuilder = new URLBuilder()
                .setHashTagText(hashTagText)
                .setStartTime(TimeUtils.getPastTimeInSeconds(timeInterval))
                .setEndTime(TimeUtils.getCurrentTimeInSeconds());

        while (true) {
            String response = urlReader.readAsText(urlBuilder.build());

            JsonObject responseJson = responseParser.parseResponse(response);
            newsFeeds.addAll(responseParser.parseNewsFeeds(responseJson));

            String startFrom = responseParser.getStartFrom(responseJson);
            if (startFrom.isEmpty())
                break;
            urlBuilder.setStartFrom(startFrom);
        }
        return newsFeeds;
    }

}
