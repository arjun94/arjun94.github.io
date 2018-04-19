package action;

import java.util.ArrayList;
import java.util.List;

import manager.FeedbackManager;
import manager.UserManager;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

import entity.Feedback;

public class FeedbackAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4668224434716491018L;
	private Feedback feedbackobj;
	private String feedback;
	private String userName;
	private String xmlString;
	private List<Feedback>feedbacks= new ArrayList<Feedback>();
	public String save(){
		String result=ERROR;
		feedbackobj=null;
		try {
			feedbackobj= new Feedback();
			feedbackobj.setFeedback(feedback);
			feedbackobj.setUser(new UserManager().getByUserName(userName));
			if(new FeedbackManager().save(feedbackobj)){
				result=SUCCESS;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		xmlString= new XStream().toXML(result);
		return result;
		
	}
	
	public String list(){
		try{
			feedbacks= new FeedbackManager().list();
			System.out.println(feedbacks.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	return SUCCESS;
	}
	
	
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getXmlString() {
		return xmlString;
	}
	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public Feedback getFeedbackobj() {
		return feedbackobj;
	}

	public void setFeedbackobj(Feedback feedbackobj) {
		this.feedbackobj = feedbackobj;
	}

}
