package com.maxgraph.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Util {
	
	
	public static void removeDb(){
		File db = new File("db");
		if(db.list()!=null){
			String[]entries = db.list();
		for(String s: entries){
		    File currentFile = new File(db.getPath(),s);
		    currentFile.delete();
		}
		db.delete();
		}
	}
	

	public static void readFile(File file){
	    try{
	     BufferedReader reader = new BufferedReader(new FileReader(file));
	     String line = null;
	     System.out.println("*** Reading File ***");
				while ((line = reader.readLine()) != null) {
				 System.out.println(line); 
				}
			
	    }catch(Exception en){
	 	   en.printStackTrace();
	    }
		
	}
	
	
	public static String cleanString(String line) {
		return	line = line
				.replace("( ?z = \"", "")
				.replace("^^xsd:string )", "")
				.replace("\"", ",")
				.replace("( ?z = <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#",
				"").replace("> )", "");	
	}
	
	public static String cleanStringRelation(String line) {
		return	line = line
				.replace("( ?y = <", "").
				replace("( ?z = <", "")
				.replace("( ?relationFrom = ", "")
				.replace("( ?s = <", "")	
				.replace("> )", "").replaceAll("http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#", "")
				.replaceAll("http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#", "")
				.replace("<", "");	
	}
	
	public static String cleanStringAll(String line) {
		return	line = line
				.replace("( ?parentOfparent = <", "").
				replace("( ?to = <", "")
				.replace("( ?parent = ", "")
				.replace("( ?from = <", "")	
				.replace("( ?relationFrom = <", "")	
				.replace("( ?fromtype = <", "")
				.replace("( ?totype = <", "")	
				.replace("> )", "").replaceAll("http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#", "")
				.replaceAll("http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#", "")
				.replace("<", "");	
	}
	
	
}
