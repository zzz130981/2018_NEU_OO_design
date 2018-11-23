package dao;
import java.util.ArrayList;

import entity.Drug;
public interface DrugDao {
	//Ôö²éÉ¾¸Ä
	ArrayList<Drug> findAll();
	boolean addDrug(Drug drug);
	boolean deletDrug(String id);
	boolean updateDrug(Drug drug);
	void editDrug(Drug drug);
	ArrayList<Drug> findByDrugID(String drugID);
	ArrayList<Drug> findByName(String name);
	Drug findDrugByDrugID(String drugID);
}
