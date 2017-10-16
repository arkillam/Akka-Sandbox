package Family;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

public class Parent extends AbstractActor {

private Map<String, ActorRef> children = new HashMap<>();

private ReceiveBuilder rb = new ReceiveBuilder();

private final LoggingAdapter log = Logging.getLogger(context().system(), this);

/**
 * @return a Receive object that handles FamilyMessage type messages and logs others as errors
 */
@Override
public Receive createReceive() {
	return rb.match(FamilyMessage.class, this::receiveFamilyMessage).matchAny(this::handleUnknown).build();
}

/**
 * Receives FamilyMessage objects and passes them on to children.
 * 
 * @param fm
 */
private void receiveFamilyMessage(FamilyMessage fm) {
	Objects.requireNonNull(fm);

	// if we do not have a child with the intended target's name, create one and store it in our map of actors
	if (!children.containsKey(fm.getIntended())) {
		ActorRef ar = context().actorOf(Props.create(Child.class, fm.getIntended()));
		children.put(fm.getIntended(), ar);
	}

	children.get(fm.getIntended()).tell(fm, self());
}

/**
 * Logs unknown messages as errors.
 * 
 * @param msg
 */
private void handleUnknown(Object msg) {
	log.error("received unknown message {}", msg.toString());
}

}
