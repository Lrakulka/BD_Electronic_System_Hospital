package hibernate;

import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

/** Цей клас призначений для налаштування Hibernate для 
 * коректної роботи з таблицею "diseases" із бази даних "hospital" */
@Entity
@Table(name="diseases")
public class Disease extends CommonField {
	/**Індефікатор даних в таблиці "diseases"*/
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")	
	private Integer id;
	
	/**Назва звороби в таблиці "diseases"*/
	@Column(name = "name")
	private String name;
	
	/**Опис зміної масиву даних для витягування всіх діагнозів, які стосуються даної хвороби,
	 *  із зв'язаної таблиці "diagnosis" по індефікатору "disease", який буде співпадати 
	 *  з індефікатором "id" поля з таблиці "disease". Відношення один до багатьох
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="disease")
    private Set<Diagnos> diagnosis;
	
	/**Створення об'єкта класа*/
	public Disease() {
		
	}
	
	/**Створення об'єкта класа із параметрами
	 * @param name ім'я хвороби*/
	public Disease(String name) {
		this.name = name;
	}
	
	/**
	  * Цей проект використовує "Lazy initialization" - це означає, що дані із зв'язаних
	  * таблиць(діагноз) не завантажуються автоматично, тому в цьому класі два метода для
	  *  отримання даних. Перший метод "getDiagnosis" повертає структуру даних, якщо ці дані
	  *   вже завантажені, другий метод "getAllDiagnosis" створює запит до бази даних і 
	  *   завантажує з неї необхідні дані і повертає структуру з цими даними, що 
	  *   стосуються поточної хвороби.
	  *   @return колекцію діагнозів
	  */
	public ArrayList<Diagnos> getAllDiagnosis() {
		ArrayList<Diagnos> listDiagnosis;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Disease disease = (Disease) session.load(Disease.class, this.id);
		Hibernate.initialize(disease.diagnosis);
		listDiagnosis = new ArrayList<>(disease.getDiagnosis());
		session.close();
		return listDiagnosis;
	}

	public Integer getId() {
		return id;
	}

	/**@return повертає назву хвороби*/
	public String getName() {
		return name;
	}

	/**@return колекцію діагнозів*/
	public Set<Diagnos> getDiagnosis() {
		return diagnosis;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**@param name встановити ім'я хвороби*/
	public void setName(String name) {
		this.name = name;
	}

	/**@param diagnosis встановити колекцію діагнозів для хвороби*/
	public void setDiagnosis(Set<Diagnos> diagnosis) {
		this.diagnosis = diagnosis;
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
		Disease other = (Disease) obj;
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
}
