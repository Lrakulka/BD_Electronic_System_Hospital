package logic;

import hibernate.CommonField;
import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import exceptions.ProjectException;
/**Абстрактний клас призначиний для реалізації загальних операцій з даними із бази даних*/
abstract class CommonOperations<I extends CommonField, Filtr> {
	/**Абстрактний метод, який має реалізувати фільтр
	 * @param session сесія з базою даних
	 * @param filtr параметри для фільтрації даних з бази даних 
	 * @return запит до бази даних для витягування всіх об'єктів, які задовільняють фільтр*/
	abstract Criteria getFiltrCriteria(Session session, Filtr filtr);
	
	/**Абстрактний метод, який має реалізувати запит до бази даних
	 * @param session сесія з базою даних
	 * @return запит до бази даних для витягування всіх об'єктів класу "I"*/
	abstract Criteria getAllObjCriteria(Session session);
	
	/**Абстрактний метод, який реалізує критерії, за якими шукається дані в таблиці
	 * @param session сесія з базою даних
	 * @param object об'єкт, який шукається в базі даних 
	 * @return запит до бази даних для витягування всіх об'єктів, які задовільняють критерії*/
	abstract Criteria getRegisterCriteria(Session session, I object);
	
	/**Абстрактний метод, який реалізує використання методу getRegisterCriteria
	 * @return повертати true потрібно, коли використання метода getRegisterCriteria не потрібно
	 * (збільшує продуктивність), при повернені false метод getRegisterCriteria
	 * буде використовуватися
	 */
	abstract boolean isRegistedMethodNotNeed();
	
	/**Метод видаляє із бази даних всі дані пов'язанні з object, для пошука 
	 * індефікатора використовується getRegisterCriteria
	 * @param object дані, які треба видалити
	 * @return true якщо видалення пройшло успішно і false якщо ні*/	
	public boolean delete(I object) {
		if ((object = isRegisted(object)) == null)
			return false;
		else {
			Session session = null;
            try {
                session = DatabaseConnect.getSessionFactory().openSession();
                session.beginTransaction();
                session.delete(object);
                session.getTransaction().commit();
            } catch (Exception e) {
            	ProjectException.showMessage("Delete " + object.toString(), e);
            	return false;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            return true;
		}
	}
	
	/**Видаляє дані по їх індефікатору і всі інші дані пов'язані з ним. 
	 * Не використовує getRegisterCriteria
	 * @param object дані для видалення
	 * @return true якщо видалення пройшло успішно і false якщо ні*/
	public boolean deleteById(I object) {
		Session session = null;
        try {
            session = DatabaseConnect.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (Exception e) {
        	ProjectException.showMessage("Delete " + object.toString(), e);
        	return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
	}
	
	/**Метод витягує всі дані з таблиці, які задоволбняють фільтр
	 * @param filtr параметри фільтра
	 * @return повертає колекцію об'єктів, що задовольняють фільтр*/
	@SuppressWarnings("unchecked")
	protected ArrayList<I> getAllObjSatisfyFiltr(Filtr filtr) {
		Session session = null;
		ArrayList<I> users = null;
        try {
            session = DatabaseConnect.getSessionFactory().openSession();
            users = new ArrayList<>(getFiltrCriteria(session, filtr).list());
        } catch (Exception e) {
        	ProjectException.showMessage("AllObjSatisfyFiltr " + filtr.toString(), e);
        	return users;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
	}
	
	/**Метод для регістрації об'єкта
	 * @param object об'єкт для збереження в базі даних
	 * @return повертає true якщо об'єкт успішно збережень у базу даних, false якщо ні*/
	public boolean register(I object) {
		Session session = null;
		try {
			session = DatabaseConnect.getSessionFactory().openSession();
			if ( isRegistedMethodNotNeed() || isRegisted(object) == null) {
				Transaction transaction = session.beginTransaction();				
	            session.save(object);
	            transaction.commit();
			}
			else return false;
		} catch (HibernateException e) {
			ProjectException.showMessage("Register " + object.toString(), e);
			return false;
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}			
		}
		return true;
	}	
	
	/**Перевіряє чи об'єкт вже є в базі даних для цього використовує 
	 * пошук getRegisterCriteria
	 * @param object об'єкт для перевірки
	 * @return повертає цей об'єкт із бази даних або null якщо таких даних немає*/
	public I isRegisted(I object) {
		if ( !isRegistedMethodNotNeed()) {
			Session session = null;
			try {
				session = DatabaseConnect.getSessionFactory().openSession();
				@SuppressWarnings("unchecked")
				List<I> objects = getRegisterCriteria(session, object).list();
				switch (objects.size()) {
					case 0 : return null;
					case 1 : object = objects.get(0); break;
					default : throw new ProjectException(String.valueOf(objects.size()) +
							" identical records");
				}
			} catch (HibernateException e) {
				ProjectException.showMessage("IsRegisted Hibernet " + object.toString(), e);
				return null;
			} catch (ProjectException e) {
				e.showMessage();
				return null;
			} finally {
				if(session != null && session.isOpen()) {
					session.close();
				}			
			}
		}
		return object;
	}
	
	/**Метод обновлює дані в базі даних
	 * @param object дані, які необхідно оновити
	 * @return true якщо оновлення виконано успішно, false якщо ні*/
	public boolean update(I object) {
		I curObject = object;
		if ((curObject = isRegisted(curObject)) == null)
			return false;
		else {
			Session session = null;
			object.setId(curObject.getId());
            try {
                session = DatabaseConnect.getSessionFactory().openSession();
                session.beginTransaction();
                session.update(object);
                session.getTransaction().commit();
            } catch (Exception e) {
            	ProjectException.showMessage("Ubdate " + object.toString(), e);
            	return false;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            return true;
		}
	}
	 
	/**Метод повертає всі дані з таблиці
	 * @return повертає колекцію всіх даних із таблиці*/
	@SuppressWarnings("unchecked")
	public ArrayList<I> getAllObj() {
		Session session = null;
		ArrayList<I> users = null;
        try {
            session = DatabaseConnect.getSessionFactory().openSession();
            users = new ArrayList<>(getAllObjCriteria(session).list());
        } catch (Exception e) {
        	ProjectException.showMessage("AllObj ", e);
        	return users;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
	}
	
	/**Видаляє всі дані з таблиці один за одгим*/
	public void deleteAllObj() {
		for(I obj: getAllObj())
			delete(obj);
	}

}
