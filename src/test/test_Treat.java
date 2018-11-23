package test;
import java.util.ArrayList;

import dao.TreatDaoImpl;
import entity.Treat;
import util.*;
import dao.*;
public class test_Treat {
	public static void main(String[] args) {
		Treat_file tf = new Treat_file();
		ArrayList<Treat> at = new ArrayList<Treat>();
		TreatDao td = new TreatDaoImpl();
		
		at = tf.read();
		System.out.println(at.size());
		Treat t = new Treat("1","1","1","1","1","1","1");
		at.add(t);
		tf.write(at);
		System.out.println(at.size());
		td.deletTreat("1");
		System.out.println(at.size());
	}
}
