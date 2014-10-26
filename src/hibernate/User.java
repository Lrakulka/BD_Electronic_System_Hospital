package hibernate;
import hibernateConnect.DatabaseConnect;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="users")
public class User {
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")	
	private Integer id;   
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "access_level")
    private Short access_level;
	
	@Column(name = "phone")
    private String phone;
	
	@Column(name = "pwd")
    private String pwd;
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy="user")
	//(fetch = FetchType.EAGER, mappedBy="user", cascade = CascadeType.ALL)
    private Set<Note> notes;
	
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

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Short getAccess_level() {
		return access_level;
	}

	public String getPhone() {
		return phone;
	}

	public String getPwd() {
		return pwd;
	}
	
	public Set<Note> getNotes() {
		return notes;
	}
	
	/**
	  * Project use Lazy initialization that is why class has two methods
	  * "getNotes" and "getAllNotes". First method return link to notes, 
	  * second method create session and get data from database and 
	  * return link to object ArrayList witch contains all notes of 
	  * current user.
	  */
	public ArrayList<Note> getAllNotes() {
		ArrayList<Note> listNotes;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		User user = (User) session.load(User.class, this.id);
		Hibernate.initialize(user.notes);
		listNotes = new ArrayList<>(user.getNotes());
		session.close();
		return listNotes;
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

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((access_level == null) ? 0 : access_level.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		return result;
	}
	
	public boolean equals(User user) {
		if(this.access_level.equals(user.access_level) && 
				this.name.equals(user.name) &&
				this.phone.equals(user.phone) &&
				this.pwd.equals(user.pwd))
			return true;
		return false;
	}
}
