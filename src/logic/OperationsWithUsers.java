package logic;
import hibernate.Users;
import hibernateConnect.DatabaseConnect;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import exceptions.ProjectException;



public class OperationsWithUsers {
	public static boolean delete(Users user) {
		if ((user = isUserRegisted(user)) == null)
			return false;
		else {
			Session session = null;
            try {
                session = DatabaseConnect.getSessionFactory().openSession();
                session.beginTransaction();
                session.delete(user);
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
	
	public static boolean register(Users user) {
		Session session = null;
		try {
			session = DatabaseConnect.getSessionFactory().openSession();
			List<Users> users = session.createCriteria(Users.class).add( 
					Restrictions.like("name", user.getName())).list();
			if ( users.isEmpty()) {
				Transaction transaction = session.beginTransaction();				
	            session.save(user);
	            transaction.commit();
			}
			else return false;
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
	
	public static Users isUserRegisted(Users user) {
		Session session = null;
		try {
			session = DatabaseConnect.getSessionFactory().openSession();
			List<Users> users = session.createCriteria(Users.class).add( 
					Restrictions.like("name", user.getName())).add(
					Restrictions.like("pwd", user.getPwd())).list();
			switch (users.size()) {
				case 0 : return null;
				case 1 : user = users.get(0); break;
				default : throw new ProjectException(String.valueOf(users.size()) +
						" identical records");
			}
		} catch (HibernateException e) {
			exceptions.ProjectException.sentExceptionMassege("Problem " +
		e.getMessage());
			return null;
		} catch (ProjectException e) {
			exceptions.ProjectException.sentExceptionMassege("Problem " +
					e.getMessage());
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}			
		}
		return user;
	}
	
	public static boolean update(Users user) {
		Users curUser = user;
		if ((curUser = isUserRegisted(curUser)) == null)
			return false;
		else {
			Session session = null;
			user.setId(curUser.getId());
            try {
                session = DatabaseConnect.getSessionFactory().openSession();
                session.beginTransaction();
                session.update(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", 
                		JOptionPane.OK_OPTION);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            return true;
		}
	}
	 
	public static List<Users> getAllUsers() {
		return getAllUsersFiltr("", "", new Short((short) 0), new Short((short)2));
	}
	
	@SuppressWarnings({"deprecation", "unchecked" })
	public static List<Users> getAllUsersFiltr(String name, 
			String phone, Short access_levelLow, Short access_levelHight) {
		Session session = null;
        List<Users> users = null;
        try {
            session = DatabaseConnect.getSessionFactory().openSession();
            users = session.createCriteria(Users.class).add(Restrictions.like("name", 
            		"%" + name + "%")).add(Restrictions.like("phone", "%" + 
            		phone + "%")).add(Restrictions.between("access_level", 
            		access_levelLow, access_levelHight)).addOrder(Order.asc("name")
            				).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
            		"Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
	}
	
	public static void deleteAllUsers() {
		for(Users user:getAllUsers())
			delete(user);
	}
}
