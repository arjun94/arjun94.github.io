package testing;

import manager.UserLocationManager;
import entity.UserLocation;
import action.UserLocationAction;

public class Testing {
public static void main(String[] args) {
	new UserLocationManager().save(new UserLocation());
}
}
