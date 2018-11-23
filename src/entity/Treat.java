package entity;
public class Treat {
	private String treatID;
	private String personID;
	private String hLevel;
	private String hNumber;
	private String hName;
	private String door;
	private String name;
	public String getPersonID() {
		return personID;
	}
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	public String gethLevel() {
		return hLevel;
	}
	public void sethLevel(String hLevel) {
		this.hLevel = hLevel;
	}
	public String gethNumber() {
		return hNumber;
	}
	public void sethNumber(String hNumber) {
		this.hNumber = hNumber;
	}
	public String gethName() {
		return hName;
	}
	public void sethName(String hName) {
		this.hName = hName;
	}
	public String getDoor() {
		return door;
	}
	public void setDoor(String door) {
		this.door = door;
	}
	public String getTreatID() {
		return treatID;
	}
	public void setTreatID(String treatID) {
		this.treatID = treatID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Treat(String treatID, String personID, String hLevel, String hNumber, String hName, String door,
			String name) {
		super();
		this.treatID = treatID;
		this.personID = personID;
		this.hLevel = hLevel;
		this.hNumber = hNumber;
		this.hName = hName;
		this.door = door;
		this.name = name;
	}
	public Treat() {
		super();
	}
	public String toString() {
		return treatID
		+ "£¬" +	 personID 
		+ "£¬" + hLevel
		+ "£¬" + hNumber
		+ "£¬" + hName
		+ "£¬" + door
		+ "£¬" + name;
	}
    
}

