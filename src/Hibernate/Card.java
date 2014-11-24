package hibernate;

import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.Type;

/** Цей клас призначений для налаштування Hibernate для 
 * коректної роботи з таблицею "cards" із бази даних "hospital" */
@Entity
@Table(name="cards")
public class Card extends CommonField {
	/**Опис поля "id" таблиці "cards"*/
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	/**Опис поля "name" таблиці "cards"*/
	@Column(name = "name")
	private String name;
	
	/**Опис поля "age" таблиці "cards"*/
	@Column(name = "age")
	private Short age;	
	
	/**Опис поля "sex" таблиці "cards"*/
	@Column(name = "sex")
	@Enumerated(EnumType.STRING) 
	private Gender sex;
	
	/**Опис поля "note" таблиці "cards"*/
	@Column(name = "note")
	@Type(type = "text")
	private String note;
	
	/**Опис поля "isAgain" таблиці "cards"*/
	@Column(name = "isAgain")
	@Type(type = "boolean")
	private Boolean isAgain;
	
	/**Опис зміної масиву даних для витягування всіх сесій, які стосуються даної картки,
	 *  із зв'язаної таблиці "sessions" по індефікатору "card_id", який буде співпадати 
	 *  з індефікатором "id" поля з таблиці "cards". Відношення один до багатьох
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL)
    private Set<hibernate.Session> sessions;
	
	/**Опис зміної масиву даних для витягування всіх нотатків, які стосуються даної картки,
	 *  із зв'язаної таблиці "sessions" по індефікатору "card_id", який буде співпадати з 
	 *  індефікатором "id" поля з таблиці "cards". Відношення один до багатьох
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="card")
    private Set<Note> notes;
	
	/**Створення об'єкта без налаштувань його полів */
	public Card() {
	}
	 
	
	/**Створення об'єкта класа із заданням полів 
	 * @param name ім'я пацієнта цієї картки
	 * @param age вік пацієнта цієї картки
	 * @param sex стать пацієнта цієї картки
	 * @param isAgain чи вперше пацієнт
	 * */
	public Card(String name, Short age, Gender sex, Boolean isAgain) {
		this.age = age;
		this.name = name;
		this.sex = sex;
		this.isAgain = isAgain;
	}
	
	/**Метод повертає структуру, що містить масив нотатків, які стосуються цієї 
	 * картки. При умові, що ці дані вже завантажені з бази даних в програму, 
	 * інакше отримаєте помилку
	 * @return колекцію сесій*/
	public Set<Note> getNotes() {
		return notes;
	}
	
	/**
	  * Цей проект використовує "Lazy initialization" - це означає, що дані із зв'язаних
	  * таблиць(нотатки) не завантажуються автоматично, тому в цьому класі два метода для
	  *  отримання даних. Перший метод "getNotes" повертає структуру даних, якщо ці дані
	  *   вже завантажені, другий метод "getAllNotes" створює запит до бази даних і 
	  *   завантажує з неї необхідні дані і повертає структуру з цими даними, що 
	  *   стосуються поточної картки.
	  *   @return колекцію нотатків
	  */
	public ArrayList<Note> getAllNotes() {
		ArrayList<Note> listNotes;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Card card = (Card) session.load(Card.class, this.id);
		Hibernate.initialize(card.notes);
		listNotes = new ArrayList<>(card.getNotes());
		session.close();
		return listNotes;
	}
	
	/**
	  * Цей проект використовує "Lazy initialization" - це означає, що дані із зв'язаних
	  * таблиць(сесії) не завантажуються автоматично, тому в цьому класі два метода для 
	  * отримання даних. Перший метод "getSessions" повертає структуру даних, якщо ці 
	  * дані вже завантажені, другий метод "getAllSessions" створює запит до бази даних
	  *  і завантажує з неї необхідні дані і повертає структуру з цими даними, що 
	  *  стосуються поточної картки.
	  *  @return колекцію сесій
	  */
	public ArrayList<hibernate.Session> getAllSessions() {
		ArrayList<hibernate.Session> listSessions;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Card card = (Card) session.load(Card.class, this.id);
		Hibernate.initialize(card.sessions);
		listSessions = new ArrayList<>(card.getSessions());
		session.close();
		return listSessions;
	}

	/**Порівнює дві картки
	 * @param card картка з якою треба порівняти
	 * @return true якщо рівні або false якщо ні*/
	public boolean equals(Card card) {
		if ( card.age.equals(age) && card.name.equals(name) && card.sex == sex 
				&& card.isAgain.equals(isAgain) && ((note == null && note == card.note)
						|| card.note.equals(note)))
			return true;
		else return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result
				+ ((sessions == null) ? 0 : sessions.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isAgain == null) ? 0 : isAgain.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
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
		Card other = (Card) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAgain == null) {
			if (other.isAgain != null)
				return false;
		} else if (!isAgain.equals(other.isAgain))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (sessions == null) {
			if (other.sessions != null)
				return false;
		} else if (!sessions.equals(other.sessions))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}


	/**@return повертає ціле число, яке є індефікатором цього об'єкта 
	 * в базі даних*/
	public Integer getId() {
		return id;
	}
	
	/**@return повертає ім'я пацієнта картки*/
	public String getName() {
		return name;
	}

	/**@return повертає вік пацієнта картки*/
	public Short getAge() {
		return age;
	}

	/**@return повертає стать пацієнта цієї картки*/
	public Gender getSex() {
		return sex;
	}

	/**@return повертає припис лікаря до цієї картки*/
	public String getNote() {
		return note;
	}

	/**@return повертає true, якщо пацієнт не вперше і 
	 * відповідно false, якщо вперше */
	public Boolean getIsAgain() {
		return isAgain;
	}

	/**Метод повертає структуру, що містить масив сесій, які стосуються цієї картки.
	 * При умові, що ці дані вже завантажені з бази даних в програму, 
	 * інакше отримаєте помилку
	 * @return повертає масив сесій, які стосуються цієї картки
	 * */
	public Set<hibernate.Session> getSessions() {
		return sessions;
	}

	/**Встановлює для картки параметр
	 * @param sessions колекція сесій*/
	public void setSessions(Set<hibernate.Session> sessions) {
		this.sessions = sessions;
	}

	/**Встановлює для картки параметр
	 * @param id індетифікатор для картки в базі даних*/
	public void setId(Integer id) {
		this.id = id;
	}

	/**Встановлює для картки параметр
	 * @param name ім'я пацієнта цієї картки*/
	public void setName(String name) {
		this.name = name;
	}

	/**Встановлює для картки параметр
	 * @param age вік пацієнта цієї картки*/
	public void setAge(Short age) {
		this.age = age;
	}

	/**Встановлює для картки параметр
	 * @param sex встановлює стать пацієнта цієї картки*/
	public void setSex(Gender sex) {
		this.sex = sex;
	}

	/**Встановлює для картки параметр
	 * @param note встановлює записЫ до картки*/
	public void setNote(String note) {
		this.note = note;
	}

	/**Встановлює для картки параметр
	 * @param isAgain встановлює вперше чи ні*/
	public void setIsAgain(Boolean isAgain) {
		this.isAgain = isAgain;
	}
	
	/**Встановлює для картки параметр
	 * @param notes встановлює масив нотатків до картки*/
	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}
}
