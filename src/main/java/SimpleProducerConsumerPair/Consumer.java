package SimpleProducerConsumerPair;

//code from https://www.java-success.com/01-simple-akka-tutorial-java-step-step/

import akka.actor.UntypedActor;

@SuppressWarnings("deprecation")
public class Consumer extends UntypedActor {

@Override
public void onReceive(Object msg) throws Exception {
	if (msg instanceof Integer) {
		System.out.println("<<< Receiving & printing " + msg);
	}
}
}