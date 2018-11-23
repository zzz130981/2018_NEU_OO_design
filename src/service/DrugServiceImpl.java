package service;

import java.util.ArrayList;
import java.util.List;

import dao.DrugDao;
import dao.DrugDaoImpl;
import entity.Drug;

public class DrugServiceImpl implements DrugService {
    private DrugDao drugDao;
    
    public DrugServiceImpl() {
        drugDao = new DrugDaoImpl();
    }
    
    @Override
    public ArrayList<Drug> findAll() {
        return drugDao.findAll();
    }

    @Override
    public ArrayList<Drug> findByDrugID(String drugID) {
    	return drugDao.findByDrugID(drugID);
    }

    @Override
    public ArrayList<Drug> findByName(String name) {
    	return drugDao.findByName(name);
    }

    @Override
    public boolean deletDrug(String id) {
        if(drugDao.deletDrug(id)) return true;
        else return false;
    }

    @Override
    public boolean addDrug(Drug drug) {
        return drugDao.addDrug(drug);
    }

    @Override
    public boolean updateDrug(Drug drug) {
        return drugDao.updateDrug(drug);
    }
    
    @Override
    public Drug findDrugByDrugID(String drugID) {
    	return drugDao.findDrugByDrugID(drugID);
    }

}