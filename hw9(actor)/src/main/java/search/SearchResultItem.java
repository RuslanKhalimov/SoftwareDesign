package search;

public class SearchResultItem {
    private final String url;
    private final String title;

    public SearchResultItem(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "{ url : \"" + url + "\", title : \"" + title + "\" }";
    }
}
