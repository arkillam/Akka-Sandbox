package FunWithFutures;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * Accepts string messages; responds back with whatever it was sent.
 */
public class EchoResponder extends AbstractActor {

private final LoggingAdapter log = Logging.getLogger(context().system(), this);

private ReceiveBuilder rb = new ReceiveBuilder();

/**
 * @return receives strings, responds with "Pong"
 */
@Override
public Receive createReceive() {
	return rb.match(String.class, s -> getSender().tell(s, self())).build();
}

}
