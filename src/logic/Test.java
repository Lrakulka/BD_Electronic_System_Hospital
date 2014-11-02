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

import org.hibernate.Hibernate;
import org.hibernate.Session;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Session session = DatabaseConnect.getSessionFactory().openSession();
        session.beginTransaction();
 
        User user1 = new User("Svin Petr",(short) 0, "+43345676543", "qwerty");
        User user2 = new User("Svin Baca",(short) 0, "+97532642225", "ytrewq");
        Note note1 = new Note(false, new Date(1000), "This is test of hibernete" +
        		" relationship");
        Note note2 = new Note(true, new Date(1234), "This is test of hibernete" +
        		" relationship");
        Card card = new Card("Bacilev Andrey", (short) 28, Gender.male, false);
        hibernate.Session session1 = new hibernate.Session();
        hibernate.Session session2 = new hibernate.Session();
        session1.setResult(true);
        session2.setResult(false);
        Diagnos diagnos1 = new Diagnos("U bolnogo otsustvuet mozg");
        Diagnos diagnos2 = new Diagnos("U bolnogo snova otsustvuet mozg");
        Disease disease1 = new Disease("Net mozga.Netu golovnogo mozga");
        Disease disease2 = new Disease("Net mozga.Netu prodolgovatogo mozga");
//        Group mainGroup = new Group("Net mozga");
//        Group slaveGroup1 = new Group("Netu golovnogo mozga");
//        Group slaveGroup2 = new Group("Netu prodolgovatogo mozga");
        
//        slaveGroup1.setGroup(mainGroup);
//        slaveGroup2.setGroup(mainGroup);
        diagnos1.setDisease(disease1);
        diagnos2.setDisease(disease2);
        session1.setDiagnos(diagnos1);
        session2.setDiagnos(diagnos2);
        session1.setCard(card);
        session2.setCard(card);
        note1.setCard(card);
        note2.setCard(card);
        note1.setUser(user1);
        note2.setUser(user2);
        
        session.save(card);
        session.save(user1);
        session.save(user2);
        session.save(note1);
        session.save(note2);
        session.save(disease1);
        session.save(disease2);
        session.save(diagnos1);
        session.save(diagnos2);
        session.save(session1);
        session.save(session2);
        
//        session.save(mainGroup);
//        session.save(slaveGroup1);
//        session.save(slaveGroup2);
        
//        session.save(user);
//        
// 
//        note1.setUser(user);
//        note2.setUser(user);
//         
//        session.save(note1);
//        session.save(note2);
 
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
