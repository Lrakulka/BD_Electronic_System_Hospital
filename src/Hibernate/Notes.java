package Hibernate;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="notes")
public class Notes {
	private Long id;
	private Long user_id;
	private Long card_id;
	private Boolean hide;
	private Date date;
	private String hidden_note;
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
	public Long getId() {
		return id;
	}
	@Column(name="user_id")
	public Long getUser_id() {
		return user_id;
	}
	@Column(name="card_id")
	public Long getCard_id() {
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
	public void setId(Long id) {
		this.id = id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public void setCard_id(Long card_id) {
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
}
