package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import util.Constants;
import util.Utility;

import manager.UserLocationManager;
import manager.UserManager;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

import entity.User;
import entity.UserLocation;

public class UserAction extends ActionSupport {

	private static final long serialVersionUID = -8534550171421612227L;

	private User user;
	private String name;
	private String mobile;
	private String email;
	private String xmlString;
	private String resultString;
	private long userId;
	private List<User> users = new ArrayList<User>();
	private List<User> usersList = new ArrayList<User>();
	private List<User> newUsers = new ArrayList<User>();
	private String location;
	String searchKeyword;

	private String oldPassword;
	private long id;
	private String iputFromPage;
	private String message;
	private InputStream inputStream;
	private String jsonString = "";
	private String username;
	private String password;
	private int verificationNumber;

	public String get() throws Exception {
		String result = ERROR;
		System.out.println("user id requst...." + userId);
		user = new User();
		try {
			user = new UserManager().getById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<User> tempList = new ArrayList<User>();
		tempList = user.getFriends();
		usersList = new ArrayList<User>();
		for (int i = 0; i < tempList.size(); i++) {
			User user = tempList.get(i);
			user.setFriends(null);
			usersList.add(user);
		}

		result = SUCCESS;
		XStream xStream = new XStream();
		xmlString = xStream.toXML(usersList);
		System.out.println("Respone to android " + xmlString);
		return result;
	}

	public String save() {
		System.out.println(xmlString);
		String result = ERROR;
		User user = new User();
		XStream xStream = new XStream();
		int verfiCationCode;
		user = (User) xStream.fromXML(xmlString);
		user.setRole(Constants.USERROLE_USER);
		System.out.println(xmlString);
		user.setLoginStatus(Constants.CHAT_STATUS_CLOSED);
		user.setAccountStatus(Constants.ACCOUNT_STATUS_ACTIVE);
		user.setVerificationStatus(Constants.VERIFICATION_STATUS_INACTIVE);
		user.setApprovalStatus(Constants.APPROVAL_STATUS_ACTIVE);
		verfiCationCode = Utility.getRandomNumber();
		user.setVerificationCode(verfiCationCode);
		List<User> usersTemp = null;
		boolean isExists = false;
		try {

			usersTemp = new UserManager().list();
			for (int i = 0; i < usersTemp.size(); i++) {
				if (usersTemp.get(i).getUserName().equals(user.getUserName())) {
					isExists = true;
				}
			}
			if (!isExists) {
				if (new UserManager().save(user)) {
					Utility.sendMessage("91" + user.getMobile(),
							"Your EcCHAT Verification code: " + verfiCationCode+"\n use this code activate your account");
					System.out.println("Your EcCHAT Verification code: " + verfiCationCode+"\n use this code to activate your account");
					resultString = "Registered Successfully";
					result = SUCCESS;
				} else {
					resultString = "Registration failed";
				}
			} else {
				result = SUCCESS;
				resultString = "Username already exists";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(isExists);
		System.out.println(resultString);
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String delete() {
		String result = ERROR;
		UserManager userManager = new UserManager();
		user = new User();
		try {
			user = userManager.getById(userId);
			user.setEmail(email);
			user.setMobile(mobile);
			user.setName(name);
			user.setLocation(location);
			if (userManager.delete(user)) {
				result = SUCCESS;
				resultString = "User Deleted Successfully";
			} else {
				resultString = "Operation failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String list() {
		String result = ERROR;
		try {
			users = new UserManager().list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = SUCCESS;
		XStream xStream = new XStream();
		xmlString = xStream.toXML(users);
		return result;
	}

	public String listForApprovingUser() {
		String result = ERROR;
		System.out.println("kdfhkdhfgkjdhfgjkhg");
		try {
			users = new UserManager().listForApprovingUser();
			System.out.println(users.size());
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String listAllUsers() {
		String result = ERROR;
		try {
			users = new UserManager().list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = SUCCESS;
		return result;
	}

	public String updateAprovalStatus() {
		String result = ERROR;
		try {
			System.out.println(userId);
			if (new UserManager().updateApprovalStatus(userId)) {
				result = SUCCESS;
				System.out.println("updated");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getNameList() {
		String result = ERROR;
		System.out.println("keyword......" + searchKeyword);
		UserManager userManager = new UserManager();
		try {

			users = userManager.getItemsBySearchKeyword(searchKeyword);
			System.out.println("LIST SIZE " + users.size());
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				user.setFriends(null);
				newUsers.add(user);
			}
			user = userManager.getById(userId);
			newUsers.remove(user);

		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xstream = new XStream();
		xmlString = xstream.toXML(newUsers); // serialize to XML
		System.out.println("parsed...String................................."
				+ xmlString);
		result = SUCCESS;
		return result;
	}

	public String changePassword() {
		String result = ERROR;
		UserManager userManager = new UserManager();
		try {
			id = userManager.getByUserName("admin").getId();
			System.out.println(id);
			if (userManager.updatePassword(password, id)) {
				result = SUCCESS;
				message = "Password changed successfully..";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "Failed to change password..";
		}
		ActionContext.getContext().getSession().put(Constants.MESSAGE, message);
		return result;
	}

	public String checkPassword() {
		String result = ERROR;
		System.out.println(iputFromPage);
		String values[] = iputFromPage.split("-");
		id = Long.parseLong(values[1]);
		oldPassword = values[0];
		System.out.println("id->>" + id);
		System.out.println("oldp->>" + oldPassword);
		try {
			// id= new UserManager().getByUserName("admin").getId();
			// user = new UserManager().getById(id);
			user = new UserManager().getByUserName("admin");
			if (user != null) {
				if (user.getPassword().equals(oldPassword)) {
					result = SUCCESS;
					message = "Password exists..";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("message-->>" + message);
		jsonString = jsonString + "{\"result\":" + "\"" + message + "\"" + "}";
		this.inputStream = new ByteArrayInputStream(jsonString.getBytes());
		return result;
	}

	public String deleteAccountPrmanently() {
		String result = ERROR;
		UserManager userManager = null;
		user = null;
		resultString = "failed";
		UserLocation userLocation = null;
		UserLocationManager userLocationManager = null;
		List<User> usersTempList;
		try {
			userManager = new UserManager();
			userLocationManager = new UserLocationManager();
			System.out.println(username);
			user = userManager.getByUserName(username);
			/*
			 * users=userManager.list(); if(!users.isEmpty()){
			 * System.out.println("beforre removal "+users.size());
			 * users.remove(users.indexOf(user));
			 * System.out.println("After removal "+users.size()); for (User
			 * userTemp : users) { usersTempList= new ArrayList<User>();
			 * usersTempList=userTemp.getFriends();
			 * if(!usersTempList.isEmpty()){
			 * System.out.println("beforre removal templist "
			 * +usersTempList.size());
			 * usersTempList.remove(usersTempList.indexOf(user));
			 * System.out.println
			 * ("after removal templist "+usersTempList.size());
			 * userTemp.setFriends(usersTempList); userManager.update(userTemp);
			 * } } }
			 */
			if (user != null) {
				// userLocation=userLocationManager.getByUser(user);
				// userLocationManager.delete(userLocation);
				if (userManager.delete(user)) {
					resultString = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String deleteAccountTemporarily() {
		String result = ERROR;

		UserManager userManager = null;
		user = null;
		resultString = "failed";
		try {
			userManager = new UserManager();
			System.out.println(username);
			user = userManager.getByUserName(username);
			if (user != null) {
				if (userManager.updateAccountStatus(user.getId())) {
					userManager.updateChatStatusToInactive(user.getUserName());
					resultString = "success";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String activateAccount() {
		String result = ERROR;

		UserManager userManager = null;
		user = null;
		resultString = "failed";
		try {
			userManager = new UserManager();
			System.out.println(username);
			user = userManager.getByUserName(username);
			if (user != null) {
				if (userManager.updateAccountStatusToActive(user.getId())) {
					userManager.updateChatStatusToInactive(username);
					resultString = "success";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String getUserByUserName() {
		String result = ERROR;

		UserManager userManager = null;
		user = null;
		resultString = "failed";
		try {
			userManager = new UserManager();
			System.out.println(username);
			user = userManager.getByUserName(username);
			user.setFriends(users);
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(user);
		return result;
	}

	public String getById() {
		String result = ERROR;
		UserManager userManager = null;
		user = null;
		resultString = "failed";
		try {
			userManager = new UserManager();
			System.out.println(id);
			user = userManager.getById(id);
			user.setFriends(users);
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(user);
		return result;
	}

	public String changePasswordFromUser() {
		String result = ERROR;
		UserManager userManager = null;
		user = null;
		resultString = "failed";
		try {
			userManager = new UserManager();
			user.setPassword(password);
			if (userManager.update(user)) {
				userManager.updateChatStatusToInactive(username);
				resultString = "success";
				result = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(resultString);
		return result;
	}

	public String verify() {
		String result = ERROR;
		user = null;
		try {
			user = new UserManager().getByUserName(username);
			if (user != null) {
				if (user.getVerificationCode() == verificationNumber) {
					user.setVerificationStatus(Constants.VERIFICATION_STATUS_ACTIVE);
					new UserManager().update(user);
					resultString = "Verification completed successfully";
				} else {
					resultString = "failed";
				}

			}
			xmlString = new XStream().toXML(resultString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIputFromPage() {
		return iputFromPage;
	}

	public void setIputFromPage(String iputFromPage) {
		this.iputFromPage = iputFromPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getVerificationNumber() {
		return verificationNumber;
	}

	public void setVerificationNumber(int verificationNumber) {
		this.verificationNumber = verificationNumber;
	}

}
