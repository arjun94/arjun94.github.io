package manager;

import java.util.List;

import dao.DAOManager;
import entity.Feedback;

public class FeedbackManager {
public boolean save(Feedback feedback){
	return new DAOManager().save(feedback);
}

@SuppressWarnings("unchecked")
public List<Feedback> list(){
	return new DAOManager().list(Feedback.class);
}
}
