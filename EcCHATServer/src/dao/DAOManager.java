package dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import entity.User;


public class DAOManager {

	DBConnection dbConnection = new DBConnection();

	public boolean save(Object object) {

		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			session = dbConnection.getSession();
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			dbConnection.closeSession(session);
		}
		return result;
	}

	public boolean delete(Object object) {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = dbConnection.getSession();
			transaction = session.beginTransaction();
			session.delete(object);
			transaction.commit();
			result = true;
		} catch (Exception e) {
			transaction.rollback();
		} finally {
			dbConnection.closeSession(session);
		}
		return result;
	}

	public boolean update(Object object) {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = dbConnection.getSession();
			transaction = session.beginTransaction();
			session.update(object);
			transaction.commit();
			result = true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			dbConnection.closeSession(session);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Object getObject(Class clas, String id) {
		Session session = null;
		Object object = null;
		try {
			session = dbConnection.getSession();
			object = session.get(clas, id);

		} catch (Exception e) {
		} finally {
			dbConnection.closeSession(session);
		}

		return object;
	}

	@SuppressWarnings("rawtypes")
	public Object get(Class clas, Long id) {
		Session session = null;
		Object object = null;
		try {
			session = dbConnection.getSession();
			object = session.get(clas, id);

		} catch (Exception e) {

		} finally {
			dbConnection.closeSession(session);
		}
		return object;
	}

	@SuppressWarnings("rawtypes")
	public Object getObjectbyProperty(Class clas, String field, Object value) {
		Object object = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = dbConnection.getSession();
			criteria = session.createCriteria(clas);
			criteria.add(Restrictions.eq(field, value));
			object = criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeSession(session);
		}

		return object;
	}

	@SuppressWarnings("rawtypes")
	public List list(Class clas) {

		Session session = null;
		List objects = null;
		Criteria criteria = null;
		try {
			session = dbConnection.getSession();
			criteria = session.createCriteria(clas);
			objects = criteria.list();

		} catch (Exception e) {

		} finally {
			dbConnection.closeSession(session);
		}
		return objects;
	}

	@SuppressWarnings("rawtypes")
	public List listProperty(Class clas, String field) {
		List list = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = dbConnection.getSession();
			criteria = session.createCriteria(clas);
			criteria.setProjection(Projections.property(field));
			list = criteria.list();
		} catch (Exception e) {

		} finally {
			dbConnection.closeSession(session);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List listByHql(String hql) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = dbConnection.getSession();
			Query q = session.createQuery(hql);
			list = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getCause().getMessage());
		} finally {
			dbConnection.closeSession(session);
		}
		return list;
	}

	

	public boolean updateByHql(String hql) throws Exception {
		Session session = null;
		boolean result = false;
		Transaction transaction = null;

		try {
			session = dbConnection.getSession();
			transaction = session.beginTransaction();
			Query q = session.createQuery(hql);
			q.executeUpdate();
			transaction.commit();
			result = true;
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
			throw new Exception(ex.getCause().getMessage());
		} finally {
			dbConnection.closeSession(session);
		}
		return result;
	}

	public Object getByHql(String hql) throws Exception {
		Session session = null;
		Object object = null;
		try {
			session = dbConnection.getSession();
			Query q = session.createQuery(hql);
			object = q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getCause().getMessage());
		} finally {
			dbConnection.closeSession(session);
		}
		return object;
	}
	@SuppressWarnings("rawtypes")
	public List listBySearchKeyword(String searchKeyword)
			throws Exception {
		Session session = null;
		List list = null;
		try {
			session = dbConnection.getSession();
			Criteria criteria = session.createCriteria(User.class,
					"user");
			//criteria.createAlias("itemAllocation.item", "item");
			//criteria.add(Restrictions.eq("itemAllocation.store", store));
			criteria.add(Restrictions.like("user.name", searchKeyword,
					MatchMode.START));
			list=criteria.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getCause().getMessage());
		} finally {
			dbConnection.closeSession(session);
		}
		return list;
	}
}
