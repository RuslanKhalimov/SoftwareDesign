package search;

public class SearchRequest {
    private final SearchEngine searchEngine;
    private final String queryText;

    public SearchRequest(SearchEngine searchEngine, String queryText) {
        this.searchEngine = searchEngine;
        this.queryText = queryText;
    }

    public SearchEngine getSearchEngine() {
        return searchEngine;
    }

    public String getQueryText() {
        return queryText;
    }
}
