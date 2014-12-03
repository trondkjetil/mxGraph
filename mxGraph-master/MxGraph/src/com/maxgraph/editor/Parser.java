package com.maxgraph.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;

public class Parser {

	public Dataset dataset;
	public Model model;
	private ArrayList<String> wordArray;
	private Constructs constructObj;
	private Relations relation;
	private ArrayList<Relations> relationCollection;

	
	public Parser() {
	}


	public void loadLocalDb(){
			
		  dataset = TDBFactory.createDataset("db") ;
		  dataset.begin(ReadWrite.READ) ;
		  model = dataset.getDefaultModel() ;
	}
	
	public void loadDataSet(File fil) {
		
		model = ModelFactory.createDefaultModel();
		dataset = TDBFactory.createDataset("db");
		model = dataset.getDefaultModel();
	
		InputStream in = FileManager.get().open(fil.getAbsolutePath());
		if (in == null) {
			throw new IllegalArgumentException("File: not found");
		}
		model.read(in, "", "RDF/XML");

		try {
			readRdf();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	public void readRdf() throws FileNotFoundException {

		File fil = new File("testfil.turtle");
		fil.setWritable(true);
		if (fil.delete()) {
		}
		try {
			model.write(new FileOutputStream(fil, true), "N-TRIPLE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> getResultList(String queryResult){
		queryResult = Util.cleanString(queryResult);
		String[] array = queryResult.split("\n");
		
		return new ArrayList<String>(Arrays.asList(array));
	}
	

	public void addObject(ConstructsCollection collection) {
		String constructQueryResult = Queries.queryFindConstructs(dataset);
		wordArray = getResultList(constructQueryResult);

		for (String index : wordArray) {
			String resultTransformation = Queries.queryFindTransformationConceptRole(index.trim(),dataset);
			String resultProperty = Queries.queryFindPropertyConceptRole(index.trim(),dataset);
			String resultClass = Queries.queryFindClassConceptRole(index.trim(),dataset);
			String resultState = Queries.queryFindStateConceptRole(index.trim(),dataset);
			
			relationCollection = new ArrayList<Relations>();
			String resultRelation = Queries.queryFindAll(index.trim(),dataset);
			resultRelation = Util.cleanStringAll(resultRelation);
			String[] list = resultRelation.split("\n");
		
			for (String index2 : list) {
				String name = "";
				String from = "";
				String to = "";
				String parent = "";
				String parentOfparent="";
				String toType="";
				String fromType="";
				
				if ("".equals(index2) || index2 == null) {
				} else {
					String[] listT = index2.split(" ");
					to = listT[0];
					parent = listT[1];
					from = listT[2];
					name = listT[3];
					fromType = listT[4];
					toType= listT[5];
					parentOfparent = Queries.queryFindParentOfParent(name,dataset);
					parentOfparent=	Util.cleanStringAll(parentOfparent);
					
					relation = new Relations(name, from, to,parent,toType,fromType,parentOfparent);
					relationCollection.add(relation);
					
				}

			}
			constructObj = new Constructs(index, getResultList(resultClass),
					getResultList(resultProperty), getResultList(resultState),
					getResultList(resultTransformation),relationCollection);
				
			collection.addConstructObject(constructObj);
		}
	}
	
	
	public ArrayList<String> getConstructList(){
		return wordArray;
	}
	
	
}
