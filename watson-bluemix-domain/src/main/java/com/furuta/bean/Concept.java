package com.furuta.bean;

import java.io.Serializable;

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
