package hibernate;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="notes")
public class Note {
	private Integer id;
	private Integer user_id;
	private Integer card_id;
	@Type(type = "boolean")
	private Boolean hide;
	private Date date;
	@Type(type = "text")
	private String hidden_note;
	private User user;
	public Note() {
		// TODO Auto-generated constructor stub
	}
	public Note(Integer user_id, Integer card_id, Boolean hide,
			String hidden_note, Date date) {
		this.user_id = user_id;
		this.card_id = card_id;
		this.date = date;
		this.hidden_note = hidden_note;
		this.hide = hide;
	}
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
	public Integer getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((card_id == null) ? 0 : card_id.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((hidden_note == null) ? 0 : hidden_note.hashCode());
		result = prime * result + ((hide == null) ? 0 : hide.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
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
		if (card_id == null) {
			if (other.card_id != null)
				return false;
		} else if (!card_id.equals(other.card_id))
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
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	@Column(name="user_id")
	public Integer getUser_id() {
		return user_id;
	}
	@Column(name="card_id")
	public Integer getCard_id() {
		return card_id;
	}
	@Column(name="hide")
	public Boolean getHide() {
		return hide;
	}
	@Column(name="date")
	public Date getDate() {
		return date;
	}
	@Column(name="hidden_note")
	public String getHidden_note() {
		return hidden_note;
	}
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	public User getUser() {
		return user;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
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
	public void setUser(User user) {
		this.user = user;
	}	
	public void setId(Integer id) {
		this.id = id;
	}
}
