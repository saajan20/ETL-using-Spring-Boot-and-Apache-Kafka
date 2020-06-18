package com.example.demo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataService {
	
	
	@Value(value = "${kafka.fileAddress}")
	private String path;
	
	
	public List<String> getDataFromFile() {
		//Reading from file line by line
		
		 FileReader reader=null;
		List<String> data = new ArrayList<String>();
		try {
			
			  
			  reader = new FileReader(path);
			  BufferedReader br=new BufferedReader(reader);
			  String line;  
			  while((line=br.readLine())!=null)  
			  { 
				  data.add(line);
				  
			  }
			  
	          
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		finally {
			
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return data;
	}
	

}
