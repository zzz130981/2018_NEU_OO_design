package service;

import java.util.ArrayList;
import java.util.List;

import dao.PersonDao;
import dao.PersonDaoImpl;
import entity.Person;

public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;
    
    public PersonServiceImpl() {
        personDao = new PersonDaoImpl();
    }
    
    @Override
    public ArrayList<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public ArrayList<Person> findByPersonID(String personID) {
    	return personDao.findByPersonID(personID);
    }

    @Override
    public ArrayList<Person> findByName(String name) {
    	return personDao.findByName(name);
    }

    @Override
    public ArrayList<Person> findByID_number(String iD_number) {
    	return personDao.findByID_number(iD_number);
    }

    @Override
    public boolean deletPerson(String id) {
        if(personDao.deletPerson(id)) return true;
        else return false;
    }

    @Override
    public boolean addPerson(Person person) {
        return personDao.addPerson(person);
    }

    @Override
    public boolean updatePerson(Person person) {
        return personDao.updatePerson(person);
    }
    
    @Override
    public Person findPersonByPersonID(String personID) {
    	return personDao.findPersonByPersonID(personID);
    }

}