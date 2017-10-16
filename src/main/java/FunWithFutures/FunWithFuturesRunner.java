package FunWithFutures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Await;
import scala.concurrent.Future;

public class FunWithFuturesRunner {

public static void main(String[] args) {

	try {
		ActorSystem system = ActorSystem.create("FunWithFuturesRunner");
		ActorRef parent = system.actorOf(Props.create(EchoResponder.class));

		// create a future, then wait until it completes (blocking the hard way, just done here for illustration)
		Future<Object> future1 = Patterns.ask(parent, "Ping", 10000);
		while (!future1.isCompleted()) {
			Thread.sleep(50);
		}
		String result = (String) Await.result(future1, null);
		System.out.println(result);

		// convert from a Scala Future to a Java CompletableFuture, then block (by calling cf.get() ) until a response
		// comes back or the 10 second timeout occurs
		Future<Object> future2 = Patterns.ask(parent, "Pong", 10000);
		CompletionStage<Object> cs = FutureConverters.toJava(future2);
		CompletableFuture<Object> cf = (CompletableFuture<Object>) cs;
		System.out.println(cf.get());

		// the same thing, but providing a function to pass the result to, when it comes through
		Future<Object> future3 = Patterns.ask(parent, "Ping Ping Pong", 10000);
		FutureConverters.toJava(future3).thenApply(s -> {
			System.out.println(s);
			return s;
		});

	} catch (Exception e) {
		e.printStackTrace();
	}

	System.exit(0);
}

}
