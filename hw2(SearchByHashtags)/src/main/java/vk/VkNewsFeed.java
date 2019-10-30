package vk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class VkNewsFeed {
    private final long id;
    private final String content;
    private final Date date;

    private final String DATE_FORMAT = "MMM d, yyyy HH:mm a";
    private final DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT, new Locale("En"));

    public VkNewsFeed(long id, String content, long date) {
        this.id = id;
        this.content = content;
        this.date = new Date(date);
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "VkPost {" +
                "  id: " + id +
                "  content: " + content +
                "  date: " + dateFormatter.format(date) +
                "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        VkNewsFeed otherFeed = (VkNewsFeed) other;
        return id == otherFeed.id &&
                content.equals(otherFeed.content) &&
                date.equals(otherFeed.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, date);
    }

}
