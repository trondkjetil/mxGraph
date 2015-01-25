package com.graphEditor;

import java.io.File;
import java.util.ArrayList;

import com.mxgraph.view.mxGraph;

public class Util {

	public ArrayList<Object> visited;

	Util() {
		visited = new ArrayList<Object>();
	}

	public static String splitLabel(String conceptRole) {
		int indeX = 0;
		String conceptName = "";
		if (conceptRole.length() > 10) {
			for (int i = 0; i < conceptRole.length(); i++) {
				if (Character.isUpperCase(conceptRole.charAt(i))) {
					indeX = i;
				}
			}
			conceptName = conceptRole.substring(0, indeX);
			String res = conceptRole.substring(indeX);
			conceptName = conceptName.concat("\n" + res);
		} else
			conceptName = conceptRole;

		return conceptName;

	}

	public ArrayList<Object> getVertecies() {
		return visited;
	}

	public void dfs(Object root, mxGraph graph) {
		if (root == null)
			return;
		if (graph.getModel().isVertex(root)) {
			visited.add(root);
		}

		for (Object n : graph.getChildCells(root)) {
			if (!visited.contains(n))
				dfs(n, graph);
		}
	}

	public static void removeDb() {
		File db = new File("db");
		if (db.list() != null) {
			String[] entries = db.list();
			for (String s : entries) {
				File currentFile = new File(db.getPath(), s);

				currentFile.delete();

			}
			db.delete();
		}
	}

	public static String cleanString(String line) {
		return line = line
				.replace("( ?z = \"", "")
				.replace("^^xsd:string )", "")
				.replace("\"", ",")
				.replace(
						"( ?z = <http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#",
						"").replace("> )", "");
	}

	public static String cleanStringRelation(String line) {
		return line = line
				.replace("( ?y = <", "")
				.replace("( ?z = <", "")
				.replace("( ?relationFrom = ", "")
				.replace("( ?s = <", "")
				.replace("> )", "")
				.replaceAll(
						"http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#",
						"")
				.replaceAll(
						"http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#",
						"").replace("<", "");
	}

	public static String cleanStringAll(String line) {
		return line = line
				.replace("( ?parentOfparent = <", "")
				.replace("( ?to = <", "")
				.replace("( ?parent = ", "")
				.replace("( ?from = <", "")
				.replace("( ?relationFrom = <", "")
				.replace("( ?fromtype = <", "")
				.replace("( ?totype = <", "")
				.replace("> )", "")
				.replaceAll(
						"http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelMapping#",
						"")
				.replaceAll(
						"http://sinoa.infomedia.uib.no/UEMLModelExamples/ontologies/InstLevelOntology#",
						"").replace("<", "");
	}

}
