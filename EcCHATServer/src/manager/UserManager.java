package manager;

import java.util.List;

import util.Constants;

import dao.DAOManager;
import entity.User;

public class UserManager {

	DAOManager daoManager = new DAOManager();

	public boolean save(User user) throws Exception {
		return daoManager.save(user);
	}

	public boolean update(User user) throws Exception {
		return daoManager.update(user);
	}

	public boolean delete(User user) throws Exception {
		return daoManager.delete(user);
	}

	public User getById(long id) throws Exception {
		return (User) daoManager.get(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> list() throws Exception {
		return daoManager.list(User.class);
	}

	public User getByUserNameAndPassword(String userName, String password)
			throws Exception {
		String query = "select u from User u where u.userName = '" + userName
				+ "' and u.password = '" + password + "'";
		Object object = daoManager.getByHql(query);
		return object == null ? null : (User) object;

	}

	public User getByUserName(String userName) throws Exception {
		String query = "select u from User u where u.userName = '" + userName
				+ "'";
		System.out.println(query);
		Object object = daoManager.getByHql(query);
		return object == null ? null : (User) object;

	}

	@SuppressWarnings("unchecked")
	public List<User> getItemsBySearchKeyword(String searchKeyword)
			throws Exception {
		return (List<User>) daoManager.listBySearchKeyword(searchKeyword);
	}

	public boolean updateChatStatus(String userName) throws Exception {
		String query = "update User u  set u.loginStatus ="
				+ Constants.CHAT_STATUS_live + " where u.userName = '"
				+ userName + "'";
		return daoManager.updateByHql(query);
	}

	public boolean updateChatStatusToInactive(String userName) throws Exception {
		String query = "update User u  set u.loginStatus ="
				+ Constants.CHAT_STATUS_CLOSED + " where u.userName = '"
				+ userName + "'";
		return daoManager.updateByHql(query);
	}

	@SuppressWarnings("unchecked")
	public List<User> listForApprovingUser() throws Exception {
		String query = "from User u where u.approvalStatus="
				+ Constants.APPROVAL_STATUS_INACTIVE;
		return (List<User>) daoManager.listByHql(query);
	}

	public boolean updateApprovalStatus(long id) throws Exception {
		String query = "update User u  set u.approvalStatus ="
				+ Constants.APPROVAL_STATUS_ACTIVE + "where u.id=" + id;
		return daoManager.updateByHql(query);
	}

	public boolean updateAccountStatus(long id) throws Exception {
		String query = "update User u  set u.accountStatus ="
				+ Constants.ACCOUNT_STATUS_INACTIVE + "where u.id=" + id;
		return daoManager.updateByHql(query);
	}
	
	
	public boolean updateAccountStatusToActive(long id) throws Exception {
		String query = "update User u  set u.accountStatus ="
				+ Constants.ACCOUNT_STATUS_ACTIVE + "where u.id=" + id;
		return daoManager.updateByHql(query);
	}
	

	public boolean updatePassword(String password, long userId)
			throws Exception {
		String query = "update User u set  u.password= '" + password
				+ "' where id=" + userId;
		return daoManager.updateByHql(query);
	}

	
	// public static void main(String[] args) {
	// try {
	// for (int j = 1; j < 7; j++) {
	//
	// System.out.println(new UserManager().updateAccountStatus(j));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
