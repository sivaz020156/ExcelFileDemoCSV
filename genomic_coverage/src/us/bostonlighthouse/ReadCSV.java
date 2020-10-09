package us.bostonlighthouse;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
/*Calculating genomic coverage from next-generation
sequencing data*/
public class ReadCSV 
	{ 
		public static void main(String file[]) { 
	    try { 
	    	System.out.println("-----------------------------------------------------------------");
	    	System.out.println("Calculating genomic coverage from next-generation sequencing data");
	    	System.out.println("-----------------------------------------------------------------");
	    	System.out.println("-----------    Starting the Job at "+ new Date() +"             ---------------------");
	    	List<String> readsList= new ArrayList<>();

	        FileReader filereader = new FileReader(new File("reads.csv"));
	        CSVReader csvReader = new CSVReader(filereader); 
	        String[] nextRecord; 
	        // we are going to read data line by line and putting read into list
	        while ((nextRecord = csvReader.readNext()) != null) { 
	                readsList.add(nextRecord[0]);
	        } 
	        
	        
	        //Hashmap to find the duplicate reads and update its value in map
	        Map<String, Integer> map = new HashMap<String, Integer>();

	    	for (String temp : readsList) {
	    		Integer count = map.get(temp);
	    		map.put(temp, (count == null) ? 1 : count + 1);
	    	}
	    	
	      
	     File inputFile = new File("loci.csv");

	     int row = 0;
	     int col = 0;
	     
	     CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
	     List<String[]> csvBody = reader.readAll();
	     int coverage=0;
	     if(null!=map.get(csvBody.get(row)[col].toString())) {
	     coverage=map.get(csvBody.get(row)[col].toString());
	     }
	  
	     
	     for(int i=0; i<csvBody.size(); i++){
	            String[] strArray = csvBody.get(i);
	            for(int j=0; j<strArray.length; j++){
	            	if(null!=map.get(csvBody.get(i)[j].toString())) {
	           	     coverage=map.get(csvBody.get(i)[j].toString());
	           	     csvBody.get(i)[j+1] = String.valueOf(coverage); // update the coverage for matching reads
	               }
	            }
	        }
	     
	     
	    	
	    csvBody.get(row)[col]=String.valueOf(coverage);
  	     reader.close();
	     // Write to CSV file which is open
	     CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',');
	     writer.writeAll(csvBody);
	     writer.flush();
	     writer.close();
        
	        
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	    }
	    
	    System.out.println("-----------    Job completed successfully at"+ new Date() +"             ---------------------");
		}
		
		
		
		
}
