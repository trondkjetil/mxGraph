package com.graphEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

public class Layout {

	private Object v;
	private HashMap<String, Object> objList;
	private HashMap<String, Object> lis;
	private String rec = "shape=rectangle;perimeter=rectanglePerimeter;gradientColor=green;fontColor=black;verticalAlign=top;";
	private String eclipse = "shape=ellipse;perimeter=ellipsePerimeter;gradientColor=red;fontColor=black;";
	private String triangel = "shape=triangle;perimeter=trianglePerimeter;gradientColor=blue;fontColor=black;";
	private String rombe = "shape=rhombus;perimeter=rhombusPerimeter;gradientColor=yellow;fontColor=black;";
	private ArrayList<String> classType;
	private ArrayList<Relations> relationList;
	private HashMap<String, Object> concepts;
	private HashMap<Object, Object> insideOf;
	private HashMap<String, Object> classParent;
	
	Layout(){
		objList = new HashMap<String, Object>();
		lis = new HashMap<String, Object>();
		concepts = new HashMap<String, Object>();
		insideOf = new HashMap<Object, Object>();
		classParent = new HashMap<String, Object>();
	}
	
		Layout(boolean val) {
		objList = new HashMap<String, Object>();
		lis = new HashMap<String, Object>();
		concepts = new HashMap<String, Object>();
		insideOf = new HashMap<Object, Object>();
		classParent = new HashMap<String, Object>();

		rec = "shape=rectangle;perimeter=rectanglePerimeter;fillColor=white;fontColor=black;verticalAlign=top;strokeColor=black";
		eclipse = "shape=ellipse;perimeter=ellipsePerimeter;fillColor=white;fontColor=black;strokeColor=black";
		triangel = "shape=triangle;perimeter=trianglePerimeter;fillColor=white;fontColor=black;strokeColor=black";
		rombe = "shape=rhombus;perimeter=rhombusPerimeter;fillColor=white;fontColor=black;strokeColor=black";

	}

	public void makeGraphLayout(int index, ArrayList<Constructs> collection,
			Object parent, mxGraph graph) {

		addToGraph(collection.get(index).getClassConceptRole(), rec, parent,
				graph, 1, collection, index);
		addToGraph(collection.get(index).getPropertyConceptRole(), eclipse,
				parent, graph, 140, collection, index);
		addToGraph(collection.get(index).getStateConceptRole(), rombe, parent,
				graph, 280, collection, index);
		addToGraph(collection.get(index).getTransformationConceptRole(),
				triangel, parent, graph, 420, collection, index);

		ArrayList<Relations> list = collection.get(index).getRelation();

		for (Relations obj : list) {
			graph.insertEdge(parent, null, obj.getParent(),
					objList.get(obj.getFromIndivid()),
					objList.get(obj.getToIndivid()), "fontColor=black;");
		}
	}

	public void addToGraph(ArrayList<String> conceptRoleCollection,
			String shape, Object parent, mxGraph graph, int startPos,
			ArrayList<Constructs> collections, int index) {

		int ant = 0;
		int newPos = 0;
		String conRole = "";
		String conname = collections.get(index).getConstructName();

		while (ant < conceptRoleCollection.size()) {
			if ("".equals(conceptRoleCollection.get(ant))) {
			} else
			conRole = conceptRoleCollection.get(ant);
			conRole = conRole.replace(conname + "_", "");
			if ("".equals(conRole)) {
			} else
				v = graph.insertVertex(parent, null, conRole, newPos + 0,
					startPos, 120, 70, shape);
			graph.addCell(v, parent);
			objList.put(conceptRoleCollection.get(ant), v);

			ant++;
			newPos += 180;

		}

	}

