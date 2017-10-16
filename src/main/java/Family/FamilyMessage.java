package Family;

public class FamilyMessage {

/** the name of the person the message is intended for */
private String intended;

/** the message itself */
private String payload;

public FamilyMessage(String intended, String payload) {
	this.intended = intended;
	this.payload = payload;
}

public String getIntended() {
	return intended;
}

public String getPayload() {
	return payload;
}

public void setIntended(String intended) {
	this.intended = intended;
}

public void setPayload(String payload) {
	this.payload = payload;
}

}
