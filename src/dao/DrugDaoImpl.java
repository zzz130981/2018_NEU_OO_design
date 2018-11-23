package dao;
import java.util.ArrayList;
import entity.Drug;
import util.Drug_file;
public class DrugDaoImpl implements DrugDao{
	Drug_file pf = new Drug_file();
	private ArrayList<Drug> Drugs= pf.read();
	
	public ArrayList<Drug> findAll() {
        return Drugs;
    }
	
	public boolean addDrug(Drug Drug) {
		if(Drugs.add(Drug)) {
			pf.write(Drugs);
			return true;
		}
		return false;
	}
	
	public boolean deletDrug(String id) {
		for(Drug Drug : Drugs) {
			if(Drug.getDrugID().equals(id)) {
				Drugs.remove(Drug);
				pf.write(Drugs);
				return true;
			}
		}
		return false;
	}
	
	public Drug findByIDAndName(String id,String name) {
		Drug ans = null;
		for(Drug Drug : Drugs) 
			if(Drug.getDrugName().equals(name) && Drug.getDrugID().equals(id)) {
				ans = Drug;
			}
		return ans;
	}

	public boolean updateDrug(Drug new_drug) {
		boolean flag = false;
		System.out.println(Drugs.size());
		for(Drug drug : Drugs) {
			System.out.println(drug.toString());
			if(drug.getDrugID().equals(new_drug.getDrugID())) {
				System.out.println("if_____"+drug.toString());
				Drugs.remove(drug);
				Drugs.add(new_drug);
				pf.write(Drugs);
				flag = true;
			}
		}
		return flag;
	}
	
	public ArrayList<Drug> findByDrugID(String Drug_ID){
		ArrayList<Drug> ans = new ArrayList<Drug>();
		for(Drug Drug : Drugs) {
			if(Drug.getDrugID().equals(Drug_ID)) ans.add(Drug);
		}
		return ans;
	}
	public ArrayList<Drug> findByID_number(String ID_number){
		ArrayList<Drug> ans = new ArrayList<Drug>();
		for(Drug Drug : Drugs) {
			if(Drug.getDrugID().equals(ID_number)) ans.add(Drug);
		}
		return ans;
	}
	public ArrayList<Drug> findByName(String Name){
		ArrayList<Drug> ans = new ArrayList<Drug>();
		for(Drug Drug : Drugs) {
			if(Drug.getDrugName().equals(Name)) ans.add(Drug);
		}
		return ans;
	}
	public Drug findDrugByDrugID(String drugID) {
		for(Drug drug : Drugs) {
			if(drug.getDrugID().equals(drugID)) {
				return drug;
			}
		}
		return null;
	}

	@Override
	public void editDrug(Drug drug) {
		// TODO Auto-generated method stub
		
	}
}
