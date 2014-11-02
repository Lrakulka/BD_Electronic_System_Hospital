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

@Entity
@Table(name="notes")
public class Note {
	@Id
    @GeneratedValue
    @Column(name = "id")	
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;
	
	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	
	@Column(name = "hide")
	private Boolean hide;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "hidden_note")
	@Type(type = "text")
	private String hidden_note;
	
	public Note() {
	}
	
	public Note(Boolean hide, Date date, String hidden_note) {
		this.hide = hide;
		this.date = date;
		this.hidden_note = hidden_note;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((card == null) ? 0 : card.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((hidden_note == null) ? 0 : hidden_note.hashCode());
		result = prime * result + ((hide == null) ? 0 : hide.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Boolean getHide() {
		return hide;
	}
	
	public Card getCard() {
		return card;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getHidden_note() {
		return hidden_note;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setCard(Card card) {
		this.card = card;
	}
	
	public void setHide(Boolean hide) {
		this.hide = hide;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setHidden_note(String hidden_note) {
		this.hidden_note = hidden_note;
	}	
}
