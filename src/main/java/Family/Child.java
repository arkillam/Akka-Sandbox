package Family;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

public class Child extends AbstractActor {

private final LoggingAdapter log = Logging.getLogger(context().system(), this);

/** child's name */
private String name;

private ReceiveBuilder rb = new ReceiveBuilder();

public Child(String name) {
	super();
	this.name = name;
}

/**
 * @return a Receive object that handles FamilyMessage type messages and logs others as errors
 */
@Override
public Receive createReceive() {
	return rb.match(FamilyMessage.class, this::receiveFamilyMessage).matchAny(this::handleUnknown).build();
}

/**
 * Logs unknown messages as errors.
 * 
 * @param msg
 */
private void handleUnknown(Object msg) {
	log.error("received unknown message {}", msg.toString());
}

/**
 * Receives FamilyMessage objects and passes them on to children.
 * 
 * @param fm
 */
private void receiveFamilyMessage(FamilyMessage fm) {
	log.info("{} acknowledges receipt of message '{}'.", name, fm.getPayload());
	sender().tell("Message recieved.", self());
}

}
