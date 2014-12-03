package com.maxgraph.editor;

import java.util.ArrayList;

public class Constructs {

	private String constructName;
	private ArrayList<String> classConceptRole;
	private ArrayList<String> propertyConceptRole;
	private ArrayList<String> stateConceptRole;
	private ArrayList<String> transformationConceptRole;
	private ArrayList<Relations> relation;
	
	public Constructs(String constructName,
			ArrayList<String> classConceptRole,
			ArrayList<String> propertyConceptRole,
			ArrayList<String> stateConceptRole,
			ArrayList<String> transformationConceptRole,
			ArrayList<Relations> relation) {
		
		this.constructName = constructName;
		this.classConceptRole = classConceptRole;
		this.propertyConceptRole = propertyConceptRole;
		this.stateConceptRole = stateConceptRole;
		this.transformationConceptRole = transformationConceptRole;
		this.relation = relation;
		
	}

	
	public ArrayList<Relations> getRelation() {
		return relation;
	}


	public void setRelation(ArrayList<Relations> relation) {
		this.relation = relation;
	}

	public int getArraySize(ArrayList<String> list){
		return list.size();
	}
	
	
	public String getConstructName() {
		return constructName;
	}


	public void setConstructName(String constructName) {
		this.constructName = constructName;
	}


	public ArrayList<String> getClassConceptRole() {
		return classConceptRole;
	}


	public void setClassConceptRole(ArrayList<String> classConceptRole) {
		this.classConceptRole = classConceptRole;
	}


	public ArrayList<String> getPropertyConceptRole() {
		return propertyConceptRole;
	}


	public void setPropertyConceptRole(ArrayList<String> propertyConceptRole) {
		this.propertyConceptRole = propertyConceptRole;
	}


	public ArrayList<String> getStateConceptRole() {
		return stateConceptRole;
	}


	public void setStateConceptRole(ArrayList<String> stateConceptRole) {
		this.stateConceptRole = stateConceptRole;
	}


	public ArrayList<String> getTransformationConceptRole() {
		return transformationConceptRole;
	}


	public void setTransformationConceptRole(
			ArrayList<String> transformationConceptRole) {
		this.transformationConceptRole = transformationConceptRole;
	}



	@Override
	public String toString() {
		return "Constructs [constructName=" + constructName
				+ ", classConceptRole=" + classConceptRole
				+ ", propertyConceptRole=" + propertyConceptRole
				+ ", stateConceptRole=" + stateConceptRole
				+ ", transformationConceptRole=" + transformationConceptRole
				+ ", relation=" + relation + "]";
	}



}
