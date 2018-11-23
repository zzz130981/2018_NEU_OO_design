package dao;
import java.util.ArrayList;

import entity.Treat;
public interface TreatDao {
	
	boolean addTreat(Treat treat);
	boolean deletTreat(String id);
	boolean updateTreat(Treat treat);
	ArrayList<Treat> findAll();
	ArrayList<Treat> findByTreatID(String treatID);
	ArrayList<Treat> findByPersonID(String personID);
	
	Treat findTreatByTreatID(String treatID);
}
