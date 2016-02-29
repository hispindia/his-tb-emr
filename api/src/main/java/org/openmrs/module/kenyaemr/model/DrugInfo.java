package org.openmrs.module.kenyaemr.model;

public class DrugInfo {
	private Integer drugInfoId;
	private String drugName;
	private String toxicity;
	private String riskFactor;
	private String suggestedManagement;
	private String drugInteraction;
	private String suggestedManagementInteraction;

	public Integer getDrugInfoId() {
		return drugInfoId;
	}

	public void setDrugInfoId(Integer drugInfoId) {
		this.drugInfoId = drugInfoId;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getToxicity() {
		return toxicity;
	}

	public void setToxicity(String toxicity) {
		this.toxicity = toxicity;
	}

	public String getRiskFactor() {
		return riskFactor;
	}

	public void setRiskFactor(String riskFactor) {
		this.riskFactor = riskFactor;
	}

	public String getSuggestedManagement() {
		return suggestedManagement;
	}

	public void setSuggestedManagement(String suggestedManagement) {
		this.suggestedManagement = suggestedManagement;
	}

	public String getDrugInteraction() {
		return drugInteraction;
	}

	public void setDrugInteraction(String drugInteraction) {
		this.drugInteraction = drugInteraction;
	}

	public String getSuggestedManagementInteraction() {
		return suggestedManagementInteraction;
	}

	public void setSuggestedManagementInteraction(
			String suggestedManagementInteraction) {
		this.suggestedManagementInteraction = suggestedManagementInteraction;
	}
}
