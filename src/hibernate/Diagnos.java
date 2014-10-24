package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="diagnosis")
public class Diagnos {
	private Integer id;
	private String description;
	private Integer group_id;
	//private Group group;
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
	/*
	@ManyToOne
	@JoinTable(name="group_id")
	public Group getGroup() {
		return group;
	}*/
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
