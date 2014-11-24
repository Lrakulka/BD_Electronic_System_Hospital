package hibernate;

/**Клас, що містить спільне поле для всіх таблиць*/
public abstract class CommonField {
	/**Індефікатор в таблиці*/	
	private Integer id;

	/**@return індефікатор даних в таблиці*/
	public Integer getId() {
		return id;
	}

	/**@param id встановити індефікатор для даних в таблиці*/
	public void setId(Integer id) {
		this.id = id;
	}  
	
	/**Отримання хеш-коду даного об'єкта
	 * @return повертає ціле число, яке є хеш-кодом*/
	abstract public int hashCode();
	
	/**Порівнює два діагнози
	 * @param obj діагноз з яким треба порівняти
	 * @return true якщо рівні або false якщо ні*/
	abstract public boolean equals(Object obj);
}
