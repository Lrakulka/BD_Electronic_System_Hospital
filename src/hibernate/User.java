package hibernate;
import hibernateConnect.DatabaseConnect;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
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

/** Цей клас призначений для налаштування Hibernate для 
 * коректної роботи з таблицею "users" із бази даних "hospital" */
@Entity
@Table(name="users")
public class User extends CommonField {
	/**Індефікатор даних в таблиці "users"*/
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")	
	private Integer id;   
	
	/**Поле містить ім'я користувача*/
	@Column(name = "name")
    private String name;
	
	/**Поле містить рівень доступу користувача*/
	@Column(name = "access_level")
    private Short access_level;
	
	/**Поле містить телефон користувача*/
	@Column(name = "phone")
    private String phone;
	
	/**Поле містить пароль користувача*/
	@Column(name = "pwd")
    private String pwd;
    
	/**Опис зміної масиву даних для витягування всіх нотатків, які стосуються даної групи,
	 *  із зв'язаної таблиці "notes" по індефікатору "user_id", який буде співпадати 
	 *  з індефікатором "id" поля з таблиці "user". Відношення один до багатьох
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="user", cascade = CascadeType.ALL)
    private Set<Note> notes;
	
	/**@param pwd встановлює пароль. Метод шифрування MD5*/
	public void setPwd(String pwd) {
		MessageDigest c = null;
		try {
			c = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	
		byte[] h = c.digest(pwd.getBytes());
		StringBuffer pass = new StringBuffer();
		
		for(int i = 0; i < h.length; ++i)
			pass.append(Integer.toHexString(0xFF & h[i]));
		this.pwd = pass.toString();
	}

	/**Створення об'єкта класа із параметрами
	 * @param name ім'я користувача
	 * @param access_level рівень доступу користувача
	 * @param phone телефон користувача
	 * @param pwd пароль користувача*/
	public User( String name,  Short access_level,
			String phone, String pwd) {
	    this.name = name;
	    this.access_level = access_level;
	    this.phone = phone;
	    this.setPwd(pwd);
	}
	
	/**Створення об'єкта класа*/
	public User() {
	}

	public Integer getId() {
		return id;
	}

	/**@return повертає ім'я користувача*/
	public String getName() {
		return name;
	}

	/**@return повертає рівень доступу користувача*/
	public Short getAccess_level() {
		return access_level;
	}

	/**@return повертає телефон користувача*/
	public String getPhone() {
		return phone;
	}

	/**@return повертає пароль користувача*/
	public String getPwd() {
		return pwd;
	}
	
	/**@return повертає колекцію нотатків*/
	public Set<Note> getNotes() {
		return notes;
	}
	
	/**
	  * Цей проект використовує "Lazy initialization" - це означає, що дані із зв'язаних
	  * таблиць(нотатки) не завантажуються автоматично, тому в цьому класі два метода для
	  *  отримання даних. Перший метод "getNotes" повертає структуру даних, якщо ці дані
	  *   вже завантажені, другий метод "getAllNotes" створює запит до бази даних і 
	  *   завантажує з неї необхідні дані і повертає структуру з цими даними, що 
	  *   стосуються поточного користувача.
	  *   @return колекцію нотатків
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

	/**@param name встановлення ім'я користувача*/
	public void setName(String name) {
		this.name = name;
	}

	/**@param access_level встановлення рівень доступу користувача*/
	public void setAccess_level(Short access_level) {
		this.access_level = access_level;
	}

	/**@param phone встановлення телефон користувача*/
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**@param notes встановлення колекція нотатків*/
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User user = (User) obj;
		if(this.access_level.equals(user.access_level) && 
				this.name.equals(user.name) &&
				this.phone.equals(user.phone) &&
				this.pwd.equals(user.pwd))
			return true;
		return false;
	}
}
