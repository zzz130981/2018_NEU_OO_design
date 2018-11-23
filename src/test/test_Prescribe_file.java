package test;
import entity.Prescribe;
import dao.PrescribeDaoImpl;
import java.util.ArrayList;
import util.Prescribe_file;
public class test_Prescribe_file {
	public static void main(String[] args) {
		Prescribe_file pf = new Prescribe_file();
		Prescribe p = new Prescribe("1","1","1","1","1","1","1");
		ArrayList<Prescribe> ps = new ArrayList<Prescribe>();
		ps.add(p);
		pf.write(ps);
	}
}
