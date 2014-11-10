package logic;

import org.hibernate.Criteria;
import org.hibernate.Session;

import hibernate.Diagnos;
import logic.OperationsWithDiagnosis.Filtr;

public class OperationsWithDiagnosis extends CommonOperations<Diagnos, Filtr> {

	private static OperationsWithDiagnosis oper = null;
	
	private OperationsWithDiagnosis() {}
	
	public static OperationsWithDiagnosis getOperationsWithDiagnosis() {
		if (oper == null)
			oper = new OperationsWithDiagnosis();
		return oper;
	}
	
	class Filtr {
		// Nothing to filter
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
