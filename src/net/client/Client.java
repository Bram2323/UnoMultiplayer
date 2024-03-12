package net.client;

import java.io.*;
import java.net.*;

public abstract class Client extends Thread {
	private final String ip;
	private final int port;

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	
	public Client(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public void run(){
		try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		catch (Exception ex){
			ex.printStackTrace();
			return;
		}

		connectedToServer();

		while (true){
			try {
				String message = in.readLine();
				receivedMessage(message);
			}
			catch (Exception ex){
				ex.printStackTrace();
				break;
			}
		}
	}

    public void sendMessage(String message) {
        out.println(message);
    }

	public abstract void connectedToServer();

	public abstract void receivedMessage(String message);
}