package entity;

import java.util.List;

public class User {
	private long id;
	private String name;
	private String email;
	private String mobile;
	private String userName;
	private String password;
	private List<User> friends;
	private int loginStatus;
	private String location;
	private int approvalStatus;
	private int accountStatus;
	private int role;
	private String gender;
	private int verificationCode;
	private int verificationStatus;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public int getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

	public int getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	
	public boolean equals(Object object){
		boolean result=false;
		if(User.class.isInstance(object)){
			if(this.id==((User)object).getId()){
				result=true;
			}
		}
		return result;
	}

	public int getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(int verificationCode) {
		this.verificationCode = verificationCode;
	}

	public int getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(int verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	
	
	
	
	

}
