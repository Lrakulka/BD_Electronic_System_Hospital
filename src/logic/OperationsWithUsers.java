package logic;

import hibernate.User;

import java.util.ArrayList;

import logic.OperationsWithUsers.Filtr;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OperationsWithUsers extends CommonOperations<User, Filtr> {

	private static OperationsWithUsers operWithUsers = null;
	
	private OperationsWithUsers() {}
	
	public static OperationsWithUsers getOperationWithUsers() {
		if (operWithUsers == null)
			operWithUsers = new OperationsWithUsers();
		return operWithUsers;
	}
	
	class Filtr {
		private String name;
		private String phone;
		private Short access_levelLow;
		private Short access_levelHight;
		
		Filtr(String name, String pwd,
				String phone, Short access_levelLow, Short access_levelHight) {
			this.name = name;
			this.phone = phone;
			this.access_levelLow = access_levelLow;
			this.access_levelHight = access_levelHight;
		}
	}

	@Override
	Criteria getRegisterCriteria(Session session, User user) {
		if (user.getId() == null)
			return session.createCriteria(User.class).add( 
				Restrictions.eq("name", user.getName())).add(
				Restrictions.eq("pwd", user.getPwd()));
		else return session.createCriteria(User.class).add( 
				Restrictions.eq("id", user.getId()));
	}

	@Override
	Criteria getFiltrCriteria(Session session, Filtr filtr) {
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.
				like("name", "%" + filtr.name + "%")).add(Restrictions.like("phone",
						"%" + filtr.phone + "%"));
		if (filtr.access_levelHight != null && filtr.access_levelLow != null)
			criteria.add(Restrictions.between("access_level", 
	        		filtr.access_levelLow, filtr.access_levelHight));
        return criteria.addOrder(Order.asc("name"));
	}

	@Override
	Criteria getAllObjCriteria(Session session) {
		return session.createCriteria(User.class);
	}
	
	public ArrayList<User> getAllObjSatisfyFiltr(String name, 
			String phone, Short access_levelLow, Short access_levelHight) {
		return getAllObjSatisfyFiltr(new Filtr(name, null, phone, access_levelLow,
				access_levelHight));
	}

	@Override
	boolean isRegistedMethodNotNeed() {
		// TODO Auto-generated method stub
		return false;
	}
}
