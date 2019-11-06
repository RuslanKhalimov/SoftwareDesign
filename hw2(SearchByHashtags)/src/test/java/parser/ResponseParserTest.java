package parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonObject;
import org.junit.Test;
import utils.TestWithResources;
import vk.VkNewsFeed;

import java.util.List;

public class ResponseParserTest extends TestWithResources {
    private final ResponseParser parser = new ResponseParser();
    private String fileExtension = ".json";

    @Test
    public void testExample() throws Exception {
        List<VkNewsFeed> parsed = parseNewsFeedsFromFile("response_example");

        assertThat(parsed).hasSize(2);
        assertThat(parsed).containsExactlyInAnyOrder(
                new VkNewsFeed(
                        2916,
                        "VIDEO | #Juve, #Dybala scatena i tifosi: esultanza polemica verso la tribuna Le ipotesi\nhttps://www.90min.com/it/posts/6481957-video-juve-dybala-scatena-i-tifosi-esultanza-polemica-verso-la-tribuna-le-ipotesi/partners/43892  #calcio #football  #JuveLokomotiv #ChampionsLeague",
                        1571833353
                ),
                new VkNewsFeed(
                        40009,
                        "#Dybala@fbomen \n#Juventus",
                        1571832010
                )
        );
    }

    @Test
    public void testEmpty() throws Exception {
        List<VkNewsFeed> parsed = parseNewsFeedsFromFile("empty");
        assertThat(parsed).isEmpty();
    }

    @Test
    public void testEmptyNextFrom() throws Exception {
        assertThat(parseNextFrom("response_example")).isBlank();
    }

    @Test
    public void testNextFrom() throws Exception {
        assertThat(parseNextFrom("with_next_from")).isEqualTo("30/384063108_1701");
    }

    private JsonObject parseResponseFromFile(String fileName) throws Exception {
        String response = readFromResources(fileName, fileExtension);
        return parser.parseResponse(response);
    }

    private String parseNextFrom(String fileName) throws Exception {
        JsonObject responseJson = parseResponseFromFile(fileName);
        return parser.getStartFrom(responseJson);
    }

    private List<VkNewsFeed> parseNewsFeedsFromFile(String fileName) throws Exception {
        JsonObject responseJson = parseResponseFromFile(fileName);
        return parser.parseNewsFeeds(responseJson);
    }

}
