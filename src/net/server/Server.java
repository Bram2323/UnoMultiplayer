package net.server;

import java.util.HashMap;
import java.io.*;
import java.net.*;

public abstract class Server extends Thread implements MessageListener {
	private HashMap<Integer, ClientThread> clients = new HashMap<>();
	private int currentID = 1;

	private final int port;
	
	
	public Server(int port){
		this.port = port;
	}


    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
			
			int clientID = currentID;
            currentID++;
			
            ClientThread client = new ClientThread(socket, clientID, this);
			clients.put(clientID, client);
			client.start();
			clientConnected(clientID);
        }
    }
	
	public void sendMessage(int clientID, String message){
		if (!clients.containsKey(clientID)) return;
		
		clients.get(clientID).sendMessage(message);
	}

    public void broadcastMessage(String message){
        for (ClientThread client : clients.values()){
            client.sendMessage(message);
        }
    }


	public abstract void receivedMessage(int id, String message);
	
	public abstract void clientConnected(int id);
}
