package logic;


import hibernate.Note;
import hibernate.User;
import hibernateConnect.DatabaseConnect;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import org.hibernate.Hibernate;
import org.hibernate.Session;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User user;
        Session session = DatabaseConnect.getSessionFactory().openSession();
        session.beginTransaction();
 
        user = new User();
        user.setName("Petr");
        user.setAccess_level((short) 1);
        user.setPhone("+3254645434");
        user.setPwd("00000");
        session.save(user);
         
        Note note1 = new Note(1, 1, false, new Date(1000), "qwerty");
        Note note2 = new Note(1, 1, false, new Date(1000), "123456");
 
        note1.setUser(user);
        note2.setUser(user);
         
        session.save(note1);
        session.save(note2);
 
        session.getTransaction().commit();
        session.close();
//		Session session = DatabaseConnect.getSessionFactory().openSession();
//		user = OperationsWithUser.getAllUsers().get(8);
//		
//		Serializable userid = 30;
//		user = (User) session.load(User.class, userid);
//		Hibernate.initialize(user);
//		session.close();
//		System.out.print(user.getNotes().toString());
//		ArrayList<Note> list = user.getAllNotes();//new ArrayList<Note>(user.getNotes());
//		Note note;
//		for(int i = 0; i < list.size(); ++i) {
//			note = list.get(i);
//			System.out.println(note.getHidden_note());
//		}
		
	}

}
