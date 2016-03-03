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

package org.openmrs.module.kenyaemr.fragment.controller.header;

import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.util.ServerInformation;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.util.OpenmrsUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller for page header
 */
public class PageHeaderFragmentController {

	public void controller(FragmentModel model,PageRequest pageRequest,
						   @SpringBean KenyaUiUtils kenyaui) {

		Map<String, Object> kenyaemrInfo = ServerInformation.getKenyaemrInformation();

		String moduleVersion = (String) kenyaemrInfo.get("version");
		boolean isSnapshot = moduleVersion.endsWith("SNAPSHOT");

		if (isSnapshot) {
			Date moduleBuildDate = (Date) kenyaemrInfo.get("buildDate");
			moduleVersion += " (" + kenyaui.formatDateTime(moduleBuildDate) + ")";
		}

		model.addAttribute("moduleVersion", moduleVersion);

		model.addAttribute("systemLocation", Context.getService(KenyaEmrService.class).getDefaultLocation());
		model.addAttribute("systemLocationCode", Context.getService(KenyaEmrService.class).getDefaultLocationMflCode());
		
		// Get apps for the current user
		List<AppDescriptor> apps = Context.getService(AppFrameworkService.class).getAppsForCurrentUser();

		// Sort by order property
		Collections.sort(apps, new Comparator<AppDescriptor>() {
			@Override
			public int compare(AppDescriptor left, AppDescriptor right) {
				return OpenmrsUtil.compareWithNullAsGreatest(left.getOrder(), right.getOrder());
			}
		});

		model.addAttribute("apps", apps);
		AppDescriptor currentApp = kenyaui.getCurrentApp(pageRequest);
		model.addAttribute("currentApp", currentApp);
		
	}
}