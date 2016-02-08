package com.furuta.bean;

import java.io.Serializable;


/**
 *  A concept returned by the Concept Expansion service, given a seed.
 *  This class is a mirror of com.ibm.watson.developer_cloud.concept_expansion.v1.model.Concept.
 */
public class Concept implements Serializable {

	private static final long serialVersionUID = -8059101017711958019L;
	
	private String name;
	private Double prevalence;

	public Concept() {}
	
	public Concept(String name, Double prevalence) {
		this.name = name;
		this.prevalence = prevalence;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrevalence() {
		return prevalence;
	}
	
	public void setPrevalence(Double prevalence) {
		this.prevalence = prevalence;
	}
}
