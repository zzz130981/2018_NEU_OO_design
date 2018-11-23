package dao;

import java.util.ArrayList;

import entity.Treat;
import util.Treat_file;
public class TreatDaoImpl implements TreatDao{
	Treat_file pf = new Treat_file();
	ArrayList<Treat> ap = pf.read();
	
	public ArrayList<Treat> findAll(){
		return ap;
	}
	
	public boolean addTreat(Treat treat) {
		if(ap.add(treat)) {
			pf.write(ap);
			return true;
		}
		return false;
	}
	
	public boolean deletTreat(String treatID) {
		for(Treat p : ap) {
			if(p.getTreatID().equals(treatID)) {
				
				ap.remove(p);
				pf.write(ap);
				return true;
			}
		}
		return false;
	}
	public boolean updateTreat(Treat treat) {
		for(Treat p : ap) {
			if(p.getTreatID().equals(treat.getTreatID())) {
				ap.remove(p);
				ap.add(treat);
				pf.write(ap);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Treat> findByTreatID(String prescribeID) {
		ArrayList<Treat> ans = new ArrayList<Treat>();
		for(Treat p : ap) {
			//System.out.println(p.getPrescribeID());
			if(p.getTreatID().equals(prescribeID)) {
				//System.out.println("+++++++" + p.getPrescribeID());
				ans.add(p);
			}
		}
		return ans;
	}
	public ArrayList<Treat> findByPersonID(String personID) {
		ArrayList<Treat> ans = new ArrayList<Treat>();
		for(Treat p : ap) {
			if(p.getPersonID().equals(personID)) {
				ans.add(p);
			}
		}
		return ans;
	}
	
	public Treat findTreatByTreatID(String prescribeID) {
		//Prescribe ans = new Prescribe();
		for(Treat p : ap) {
			if(p.getTreatID().equals(prescribeID)) {
				return p;
			}
		}
		return null;
	}
}
