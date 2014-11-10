package logic;


import hibernateConnect.DatabaseConnect;

import org.hibernate.Session;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session session = DatabaseConnect.getSessionFactory().openSession();
        session.beginTransaction();
        String tablesName[]  = {"card", "diagnosis", "diseases", "groups", "notes",
        		"session", "users"};
        session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for(int i = 0; i < tablesName.length; ++i) {
        	session.createSQLQuery("TRUNCATE " + tablesName[i]).executeUpdate();
        }
        session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        session.getTransaction().commit();
        session.close();
	}

}
