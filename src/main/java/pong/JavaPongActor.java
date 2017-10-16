package pong;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

// based on:  https://github.com/jasongoodwin/learning-akka/blob/master/ch2/futures-examples-java/src/main/java/pong/JavaPongActor.java

public class JavaPongActor extends AbstractActor {

private final LoggingAdapter log = Logging.getLogger(context().system(), this);

/** the actor's name */
private String name;

private ReceiveBuilder rb = new ReceiveBuilder();

public JavaPongActor(String name) {
	super();
	this.name = name;
}

/**
 * @return a Receive object that accepts "Ping" messages and responds with "Pong"
 */
@Override
public Receive createReceive() {
	// matches "Ping", tells sender "Pong"

	// lambda version
	// return rb.matchEquals("Ping", s -> sender().tell("Pong", ActorRef.noSender())).build();

	// passing a function version
	return (rb.matchEquals("Ping", this::sayPong)).build();
}

public String getName() {
	return name;
}

private void sayPong(String s) {
	log.info("{} was told '{}'", getName(), s);
	// sender().tell("Pong", ActorRef.noSender());
	getSender().tell("Pong", ActorRef.noSender());
	try {
		Thread.sleep(100);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public void setName(String name) {
	this.name = name;
}

}