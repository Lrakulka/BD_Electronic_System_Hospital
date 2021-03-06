package logic;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import hibernate.Disease;
import logic.OperationsWithDiseases.Filtr;
public class OperationsWithDiseases extends CommonOperations<Disease, Filtr> {
	/**����, �� ����� ���� �������, �� ������ ����������������� ��� �������*/
	class Filtr {
		private String name;
		
		/**��������� ��'���� ����� �� �����������
		 * @param name ��'� �������*/
		Filtr(String name) {
			this.name = name;
		}
	}

	/**���� ���������� ��� ��������� ����� �� ��'��� ����� �����*/
	private static OperationsWithDiseases oper = null;
	
	/**����� ������� ��'��� ����� �����*/
	private OperationsWithDiseases() {}
	
	/**����� ������� ��'��� ����� �����
	 *  @return ������� ��'��� �����*/
	public static OperationsWithDiseases getOperationsWithDiseases() {
		if (oper == null)
			oper = new OperationsWithDiseases();
		return oper;
	}
	
	@Override
	Criteria getFiltrCriteria(Session session, Filtr filtr) {
		return session.createCriteria(Disease.class).
				add(Restrictions.like("name", "%" + filtr.name + 
						"%")).addOrder(Order.asc("name"));
	}

	@Override
	Criteria getAllObjCriteria(Session session) {
		return session.createCriteria(Disease.class);
	}

	@Override
	Criteria getRegisterCriteria(Session session, Disease object) {
		if (object.getId() != null)
			return session.createCriteria(Disease.class).add(Restrictions.eq("id", object.getId()));
		else return session.createCriteria(Disease.class).add(Restrictions.like("name", 
				object.getName()));
	}

	@Override
	boolean isRegistedMethodNotNeed() {
		return false;
	}
}
