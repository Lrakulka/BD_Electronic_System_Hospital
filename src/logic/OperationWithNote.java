package logic;

import hibernate.Note;
import hibernate.Gender;
import hibernateConnect.DatabaseConnect;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import exceptions.ProjectException;

	
public class OperationWithNote {
		public static boolean delete(Note note) {
			 {
				Session session = null;
	            try {
	                session = DatabaseConnect.getSessionFactory().openSession();
	                session.beginTransaction();
	                session.delete(note);
	                session.getTransaction().commit();
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, e.getMessage(), 
	                		"Ошибка I/O", JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            return true;
		}
	}
	
	public static boolean register(Note note) {
		Session session = null;
		try {
			session = DatabaseConnect.getSessionFactory().openSession();			
			Transaction transaction = session.beginTransaction();				
            session.save(note);
            transaction.commit();
		} catch (HibernateException e) {
			exceptions.ProjectException.sentExceptionMassege("Problem " +
					e.getMessage());
			return false;
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}			
		}
		return true;
	}	
	
	
	
	 
	public static List<Note> getAllNotes() {
		Session session = null;
        List<Note> Notes = null;
        try {
            session = DatabaseConnect.getSessionFactory().openSession();
            Notes = session.createCriteria(Note.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
            		"Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Notes;
	}
	
	public static List<Note> getAllNotesFiltr(String name, 
			Gender gender, Short age_levelLow, Short age_levelHight) {
		Session session = null;
        List<Note> Notes = null;
        try {
            session = DatabaseConnect.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Note.class).
            		add(Restrictions.like("name", "%" + name + "%")).add(
            		Restrictions.between("age", age_levelLow, 
            				age_levelHight)).addOrder(Order.asc("name"));
            if (gender != null) 
            	criteria= criteria.add(Restrictions.eq("sex", gender));
            Notes = criteria.list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
            		"Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Notes;
	}
	
	public static void deleteAllNotes() {
		for(Note note:getAllNotes())
			delete(note);
	}
}

