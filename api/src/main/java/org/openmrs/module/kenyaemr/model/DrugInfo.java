package org.openmrs.module.kenyaemr.model;

public class DrugInfo {
	private Integer drugInfoId;
	private String drugName;
	private String drugCode;
	private String adverseEffect;

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
	
	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getAdverseEffect() {
		return adverseEffect;
	}

	public void setAdverseEffect(String adverseEffect) {
		this.adverseEffect = adverseEffect;
	}
}