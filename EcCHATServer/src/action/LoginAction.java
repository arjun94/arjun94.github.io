package action;

import java.util.List;

import util.Constants;

import manager.FriendRequestManager;
import manager.UserLocationManager;
import manager.UserManager;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

import entity.FriendRequest;
import entity.User;
import entity.UserLocation;

public class LoginAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String userName;
	private String password;
	private String resultString;
	private String xmlString;

	public String adminLogin() {
		String result = ERROR;
		UserManager userManager = new UserManager();
		try {
			User user = userManager
					.getByUserNameAndPassword(userName, password);
			if (user != null) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String login() {
		String result = ERROR;
		try {
			int requestCount = 0;
			UserManager userManager = new UserManager();
			User user = userManager
					.getByUserNameAndPassword(userName, password);
			if (user != null) {
				if ((user.getVerificationStatus() == Constants.VERIFICATION_STATUS_ACTIVE)) {
					if ((user.getLoginStatus() == Constants.CHAT_STATUS_CLOSED)) {
						if (user.getAccountStatus() == Constants.ACCOUNT_STATUS_ACTIVE) {
							if (user.getApprovalStatus() == Constants.APPROVAL_STATUS_ACTIVE) {
								FriendRequestManager manager = new FriendRequestManager();
								List<FriendRequest> requests = manager.listOfRequests(user.getId());
								requestCount = requests != null ? requests.size() : 0;
								resultString = "success" + "-" + user.getId()+ "-" + requestCount + "-"+ user.getUserName();
								System.out.println("id............."+ user.getId());
								System.out.println("request id" + requestCount);
								new UserManager().updateChatStatus(user.getUserName());
								result = SUCCESS;
							} else {
								result = SUCCESS;
								resultString = "You are not yet approved";
							}
						} else {
							resultString = "deactivated" + "-" + user.getId()
									+ "-" + requestCount + "-"
									+ user.getUserName();
							result = SUCCESS;
						}
				} else {
					result = SUCCESS;
					resultString = "Already login";
				}
				} else {
					resultString = "notverified" + "-" + user.getId() + "-"
							+ requestCount + "-" + user.getUserName();
					result = SUCCESS;
				}
			} else {

				resultString = "Login Failed";
				result = SUCCESS;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		System.out.println(resultString);
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;

	}

	public String logoutFromClient() {
		String result = ERROR;
		resultString = "Failed to logout";
		UserLocation userLocation = null;
		try {
			if (new UserManager().updateChatStatusToInactive(userName)) {
				userLocation = new UserLocationManager()
						.getByUser(new UserManager().getByUserName(userName));
				if (userLocation != null) {
					new UserLocationManager().delete(userLocation);
				}
				result = SUCCESS;
				resultString = "Logout success";
			} else {
				resultString = "Failed to logout";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

}
