package search;

import java.util.ArrayList;
import java.util.List;

public class SearchStubClient implements SearchClient {
    private final int SEARCH_RESULT_BATCH_SIZE = 10;

    private final long responseDelay;

    public SearchStubClient(long responseDelay) {
        this.responseDelay = responseDelay;
    }

    @Override
    public SearchResult searchQuery(SearchRequest searchRequest) {
        List<SearchResultItem> searchResultItems = new ArrayList<>();
        for (int i = 0; i < SEARCH_RESULT_BATCH_SIZE; i++) {
            searchResultItems.add(new SearchResultItem(
                    generateUrl(i, searchRequest.getQueryText()),
                    generateTitle(i, searchRequest.getQueryText())
            ));
        }
        try {
            Thread.sleep(responseDelay);
            return new SearchResult(searchRequest.getSearchEngine(), searchResultItems);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new SearchResult(searchRequest.getSearchEngine(), searchResultItems);
        }
    }

    private String generateUrl(int i, String queryText) {
        return "https://search-result/" + queryText + "/" + i;
    }

    private String generateTitle(int i, String queryText) {
        return "Search result title #" + i + " for query \"" + queryText + "\" #";
    }
}
