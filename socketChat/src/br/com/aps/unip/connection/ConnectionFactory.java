package br.com.aps.unip.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChatAps");
	
	public EntityManager getConnection() {
		return emf.createEntityManager();
		
	}
}

