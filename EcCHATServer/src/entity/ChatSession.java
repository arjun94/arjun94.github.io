package entity;

import manager.UserManager;

public class ChatSession {
	private long sessionId;
	private String fromUser;
	private String toUser;
	private String fromUserName;
	private String toUserName;
	
	public ChatSession(){
		
	}
	
	public ChatSession(String fromUserId, String toUSerId){
		UserManager userManager = new UserManager();
		this.fromUser = fromUserId;
		this.toUser = toUSerId;
		User fUser;
		User tUser;
		try {
			fUser = userManager.getByUserName(fromUserId);
			tUser = userManager.getByUserName(toUSerId);
			if(fUser!=null){
				fromUserName = fUser.getName();
			}
			if(tUser!=null){
				toUserName = tUser.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

}
