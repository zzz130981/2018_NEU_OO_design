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

import entity.Treat;

public class Treat_file {
	public ArrayList<Treat> read() {
        String fileName = "C:\\Users\\17842\\Desktop\\就诊信息.txt";
        FileReader reader = null;
        ArrayList<Treat> ans = new ArrayList<Treat>();
        try {
        	reader = new FileReader(fileName);
        	Scanner br= new Scanner(reader);
        	String line = br.nextLine();
        	while(br.hasNextLine()){
        		line = br.nextLine();
        		//System.out.println(line);
        		ans.add(new_Treat(line));
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
	
	public Treat new_Treat(String line) {
		//System.out.println("_______"+line);
        StringTokenizer stringTokenizer = new StringTokenizer(line,"，");
        //System.out.println(line);
        return new Treat(stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken()
        );
    }
	
    public void write(ArrayList<Treat> treats) {
    	String fileName = "C:\\Users\\17842\\Desktop\\就诊信息.txt";
        FileWriter fw = null;
        try {
        	//System.out.println("开始重新写入——————————————————");
            fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("#记录ID，人员ID，医院等级，医院编号，医院名称，门诊号，疾病名称");
            bw.newLine();
            //bw.flush();
            for(Treat treat : treats) {
            	bw.write(treat.toString());
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
