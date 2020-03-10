package actor;

import akka.actor.UntypedActor;
import search.SearchClient;
import search.SearchRequest;

public class ChildActor extends UntypedActor {
    private final SearchClient searchClient;

    public ChildActor(SearchClient searchClient) {
        this.searchClient = searchClient;
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof SearchRequest) {
            SearchRequest searchRequest = (SearchRequest) o;
            getSender().tell(searchClient.searchQuery(searchRequest), self());
            getContext().stop(self());
        }
    }
}
