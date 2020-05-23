package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import common.Utils;

public class Server {

	public static final String HOST = "127.0.0.1";
	public static final int PORT = 4444;
	
	private ServerSocket server;
	private Map<String, ClientListener> clients;
	
	public Server() {
		try {
			String connectionInfo = "";
			clients = new HashMap<String, ClientListener>();
			server = new ServerSocket(PORT);
			System.out.println("Servidor iniciado no host: " + HOST + " e porta: " + PORT);
			while(true) {
				Socket connection = server.accept();
				connectionInfo = Utils.receiveMessage(connection);
				if(checkLogin(connectionInfo)) {
					ClientListener clientListener = new ClientListener(connectionInfo, connection, this);
					clients.put(connectionInfo, clientListener);
					Utils.sendMessage(connection, "SUCESSO");
					new Thread(clientListener).start();
				}else {
					Utils.sendMessage(connection, "ERRO");
				}
			}
		} catch (IOException e) {
			System.out.println("[ERROR:Server] -> " + e.getMessage());
		}
	}
	
	public Map<String, ClientListener> getClients(){
		return clients;
	}
 	
	private boolean checkLogin(String connectionInfo) {
		String[] splited = connectionInfo.split(":");
		for(Map.Entry<String, ClientListener> pair: clients.entrySet()) {
			String[] parts = pair.getKey().split(":");
			if(parts[0].toLowerCase().equals(splited[0].toLowerCase())) {
				return false;
			}else if((parts[1] + parts[2]).equals(splited[1] + splited[2])) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Server server = new Server();
	}
}
