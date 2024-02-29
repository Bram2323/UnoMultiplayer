package net.examples;

import java.util.Scanner;
import net.client.Client;

public class ExampleClient extends Client {
	public static final String IP = "localhost";
	public static final int PORT = 2323;


	public static void main(String[] args){
		ExampleClient client = new ExampleClient(IP, PORT);
		client.start();
		
		Scanner input = new Scanner(System.in);
		
		while (true){
			System.out.println("What would you like to send?");
			String message = input.nextLine();
			
			client.sendMessage(message);
		}
	}


	public ExampleClient(String ip, int port){
		super(ip, port);
	}

	@Override
	public void receivedMessage(String message) {
		System.out.println("Recieved message: " + message);
	}
}
