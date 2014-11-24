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
/**����������� ���� ����������� ��� ��������� ��������� �������� � ������ �� ���� �����*/
abstract class CommonOperations<I extends CommonField, Filtr> {
	/**����������� �����, ���� �� ���������� ������
	 * @param session ���� � ����� �����
	 * @param filtr ��������� ��� ���������� ����� � ���� ����� 
	 * @return ����� �� ���� ����� ��� ����������� ��� ��'����, �� ������������ ������*/
	abstract Criteria getFiltrCriteria(Session session, Filtr filtr);
	
	/**����������� �����, ���� �� ���������� ����� �� ���� �����
	 * @param session ���� � ����� �����
	 * @return ����� �� ���� ����� ��� ����������� ��� ��'���� ����� "I"*/
	abstract Criteria getAllObjCriteria(Session session);
	
	/**����������� �����, ���� ������ ������, �� ����� �������� ��� � �������
	 * @param session ���� � ����� �����
	 * @param object ��'���, ���� �������� � ��� ����� 
	 * @return ����� �� ���� ����� ��� ����������� ��� ��'����, �� ������������ ������*/
	abstract Criteria getRegisterCriteria(Session session, I object);
	
	/**����������� �����, ���� ������ ������������ ������ getRegisterCriteria
	 * @return ��������� true �������, ���� ������������ ������ getRegisterCriteria �� �������
	 * (������ �������������), ��� �������� false ����� getRegisterCriteria
	 * ���� �����������������
	 */
	abstract boolean isRegistedMethodNotNeed();
	
	/**����� ������� �� ���� ����� �� ��� ���'����� � object, ��� ������ 
	 * ������������ ��������������� getRegisterCriteria
	 * @param object ���, �� ����� ��������
	 * @return true ���� ��������� ������� ������ � false ���� �*/	
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
	
	/**������� ��� �� �� ������������ � �� ���� ��� ���'���� � ���. 
	 * �� ����������� getRegisterCriteria
	 * @param object ��� ��� ���������
	 * @return true ���� ��������� ������� ������ � false ���� �*/
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
	
	/**����� ������ �� ��� � �������, �� ������������� ������
	 * @param filtr ��������� �������
	 * @return ������� �������� ��'����, �� ������������� ������*/
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
	
	/**����� ��� ���������� ��'����
	 * @param object ��'��� ��� ���������� � ��� �����
	 * @return ������� true ���� ��'��� ������ ��������� � ���� �����, false ���� �*/
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
	
	/**�������� �� ��'��� ��� � � ��� ����� ��� ����� ����������� 
	 * ����� getRegisterCriteria
	 * @param object ��'��� ��� ��������
	 * @return ������� ��� ��'��� �� ���� ����� ��� null ���� ����� ����� ����*/
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
	
	/**����� �������� ��� � ��� �����
	 * @param object ���, �� ��������� �������
	 * @return true ���� ��������� �������� ������, false ���� �*/
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
	 
	/**����� ������� �� ��� � �������
	 * @return ������� �������� ��� ����� �� �������*/
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
	
	/**������� �� ��� � ������� ���� �� �����*/
	public void deleteAllObj() {
		for(I obj: getAllObj())
			delete(obj);
	}

}
