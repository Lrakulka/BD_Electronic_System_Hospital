package logic;
import hibernate.Card;
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

	
public class OperationsWithCard {
		public static boolean delete(Card card) {
			if ((card = isCardRegisted(card)) == null)
				return false;
			else {
				Session session = null;
	            try {
	                session = DatabaseConnect.getSessionFactory().openSession();
	                session.beginTransaction();
	                session.delete(card);
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
	
	public static boolean register(Card card) {
		Session session = null;
		try {
			session = DatabaseConnect.getSessionFactory().openSession();
			List<Card> cards = session.createCriteria(Card.class).add( 
					Restrictions.like("name", card.getName())).list();
			if ( cards.isEmpty()) {
				Transaction transaction = session.beginTransaction();				
	            session.save(card);
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
	
	public static Card isCardRegisted(Card card) {
		Session session = null;
		try {
			session = DatabaseConnect.getSessionFactory().openSession();
			List<Card> cards = session.createCriteria(Card.class).add( 
					Restrictions.like("name", card.getName())).add(
					Restrictions.like("age", card.getAge())).add(
							Restrictions.like("sex", card.getSex())).list();
			switch (cards.size()) {
				case 0 : return null;
				case 1 : card = cards.get(0); break;
				default : throw new ProjectException(String.valueOf(cards.size()) +
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
		return card;
	}
	
	public static boolean update(Card card) {
		Card curCard = card;
		if ((curCard = isCardRegisted(curCard)) == null)
			return false;
		else {
			Session session = null;
			card.setId(curCard.getId());
            try {
                session = DatabaseConnect.getSessionFactory().openSession();
                session.beginTransaction();
                session.update(card);
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
	 
	public static List<Card> getAllCards() {
		Session session = null;
        List<Card> cards = null;
        try {
            session = DatabaseConnect.getSessionFactory().openSession();
            cards = session.createCriteria(Card.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
            		"Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cards;
	}
	
	public static List<Card> getAllCardsFiltr(String name, 
			Gender gender, Short age_levelLow, Short age_levelHight) {
		Session session = null;
        List<Card> cards = null;
        try {
            session = DatabaseConnect.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Card.class).
            		add(Restrictions.like("name", "%" + name + "%")).add(
            		Restrictions.between("age", age_levelLow, 
            				age_levelHight)).addOrder(Order.asc("name"));
            if (gender != null) 
            	criteria= criteria.add(Restrictions.eq("sex", gender));
            cards = criteria.list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
            		"Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cards;
	}
	
	public static void deleteAllCards() {
		for(Card card:getAllCards())
			delete(card);
	}
}
