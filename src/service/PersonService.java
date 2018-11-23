package service;

import java.util.ArrayList;
import java.util.List;

import entity.Person;

public interface PersonService {
	
    ArrayList<Person> findAll();
    //Person findById(String id);
    
    ArrayList<Person> findByPersonID(String personID);
    ArrayList<Person> findByID_number(String iD_number);
    ArrayList<Person> findByName(String name);
    
    boolean deletPerson(String id);
    boolean addPerson(Person song);
    boolean updatePerson(Person song);
    Person findPersonByPersonID(String personID);
}