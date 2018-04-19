package manager;

import java.util.List;

import dao.DAOManager;
import entity.User;
import entity.UserLocation;

public class UserLocationManager {
	DAOManager daoManager = new DAOManager();

	public boolean save(UserLocation userLocation) {
		return daoManager.save(userLocation);
	}

	public boolean update(UserLocation userLocation) throws Exception {
		return daoManager.update(userLocation);
	}

	public boolean delete(UserLocation userLocation) throws Exception {
		return daoManager.delete(userLocation);
	}

	public UserLocation getById(long id) throws Exception {
		return (UserLocation) daoManager.get(UserLocation.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<UserLocation> list() throws Exception {
		return daoManager.list(UserLocation.class);
	}

	public UserLocation getByUser(User user) throws Exception {
		String query="from UserLocation u where u.user="+user.getId();
		return (UserLocation)daoManager.getByHql(query);
	}
/*public static void main(String[] args) throws Exception {
	System.out.println( (new UserLocationManager().getByUser(new UserManager().getById(6))).getId());
}*/
	
	/*public static void main(String[] args) {
		UserLocation location= new UserLocation();
		location.setLatitude(8.45464);
		location.setLongitude(76.98989);
		location.setTime(new Date());
		try {
			location.setUser(new UserManager().getById(1));
			if(new UserLocationMnager().save(location)){
				System.out.println("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
}
