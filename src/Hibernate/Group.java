package hibernate;

import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/** Цей клас призначений для налаштування Hibernate для 
 * коректної роботи з таблицею "groups" із бази даних "hospital" */
@Entity
@Table(name="groups")
public class Group extends CommonField {
	/**Індефікатор даних в таблиці "diagnosis"*/
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")	
	private Integer id;
	
	/**Поле ім'я групи в таблиці "groups"*/
	@Column(name = "name")
	private String name;
	
	/**Поле статусу групи в таблиці "groups"*/
	@Column(name = "isHead")
	@Type(type = "boolean")
	private Boolean isHead;
	
	/**Поле групи в таблиці "groups"*/
	@ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

	/**Опис зміної масиву даних для витягування всіх груп, які стосуються даної групи,
	 *  із зв'язаної таблиці "groups" по індефікатору "group_id", який буде співпадати 
	 *  з індефікатором "id" поля з таблиці "groups". Відношення один до багатьох
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="group")
	private Set<Group> groups;
	
	/**Створення об'єкта класа*/
	public Group() {		
	}
	
	/**Створення об'єкта класа із параметрами
	 * @param name назва групи
	 * @param isHead при встановлені в true ця група важається головою,
	 * при false не головою групи*/
	public Group(String name, Boolean isHead) {		
		this.name = name;
		this.isHead = isHead;
	}
	
	/**
	  * Цей проект використовує "Lazy initialization" - це означає, що дані із зв'язаних
	  * таблиць(група) не завантажуються автоматично, тому в цьому класі два метода для
	  *  отримання даних. Перший метод "getGroups" повертає структуру даних, якщо ці дані
	  *   вже завантажені, другий метод "getAllGroups" створює запит до бази даних і 
	  *   завантажує з неї необхідні дані і повертає структуру з цими даними, що 
	  *   стосуються поточної групи.
	  *   @return колекцію груп
	  */
	public ArrayList<Group> getAllGroups() {
		ArrayList<Group> listGroups;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Group group = (Group) session.load(Group.class, this.id);
		Hibernate.initialize(group.groups);
		listGroups = new ArrayList<>(group.getGroups());
		session.close();
		if ( listGroups.isEmpty() || listGroups.get(0).id.equals(this.id))
			return null;
		else return listGroups;
	}
	
	public Integer getId() {
		return id;
	}

	/**@return повертає true якщо група є початком, false якщо ні*/
	public Boolean getIsHead() {
		return isHead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**@return повертає назву групи*/
	public String getName() {
		return name;
	}
	
	/**@return повертає об'єкт групи*/
	public Group getGroup() {
		return group;
	}

	/**@return повертає колекцію груп*/
	public Set<Group> getGroups() {
		return groups;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**@param name встановлення назву для групи*/
	public void setName(String name) {
		this.name = name;
	}

	/**@param group встановлення групу для поточної групи*/
	public void setGroup(Group group) {
		this.group = group;
	}

	/**@param groups встановлення колекцію груп*/
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	/**@param isHead встановлення голову групи*/
	public void setIsHead(Boolean isHead) {
		this.isHead = isHead;
	}	
}
