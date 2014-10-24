package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="session")
public class Session {
	private Integer id;
	private Integer card_id;
	@Type(type = "boolean")
	private Boolean result; 
	private Integer diagnos_id;
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
	public Integer getId() {
		return id;
	}
	@Column(name="card_id")
	public Integer getCard_id() {
		return card_id;
	}
	@Column(name="result")
	public Boolean getResult() {
		return result;
	}
	@Column(name="diagnosis_id")
	public Integer getDiagnosis_id() {
		return diagnos_id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public void setDiagnosis_id(Integer diagnosis_id) {
		this.diagnos_id = diagnosis_id;
	}
}
