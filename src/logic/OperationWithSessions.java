package logic;

import hibernate.Session;

import java.util.ArrayList;

import logic.OperationWithSessions.Filtr;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class OperationWithSessions extends CommonOperations<Session, Filtr> {
	
	/**���� ���������� ��� ��������� ����� �� ��'��� ����� �����*/
	private static OperationWithSessions oper = null;
	
	/**����� ������� ��'��� ����� �����*/
	private OperationWithSessions() {}
	
	/**����� ������� ��'��� ����� �����
	 *  @return ������� ��'��� �����*/
	public static OperationWithSessions getOperationWithSessions() {
		if (oper == null)
			oper = new OperationWithSessions();
		return oper;
	}
	
	/**����, �� ����� ���� �������, �� ������ ����������������� ��� �������*/
	class Filtr {
		private Boolean result;
		
		/**��������� ��'���� ����� �� ����������
		 * @param result ���� ��������� ��������*/
		Filtr(Boolean result) {
			this.result = result;
		}
	}

	@Override
	Criteria getFiltrCriteria(org.hibernate.Session session, Filtr filtr) {
		Criteria criteris = session.createCriteria(Session.class);
		if (filtr.result != null)
			criteris.add(Restrictions.eq("result", filtr.result));
		return null;
	}

	@Override
	Criteria getAllObjCriteria(org.hibernate.Session session) {
		return session.createCriteria(Session.class);
	}
	
	@Override
	Criteria getRegisterCriteria(org.hibernate.Session session, Session object) {
		return null;
	}
	
	@Override
	boolean isRegistedMethodNotNeed() {
		return true;
	}
	
	/**����� ��� ��������� ��� ����� � �������, �� ������������ ������ {@link getFiltrCriteria} 
	 * @param result ������� ���������� ��������
	 * @return �������� ����*/
	public ArrayList<Session> getAllObjSatisfyFiltr(Boolean result) {
		return getAllObjSatisfyFiltr(new Filtr(result));
	}
}
