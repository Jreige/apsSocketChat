package br.unip.aps.server.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Utils {

	
	public static String receiveMessage(Socket connection) {
		String response = null;
		try {
			ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
			response = (String) input.readObject();
		}catch (IOException e) {
			System.out.println("[ERROR:receiveMessage] -> " + e.getMessage());
		}catch (ClassNotFoundException e) {
			System.out.println("[ERROR:receiveMessage] -> " + e.getMessage());
		}
		return response;
	}
	
	public static boolean sendMessage(Socket connection, String message) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			output.writeObject(message);
			return true;
		} catch (IOException e) {
			System.out.println("[ERROR:sendMessage] -> " + e.getMessage());
		}
		return false;
	}
}
