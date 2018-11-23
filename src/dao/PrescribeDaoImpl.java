package dao;

import java.util.ArrayList;

import entity.Prescribe;
import util.Prescribe_file;
public class PrescribeDaoImpl implements PrescribeDao{
	Prescribe_file pf = new Prescribe_file();
	ArrayList<Prescribe> ap = pf.read();
	public ArrayList<Prescribe> findAll(){
		return ap;
	}
	public boolean addPrescribe(Prescribe prescribe) {
		if(ap.add(prescribe)) {
			pf.write(ap);
			return true;
		}
		return false;
	}
	public boolean deletPrescribe(String prescribeID) {
		for(Prescribe p : ap) {
			if(p.getPrescribeID().equals(prescribeID)) {
				
				ap.remove(p);
				pf.write(ap);
				return true;
			}
		}
		return false;
	}
	public boolean updatePrescribe(Prescribe prescribe) {
		for(Prescribe p : ap) {
			if(p.getPrescribeID().equals(prescribe.getPrescribeID())) {
				ap.remove(p);
				ap.add(prescribe);
				pf.write(ap);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Prescribe> findByPrescribeID(String prescribeID) {
		ArrayList<Prescribe> ans = new ArrayList<Prescribe>();
		for(Prescribe p : ap) {
			//System.out.println(p.getPrescribeID());
			if(p.getPrescribeID().equals(prescribeID)) {
				//System.out.println("+++++++" + p.getPrescribeID());
				ans.add(p);
			}
		}
		return ans;
	}
	public ArrayList<Prescribe> findByPersonID(String personID) {
		ArrayList<Prescribe> ans = new ArrayList<Prescribe>();
		for(Prescribe p : ap) {
			if(p.getPersonID().equals(personID)) {
				ans.add(p);
			}
		}
		return ans;
	}
	public ArrayList<Prescribe> findByDrugID(String drugID) {
		ArrayList<Prescribe> ans = new ArrayList<Prescribe>();
		for(Prescribe p : ap) {
			if(p.getDrugID().equals(drugID)) {
				ans.add(p);
			}
		}
		return ans;
	}
	public Prescribe findPrescribeByPrescribeID(String prescribeID) {
		//Prescribe ans = new Prescribe();
		for(Prescribe p : ap) {
			if(p.getPrescribeID().equals(prescribeID)) {
				return p;
			}
		}
		return null;
	}
}
