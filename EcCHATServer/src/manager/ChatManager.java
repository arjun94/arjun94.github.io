package manager;

import java.util.HashMap;

import entity.ChatSession;

public class ChatManager {
	private static HashMap<String, ChatSession> sessionPool = new HashMap<String, ChatSession>();

	public HashMap<String, ChatSession> getSessionPool() {
		return sessionPool;
	}

}
