package org.openmrs.module.kenyaemr.model;

public class RegimenDetails {
	private String drugName;
	private Integer drugConcept;
	private String strength;
	private String formulation;
	private String frequency;
	private String route;
	private String duration;
	
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public Integer getDrugConcept() {
		return drugConcept;
	}
	public void setDrugConcept(Integer drugConcept) {
		this.drugConcept = drugConcept;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getFormulation() {
		return formulation;
	}
	public void setFormulation(String formulation) {
		this.formulation = formulation;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
}
