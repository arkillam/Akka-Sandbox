package pong;

import java.util.Properties;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

public class AndrewsRunner {

public static void main(String[] args) {
	try {
		// not sure if these logging properties work or not ... need to investigate
		Properties properties = new Properties();
		properties.setProperty("loglevel", "DEBUG");
		properties.setProperty("autoreceive", "on");
		properties.setProperty("lifecycle", "on");
		properties.setProperty("receive", "on");
		Config overrides = ConfigFactory.parseProperties(properties);
		Config actualConfig = overrides.withFallback(ConfigFactory.load());

		ActorSystem system = ActorSystem.create("AndrewsRunner", actualConfig);

		TestActorRef<JavaPongActor> firstActor = TestActorRef.create(system, Props.create(JavaPongActor.class, "Andrew"));
		TestActorRef<JavaPongActor> secondActor = TestActorRef.create(system, Props.create(JavaPongActor.class, "Robert"));

		for (int i = 0; i < 10; i++) {
			firstActor.tell("Ping", secondActor);
			secondActor.tell("Ping", firstActor);
		}

		firstActor.wait();
		secondActor.wait();
		
		System.exit(0);

	} catch (Exception e) {
		e.printStackTrace();
		System.exit(1);
	}

	System.exit(1);
}

}
