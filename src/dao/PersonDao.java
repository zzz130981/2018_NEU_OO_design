package dao;
import java.util.ArrayList;

import entity.Person;
public interface PersonDao {
	//Ôö²éÉ¾¸Ä
	ArrayList<Person> findAll();
	boolean addPerson(Person person);
	boolean deletPerson(String id);
	boolean updatePerson(Person person);
	void editPerson(Person person);
	ArrayList<Person> findByPersonID(String personID);
	ArrayList<Person> findByName(String name);
	ArrayList<Person> findByID_number(String iD_number);
	Person findPersonByPersonID(String personID);
}
