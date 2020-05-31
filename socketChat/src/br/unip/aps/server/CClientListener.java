package br.unip.aps.server;

import java.io.IOException;
import java.net.Socket;

import br.unip.aps.server.common.Utils;
import br.unip.aps.view.Chat;
import br.unip.aps.view.Home;

public class CClientListener implements Runnable {
	
	private boolean running;
	private Socket connection;
	private Home home;
	private boolean chatOpen;
	private String connectionInfo;
	private Chat chat;
	
	public CClientListener(Home home, Socket connection) {
		this.running = false;
		this.chatOpen = false;
		this.home = home;
		this.connection = connection;
		this.connectionInfo = null;
		this.chat = null;
	}
	
	@Override
	public void run() {
		running = true;
		String message;
		while(running) {
			message = Utils.receiveMessage(connection);
			if (message == null || message.equals("CHAT_CLOSE")) {
				if (chatOpen) {
					home.getOpenedChats().remove(connectionInfo);
					home.getConnectedListeners().remove(connectionInfo);
					chatOpen = false;
					try {
						connection.close();
					}catch(IOException e) {
						System.out.println("[CClientListener:Run] -> " + e.getMessage());
					}
					
					chat.dispose();
				}
				running = false;
			} else {
				String[] fields = message.split(";");
				if(fields.length > 1) {
					if(fields[0].equals("OPEN_CHAT")) {
						String[] splited = fields[1].split(":");
						connectionInfo = fields[1];
						if(!chatOpen) {
							home.getOpenedChats().add(connectionInfo);
							home.getConnectedListeners().put(connectionInfo, this);
							chatOpen = true;
							chat = new Chat(home, connection, connectionInfo, home.getConnectionInfo());
						}
					} else if (fields[0].equals("MESSAGE")){
						String msg = "";
						for(int i = 1; i < fields.length; i++) {
							msg += fields[i];
							if (i > 1) msg += ";";
						}
						chat.appenedMessage(fields[1]);
						
					}
				}
				
			}
			System.out.println("> Mensagens: " + message);
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isChatOpen() {
		return chatOpen;
	}

	public void setChatOpen(boolean chatOpen) {
		this.chatOpen = chatOpen;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	
}


