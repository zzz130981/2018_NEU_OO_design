package test;
import util.*;
import java.io.*;
import entity.*;
import service.PersonService;

import java.io.FileNotFoundException;
import java.util.*;
import util.Person_file;
import dao.PersonDaoImpl;
public class test_file {
	public static void main(String[] args) {
		PersonDaoImpl pdi = new PersonDaoImpl();
		//ArrayList<Person> persons = pf.read();
		/*for(Person person : persons) {
				System.out.println("��ʼд�롪��������������"+person.toString());
		}*/
		System.out.println("��ʼ����������������");
		Person person = new Person("CN00005","���֤","130981197011244816","����׿","��","����","19701124");
		pdi.updatePerson(person);
		
	}
}
