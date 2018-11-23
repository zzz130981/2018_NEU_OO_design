package dao;
import java.util.ArrayList;
import entity.Prescribe;

public interface PrescribeDao {
	ArrayList<Prescribe> findAll();
	boolean addPrescribe(Prescribe prescribe);
	boolean deletPrescribe(String prescribeID);
	boolean updatePrescribe(Prescribe prescribe);
	
	ArrayList<Prescribe> findByPrescribeID(String prescribeID);
	ArrayList<Prescribe> findByPersonID(String personID);
	ArrayList<Prescribe> findByDrugID(String drugID);
	Prescribe findPrescribeByPrescribeID(String prescribeID);

}
