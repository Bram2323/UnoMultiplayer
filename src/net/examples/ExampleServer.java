package net.examples;

import java.util.Scanner;
import net.server.Server;

public class ExampleServer extends Server {
	public static final int PORT = 2323;


	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
		ExampleServer server = new ExampleServer(PORT);
		server.start();
		
		while (true){
			int clientID;
			do {
				System.out.println("To who do you want to send?");
				if (input.hasNextInt()){
					clientID = input.nextInt();
					input.nextLine();
					break;
				}
				input.nextLine();
			}
			while (true);
			
			String message = input.nextLine();
			
			server.sendMessage(clientID, message);
		}
	}
	
	
	public ExampleServer(int port){
		super(port);
	}
	
	@Override
	public void receivedMessage(int id, String message){
		System.out.printf("%d send a message: %s%n", id, message);
	}
	
	@Override
	public void clientConnected(int id){
		System.out.printf("%d connected!%n", id);
	}
}
