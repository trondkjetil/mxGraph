package com.graphEditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;

public class Parser {

	public Dataset dataset;
	public Model model;
	public Model ontModel;
	private ArrayList<String> wordArray;
	private Constructs constructObj;
	private Relations relation;
	private ArrayList<Relations> relationCollection;
	public final static String base = "http://www.w3.org/1999#";

	public Parser() {
	}

	public void loadLocalDb() {
		dataset = TDBFactory.createDataset("db");
		model = dataset.getDefaultModel();
	}

	public void loadDataSet(File fil) throws FileNotFoundException {
		model = ModelFactory.createDefaultModel();
		dataset = TDBFactory.createDataset("db");
		model = dataset.getDefaultModel();
		
		InputStream in = new FileInputStream(fil.getAbsolutePath());  //  FileManager.get().open(fil.getAbsolutePath());

		model.read(in, base, "RDF/XML");
		ontModel = ModelFactory.createDefaultModel();
		ontModel = dataset.getDefaultModel();
		ontModel.setNsPrefix("abc", base);
		model.setNsPrefix("abc", base);

	}

	public void addXmlConstructToModel(String constructName,
			ArrayList<Object> concepts, mxGraphComponent graphComponent) {
		    ontModel.setNsPrefix("abc", base);
		
		Resource construct = ontModel.createResource(base + constructName);
		Property constructHasConcept = ontModel.createProperty(base+ "hasConceptRole");
		Property conceptHasGeometry = ontModel.createProperty(base+ "hasGeometry");
		construct.removeAll(conceptHasGeometry);
		construct.removeAll(constructHasConcept);
		construct.removeProperties();

		 for (Object con : concepts) {
			if (con != null) {
				String cell = ((mxCell) con).getId();
				String geometry = graphComponent.getGraph().getCellGeometry(con).toString();
				geometry = geometry.replace("com.mxgraph.model.mxGeometry", "");
				cell = cell.replaceAll("\n", "");
				cell = cell.replaceAll(" ", "");
				
				Resource conceptRole = ontModel.createResource(base + cell);
				conceptRole.removeAll(conceptHasGeometry);
				conceptRole.removeAll(constructHasConcept);
				conceptRole.removeProperties();
				Literal conceptRoleGeometry = ontModel.createLiteral(geometry);
				conceptRole.addProperty(conceptHasGeometry, conceptRoleGeometry);
				construct.addProperty(constructHasConcept, conceptRole);

			}
		}
		
			ontModel.add(model);
	}

	public HashMap<String, mxGeometry> getGeometries(ArrayList<Object> concepts) {
		HashMap<String, mxGeometry> conGeo = new HashMap<String, mxGeometry>();
		String[] geoData = null;
		mxGeometry geometry = null;

		for (Object n : concepts) {
			String input = ((mxCell) n).getId();
			input = input.replaceAll("\n", "");
			String result = "";
		result = Queries.queryFindConceptGeometry(input,ontModel, dataset);
			if (result != "") {
				result = result.replaceAll("\"", "").replaceAll("[^\\d.,]", "")
						.replaceAll("=", "");
				geoData = new String[3];
				geoData = result.split(",");
				int x = (int) Double.parseDouble(geoData[0]);
				int y = (int) Double.parseDouble(geoData[1]);
				int w = (int) Double.parseDouble(geoData[2]);
				int h = (int) Double.parseDouble(geoData[3]);
				geometry = new mxGeometry(x, y, w, h);
				conGeo.put(input, geometry);
			}
		}
		return conGeo;
	}

	public ArrayList<String> getResultList(String queryResult) {
		queryResult = Util.cleanString(queryResult);
		String[] array = queryResult.split("\n");

		return new ArrayList<String>(Arrays.asList(array));
	}

	public void addObject(ConstructsCollection collection) {
		String constructQueryResult = Queries.queryFindConstructs(dataset);
		wordArray = getResultList(constructQueryResult);

		for (String index : wordArray) {
			String resultTransformation = Queries
					.queryFindTransformationConceptRole(index.trim(), dataset);
			String resultProperty = Queries.queryFindPropertyConceptRole(
					index.trim(), dataset);
			String resultClass = Queries.queryFindClassConceptRole(
					index.trim(), dataset);
			String resultState = Queries.queryFindStateConceptRole(
					index.trim(), dataset);

			relationCollection = new ArrayList<Relations>();
			String resultRelation = Queries.queryFindAll(index.trim(), dataset);
			resultRelation = Util.cleanStringAll(resultRelation);
			String[] list = resultRelation.split("\n");

			for (String index2 : list) {
				String name = "";
				String from = "";
				String to = "";
				String parent = "";
				String parentOfparent = "";
				String toType = "";
				String fromType = "";

				if ("".equals(index2) || index2 == null) {
				} else {
					String[] listT = index2.split(" ");
					to = listT[0];
					parent = listT[1];
					from = listT[2];
					name = listT[3];
					fromType = listT[4];
					toType = listT[5];
					parentOfparent = Queries.queryFindParentOfParent(name,
							dataset);
					parentOfparent = Util.cleanStringAll(parentOfparent);

					relation = new Relations(name, from, to, parent, toType,
							fromType, parentOfparent);
					relationCollection.add(relation);

				}

			}
			constructObj = new Constructs(index, getResultList(resultClass),
					getResultList(resultProperty), getResultList(resultState),
					getResultList(resultTransformation), relationCollection);

			collection.addConstructObject(constructObj);
		}
	}

	public ArrayList<String> getConstructList() {
		return wordArray;
	}

	public void saveModel(File file) throws FileNotFoundException {
		model.getWriter("RDF/XML").write(model,
				new FileOutputStream(file + ".owl"), base);
		
	}

}
