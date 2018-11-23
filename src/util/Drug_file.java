package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import entity.Drug;

public class Drug_file {
	public ArrayList<Drug> read() {
        String fileName = "C:\\Users\\17842\\Desktop\\药品基本信息.txt";
        FileReader fr = null;
         //ans = null;
        try {
        	ArrayList<Drug> ans = new ArrayList<Drug>();
            fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                ans.add(new_Drug(line));
                line = br.readLine();
            }
            
            br.close();
            return ans;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fr.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
    public Drug new_Drug(String line) {
        StringTokenizer stringTokenizer = new StringTokenizer(line,"，");
        System.out.println(line);
        return new Drug(stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken(),
                stringTokenizer.nextToken()
        );
    }
    public void write(ArrayList<Drug> drugs) {
    	String fileName = "C:\\Users\\17842\\Desktop\\药品基本信息.txt";
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("#药品ID，药品名，最高价格，计量单位，药品等级，医院等级");
            bw.newLine();
            for(Drug drug: drugs) {
            	bw.write(drug.toString());
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
                //bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
