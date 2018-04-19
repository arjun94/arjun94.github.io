package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConnection {

	public static final SessionFactory factory;
	
	public DBConnection(){
		
	}
	static{
		try{
			factory= new Configuration().configure().buildSessionFactory();
		}catch(Throwable e){
			System.err.println("SessionFactory creation Failed "+e);
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
	public Session getSession(){
		
		Session session=null;
		try{
			session=factory.openSession();
		}catch(Exception e){
			e.printStackTrace();
		}
		return session;
	}
	public void closeSession(Session session){
		try{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
