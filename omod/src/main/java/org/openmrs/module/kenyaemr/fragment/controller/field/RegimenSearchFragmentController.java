/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.fragment.controller.field;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugInfo;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.module.kenyaemr.model.RegimenDetails;
import org.openmrs.module.kenyaemr.regimen.RegimenDefinition;
import org.openmrs.module.kenyaemr.regimen.RegimenDefinitionGroup;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.regimen.RegimenPropertyConfiguration;
import org.openmrs.module.kenyaemr.util.EmrUiUtils;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 *
 */
public class RegimenSearchFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
			               @FragmentParam("category") String category,
						   @FragmentParam(value = "includeGroups", required = false) Set<String> includeGroups,
						   FragmentModel model,
						   UiUtils ui,
						   @SpringBean RegimenManager regimenManager,
						   @SpringBean EmrUiUtils kenyaUi) {

		List<RegimenDefinitionGroup> regimenGroups = regimenManager.getRegimenGroups(category);

		if (includeGroups != null) {
			regimenGroups = filterGroups(regimenGroups, includeGroups);
		}

		List<RegimenDefinition> regimenDefinitions = new ArrayList<RegimenDefinition>();
		for (RegimenDefinitionGroup group : regimenGroups) {
			regimenDefinitions.addAll(group.getRegimens());
		}
		
		KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
		Map<String, DrugInfo> drugInfoMap = new LinkedHashMap<String, DrugInfo>();
		for(DrugInfo drugInfo:kenyaEmrService.getDrugInfo()){
			//drugInfoMap.put(drugInfo.getDrugName().toString(), drugInfo);
		}
		
		model.addAttribute("maxComponents", 5);
		model.addAttribute("drugs", regimenManager.getDrugs(category));
		model.addAttribute("regimenGroups", regimenGroups);
		model.addAttribute("regimenDefinitions", kenyaUi.simpleRegimenDefinitions(regimenDefinitions, ui));
		model.addAttribute("patient", patient);
		model.addAttribute("drugInfoMap", drugInfoMap);
		model.addAttribute("count", 1);
		Concept routeCon=Context.getConceptService().getConceptByUuid("162394AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		model.addAttribute("routeConAnss",routeCon.getAnswers());
		
		Properties props = new Properties();
		InputStream stream=null;
		for (RegimenPropertyConfiguration configuration : Context.getRegisteredComponents(RegimenPropertyConfiguration.class)) {
				ClassLoader loader = configuration.getClassLoader();
				stream = loader.getResourceAsStream(configuration.getDefinitionsPath());
		}
		try {
			props.loadFromXML(stream);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Concept concept1=Context.getConceptService().getConceptByUuid("71060AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept2=Context.getConceptService().getConceptByUuid("86767AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept3=Context.getConceptService().getConceptByUuid("78788AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept4=Context.getConceptService().getConceptByUuid("75976AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept5=Context.getConceptService().getConceptByUuid("74123AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept6=Context.getConceptService().getConceptByUuid("71108AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		String drugNam="";
	    
		RegimenDetails regimenDetails1=new RegimenDetails();
		drugNam=concept1.getName().getName();
		regimenDetails1.setDrugName(concept1.getName().getName());
		regimenDetails1.setDrugConcept(concept1.getConceptId());
		regimenDetails1.setStrength(props.getProperty(drugNam+".strength"));
		regimenDetails1.setFormulation(props.getProperty(drugNam+".type"));
		regimenDetails1.setFrequency(props.getProperty(drugNam+".frequency"));
		regimenDetails1.setRoute(props.getProperty(drugNam+".route"));
		model.addAttribute("regimenDetails1",regimenDetails1);
		
		RegimenDetails regimenDetails2=new RegimenDetails();
		drugNam=concept2.getName().getName();
		regimenDetails2.setDrugName(concept2.getName().getName());
		regimenDetails2.setDrugConcept(concept2.getConceptId());
		regimenDetails2.setStrength(props.getProperty(drugNam+".strength"));
		regimenDetails2.setFormulation(props.getProperty(drugNam+".type"));
		regimenDetails2.setFrequency(props.getProperty(drugNam+".frequency"));
		regimenDetails2.setRoute(props.getProperty(drugNam+".route"));
		model.addAttribute("regimenDetails2",regimenDetails2);
		
		RegimenDetails regimenDetails3=new RegimenDetails();
		drugNam=concept3.getName().getName();
		regimenDetails3.setDrugName(concept3.getName().getName());
		regimenDetails3.setDrugConcept(concept3.getConceptId());
		regimenDetails3.setStrength(props.getProperty(drugNam+".strength"));
		regimenDetails3.setFormulation(props.getProperty(drugNam+".type"));
		regimenDetails3.setFrequency(props.getProperty(drugNam+".frequency"));
		regimenDetails3.setRoute(props.getProperty(drugNam+".route"));
		model.addAttribute("regimenDetails3",regimenDetails3);
		
		RegimenDetails regimenDetails4=new RegimenDetails();
		drugNam=concept4.getName().getName();
		regimenDetails4.setDrugName(concept4.getName().getName());
		regimenDetails4.setDrugConcept(concept4.getConceptId());
		regimenDetails4.setStrength(props.getProperty(drugNam+".strength"));
		regimenDetails4.setFormulation(props.getProperty(drugNam+".type"));
		regimenDetails4.setFrequency(props.getProperty(drugNam+".frequency"));
		regimenDetails4.setRoute(props.getProperty(drugNam+".route"));
		model.addAttribute("regimenDetails4",regimenDetails4);
		
		RegimenDetails regimenDetails5=new RegimenDetails();
		drugNam=concept5.getName().getName();
		regimenDetails5.setDrugName(concept5.getName().getName());
		regimenDetails5.setDrugConcept(concept5.getConceptId());
		regimenDetails5.setStrength(props.getProperty(drugNam+".strength"));
		regimenDetails5.setFormulation(props.getProperty(drugNam+".type"));
		regimenDetails5.setFrequency(props.getProperty(drugNam+".frequency"));
		regimenDetails5.setRoute(props.getProperty(drugNam+".route"));
		model.addAttribute("regimenDetails5",regimenDetails5);
		
		RegimenDetails regimenDetails6=new RegimenDetails();
		drugNam=concept6.getName().getName();
		regimenDetails6.setDrugName(concept6.getName().getName());
		regimenDetails6.setDrugConcept(concept6.getConceptId());
		regimenDetails6.setStrength(props.getProperty(drugNam+".strength"));
		regimenDetails6.setFormulation(props.getProperty(drugNam+".type"));
		regimenDetails6.setFrequency(props.getProperty(drugNam+".frequency"));
		regimenDetails6.setRoute(props.getProperty(drugNam+".route"));
		model.addAttribute("regimenDetails6",regimenDetails6);
		
		List<DrugOrderProcessed> drugOrderProcessedd = new ArrayList<DrugOrderProcessed>();
		for(int i=0;i<6;i++){
			DrugOrderProcessed drugOrderProcess=new DrugOrderProcessed();
			drugOrderProcessedd.add(drugOrderProcess);
		}
		model.addAttribute("drugOrderProcessedd", drugOrderProcessedd);
	}

	/**
	 * Filter regimen groups by code
	 * @param groups the groups
	 * @param includeGroupCodes the group codes to include
	 * @return the filtered groups
	 */
	private static List<RegimenDefinitionGroup> filterGroups(List<RegimenDefinitionGroup> groups, Set<String> includeGroupCodes) {
		List<RegimenDefinitionGroup> filtered = new ArrayList<RegimenDefinitionGroup>();
		for (RegimenDefinitionGroup group : groups) {
			if (includeGroupCodes.contains(group.getCode())) {
				filtered.add(group);
			}
		}
		return filtered;
	}
}