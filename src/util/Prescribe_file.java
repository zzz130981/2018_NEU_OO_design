package util;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import entity.Prescribe;

public class Prescribe_file {
	public ArrayList<Prescribe> read() {
        String fileName = "C:\\Users\\17842\\Desktop\\处方明细维护.txt";
        FileReader reader = null;
        ArrayList<Prescribe> ans = new ArrayList<Prescribe>();
        try {
        	reader = new FileReader(fileName);
        	Scanner br= new Scanner(reader);
        	String line = br.nextLine();
        	while(br.hasNextLine()){
        		line = br.nextLine();
        		//System.out.println(line);
        		ans.add(new_Prescribe(line));
        	}
        	br.close();
        	return ans;
        } catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ans;
    }
	
	public Prescribe new_Prescribe(String line) {
		System.out.println("_______"+line);
        StringTokenizer stringTokenizer = new StringTokenizer(line,"，");
        //System.out.println(line);
        return new Prescribe(stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken()
        );
    }
	
    public void write(ArrayList<Prescribe> prescribes) {
    	String fileName = "C:\\Users\\17842\\Desktop\\处方明细维护.txt";
        FileWriter fw = null;
        try {
        	System.out.println("开始重新写入——————————————————");
            fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("#处方ID，人员ID，药品ID，医生姓名，数量，单价，总价");
            bw.newLine();
            //bw.flush();
            for(Prescribe prescribe: prescribes) {
            	bw.write(prescribe.toString());
            	bw.newLine();
            	bw.flush();
            	
            }
            bw.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fw.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
