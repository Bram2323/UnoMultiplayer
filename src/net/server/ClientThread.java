package net.server;

import java.io.*;
import java.net.*;

class ClientThread extends Thread {
	private Socket socket;
	private PrintWriter out;
    private BufferedReader in;
	
	private final int id;
	private final MessageListener messageListener;

    public ClientThread(Socket clientSocket, int id, MessageListener messageListener) {
		this.socket = clientSocket;
		this.id = id;
		this.messageListener = messageListener;
		
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
    }

    public void run() {
        while (true){
			try {
				String line = in.readLine();
				messageListener.receivedMessage(id, line);
			}
			catch (Exception ex) {
				break;
			}
		}
    }
	
	public void sendMessage(String message){
		out.println(message);
	}
}
