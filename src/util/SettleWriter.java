package util;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.*;
import entity.Prescribe;
public class SettleWriter {
	public void write(ArrayList<Prescribe> arr) throws IOException {
		
		String fileName = "C:\\Users\\17842\\Desktop\\������ϸ.txt";
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("                         		����ҽ�Ʊ����嵥");
		bw.close();
	}
}
