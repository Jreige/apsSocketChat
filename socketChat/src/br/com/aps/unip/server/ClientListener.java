package br.com.aps.unip.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import br.com.aps.unip.server.common.Utils;

public class ClientListener implements Runnable {

private String connectionInfo;
private Socket connection;
private Server server;
private boolean running;
	 
	public ClientListener(String connectionInfo, Socket connection, Server server) {
		this.connectionInfo = connectionInfo;
		this.connection = connection;
		this.server = server;
		this.running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void run() {
		running = true;
		String message;
		while(running) {
			message = Utils.receiveMessage(connection);
			if(message.equals("QUIT")){
				server.getClients().remove(connectionInfo);
				try {
					connection.close();
				} catch (IOException e) {
					System.out.println("[CLientListener:Run] -> " + e.getMessage());
				}
				running = false;		
			} else if(message.equals("GET_CONNECTED_USERS")) {
				System.out.println("Solicitação de atualizar lista de contatos...");
				String response = "";
				for(Map.Entry<String, ClientListener> pair : server.getClients().entrySet()) {
					response += (pair.getKey() + ";");
				}
				Utils.sendMessage(connection, response);
			} else {
				System.out.println("Recebido: " + message);
			}
		}
	}
	
}
