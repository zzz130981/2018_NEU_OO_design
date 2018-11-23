package dao;
import java.util.ArrayList;
import entity.Person;
import util.Person_file;
public class PersonDaoImpl implements PersonDao{
	Person_file pf = new Person_file();
	private ArrayList<Person> persons= pf.read();
	
	public ArrayList<Person> findAll() {
        return persons;
    }
	
	public boolean addPerson(Person person) {
		if(persons.add(person)) {
			pf.write(persons);
			return true;
		}
		return false;
	}
	
	public boolean deletPerson(String id) {
		for(Person person : persons) {
			if(person.getPersonID().equals(id)) {
				persons.remove(person);
				pf.write(persons);
				return true;
			}
		}
		return false;
	}
	/*
	public boolean editPerson(Person new_person) {
		boolean flag = false;
		for(Person person : persons) {
			if(person.getPersonID().equals(new_person.getPersonID())) {
				persons.remove(person);
				persons.add(new_person);
				pf.write(persons);
				flag = true;
			}
		}
		return flag;
	}*/
	
	public Person findByIDAndName(String id,String name) {
		Person ans = null;
		for(Person person : persons) 
			if(person.getName().equals(name) && person.getID_number().equals(id)) {
				ans = person;
			}
		return ans;
	}

	@Override
	public boolean updatePerson(Person new_person) {
		//boolean flag = false;
		for(Person person : persons) {
			System.out.println("演煽嶄！！！！！！！！"+person.toString());
			if(person.getPersonID().equals(new_person.getPersonID())) {
				System.out.println("if嶄！！！！！！！！"+person.toString());
				persons.remove(person);
				persons.add(new_person);
				pf.write(persons);
				return true;
			}
		}
		return false;
		//return false;
	}

	@Override
	public void editPerson(Person person) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<Person> findByPersonID(String Person_ID){
		ArrayList<Person> ans = new ArrayList<Person>();
		for(Person person : persons) {
			if(person.getPersonID().equals(Person_ID)) ans.add(person);
		}
		return ans;
	}
	public ArrayList<Person> findByID_number(String ID_number){
		ArrayList<Person> ans = new ArrayList<Person>();
		for(Person person : persons) {
			if(person.getID_number().equals(ID_number)) ans.add(person);
		}
		return ans;
	}
	public ArrayList<Person> findByName(String Name){
		ArrayList<Person> ans = new ArrayList<Person>();
		for(Person person : persons) {
			if(person.getName().equals(Name)) ans.add(person);
		}
		return ans;
	}
	public Person findPersonByPersonID(String personID) {
		for(Person person : persons) {
			if(person.getPersonID().equals(personID)) {
				return person;
			}
		}
		return null;
	}
}
