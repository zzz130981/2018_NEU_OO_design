package entity;

public class Drug {
	 @Override
	    public String toString() {
	        return drugID + "£¬"
	                + drugName + "£¬"
	                + maxPrice + "£¬"
	                + unit + "£¬"
	                + chargeLevel + "£¬"
	                + hospital;
	     }
	private String drugID;
	private String drugName;
	private String maxPrice;
	private String unit;
	private String chargeLevel;
	private String hospital;
	
	public Drug() {
		
	}
	
	public Drug(String drugID, String drugName, String maxPrice, String unit, String chargeLevel, String hospital) {
		super();
		this.drugID = drugID;
		this.drugName = drugName;
		this.maxPrice = maxPrice;
		this.unit = unit;
		this.chargeLevel = chargeLevel;
		this.hospital = hospital;
	}
	public String getDrugID() {
		return drugID;
	}
	public void setDrugID(String drugID) {
		this.drugID = drugID;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getChargeLevel() {
		return chargeLevel;
	}
	public void setChargeLevel(String chargeLevel) {
		this.chargeLevel = chargeLevel;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	
	
}
