package test;
import dao.*;
import entity.*;
import java.util.*;
import service.*;
public class testPersonDaoImpl {
	public static void main(String[] args) {
		Person person = new PersonServiceImpl().findPersonByPersonID("CN00001");
		System.out.println(person.toString());
	}
}
