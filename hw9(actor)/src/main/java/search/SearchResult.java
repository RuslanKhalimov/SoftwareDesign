package search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResult {
    private final Map<SearchEngine, List<SearchResultItem>> searchEngineResults;

    public SearchResult() {
        searchEngineResults = new HashMap<>();
    }

    public SearchResult(SearchEngine searchEngine, List<SearchResultItem> searchResultItems) {
        this();
        searchEngineResults.put(searchEngine, searchResultItems);
    }

    public void mergeSearchResults(SearchResult searchResult) {
        searchEngineResults.putAll(searchResult.getSearchEngineResults());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[\n");
        for (SearchEngine searchEngine : searchEngineResults.keySet()) {
            StringBuilder items = new StringBuilder();
            for (SearchResultItem item : searchEngineResults.get(searchEngine)) {
                items.append("    ").append(item.toString()).append("\n");
            }
            result.append("{\n")
                    .append("  search-engine : \"").append(searchEngine).append("\",\n")
                    .append("  items : [\n")
                    .append(items.toString())
                    .append("  ]\n")
                    .append("}");
        }
        return result.append("]").toString();
    }

    public Map<SearchEngine, List<SearchResultItem>> getSearchEngineResults() {
        return searchEngineResults;
    }
}
