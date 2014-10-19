package HibernateConnect;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConnect {
	private static SessionFactory sessionFactory =  null;
    
    static {
        try {
                //creates the session factory from hibernate.cfg.xml
                sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
              e.printStackTrace();
        }
    }

	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
