package net.server;

public interface MessageListener {
	public void receivedMessage(int clientID, String message);
}
