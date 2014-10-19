package Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="diagnosis")
public class Diagnosis {
	private Integer id;
	private String description;
	private Integer group_id;
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
	public Integer getId() {
		return id;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	@Column(name="group_id")
	public Integer getGroup_id() {
		return group_id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
}
