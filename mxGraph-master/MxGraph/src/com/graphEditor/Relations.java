package com.graphEditor;

public class Relations {

	private String relationName;
	private String fromIndivid;
	private String toIndivid;
	private String parent;
	private String toIndividConceptType;
	private String fromIndividConceptType;
	private String parentOfparent;
	
	
	public Relations(String relationName, String fromIndivid, String toIndivid,
			String parent, String toIndividConceptType,
			String fromIndividConceptType, String parentOfparent) {
		super();
		this.relationName = relationName;
		this.fromIndivid = fromIndivid;
		this.toIndivid = toIndivid;
		this.parent = parent;
		this.toIndividConceptType = toIndividConceptType;
		this.fromIndividConceptType = fromIndividConceptType;
		this.parentOfparent = parentOfparent;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getFromIndivid() {
		return fromIndivid;
	}

	public void setFromIndivid(String fromIndivid) {
		this.fromIndivid = fromIndivid;
	}

	public String getToIndivid() {
		return toIndivid;
	}

	public void setToIndivid(String toIndivid) {
		this.toIndivid = toIndivid;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getToIndividConceptType() {
		return toIndividConceptType;
	}

	public void setToIndividConceptType(String toIndividConceptType) {
		this.toIndividConceptType = toIndividConceptType;
	}

	public String getFromIndividConceptType() {
		return fromIndividConceptType;
	}

	public void setFromIndividConceptType(String fromIndividConceptType) {
		this.fromIndividConceptType = fromIndividConceptType;
	}

	public String getParentOfparent() {
		return parentOfparent;
	}

	public void setParentOfparent(String parentOfparent) {
		this.parentOfparent = parentOfparent;
	}

	@Override
	public String toString() {
		return "Relations [relationName=" + relationName + ", fromIndivid="
				+ fromIndivid + ", toIndivid=" + toIndivid + ", parent="
				+ parent + ", toIndividConceptType=" + toIndividConceptType
				+ ", fromIndividConceptType=" + fromIndividConceptType
				+ ", parentOfparent=" + parentOfparent + "]";
	}
	
	
	



	
	
}
