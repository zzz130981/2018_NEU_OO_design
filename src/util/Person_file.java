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

import entity.Drug;
import entity.Person;

public class Person_file {
	public ArrayList<Person> read() {
        String fileName = "C:\\Users\\17842\\Desktop\\人员基本信息.txt";
        FileReader reader = null;
        
        try {
        	ArrayList<Person> ans = new ArrayList<Person>();
        	reader = new FileReader(fileName);
        	Scanner br= new Scanner(reader);
        	String line = br.nextLine();
        	while(br.hasNextLine()){
        		line = br.nextLine();
        		//System.out.println(line);
        		ans.add(new_Person(line));
        	}
        	br.close();
        	return ans;
        } catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
	
	public Person new_Person(String line) {
        StringTokenizer stringTokenizer = new StringTokenizer(line,"，");
        
        System.out.println(line);
        return new Person(stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken()
        );
    }
	
    public void write(ArrayList<Person> persons) {
    	String fileName = "C:\\Users\\17842\\Desktop\\人员基本信息.txt";
        FileWriter fw = null;
        try {
        	System.out.println("开始重新写入——————————————————");
            fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("#个人ID，证件类型，证件号，姓名，性别，民族，出生日期");
            bw.newLine();
            for(Person person: persons) {
            	bw.write(person.toString());
            	bw.flush();
            	bw.newLine();
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
