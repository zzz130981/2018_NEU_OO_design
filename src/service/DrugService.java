package service;

import java.util.ArrayList;
import java.util.List;

import entity.Drug;

public interface DrugService {
	
    ArrayList<Drug> findAll();
    //Drug findById(String id);
    
    ArrayList<Drug> findByDrugID(String drugID);
    //ArrayList<Drug> findByID_number(String iD_number);
    ArrayList<Drug> findByName(String name);
    
    boolean deletDrug(String id);
    boolean addDrug(Drug song);
    boolean updateDrug(Drug song);
    Drug findDrugByDrugID(String DrugID);
}