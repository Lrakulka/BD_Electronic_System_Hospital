package logic;

import java.util.ArrayList;

import hibernate.Card;
import hibernate.Gender;
import logic.OperationsWithCards.Filtr;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OperationsWithCards extends CommonOperations<Card, Filtr> {

	private static OperationsWithCards oper = null;
	
	private OperationsWithCards() {}
	
	public static OperationsWithCards getOperationWithCard() {
		if(oper == null)
			oper = new OperationsWithCards();
		return oper;
	}
	
	class Filtr{
		private String name;
		private Short age_low;
		private Short age_hight;
		private Gender sex;
		private Boolean isAgain;
		
		Filtr(String name, 
				Gender gender, Short age_levelLow, Short age_levelHight, Boolean isAgain) {
			this.name = name;
			this.sex = gender;
			this.age_low = age_levelLow;
			this.age_hight = age_levelHight;
			this.isAgain = isAgain;
		}		
	}

	@Override
	Criteria getRegisterCriteria(Session session, Card card) {
		if (card.getId() != null)
			return session.createCriteria(Card.class).add(
					Restrictions.eqOrIsNull("id", card.getId()));
		else return session.createCriteria(Card.class).add( 
				Restrictions.eq("name", card.getName())).add(
				Restrictions.eq("age", card.getAge())).add(
				Restrictions.eq("sex", card.getSex())).add(
				Restrictions.eq("isAgain", card.getIsAgain()));
	}

	@Override
	Criteria getFiltrCriteria(Session session, Filtr filtr) {
		Criteria criteria = session.createCriteria(Card.class).
		add(Restrictions.like("name", "%" + filtr.name + "%"));
		if (filtr.age_hight != null && filtr.age_low != null)
			criteria.add(Restrictions.between("age", filtr.age_low, filtr.age_hight));
		if (filtr.sex != null) 
			criteria = criteria.add(Restrictions.eq("sex", filtr.sex));
		if (filtr.isAgain != null) 
			criteria = criteria.add(Restrictions.eq("isAgain", filtr.isAgain));
		return criteria.addOrder(Order.asc("name"));
	}

	@Override
	Criteria getAllObjCriteria(Session session) {
		return session.createCriteria(Card.class);
	}

	public ArrayList<Card> getAllCardsFiltr(String name, 
			Gender gender, Short age_levelLow, Short age_levelHight, Boolean isAgain) {
		return getAllObjSatisfyFiltr(new Filtr(name, gender, age_levelLow,
				age_levelHight, isAgain));
	}

	@Override
	boolean isRegistedMethodNotNeed() {
		// TODO Auto-generated method stub
		return false;
	}	
}
