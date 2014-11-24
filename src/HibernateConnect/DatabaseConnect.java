package hibernateConnect;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**���� ����������� ��� ���������� ���������� �� ���� �����*/
public class DatabaseConnect {
	/**ϳ��������� �� ���� �����*/
	private static SessionFactory sessionFactory =  null;    

    static {
    	Configuration config = new Configuration();
    	config.configure("hibernate.cfg.xml");
    	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
    			applySettings(config.getProperties()).build();
        sessionFactory = new Configuration().configure().
        		buildSessionFactory(serviceRegistry);
    }

    /**@return ������� ���������� �� ���� �����*/
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
