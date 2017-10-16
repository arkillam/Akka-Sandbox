package Family;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class FamilyRunner {

public static void main(String[] args) {

	try {
		ActorSystem system = ActorSystem.create("FamilyRunner");
		ActorRef parent = system.actorOf(Props.create(Parent.class));

		// send some messages
		parent.tell(new FamilyMessage("Theresa", "How was your day at school?"), parent);
		parent.tell(new FamilyMessage("Thomas", "Hi, Thoams."), parent);
		parent.tell(new FamilyMessage("Lisa", "Come for dinner."), parent);
		parent.tell(new FamilyMessage("Thomas", "Come for dinner."), parent);
		parent.tell(new FamilyMessage("Theresa", "Come for dinner."), parent);
		parent.tell("Hello, world!", parent);

		System.out.println("Press ENTER to exit.");
		System.in.read();

	} catch (Exception e) {
		e.printStackTrace();
	}

	System.exit(0);
}

}
