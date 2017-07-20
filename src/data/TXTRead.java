package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TXTRead {
	static String[] arr;
	static FileReader fileToRead;
	static BufferedReader bf;
	public TXTRead(File fileKey) throws IOException{
		fileToRead = new FileReader(fileKey);
		bf = new BufferedReader(fileToRead);
		
		int numLines = readLines(fileKey.getAbsolutePath());
		arr = new String[numLines];
		for(int i = 0; i <numLines;i++){
			arr[i] = bf.readLine();
		}
		bf.close();
	}
	public static int readLines(String file) throws IOException{
		fileToRead = new FileReader(file);
		bf = new BufferedReader(fileToRead);
		
		String aLine = "";
		int numLines = 0;
		while((aLine = bf.readLine())!=null){
			numLines++;
		}
		bf.close();
		return numLines;
	}
	public String[] getArr(){
		return arr;
	}
}
