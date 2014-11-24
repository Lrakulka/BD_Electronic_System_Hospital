package logic;

import hibernate.Session;

import java.util.ArrayList;

import logic.OperationWithSessions.Filtr;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class OperationWithSessions extends CommonOperations<Session, Filtr> {
	
	/**Поле призначене для зберігання силки на об'єкт цього класу*/
	private static OperationWithSessions oper = null;
	
	/**Метод створюэ об'єкт цього класу*/
	private OperationWithSessions() {}
	
	/**Метод повертає об'єкт цього класу
	 *  @return повертає об'єкт класу*/
	public static OperationWithSessions getOperationWithSessions() {
		if (oper == null)
			oper = new OperationWithSessions();
		return oper;
	}
	
	/**Клас, що описує поля таблиці, які будуть використовуватися для фільтру*/
	class Filtr {
		private Boolean result;
		
		/**Створення об'єкта класа із параметром
		 * @param result задає результат лікування*/
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
	
	/**Метод для отримання всіх даних з таблиці, що задовільняєть фільтр {@link getFiltrCriteria} 
	 * @param result задання результату лікування
	 * @return колекцію сесій*/
	public ArrayList<Session> getAllObjSatisfyFiltr(Boolean result) {
		return getAllObjSatisfyFiltr(new Filtr(result));
	}
}
