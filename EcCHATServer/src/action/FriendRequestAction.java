package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Constants;

import manager.FriendRequestManager;
import manager.UserManager;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

import entity.FriendRequest;
import entity.User;

public class FriendRequestAction extends ActionSupport {

	private static final long serialVersionUID = -1786913598801528008L;

	private long toUserId;
	private long fromUserId;
	private String xmlString;
	private String resultString;
	private FriendRequest friendRequest;
	private long friendRequestId;
	private long userId;
	private long friendId;
	
	private List<User> usersList = new ArrayList<User>();
	// private List<User> newUsers = new ArrayList<User>();
	private List<FriendRequest> friendRequests = new ArrayList<FriendRequest>();

	public String save() {
		String result = ERROR;
		try {
			User toUser = new UserManager().getById(toUserId);
			User fromUser = new UserManager().getById(fromUserId);
			friendRequest = new FriendRequestManager().checkRequest(fromUserId,
					toUserId);
			if (friendRequest == null) {
				friendRequest = new FriendRequest();
				System.out.println("Inside if...");
				friendRequest.setDate(new Date());
				friendRequest.setFromUser(fromUser);
				friendRequest.setToUser(toUser);
				friendRequest.setStatus(Constants.REQUEST_STATUS_ACTIVE);
				if (new FriendRequestManager().save(friendRequest)) {
					result = SUCCESS;
					resultString = "Request Send";
				} else {
					resultString = "Cannot Send Request";
				}
			} else {
				System.out.println("Inside else...");
				resultString = "Request Already sent...";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String update() {
		String result = ERROR;
		try {
			User toUser = new UserManager().getById(toUserId);
			User fromUser = new UserManager().getById(fromUserId);
			friendRequest = new FriendRequest();
			System.out.println("userIds////////" + fromUserId + ">>>>"
					+ toUserId);
			friendRequest = new FriendRequestManager().checkRequest(fromUserId,
					toUserId);
			friendRequest.setStatus(Constants.REQUEST_STATUS_INACTIVE);
			if (new FriendRequestManager().update(friendRequest)) {
				usersList = toUser.getFriends();
				usersList.add(fromUser);
				toUser.setFriends(usersList);
				new UserManager().update(toUser);
				usersList = fromUser.getFriends();
				usersList.add(toUser);
				fromUser.setFriends(usersList);
				new UserManager().update(fromUser);
				result = SUCCESS;
				resultString = "Friends Request Accepted ";
			} else {
				resultString = " failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String reject() {
		String result = ERROR;
		try {
			friendRequest = new FriendRequest();
			System.out.println("userIds////////" + fromUserId + ">>>>"
					+ toUserId);
			friendRequest = new FriendRequestManager().checkRequest(fromUserId,
					toUserId);
			friendRequest.setStatus(Constants.REQUEST_STATUS_INACTIVE);
			if (new FriendRequestManager().update(friendRequest)) {
				result = SUCCESS;
				resultString = "rejected";
			} else {
				resultString = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String delete() {
		String result = ERROR;
		try {
			friendRequest = new FriendRequest();
			friendRequest = new FriendRequestManager().getById(friendRequestId);
			if (new FriendRequestManager().delete(friendRequest)) {
				result = SUCCESS;
				resultString = "deleted successfully";
			} else {
				resultString = "operation failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String get() {
		String result = ERROR;
		try {
			friendRequest = new FriendRequest();
			friendRequest = new FriendRequestManager().getById(friendRequestId);
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(friendRequest);
		return result;
	}

	public String list() {
		String result = ERROR;
		try {
			friendRequests = new FriendRequestManager().list();
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(friendRequests);
		return result;
	}

	public String listNotification() throws Exception {
		String result = ERROR;
		try {
			friendRequests = new FriendRequestManager().listOfRequests(userId);
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}/*
		 * for (int i = 0; i < friendRequests.size(); i++) {
		 * 
		 * usersList = new ArrayList<User>(); for (int j = 0; j <
		 * friendRequests.get(i).getToUser().getFriends().size(); i++) {
		 * usersList.add(newUsers.get(i).getFriends().get(j)); }
		 * friendRequests.get(i).getToUser().setFriends(usersList);
		 * 
		 * }
		 */
		for (int i = 0; i < friendRequests.size(); i++) {

			User user = new User();
			user = new UserManager().getById(friendRequests.get(i)
					.getFromUser().getId());
			user.setFriends(null);
			usersList.add(user);

		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(usersList);
		return result;
	}

	public String removeFriends() {
		String result = ERROR;
		User user = null;
		User friend = null;
		UserManager userManager=null;
		resultString="failed";
		try {
			userManager= new UserManager();
			user=userManager.getById(userId);
			friend=userManager.getById(friendId);
			usersList = user.getFriends();
			usersList.remove(friend);
			user.setFriends(usersList);
			userManager.update(user);
			usersList=friend.getFriends();
			usersList.remove(user);
			friend.setFriends(usersList);
			userManager.update(friend);
			result=SUCCESS;
			resultString="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	
	public String getRequestCount(){
		FriendRequestManager manager=null;
		int requestCount=0;
		try {
			 manager = new FriendRequestManager();
			List<FriendRequest> requests = manager
					.listOfRequests(userId);
			requestCount = requests != null ? requests.size(): 0;
			xmlString= new XStream().toXML(String.valueOf(requestCount));
			System.out.println(xmlString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SUCCESS;
	}
	
	
	
	
	
	public long getToUserId() {
		return toUserId;
	}

	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}

	public long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public FriendRequest getFriendRequest() {
		return friendRequest;
	}

	public void setFriendRequest(FriendRequest friendRequest) {
		this.friendRequest = friendRequest;
	}

	public long getFriendRequestId() {
		return friendRequestId;
	}

	public void setFriendRequestId(long friendRequestId) {
		this.friendRequestId = friendRequestId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}
}
