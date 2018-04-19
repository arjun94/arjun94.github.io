package manager;

import util.Constants;
import dao.DAOManager;
import entity.User;


public class InstallManager {
	private DAOManager daoManager = new DAOManager();
	
	public boolean install() throws Exception {
		User user = new User();
		user.setUserName(Constants.ADMIN_USERNAME);
		user.setPassword(Constants.ADMIN_PASSWORD);
		user.setRole(Constants.USERROLE_ADMIN);
		return daoManager.save(user);
	}
}
