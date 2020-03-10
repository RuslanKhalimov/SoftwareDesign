import actor.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import search.SearchStubClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("search");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String queryText;
            while ((queryText = reader.readLine()) != null) {
                ActorRef master = system.actorOf(Props.create(
                        MasterActor.class,
                        new SearchStubClient(500)
                ));

                Object response = ask(master, queryText, Timeout.apply(10, TimeUnit.SECONDS))
                        .toCompletableFuture()
                        .join();

                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        system.terminate();
    }
}
