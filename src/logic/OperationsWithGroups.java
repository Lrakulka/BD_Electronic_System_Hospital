package logic;

import hibernate.Group;

import java.util.ArrayList;

import logic.OperationsWithGroups.Filtr;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class OperationsWithGroups extends CommonOperations<Group, Filtr>{

	/**Поле призначене для зберігання силки на об'єкт цього класу*/
	private static OperationsWithGroups oper = null;
	
	/**Метод створюэ об'єкт цього класу*/
	private OperationsWithGroups() {}
	
	/**Метод повертає об'єкт цього класу
	 *  @return повертає об'єкт класу*/
	public static OperationsWithGroups getOperationsWithGroups() {
		if (oper == null)
			oper = new OperationsWithGroups();
		return oper;
	}
	
	/**Клас, що описує поля таблиці, які будуть використовуватися для фільтру*/
	class Filtr {
		private String name;
		
		/**Створення об'єкта класа із параметрами
		 * @param name ім'я групи*/
		Filtr(String name) {
			this.name = name;
		}
	}

	// Повертає лише голови груп
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
	
	/**Метод для отримання всіх даних з таблиці, що задовільняєть фільтр {@link getFiltrCriteria} 
	 * @param name задання ім'я групи
	 * @return колекцію груп*/
	public ArrayList<Group> getAllObjSatisfyFiltr(String name) {
		return getAllObjSatisfyFiltr(new Filtr(name));
	}
}
