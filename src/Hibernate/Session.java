package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/** Цей клас призначений для налаштування Hibernate для 
 * коректної роботи з таблицею "sessions" із бази даних "hospital" */
@Entity
@Table(name="sessions")
public class Session extends CommonField {
	/**Індефікатор даних в таблиці "sessions"*/
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	/**Результат сесії лікування*/
	@Column(name = "result")
	@Type(type = "boolean")
	private Boolean result;
	
	/**Зв'язний параметр з таблицею "cards". Відношення багато до одного*/
	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;
	
	/**Зв'язний параметр з таблицею "diagnosis". Відношення багато до одного*/
	@ManyToOne
    @JoinColumn(name = "diagnosis_id")
    private Diagnos diagnos;

	public Integer getId() {
		return id;
	}

	/**@return повертає результат лікування*/
	public Boolean getResult() {
		return result;
	}
	
	/**@return повертає об'єкт карти*/
	public Card getCard() {
		return card;
	}

	/**@return повертає об'єкт діагнозу*/
	public Diagnos getDiagnos() {
		return diagnos;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	/**@param card встановлення об'єкт карти*/
	public void setCard(Card card) {
		this.card = card;
	}

	/**@param diagnos встановлення об'єкт діагнозу*/
	public void setDiagnos(Diagnos diagnos) {
		this.diagnos = diagnos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
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
		Session other = (Session) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}
}
