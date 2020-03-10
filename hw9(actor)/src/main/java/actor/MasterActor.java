package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;
import search.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MasterActor extends UntypedActor {
    private final Set<SearchEngine> SEARCH_ENGINES = EnumSet.allOf(SearchEngine.class);

    private final SearchClient searchClient;
    private ActorRef requestSender;
    private final SearchResult response;

    public MasterActor(SearchClient searchClient) {
        this.response = new SearchResult();
        this.searchClient = searchClient;
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof String) {
            requestSender = getSender();
            String queryText = (String) o;

            for (SearchEngine searchEngine : SEARCH_ENGINES) {
                ActorRef childActor = getContext().actorOf(Props.create(ChildActor.class, searchClient));
                childActor.tell(new SearchRequest(searchEngine, queryText), self());
            }

            getContext().setReceiveTimeout(Duration.create(1, TimeUnit.SECONDS));
        } else if (o instanceof SearchResult) {
            SearchResult searchResult = (SearchResult) o;
            response.mergeSearchResults(searchResult);
            SEARCH_ENGINES.removeAll(searchResult.getSearchEngineResults().keySet());

            if (SEARCH_ENGINES.isEmpty()) {
                requestSender.tell(response, self());
                getContext().stop(self());
            }
        } else if (o instanceof ReceiveTimeout) {
            for (SearchEngine searchEngine : SEARCH_ENGINES) {
                response.mergeSearchResults(new SearchResult(searchEngine, Collections.emptyList()));
            }

            requestSender.tell(response, self());
            getContext().stop(self());
        }
    }
}
