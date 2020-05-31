package br.unip.aps.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import config.HibernateConfig;

@SuppressWarnings("unchecked")
public class DaoGenerico<T> {
	//c
	public void salva(T t) {
		Transaction transaction = null;
		Session session = null;
		try{
			session = HibernateConfig.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			session.save(t);
			
			transaction.commit();			
		} catch(Exception e){
			if(transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	//r
	
	public List<T> buscaTodos(Class<T> clazz){
		Transaction transaction = null;
		Session session = null;
		List<T>  ret = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			
			transaction = session.beginTransaction();
			
			ret = (List<T>) session.createQuery("from " + clazz.getName()).list();
			
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return ret;
	}
	public T buscaPorId(Class<T> clazz, int id){
		Transaction transaction = null;
		Session session = null;
		T ret = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			ret = (T) session.get(clazz.getName(), id);
			
			transaction.commit();
		}catch(Exception e){
			if(transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return ret;
	}
	//u
	public void atualiza(T t) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			session.update(t);
			
			transaction.commit();
		} catch(Exception e){
			if(transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	//d
	public void deleta(T t) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateConfig.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			session.delete(t);
			
			t = null;
			
			transaction.commit();
		} catch(Exception e){
			if(transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
}
