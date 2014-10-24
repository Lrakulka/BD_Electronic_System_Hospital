package hibernate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="users")
public class User {
	private Integer id;    
    private String name;
    private Short access_level;
    private String phone;
    private String pwd;
    private Set<Note> notes = new HashSet<>();
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
    @Column(name="access_level")
	public Short getAccess_level() {
		return access_level;
	}
    @Column(name="phone")
	public String getPhone() {
		return phone;
	}
    @Column(name="pwd")
	public String getPwd() {
		return pwd;
	}  
	@OneToMany
	@JoinColumn(name="user_id")
	public Set<Note> getNotes() {
		return notes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((access_level == null) ? 0 : access_level.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		return result;
	} 
	public boolean equals(User user) {
		if(this.access_level.equals(user.getAccess_level()) && 
				this.name.equals(user.getName()) &&
				this.phone.equals(user.getPhone()) &&
				this.pwd.equals(user.getPwd()))
			return true;
		return false;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAccess_level(Short access_level) {
		this.access_level = access_level;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setPwd(String pwd) {
		MessageDigest c = null;
		try {
			c = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		byte[] h = c.digest(pwd.getBytes());
		StringBuffer pass = new StringBuffer();
		
		for(int i = 0; i < h.length; ++i)
			pass.append(Integer.toHexString(h[i]));
		this.pwd = pass.toString();
		this.pwd = pwd;
	}

	public User( String name,  Short access_level,
			String phone, String pwd) {
	    this.name = name;
	    this.access_level = access_level;
	    this.phone = phone;
	    this.setPwd(pwd);
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}
}
