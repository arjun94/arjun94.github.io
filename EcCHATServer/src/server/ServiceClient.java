/**
 * 
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import util.Utility;

import entity.ChatSession;
import entity.ClientConnection;

public class ServiceClient extends Thread {

	private DataInputStream in;
	private DataOutputStream out;
	private Server server;
	private String clientId;
	private boolean running = false;
	private int competition;
	private int stage;
	private int timeStamp;
	ChatSession chatSession = null;

	public ServiceClient(Socket socket, Server server) throws IOException {
		this.server = server;
		this.in = new DataInputStream(socket.getInputStream());
		// Enable auto-flush:
		this.out = new DataOutputStream(socket.getOutputStream());
		// If any of the above calls throw an
		// exception, the caller is responsible for
		// closing the socket. Otherwise the thread
		// will close it.
		running = true;
		if (doLogin().equals("SUCCESS")) {
			this.start(); // Calls run()
		}
	}

	private String doLogin() {
		String result = "ERROR";
		try {
			String message = in.readUTF();
			if (message.startsWith("LOGIN:")) {
				String[] messageContent = message.split(":");
				clientId = messageContent[1];
				if (this.server.getClients().keySet().contains(clientId)) {
					// out.writeUTF("ERROR:Please try to join with another name");
				} else {
					ClientConnection clientConnection = new ClientConnection(
							this.in, this.out);
					this.server.addClient(clientId, clientConnection);
					// this.server.addClientThread(this);
					result = "SUCCESS";
				}
				out.writeUTF(result);
				System.out.println("result="+result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void run() {
		try {
			String message;
			// Listening for messages ...
			while (running) {
				message = this.in.readUTF();
				if (message.toUpperCase().equals("LOGOUT")) {
					this.server.removeClient(clientId);
					this.server.removeClientThread(clientId);
					// code to stop chat session
					running = false;
					break;
				} else if (message.toUpperCase().equals("CHATWITH")) {
					System.out.println("checking message....."+message);
					String toClient = "";
					String[] messageContent = message.split(":");
					toClient = messageContent[1];
					chatSession = this.server
							.getChatSession(clientId, toClient);
					if (chatSession == null) {
						chatSession = new ChatSession(clientId, toClient);
						/*
						 * chatSession.setFromUser(clientId);
						 * chatSession.setToUser(toClient);
						 */
						// code to check if the toclient user is online now. If
						// not send back a message to the user
						chatSession.setSessionId(Utility.getRandomNumber());
						this.server.addChatSession(
								Long.toString(chatSession.getSessionId()),
								chatSession);
					}
					out.writeUTF(Long.toString(chatSession.getSessionId()));
					System.out.println("testing output value....."+(Long.toString(chatSession
							.getSessionId())));
				} else {
					String sessionId = "";
					ClientConnection clientConnection;
					String toUserId = "";
					String toUserName = "";
					String[] messageContent = message.split(":");
					sessionId = messageContent[0];
					chatSession = this.server.getChatSession(sessionId);
					if (chatSession != null) {
						if (chatSession.getFromUser().equals(clientId)) {
							toUserId = chatSession.getToUser();
							toUserName = chatSession.getToUserName();
						} else {
							toUserId = chatSession.getFromUser();
							toUserName = chatSession.getFromUserName();
						}
						clientConnection = this.server.getClient(toUserId);
						clientConnection.getOut().writeUTF(
								toUserName + " says: " + messageContent[1]);

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCompetition() {
		return competition;
	}

	public void setCompetition(int competition) {
		this.competition = competition;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof ServiceClient))
			return false;
		ServiceClient serviceClient = (ServiceClient) other;
		if (this.clientId.equals(serviceClient.getClientId())) {
			return true;
		} else {
			return false;
		}
	}

}
