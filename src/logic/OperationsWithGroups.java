package logic;

import hibernate.Group;

import java.util.ArrayList;

import logic.OperationsWithGroups.Filtr;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class OperationsWithGroups extends CommonOperations<Group, Filtr>{

	/**���� ���������� ��� ��������� ����� �� ��'��� ����� �����*/
	private static OperationsWithGroups oper = null;
	
	/**����� ������� ��'��� ����� �����*/
	private OperationsWithGroups() {}
	
	/**����� ������� ��'��� ����� �����
	 *  @return ������� ��'��� �����*/
	public static OperationsWithGroups getOperationsWithGroups() {
		if (oper == null)
			oper = new OperationsWithGroups();
		return oper;
	}
	
	/**����, �� ����� ���� �������, �� ������ ����������������� ��� �������*/
	class Filtr {
		private String name;
		
		/**��������� ��'���� ����� �� �����������
		 * @param name ��'� �����*/
		Filtr(String name) {
			this.name = name;
		}
	}

	// ������� ���� ������ ����
	@Override
	Criteria getFiltrCriteria(Session session, Filtr filtr) {
		return session.createCriteria(Group.class).add(Restrictions.like("name", "%" +
				filtr.name + "%")).add(Restrictions.eq("isHead", true));
	}

	@Override
	Criteria getAllObjCriteria(Session session) {
		return session.createCriteria(Group.class).add(Restrictions.eq("isHead", true));
	}

	@Override
	Criteria getRegisterCriteria(Session session, Group object) {
		return session.createCriteria(Group.class).add(Restrictions.eq("name", 
				object.getName()));
	}

	@Override
	boolean isRegistedMethodNotNeed() {
		return false;
	}
	
	/**����� ��� ��������� ��� ����� � �������, �� ������������ ������ {@link getFiltrCriteria} 
	 * @param name ������� ��'� �����
	 * @return �������� ����*/
	public ArrayList<Group> getAllObjSatisfyFiltr(String name) {
		return getAllObjSatisfyFiltr(new Filtr(name));
	}
}
