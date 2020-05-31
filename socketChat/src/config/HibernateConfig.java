package config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import br.unip.aps.model.Cliente;
import br.unip.aps.model.Mensagem;

public class HibernateConfig {
private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				
				Configuration config = new Configuration();
				
				Properties prop = new Properties();
				
				//prop do bd
				prop.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
				prop.put(Environment.URL, "jdbc:mysql://localhost:3306/aps_20201");
				prop.put(Environment.USER, "root");
				prop.put(Environment.PASS, "root");
				prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
				
				prop.put(Environment.SHOW_SQL, "true");
				prop.put(Environment.HBM2DDL_AUTO, "create-drop");
				
				config.setProperties(prop);
				
				config.addAnnotatedClass(Cliente.class);
				config.addAnnotatedClass(Mensagem.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
														.applySettings(config.getProperties())
														.build();
				
				sessionFactory = config.buildSessionFactory(serviceRegistry);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
