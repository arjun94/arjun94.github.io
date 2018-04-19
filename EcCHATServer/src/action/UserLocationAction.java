package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import manager.UserLocationManager;
import manager.UserManager;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

import entity.User;
import entity.UserLocation;

public class UserLocationAction extends ActionSupport {

	private static final long serialVersionUID = -1444652432971337277L;
	private double latitude;
	private double longitude;
	private long userId;
	private String xmlString;
	private String resultString;
	private long userLocationId;
	private UserLocation userLocation;
	private List<UserLocation> userLocations = new ArrayList<UserLocation>();
	

	public String save() {
		String result = ERROR;
		User user = new User();
		UserLocation userLocation = null;
		UserLocation userLocationTemp = null;
		try {
			userLocation = new UserLocation();
			user = new UserManager().getById(userId);
			if (user != null) {
				userLocationTemp = new UserLocationManager().getByUser(user);
				if (userLocationTemp == null) {
					userLocation.setUser(user);
					userLocation.setLatitude(latitude);
					userLocation.setLongitude(longitude);
					userLocation.setTime(new Date());
					if (new UserLocationManager().save(userLocation)) {
						result = SUCCESS;
						resultString = "Saved Successfully";
					} else {
						resultString = "operation failed";
					}
				} else {
					userLocationTemp.setLatitude(latitude);
					userLocationTemp.setLongitude(longitude);
					if (new UserLocationManager().update(userLocationTemp)) {
						result = SUCCESS;
						resultString = "Saved Successfully";
					} else {
						resultString = "operation failed";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(user);
		return result;
	}

	public String update() {
		String result = ERROR;
		User user = new User();
		userLocation = new UserLocation();
		try {
			user = new UserManager().getById(userId);
			System.out.println("in update location>>>>" + user.getName());
			userLocation = new UserLocationManager().getById(userLocationId);
			userLocation.setUser(user);
			userLocation.setLatitude(latitude);
			userLocation.setLongitude(longitude);
			userLocation.setTime(new Date());
			if (new UserLocationManager().update(userLocation)) {
				result = SUCCESS;
				resultString = "updated Successfully";
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

	public String delete() {
		String result = ERROR;
		userLocation = new UserLocation();
		try {
			userLocation = new UserLocationManager().getById(userLocationId);
			if (new UserLocationManager().delete(userLocation)) {
				result = SUCCESS;
				resultString = "deleted Successfully";
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

	public String get() {
		String result = ERROR;
		userLocation = null;
		try {
			userLocation = new UserLocationManager().getById(userLocationId);
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(userLocation);
		return result;
	}

	public String getByUser() {
		String result = ERROR;
		userLocation = new UserLocation();
		User user = null;
		try {
			System.out.println(userId);
			user = new UserManager().getById(userId);
			userLocation = new UserLocationManager().getByUser(user);
			userLocation.getUser().setFriends(null);
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(userLocation);
		System.out.println(xmlString);
		return result;
	}

	public String list() {
		String result = ERROR;
		userLocation = new UserLocation();
		try {
			userLocations = new UserLocationManager().list();
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		XStream xStream = new XStream();
		xmlString = xStream.toXML(userLocations);
		return result;
	}

	

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public long getUserLocationId() {
		return userLocationId;
	}

	public void setUserLocationId(long userLocationId) {
		this.userLocationId = userLocationId;
	}

	public UserLocation getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(UserLocation userLocation) {
		this.userLocation = userLocation;
	}

	public List<UserLocation> getUserLocations() {
		return userLocations;
	}

	public void setUserLocations(List<UserLocation> userLocations) {
		this.userLocations = userLocations;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
