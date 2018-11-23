package entity;

public class Prescribe {
	private String prescribeID;
	private String personID;
	private String drugID;
	private String name;
	private String number;
	private String price;
	private String total;
	public Prescribe(String prescribeID, String personID, String drugID, String name, String number, String price,
			String total) {
		super();
		this.prescribeID = prescribeID;
		this.personID = personID;
		this.drugID = drugID;
		this.name = name;
		this.number = number;
		this.price = price;
		this.total = total;
	}
	@Override
	public String toString() {
		return prescribeID + "£¬" 
				+ personID + "£¬"
				+ drugID + "£¬"
				+ name + "£¬"
				+ number + "£¬"
				+ price + "£¬"
				+ total;
	}
	public String getPrescribeID() {
		return prescribeID;
	}
	public void setPrescribeID(String prescribeID) {
		this.prescribeID = prescribeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDrugID() {
		return drugID;
	}
	public void setDrugID(String drugID) {
		this.drugID = drugID;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
	public Prescribe() {
		super();
	}
	public String getPersonID() {
		return personID;
	}
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	
	
}
