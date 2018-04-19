/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import entity.ChatSession;
import entity.ClientConnection;
import util.Constants;


public class Server extends Thread{

	private Map<String, ClientConnection> clients;
	private Map<String, ChatSession> chatSesions;
	private Map<String, ServiceClient> clientThreads;
	
	
	ServerSocket serverSocket = null;
	boolean isServerRunning = false;


	@Override
	public void run() {
		try {
		      while(isServerRunning) {
		    	  if(serverSocket.isClosed()) {
		    		  break;
		    	  }
		        // Blocks until a connection occurs:
	//	    	  If the server is stopped then break the loop.
		    	  Socket socket = null;
		    	  try{
		    		  socket = serverSocket.accept();
		    		  
		    		  
		    	  } catch(SocketException se){
	//	    		  If exception then it means, the server is closed.
	//	    		  Then break the loop.
		    		  break;
		    	  }
		        try {
		        	
		        	new ServiceClient(socket,this);
		        	
		        	
		        } catch(IOException ex) {
		          // If it fails, close the socket,
		          // otherwise the thread will close it:
		        	ex.printStackTrace();
		          socket.close();
		        }
		      }
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
		    try {
		    	  if(!serverSocket.isClosed()) {
		    		  serverSocket.close();
		    	  }
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void startServer() {
		 try{
			 	this.clients = Collections.synchronizedMap(new HashMap<String, ClientConnection>());
			 	this.chatSesions = Collections.synchronizedMap(new HashMap<String, ChatSession>());
			 	this.clientThreads = Collections.synchronizedMap(new HashMap<String, ServiceClient>());
			 	
		    	serverSocket = new ServerSocket(Constants.SERVER_SOCKET); 
		    	System.out.println("Listening to port "+Constants.SERVER_SOCKET);
		    	isServerRunning = true;
		    	this.start();
		    } catch (IOException e) {
		      System.out.println("Could not listen on port "+Constants.SERVER_SOCKET);
		      e.printStackTrace();
		      System.exit(-1);
		    }
		
	}

	public void closeConnection() {

		try {
			if(serverSocket != null && !serverSocket.isClosed()){
				Set<String> keySet = this.clients.keySet();
				for (String string : keySet) {
					ClientConnection client = this.clients.get(string);
					client.getOut().writeUTF("SERVER_CLOSED:");
					client.getIn().close();
					client.getOut().close();
				}
				this.serverSocket.close();
				System.out.println("Server Closed...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addClient(String clientID,ClientConnection client){
		this.clients.put(clientID,client);
	  }
	
	public void addClientThread(String chatId, ServiceClient clientThread){
		this.clientThreads.put(chatId,clientThread);
	}
	
	public void addChatSession(String chatId, ChatSession chatSession){
		this.chatSesions.put(chatId,chatSession);
	}
	
	public void removeClient(String clientID){
		this.clients.remove(clientID);
	  }
	
	public void removeClientThread(String clientID){
		this.clientThreads.remove(clientID);
	  }
	
	public Map<String, ServiceClient> getClientThreads() {
		return clientThreads;
	}
	
	public Map<String, ClientConnection> getClients(){
		return clients;
	}

	public void setClients(Map<String, ClientConnection> clients) {
		this.clients = clients;
	}

	public boolean isServerRunning() {
		return isServerRunning;
	}

	public void setServerRunning(boolean isServerRunning) {
		this.isServerRunning = isServerRunning;
	}
	
	public ClientConnection getClient(String clientID){
		ClientConnection clientConnection = null;
		clientConnection = clients.get(clientID);
		this.clients.put(clientID,clientConnection);
		return clientConnection;
	  }
	
	
	public ChatSession getChatSession(String toUser, String fromUser){
		ChatSession chatSession = null;
		for(String s : chatSesions.keySet()){
			if((chatSesions.get(s).getFromUser().equals(fromUser) && chatSesions.get(s).getToUser().equals(toUser)) ||
					(chatSesions.get(s).getFromUser().equals(toUser) && chatSesions.get(s).getToUser().equals(fromUser))){
				chatSession = chatSesions.get(s);
				break;
			}
		}
		return chatSession;
	}
	
	public ChatSession getChatSession(String sessionId){
		ChatSession chatSession = null;
		chatSession = chatSesions.get(sessionId);
		return chatSession;
	}
	
}
