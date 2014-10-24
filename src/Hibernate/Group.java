package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="groups")
public class Group {
	private Integer id;
	private String name;
	private Integer group_id;
	//private Set<Diagnosis> diagnosis = new HashSet<>();
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
	public Integer getId() {
		return id;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	@Column(name="group_id")
	public Integer getGroup_id() {
		return group_id;
	}
	/*
	@OneToMany
	@JoinTable(name="group_id")
	public Set<Diagnosis> getDiagnosis() {
		return diagnosis;
	}*/
	public void setName(String name) {
		this.name = name;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
