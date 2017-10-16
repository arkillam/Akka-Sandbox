package DistributedDatabase;

import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

public class AkkademyDb extends AbstractActor {

private final LoggingAdapter log = Logging.getLogger(context().system(), this);

private final Map<String, Object> map = new HashMap<>();

private ReceiveBuilder rb = new ReceiveBuilder();

@Override
public Receive createReceive() {
	return (rb.match(SetRequest.class, message -> {
		log.info("Received set request – key: {} value: {}", message.getKey(), message.getValue());
		map.put(message.getKey(), message.getValue());
	}).matchAny(o -> log.info("received unknown message {}", o)).build());
}

public Map<String, Object> getMap() {
	return map;
}

}