package com.graphEditor;

import java.util.ArrayList;
import java.util.Iterator;

public class ConstructsCollection {

	private ArrayList<Constructs> constructList;

	public ConstructsCollection() {
		constructList = new ArrayList<Constructs>();
	}

	public ArrayList<Constructs> getConstructList() {
		return constructList;
	}

	public void removeObjects() {
		constructList.clear();
	}

	public void addConstructObject(Constructs obj) {
		if (obj instanceof Constructs && obj != null) {
			constructList.add(obj);
		} else
			System.out.println("Unable to add object!");
	}

	public Constructs getConstructIndex(int index) {
		if (index >= 0) {
			return constructList.get(index);
		} else
			return null;
	}

	public String printCollection() {
		String display = "";
		Iterator<Constructs> iter = constructList.iterator();
		while (iter.hasNext()) {
			display = display + "\n" + iter.next().toString();
		}
		return display;
	}

}
