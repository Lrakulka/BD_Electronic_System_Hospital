package logic;


import hibernate.Card;
import hibernate.Diagnos;
import hibernate.Disease;
import hibernate.Gender;
import hibernate.Group;
import hibernate.Note;
import hibernate.User;
import hibernateConnect.DatabaseConnect;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
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
