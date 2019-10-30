package parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import vk.VkNewsFeed;

import java.util.ArrayList;
import java.util.List;

public class ResponseParser {

    public JsonObject parseResponse(String response) {
        return new Gson().fromJson(response, JsonObject.class).getAsJsonObject("response");
    }

    public List<VkNewsFeed> parseNewsFeeds(JsonArray items) {
        List<VkNewsFeed> newsFeeds = new ArrayList<>();
        items.forEach(jsonElement -> newsFeeds.add(parseNewsFeed(jsonElement.getAsJsonObject())));

        return newsFeeds;
    }

    public JsonArray getItems(JsonObject responseJson) {
        return responseJson.getAsJsonArray("items");
    }

    public String getStartFrom(JsonObject responseJson) {
        JsonElement nextFromNode = responseJson.get("next_from");
        if (nextFromNode == null) {
            return "";
        }
        return nextFromNode.getAsString();
    }

    private VkNewsFeed parseNewsFeed(JsonObject item) {
        return new VkNewsFeed(
                item.get("id").getAsLong(),
                item.get("text").getAsString(),
                item.get("date").getAsLong());
    }

}
