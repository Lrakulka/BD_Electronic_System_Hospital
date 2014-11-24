package hibernate;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/** Цей клас призначений для налаштування Hibernate для 
 * коректної роботи з таблицею "notes" із бази даних "hospital" */
@Entity
@Table(name="notes")
public class Note extends CommonField {
	/**Індефікатор даних в таблиці "notes"*/
	@Id
    @GeneratedValue
    @Column(name = "id")	
	private Integer id;
	
	/**Зв'язний параметр з таблицею "cards". Відношення багато до одного*/
	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;
	
	/**Зв'язний параметр з таблицею "user". Відношення багато до одного*/
	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	
	/**Поле для встановлення режиму видемості нотатку*/
	@Column(name = "hide")
	private Boolean hide;
	
	/**Поле часу створення нотатку*/
	@Column(name = "date")
	@Type(type = "date")
	private Date date;
	
	/**Поле текстового повідомлення нотатку*/
	@Column(name = "hidden_note")
	@Type(type = "text")
	private String hidden_note;
	
	/**Створення об'єкта класа*/
	public Note() {
	}
	
	/**Створення об'єкта класа із параметрами
	 * @param hide режим видимості
	 * @param date час створення нотатку
	 * @param hidden_note текст нотатку*/
	public Note(Boolean hide, Date date, String hidden_note) {
		this.hide = hide;
		this.date = date;
		this.hidden_note = hidden_note;
	}
	
	/**@return повертає об'єкт користувача*/
	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((hidden_note == null) ? 0 : hidden_note.hashCode());
		result = prime * result + ((hide == null) ? 0 : hide.hashCode());
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
		Note other = (Note) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (hidden_note == null) {
			if (other.hidden_note != null)
				return false;
		} else if (!hidden_note.equals(other.hidden_note))
			return false;
		if (hide == null) {
			if (other.hide != null)
				return false;
		} else if (!hide.equals(other.hide))
			return false;
		return true;
	}
	
	public Integer getId() {
		return id;
	}

	/**@return повертає режим видемості нотатку*/
	public Boolean getHide() {
		return hide;
	}

	/**@return повертає об'єкт карти*/
	public Card getCard() {
		return card;
	}

	/**@return повертає об'єкт часу*/
	public Date getDate() {
		return date;
	}
	

	/**@return повертає текст нотатку*/
	public String getHidden_note() {
		return hidden_note;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	/**@param user встановлення об'єкта користувача*/
	public void setUser(User user) {
		this.user = user;
	}
	
	/**@param card встановлення об'єкта карти*/
	public void setCard(Card card) {
		this.card = card;
	}
	
	/**@param hide встановлення видимості нотатку, якщо true то нотаток
	 * бачить лікарь, який його створив та адміністратори*/
	public void setHide(Boolean hide) {
		this.hide = hide;
	}
	
	/**@param date встановлення об'єкт часу*/
	public void setDate(Date date) {
		this.date = date;
	}
	/**@param hidden_note встановлення текст нотатку*/
	public void setHidden_note(String hidden_note) {
		this.hidden_note = hidden_note;
	}	
}
