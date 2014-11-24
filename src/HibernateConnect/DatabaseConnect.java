package hibernateConnect;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**Клас призначиний для організації підключення до бази даних*/
public class DatabaseConnect {
	/**Підключення до бази даних*/
	private static SessionFactory sessionFactory =  null;    

    static {
    	Configuration config = new Configuration();
    	config.configure("hibernate.cfg.xml");
    	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
    			applySettings(config.getProperties()).build();
        sessionFactory = new Configuration().configure().
        		buildSessionFactory(serviceRegistry);
    }

    /**@return повертає підключення до бази даних*/
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
