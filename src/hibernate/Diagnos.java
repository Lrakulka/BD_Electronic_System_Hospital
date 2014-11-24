package hibernate;

import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
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

/** Цей клас призначений для налаштування Hibernate для 
 * коректної роботи з таблицею "diagnosis" із бази даних "hospital" */
@Entity
@Table(name="diagnosis")
public class Diagnos extends CommonField {
	/**Індефікатор даних в таблиці "diagnosis"*/
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")	
	private Integer id;
	
	/**Приспис до діагнозу в таблиці "diagnosis"*/
	@Column(name = "description")
	private String description;
	
	/**Опис зміної масиву даних для витягування всіх сесій, які стосуються даного діагнозу,
	 *  із зв'язаної таблиці "sessions" по індефікатору "diagnosis_id", який буде співпадати 
	 *  з індефікатором "id" поля з таблиці "diagnos". Відношення один до багатьох
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="diagnos", cascade = CascadeType.ALL)
	private Set<hibernate.Session> sessions;

	/**Зв'язний параметр з таблицею "diseases". Відношення багато до одного*/
	@ManyToOne
    @JoinColumn(name="disease")
    private Disease disease;
	
	/**Створення об'єкта класа*/
	public Diagnos() {
	}
	
	/**Створення об'єкта класа із параметрами
	 * @param description припис до діагнозу*/
	public Diagnos(String description) {
		this.description = description;
	}
	
	/**@return повертає об'єкт хвороби*/
	public Disease getDisease() {
		return disease;
	}

	/**@param disease встановлення хвороби для діагнозу*/
	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	/**
	  * Цей проект використовує "Lazy initialization" - це означає, що дані із зв'язаних
	  * таблиць(сесії) не завантажуються автоматично, тому в цьому класі два метода для
	  *  отримання даних. Перший метод "getSessions" повертає структуру даних, якщо ці дані
	  *   вже завантажені, другий метод "getAllSessions" створює запит до бази даних і 
	  *   завантажує з неї необхідні дані і повертає структуру з цими даними, що 
	  *   стосуються поточного діагнозу.
	  *   @return колекцію сесій
	  */
	public ArrayList<hibernate.Session> getAllSessions() {
		ArrayList<hibernate.Session> listSessions;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Diagnos diagnos = (Diagnos) session.load(Diagnos.class, this.id);
		Hibernate.initialize(diagnos.sessions);
		listSessions = new ArrayList<>(diagnos.getSessions());
		session.close();
		return listSessions;
	}
	
	/**@return повертає індефікатор в базі даних*/
	public Integer getId() {
		return id;
	}

	/**@return повертає припис до діагнозу*/
	public String getDescription() {
		return description;
	}

	/**@return повертає колекцію сесій*/
	public Set<hibernate.Session> getSessions() {
		return sessions;
	}

	/**@param id встановлює індефікатор в базі даних*/
	public void setId(Integer id) {
		this.id = id;
	}

	/**@param description встановлює припис до діагнозу*/
	public void setDescription(String description) {
		this.description = description;
	}

	/**@param sessions встановлює колекцію сесій для діагнозу*/
	public void setSessions(Set<hibernate.Session> sessions) {
		this.sessions = sessions;
	}

	/**Отримання хеш-коду даного об'єкта
	 * @return повертає ціле число, яке є хеш-кодом*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**Порівнює два об'єкта
	 * @param obj об'єкт з яким треба порівняти
	 * @return true якщо рівні або false якщо ні*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diagnos other = (Diagnos) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
