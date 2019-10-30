import http.URLReader;
import parser.ResponseParser;
import time.TimeUtils;
import vk.VkClient;
import vk.VkNewsFeedsManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Application {
    private final static String DATE_FORMAT = "MMM d, yyyy HH:mm a";
    private final static DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT, new Locale("En"));
    private final static VkClient client = new VkClient(new URLReader(), new ResponseParser());
    private final static VkNewsFeedsManager manager = new VkNewsFeedsManager(client);

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Incorrect number of arguments");
            System.err.println("Expected <hashTagText> <timeInterval>");
            return;
        }

        String hashTagText = args[0];
        int timeInterval;
        try {
            timeInterval = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Expected number : time interval");
            return;
        }

        try {
            int result[] = manager.getHashTagOccurrence(hashTagText, timeInterval);
            for (int i = 0; i < timeInterval; i++) {
                String toPrint = String.format(
                        "In time interval from %s till %s was %d feeds with hashtag #%s",
                        dateFormatter.format(new Date(TimeUtils.getPastTime(i + 1))),
                        dateFormatter.format(new Date(TimeUtils.getPastTime(i))),
                        result[i],
                        hashTagText);

                System.out.println(toPrint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
