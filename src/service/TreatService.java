package service;

import java.util.ArrayList;

import entity.Treat;

public interface TreatService {
	
    ArrayList<Treat> findAll();
    //Treat findById(String id);
    
    ArrayList<Treat> findByPrescribeID(String prescribeID);
    ArrayList<Treat> findByPersonID(String personID);
    ArrayList<Treat> findByDrugID(String drugID);
    
    boolean deletPrescrbie(String id);
    boolean addPrescrbie(Treat song);
    boolean updatePrescrbie(Treat song);
    Treat findTreatByTreatID(String treatID);
}