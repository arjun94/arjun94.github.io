package entity;

import java.util.Date;

public class FriendRequest {
private long id;
private User fromUser;
private User toUser;
private Date date;
private int status;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public User getFromUser() {
	return fromUser;
}
public void setFromUser(User fromUser) {
	this.fromUser = fromUser;
}
public User getToUser() {
	return toUser;
}
public void setToUser(User toUser) {
	this.toUser = toUser;
}
}
