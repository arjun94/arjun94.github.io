package manager;


import java.util.List;

import util.Constants;

import dao.DAOManager;
import entity.FriendRequest;

public class FriendRequestManager {
	DAOManager daoManager = new DAOManager();

	public boolean save(FriendRequest friendRequest) {
		return daoManager.save(friendRequest);
	}

	public boolean update(FriendRequest friendRequest) throws Exception {
		return daoManager.update(friendRequest);
	}

	public boolean delete(FriendRequest friendRequest) throws Exception {
		return daoManager.delete(friendRequest);
	}

	public FriendRequest getById(long id) throws Exception {
		return (FriendRequest) daoManager.get(FriendRequest.class, id);
	}
	
	public FriendRequest checkRequest(long fromUser,long toUser) throws Exception{
		String query="From FriendRequest where fromUser="+fromUser+" and toUser="+toUser;
		return (FriendRequest)daoManager.getByHql(query);
	}

	@SuppressWarnings("unchecked")
	public List<FriendRequest> list() throws Exception {
		return daoManager.list(FriendRequest.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<FriendRequest> listOfRequests(long userId) throws Exception {
		String query="from FriendRequest fr where fr.toUser="+ userId+ "and fr.status="+Constants.REQUEST_STATUS_ACTIVE;
		return (List<FriendRequest>)daoManager.listByHql(query);
	}

	/*public static void main(String[] args) {
//		FriendRequest request = new FriendRequest();
//		request.setDate(new Date());
		try {
			System.out.println(new FriendRequestManager().listOfRequests(1).get(0).getFromUser().getName());
//			request.setFromUser(new UserManager().getById(1));
//			request.setToUser(new UserManager().getById(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		request.setStatus(0);
//		if (new FriendRequestManager().save(request)) {
//			System.out.println("success");
//		}

	}*/

}
