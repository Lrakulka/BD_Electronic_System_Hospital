package logic;

import hibernate.Note;

import java.sql.Date;
import java.util.ArrayList;

import logic.OperationWithNotes.Filtr;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class OperationWithNotes extends CommonOperations<Note, Filtr> {

	private static OperationWithNotes oper = null;
	
	private OperationWithNotes() {}
	
	public static OperationWithNotes getOperationWithNotes() {
		if (oper == null)
			oper = new OperationWithNotes();
		return oper;
	}
	
	class Filtr {
		private Boolean hide;
		private Date date_low;
		private Date date_hight;
		
		Filtr(Boolean hide, Date date_low, Date date_hight) {
			this.hide = hide;
			this.date_hight = date_hight;
			this.date_low = date_low;
		}
	}

	@Override
	Criteria getRegisterCriteria(Session session, Note object) {
		return null;
	}

	@Override
	Criteria getFiltrCriteria(Session session, Filtr filtr) {
		Criteria criteris = session.createCriteria(Note.class);
		if ( filtr.date_low != null && filtr.date_hight != null)
			criteris.add(Restrictions.between("date", filtr.date_low, filtr.date_hight));
		if (filtr.hide != null)
			criteris.add(Restrictions.eq("hide", filtr.hide));
		return criteris;
	}

	@Override
	Criteria getAllObjCriteria(Session session) {
		return session.createCriteria(Note.class);
	}

	@Override
	boolean isRegistedMethodNotNeed() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public ArrayList<Note> getAllObjSatisfyFiltr(Boolean hide, Date date_low, 
			Date date_hight) {
		return getAllObjSatisfyFiltr(new Filtr(hide, date_low, date_hight));
	}
}
