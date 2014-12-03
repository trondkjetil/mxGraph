package com.maxgraph.editor;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class Queries {


	public static String queryFindParentOfParent(String input, Dataset dataset) {
		
		String output = "";
		
		Query selectQuery = QueryFactory
				.create(""
						+ "SELECT ?parentOfparent WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"+ input +"> "
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#parents> ?parent."
						+ "OPTIONAL {?parent  <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#parents> ?parentOfparent }"
						+"}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();
			
			output = output + "\n" + res.toString();
		}

		return output;
	}

	public static String queryFindAll(String input, Dataset dataset) {
		// to = individual with relation to it
		// totype = type of conceptRole 
		// relationFrom = the relation
		// from = the individual with relation from it
		// frometype =  type of conceptRole 
		// parent = the parent of the relation
		// parentOfparent = parent of the parent.
		String output = "";
		//?parentOfparent
		Query selectQuery = QueryFactory
				.create("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
						+ "SELECT ?to ?totype ?relationFrom ?from ?fromtype ?parent  WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"
						+ input
						+ ">"
						+ " <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#instLevelRole> ?to."
						+ "?to rdf:type ?totype."
						+ "?totype rdfs:subClassOf ?p."
						+ "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#to> ?to."
						+ "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#from> ?from."
						+ "?from rdf:type ?fromtype."
						+ "?fromtype rdfs:subClassOf ?p1."
						+ "?relationFrom  <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#parents>  ?parent."
					//	+ "OPTIONAL {?parent  <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#parents> ?parentOfparent }"
						+"}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();
			
			output = output + "\n" + res.toString();
		
		}

		return output;
	}
	
	
	
	
	public static String queryFindRelationsWithParent(String input, Dataset dataset) {

		String output = "";
		Query selectQuery = QueryFactory
				.create(""
						+ "SELECT * WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"
						+ input
						+ ">"
						+ " <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#instLevelRole> ?z."
						+ "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#to> ?z."
						+ "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#from> ?y."
						+ "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#parents> ?s." +"}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();
			
			output = output + "\n" + res.toString();
		
		}

		return output;
	}
	
	
	public static String queryFindRelationsFrom(String input, Dataset dataset) {

		String output = "";
		Query selectQuery = QueryFactory
				.create(""
						+ "SELECT DISTINCT * WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"
						+ input
						+ ">"
						+ " <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#instLevelRole>"
						+ "?z." + "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#to> ?z."+ "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#from> ?y" + "}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();
		
			output = output + "\n" + res.toString();
		}

		return output;
	}
	
	
	public static String queryFindConstructs(Dataset dataset) {


		String output = "";
		Query selectQuery = QueryFactory
				.create("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "SELECT DISTINCT * WHERE {"
						+ "?z rdf:type <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#InstLevelConstruct>" + "}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();

			output = output + "\n" + res.toString();
		
		}

		return output;

	}

	
	
	
	public static String queryFindRelations(String input, Dataset dataset) {

		String output = "";
		Query selectQuery = QueryFactory
				.create(""
						+ "SELECT DISTINCT * WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"
						+ input
						+ ">"
						+ " <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#instLevelRole>"
						+ "?z." + "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#to> ?z."+ "?relationFrom <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#from> ?y" + "}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();
		
			output = output + "\n" + res.toString();
		}

		return output;
	}
	
	

	

	public static String queryFindStateConceptRole(String input, Dataset dataset) {

		String output = "";
		Query selectQuery = QueryFactory
				.create("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "SELECT DISTINCT ?z WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"
						+ input
						+ ">"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#instLevelRole>"
						+ "?z." + "?z rdf:type <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#StateConceptRole>" + "}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();

			output = output + "\n" + res.toString();
		}

		return output;

	}
	
	

	public static String queryFindClassConceptRole(String input, Dataset dataset) {

		String output = "";
		Query selectQuery = QueryFactory
				.create("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "SELECT DISTINCT ?z WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"
						+ input
						+ ">"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#instLevelRole>"
						+ "?z." + "?z rdf:type <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#ClassConceptRole>" + "}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();

			output = output + "\n" + res.toString();
		}

		return output;

	}
	
	
	public static String queryFindPropertyConceptRole(String input, Dataset dataset) {

		String output = "";
		Query selectQuery = QueryFactory
				.create("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "SELECT DISTINCT ?z WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"
						+ input
						+ ">"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#instLevelRole>"
						+ "?z." + "?z rdf:type <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#PropertyConceptRole>" + "}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();

			output = output + "\n" + res.toString();
		}

		return output;

	}
	
	
	public static String queryFindTransformationConceptRole(String input, Dataset dataset) {

		String output = "";
		Query selectQuery = QueryFactory
				.create("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "SELECT ?z WHERE {"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#"
						+ input
						+ ">"
						+ "<http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#instLevelRole>"
						+ "?z." + "?z rdf:type <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#TransformationConceptRole>" + "}");
		QueryExecution queryExecution = QueryExecutionFactory.create(
				selectQuery, dataset);
		ResultSet resultSet = queryExecution.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution res = resultSet.nextSolution();

			output = output + "\n" + res.toString();
		}

		return output;

	}
	
	
	
	
	
}
