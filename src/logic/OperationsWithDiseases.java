package logic;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import hibernate.Disease;
import logic.OperationsWithDiseases.Filtr;
public class OperationsWithDiseases extends CommonOperations<Disease, Filtr> {
	/**Клас, що описує поля таблиці, які будуть використовуватися для фільтру*/
	class Filtr {
		private String name;
		
		/**Створення об'єкта класа із параметрами
		 * @param name ім'я хвороби*/
		Filtr(String name) {
			this.name = name;
		}
	}

	/**Поле призначене для зберігання силки на об'єкт цього класу*/
	private static OperationsWithDiseases oper = null;
	
	/**Метод створюэ об'єкт цього класу*/
	private OperationsWithDiseases() {}
	
	/**Метод повертає об'єкт цього класу
	 *  @return повертає об'єкт класу*/
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
