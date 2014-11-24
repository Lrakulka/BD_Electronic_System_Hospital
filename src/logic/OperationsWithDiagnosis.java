package logic;

import org.hibernate.Criteria;
import org.hibernate.Session;

import hibernate.Diagnos;
import logic.OperationsWithDiagnosis.Filtr;

public class OperationsWithDiagnosis extends CommonOperations<Diagnos, Filtr> {

	/**Поле призначене для зберігання силки на об'єкт цього класу*/
	private static OperationsWithDiagnosis oper = null;
	
	/**Метод створюэ об'єкт цього класу*/
	private OperationsWithDiagnosis() {}
	
	/**Метод повертає об'єкт цього класу
	 *  @return повертає об'єкт класу*/
	public static OperationsWithDiagnosis getOperationsWithDiagnosis() {
		if (oper == null)
			oper = new OperationsWithDiagnosis();
		return oper;
	}
	
	/**Клас, що описує поля таблиці, які будуть використовуватися для фільтру*/
	class Filtr {
		// Фільтр не потрібен
	}

	@Override
	Criteria getFiltrCriteria(Session session, Filtr filtr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Criteria getAllObjCriteria(Session session) {
		return session.createCriteria(Diagnos.class);
	}

	@Override
	Criteria getRegisterCriteria(Session session, Diagnos object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	boolean isRegistedMethodNotNeed() {
		// TODO Auto-generated method stub
		return true;
	}
}
