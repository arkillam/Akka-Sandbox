package DistributedDatabaseTests;

import org.junit.Assert;
import org.junit.Test;

import DistributedDatabase.AkkademyDb;
import DistributedDatabase.SetRequest;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

public class AkkademyDbTest {

private ActorSystem system = ActorSystem.create();

@Test
public void itShouldPlaceKeyValueFromSetMessageIntoMap() {
	// create actor, get a reference to them back
	// Props lets us pass a series of arguments in after the class type (we are not sending any here, but we could)
	TestActorRef<AkkademyDb> actorRef = TestActorRef.create(system, Props.create(AkkademyDb.class));

	// send the actor a message
	actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());

	// get the underlying actor (only okay because this is a test) and validate that the actor saved the message
	AkkademyDb akkademyDb = actorRef.underlyingActor();
	Assert.assertEquals(akkademyDb.getMap().get("key"), "value");
}

}