	public void nestedLayout(Object parent, mxGraph graph, ArrayList<Constructs> collection, int index) {
	    classType = collection.get(index).getClassConceptRole();
	    relationList = collection.get(index).getRelation();
			
		int exhibitedBy = 0, undergoneBy = 0, belongsTo = 0, defines = 0, changedBy = 0, factorIn = 0, sequenceOf = 0;
		int	conjunctionContentFrom = 0,	conjunctionLaw = 0, substateOf = 0, effects = 0, belongsToInput = 0, definedBy = 0, composedOf = 0, subclassClassRelationship = 0, conjunctOf = 0, conjunctOfM = 0, conjunctOfContent = 0, chainOf = 0;
		String definedByF = "", definedByT = "", belongsToInputT = "", belongsToInputF = "", definesF = "", changedByF = "", exhibitedByT = "", exhibitedByF = "";
		String factorInT = "", factorInF = "", undergoneByT = "", undergoneByF = "", sequenceOfT = "", sequenceOfF = "", composedOfT = "", composedOfF = "", conjunctionBehaviourT = "";
		String conjunctionBehaviourF = "", belongsToT = "", belongsToF = "", definesT = "", changedByT = "", substateOfF = "", substateOfT = "", effectsT = "", chainOfT = "", chainOfF = "", conjunctionLawT = "", conjunctionLawF = "";
		String effectsF = "", subclassClassRelationshipT = "", subclassClassRelationshipF = "", belongsToWholeT = "", belongsToWholeF = "", conjunctionBehaviourManipulatedT = "", conjunctionBehaviourManipulatedF = "";
		String	conjunctionContentFromT = "",conjunctionContentFromF = "",	conjunctionContentF = "", conjunctionContentT = "";
	
		boolean classInClass = false;
		Object[] l = new Object[classType.size()];
		int pushed = 0;
		int in = 0;
		for (String role : classType) {
			if ("".equals(role)) {
			} else if (concepts.get(role) == null) {
				String conceptRole = "";
				String conname = collection.get(index).getConstructName();
				conceptRole = role;
				conceptRole = conceptRole.replace(conname + "_", "");
				
				if ("".equals(conceptRole)) {
					continue;
				} else {
				v = graph.createVertex(parent, Util.splitLabel(conceptRole), conceptRole, 0 + pushed, 0, 400,400, rec);
				l[in] = v;
				graph.addCell(v, parent);
				classParent.put(role, v);
				concepts.put(role, v);
				insideOf.put(v, null);
				graph.cellsOrdered(l, true);
				in++;
				pushed += 450;	
			
				}
			}
		}
		for (Entry<String, Object> entry : lis.entrySet()) {
			String key = entry.getKey();

			if (key == null || "".equals(key)) {
			} else
				for (Relations rel : relationList) {
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"ClassConceptRole")
							&& rel.getParent().contains(
									"subclassClassRelationship")
							|| rel.getParent().contains(
									"classSubclassRelationship")) {
						subclassClassRelationship++;
						subclassClassRelationshipT = rel.getToIndivid();
						subclassClassRelationshipF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("belongsToWhole")) {
						classInClass = true;
						belongsToWholeF = rel.getFromIndivid();

					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("belongsToPart")) {
						classInClass = true;
						belongsToWholeT = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"StateConceptRole")
							&& rel.getParent().contains("exhibitedBy")) {
						exhibitedBy++;
						exhibitedByT = rel.getToIndivid();
						exhibitedByF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"StateConceptRole")
							&& rel.getParent().contains("factorIn")) {
						factorIn++;
						factorInF = rel.getFromIndivid();
						factorInT = rel.getToIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"StateConceptRole")
							&& rel.getParent().contains("substateOf")) {
						substateOf++;
						substateOfF = rel.getFromIndivid();
						substateOfT = rel.getToIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"TransformationConceptRole")
							&& rel.getParent().contains("undergoneBy")) {
						undergoneBy++;
						undergoneByT = rel.getToIndivid();
						undergoneByF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"TransformationConceptRole")
							&& rel.getParent().contains("sequenceOf")
							|| rel.getParent().contains("StepIn")) {
						sequenceOf++;
						sequenceOfT = rel.getToIndivid();
						sequenceOfF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"TransformationConceptRole")
							&& rel.getParent().contains("composedOf")) {
						composedOf++;
						composedOfT = rel.getToIndivid();
						composedOfF = rel.getFromIndivid();
					}
					if (key.equals(rel.getFromIndivid())
							&& rel.getToIndividConceptType().equals(
									"TransformationConceptRole")
							&& rel.getParent().contains("chainOf")) {
						chainOf++;
						chainOfT = rel.getToIndivid();
						chainOfF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("conjunctionLaw")) {
						conjunctionLaw++;
						conjunctionLawT = rel.getToIndivid();
						conjunctionLawF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("conjunctionBehaviour")) {
						conjunctOf++;
						conjunctionBehaviourT = rel.getToIndivid();
						conjunctionBehaviourF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains(
									"conjunctionBehaviourManipulated")) {
						conjunctOfM++;
						conjunctionBehaviourManipulatedT = rel.getToIndivid();
						conjunctionBehaviourManipulatedF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getParent().contains(
									"conjunctionBehaviourConstrainedContent")) {
						conjunctOfContent++;
						conjunctionContentT = rel.getToIndivid();
						conjunctionContentF = rel.getFromIndivid();
					}

					if (key.equals(rel.getFromIndivid())
							&& rel.getParent().contains(
									"conjunctionBehaviourConstrainedContent")) {
						conjunctionContentFrom++;
						conjunctionContentFromT = rel.getToIndivid();
						conjunctionContentFromF = rel.getFromIndivid();
					}

					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("belongsTo")) {
						belongsTo++;
						belongsToT = rel.getToIndivid();
						belongsToF = rel.getFromIndivid();

					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("belongsToInput")) {
						belongsToInput++;
						belongsToInputT = rel.getToIndivid();
						belongsToInputF = rel.getFromIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("defines")) {
						defines++;
						definesF = rel.getFromIndivid();
						definesT = rel.getToIndivid();
					}
					if (key.equals(rel.getFromIndivid())
							&& rel.getFromIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("definedBy")) {
						definedBy++;
						definedByF = rel.getFromIndivid();
						definedByT = rel.getToIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("changedBy")) {
						changedBy++;
						changedByF = rel.getFromIndivid();
						changedByT = rel.getToIndivid();
					}
					if (key.equals(rel.getToIndivid())
							&& rel.getToIndividConceptType().equals(
									"PropertyConceptRole")
							&& rel.getParent().contains("effectedBy")
							|| rel.getParent().contains("effects")) {
						effects++;
						effectsF = rel.getFromIndivid();
						effectsT = rel.getToIndivid();
					}

				}
			if (sequenceOf == 1) {
				concepts.put(sequenceOfF, lis.get(sequenceOfF));
				insideOf.put(lis.get(sequenceOfT), lis.get(sequenceOfF));
				System.out.println("stepIn: transformation to transformation");
			}
			if (subclassClassRelationship == 1) {
				insideOf.put(classParent.get(subclassClassRelationshipF),
						classParent.get(subclassClassRelationshipT));
				concepts.put(subclassClassRelationshipF,
						lis.get(subclassClassRelationshipF));
				concepts.put(subclassClassRelationshipT,
						lis.get(subclassClassRelationshipT));
				System.out
						.println("subclassClassRelationship: class  in class");
			}

			if (classInClass) {
				concepts.put(belongsToWholeT, lis.get(belongsToWholeT));
				concepts.put(belongsToWholeF, lis.get(belongsToWholeF));
				insideOf.put(classParent.get(belongsToWholeF),
						classParent.get(belongsToWholeT));
				graph.resizeCell(classParent.get(belongsToWholeF),
						new mxRectangle(70, 70, 70, 70));
				System.out.println("BelongsToWhole: class  in class");
			}

			if (effects == 1) {
				concepts.put(effectsT, lis.get(effectsT));
				insideOf.put(lis.get(effectsF), lis.get(effectsT));
				System.out.println("Effects: property  in transformation");
			}

			if (conjunctionLaw == 1) {
				System.out.println("conjunctionLaw : prop in prop");
				insideOf.put(lis.get(conjunctionLawT), lis.get(conjunctionLawF));
				concepts.put(conjunctionLawT, lis.get(conjunctionLawT));
			}
			if (conjunctOf >= 1 && conjunctOfM >= 1) {
				System.out
						.println("conjunctionBehaviour double : prop in prop");
				insideOf.put(lis.get(conjunctionBehaviourT),
						lis.get(conjunctionBehaviourF));
				insideOf.put(lis.get(conjunctionBehaviourManipulatedT),
						lis.get(conjunctionBehaviourManipulatedF));
				concepts.put(conjunctionBehaviourT,
						lis.get(conjunctionBehaviourT));
				concepts.put(conjunctionBehaviourF,
						lis.get(conjunctionBehaviourF));
				concepts.put(conjunctionBehaviourManipulatedT,
						lis.get(conjunctionBehaviourManipulatedT));
				concepts.put(conjunctionBehaviourManipulatedF,
						lis.get(conjunctionBehaviourManipulatedF));
			}

			if (conjunctOf == 1 && belongsTo + belongsToInput > 1) {
				Object[] ob = new Object[2];
				System.out.println("First conjunctionBehaviour : prop in prop");
				System.out.println(conjunctionBehaviourT);
				insideOf.put(lis.get(conjunctionBehaviourT), parent);
				insideOf.put(lis.get(conjunctionBehaviourF), parent);
				insideOf.put(lis.get(conjunctionBehaviourT),
						lis.get(conjunctionBehaviourF));
				concepts.put(conjunctionBehaviourT,
						lis.get(conjunctionBehaviourT));
				concepts.put(conjunctionBehaviourF,
						lis.get(conjunctionBehaviourF));
				ob[0] = lis.get(conjunctionBehaviourT);
				ob[1] = lis.get(conjunctionBehaviourF);
				graph.moveCells(ob, 150, 150);
			}

			if (conjunctOfContent == 1 && belongsTo + belongsToInput > 1) {
				Object[] ob1 = new Object[2];
				System.out
						.println("Second conjunctionBehaviour : prop in prop");
				insideOf.put(lis.get(conjunctionBehaviourT), parent);
				insideOf.put(lis.get(conjunctionBehaviourF), parent);
				insideOf.put(lis.get(conjunctionBehaviourT),
						lis.get(conjunctionBehaviourF));
				concepts.put(conjunctionBehaviourT,
						lis.get(conjunctionBehaviourT));
				concepts.put(conjunctionBehaviourF,
						lis.get(conjunctionBehaviourF));
				ob1[0] = lis.get(conjunctionBehaviourT);
				ob1[1] = lis.get(conjunctionBehaviourF);
				graph.moveCells(ob1, 150, 150);
			}

			if (conjunctOf == 1) {
				System.out.println("Third conjunctionBehaviour : prop in prop");
				insideOf.put(lis.get(conjunctionBehaviourT),
						lis.get(conjunctionBehaviourF));
				concepts.put(conjunctionBehaviourT,
						lis.get(conjunctionBehaviourT));
				concepts.put(conjunctionBehaviourF,
						lis.get(conjunctionBehaviourF));
			}

			if (conjunctionContentFrom == 1) {
				System.out
						.println("Fourth conjunctionBehaviour : prop in prop");
				Object[] ob2 = new Object[2];
				insideOf.put(lis.get(conjunctionContentFromT), parent);
				insideOf.put(lis.get(conjunctionContentFromF), parent);
				insideOf.put(lis.get(conjunctionContentFromT),
						lis.get(conjunctionContentFromF));
				concepts.put(conjunctionContentFromT,
						lis.get(conjunctionContentFromT));
				concepts.put(conjunctionContentFromF,
						lis.get(conjunctionContentFromF));
				ob2[0] = lis.get(conjunctionContentFromT);
				ob2[1] = lis.get(conjunctionContentFromF);
				graph.moveCells(ob2, 150, 150);
			}

			if (exhibitedBy == 1) {
				concepts.put(exhibitedByT, lis.get(exhibitedByT));
				insideOf.put(lis.get(exhibitedByT),
						classParent.get(exhibitedByF));
				System.out.println("exhibitedBy: State to class");
			}

			if (factorIn == 1) {
				concepts.put(factorInT, lis.get(factorInT));
				insideOf.put(lis.get(factorInT), lis.get(factorInF));
				System.out.println("Factor in: state to state");
			}

			if (composedOf == 1) {
				concepts.put(composedOfF, lis.get(composedOfF));
				insideOf.put(lis.get(composedOfT), lis.get(composedOfF));
				System.out.println(composedOfF + " skal inni " + composedOfT);
				System.out
						.println("composedOf: transformation to transformation");
			}
			if (chainOf == 1) {
				concepts.put(chainOfF, lis.get(chainOfF));
				insideOf.put(lis.get(chainOfF), lis.get(chainOfT));
				System.out.println("chainOF: transformation to transformation");
			}

			if (substateOf == 1) {
				concepts.put(substateOfT, lis.get(substateOfT));
				concepts.put(substateOfF, lis.get(substateOfF));
				insideOf.put(lis.get(substateOfT), lis.get(substateOfF));
				System.out.println("substateOf: state in state");
			}

			if (undergoneBy == 1) {
				concepts.put(undergoneByT, lis.get(undergoneByT));
				insideOf.put(lis.get(undergoneByT),
						classParent.get(undergoneByF));
				System.out.println("Transformation to class");
			}

			if (defines == 1 && changedBy != 1 && conjunctOf != 1) {
				if (concepts.get(definesT) == null) {
					insideOf.put(lis.get(definesT), concepts.get(definesF));
					concepts.put(definesT, lis.get(definesT));
					System.out.println("Defines nr 1");
				} else if (concepts.get(definesT) != null) {
					insideOf.put(lis.get(definesF), lis.get(definesT));
					concepts.put(definesT, lis.get(definesT));
					System.out.println("Defines nr 2");
				}
				System.out.println("Defines: Property to state");

			} else if (definedBy == 1 && changedBy != 1 && conjunctOf != 1) {
				insideOf.put(lis.get(definedByF), lis.get(definedByT));
				concepts.put(definedByT, lis.get(definedByT));
				System.out.println("Enters sec: Property to state");

			} else if (changedBy == 1 && defines != 1 && conjunctOf < 1
					&& conjunctOfM < 1 && defines != 1 && definedBy != 1) {
				insideOf.put(lis.get(changedByT), lis.get(changedByF));
				concepts.put(changedByT, lis.get(changedByT));
				System.out.println("Enters third: Property to transformation");

			} else if (belongsTo == 1 && defines == 0 && definedBy == 0
					&& changedBy == 0 && effects == 0) {
				insideOf.put(lis.get(belongsToT), classParent.get(belongsToF));
				concepts.put(belongsToT, lis.get(belongsToT));
				System.out.println("Enters first: Property to class");

			} else if (belongsToInput == 1 && belongsTo < 2 && defines == 0
					&& changedBy == 0 && conjunctOfM < 1 && conjunctOf < 1) {
				insideOf.put(lis.get(belongsToInputT),
						classParent.get(belongsToInputF));
				concepts.put(belongsToInputT, lis.get(belongsToInputT));
				System.out.println("Enters first,second: Property to class");
			}

			definedByF = "";
			definedByT = "";
			belongsToInputT = "";
			belongsToInputF = "";
			definesF = "";
			changedByF = "";
			exhibitedByT = "";
			exhibitedByF = "";
			factorInT = "";
			factorInF = "";
			undergoneByT = "";
			undergoneByF = "";
			sequenceOfT = "";
			sequenceOfF = "";
			composedOfT = "";
			composedOfF = "";
			conjunctionBehaviourT = "";
			conjunctionBehaviourF = "";
			belongsToT = "";
			belongsToF = "";
			definesT = "";
			changedByT = "";
			substateOfF = "";
			substateOfT = "";
			effectsT = "";
			effectsF = "";
			conjunctionContentF = "";
			conjunctionContentT = "";
			conjunctionBehaviourManipulatedT = "";
			conjunctionBehaviourManipulatedF = "";
			conjunctionContentFromT = "";
			conjunctionContentFromF = "";
			chainOfT = "";
			chainOfF = "";
			conjunctionLawT = "";
			conjunctionLawF = "";
			conjunctionLaw = 0;
			chainOf = 0;
			conjunctOfContent = 0;
			conjunctionContentFrom = 0;
			conjunctOfM = 0;
			exhibitedBy = 0;
			undergoneBy = 0;
			belongsTo = 0;
			defines = 0;
			changedBy = 0;
			factorIn = 0;
			sequenceOf = 0;
			composedOf = 0;
			conjunctOf = 0;
			substateOf = 0;
			effects = 0;
			belongsToInput = 0;
			definedBy = 0;

		}

		int inc = 0;
		Object[] o = new Object[lis.size()];
		for (Entry<String, Object> entry1 : lis.entrySet()) {

			String key1 = entry1.getKey();
			Object obj1 = entry1.getValue();

			if (key1 == null || "".equals(key1)) {
			} else if (concepts.get(key1) == null) {

				if (key1.contains("PartWholeRelation")) {
					String ec = "shape=doubleEllipse;perimeter=ellipsePerimeter;gradientColor=#d01265;fontColor=black;";
					v = graph.createVertex(parent, null, "PartWholeRealtion",
							400, 400, 60, 60, ec);
					
					graph.addCell(v, parent);
					concepts.put(key1, v);
				} else
					graph.addCell(obj1, parent);
					concepts.put(key1, obj1);

				if (insideOf.get(key1) == null)
					o[inc++] = obj1;
				
			}
		}

		mxHierarchicalLayout gr = new mxHierarchicalLayout(graph);
		ArrayList<Object> parents = new ArrayList<Object>();
		ArrayList<Object> children = new ArrayList<Object>();
		
		for (Entry<Object, Object> entry : insideOf.entrySet()) {
			Object c = entry.getKey();
			Object p = entry.getValue();

		if(c != null){
			children.add(c);
		}
		if(p != null){
			parents.add(p);
		}
			graph.addCell(c, p);			
			gr.execute(p);
			gr.setVertexLocation(p, 0, 0);
			gr.setIntraCellSpacing(40);
			gr.setFineTuning(true);
			gr.setInterRankCellSpacing(500);
			gr.setResizeParent(true);
			gr.setVertexLocation(p, 0, 0);
			gr.setInterHierarchySpacing(50);
			gr.setParallelEdgeSpacing(30);
			gr.setInterRankCellSpacing(0);
			
		}
	
		for (Relations rel : relationList) {
			if (rel.getParent().contains("postStateOf")
					|| rel.getParent().contains("enters")) {
				graph.insertEdge(parent, rel.getParent(), null,
						concepts.get(rel.getFromIndivid()),
						concepts.get(rel.getToIndivid()));
			}
			if (rel.getParent().contains("preStateOf")
					|| rel.getParent().contains("exits")) {
				graph.insertEdge(parent, rel.getParent(), null,
						concepts.get(rel.getToIndivid()),
						concepts.get(rel.getFromIndivid()));
			}

			if (rel.getParent().contains("definedBy")) {
				graph.insertEdge(parent, rel.getParent(), null,
						concepts.get(rel.getFromIndivid()),
						concepts.get(rel.getToIndivid()));
			}

			if (rel.getParent().contains("changedBy")) {
				graph.insertEdge(parent, rel.getParent(), null,
						concepts.get(rel.getFromIndivid()),
						concepts.get(rel.getToIndivid()));
			}

		}
		
		
		
	}

	
	public void makeGraph(int index, ArrayList<Constructs> collection,
			Object parent, mxGraph graph) {
		addToList(collection.get(index).getClassConceptRole(), rec, parent,
				graph, 0, collection, index, 400, 400);
		addToList(collection.get(index).getPropertyConceptRole(), eclipse,
				parent, graph, 120, collection, index, 50, 50);
		addToList(collection.get(index).getStateConceptRole(), rombe, parent,
				graph, 120, collection, index, 60, 60);
		addToList(collection.get(index).getTransformationConceptRole(),
				triangel, parent, graph, 120, collection, index, 60, 60);

	}

	public void addToList(ArrayList<String> conceptRoleCollection, String shape, Object parent, mxGraph graph, int startPos,
			ArrayList<Constructs> collections, int index, int shape1, int shape2) {
	
		int ant = 0;
		int newPos = 0;
		String conceptRole = "";
		String conname = collections.get(index).getConstructName();

		while (ant < conceptRoleCollection.size()) {
			if ("".equals(conceptRoleCollection.get(ant))) {
			} else {
				conceptRole = conceptRoleCollection.get(ant);
				conceptRole = conceptRole.replace(conname + "_", "");
				if ("".equals(conceptRole)) {
					continue;
				}
			}

			String conceptName = Util.splitLabel(conceptRole);
			if (shape == rec && "" != conceptName) {
			} else
				v = graph.createVertex(parent, conceptName, conceptName,
						newPos + 5, startPos, shape1, shape2, shape);
			lis.put(conceptRoleCollection.get(ant), v);
			ant++;
		}
	}

}
