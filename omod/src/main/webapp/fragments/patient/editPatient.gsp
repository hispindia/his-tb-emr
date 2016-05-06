<%
	ui.decorateWith("kenyaui", "panel", [ heading: (config.heading ?: "Edit Patient"), frameOnly: true ])
	
	ui.includeJavascript("kenyaemr", "controllers/addresshierarchy.js")

	def patientNameField = [
			[
					[ object: command, property: "personName.givenName", label: "Patient's Name *" ]
			]
	]
	
	def fatherNameField = [
			[
					[ object: command, property: "fatherName", label: "Father's Name " ]
			]
	]
	
	def nationalId = [
			[
					[ object: command, property: "nationalId", label: "National ID Number" ]
			]
	]
	
	def currentTownshipTBNumber = [
			[
					[ object: command, property: "currentTownshipTBNumber", label: "Current Township TB Number" , config: [  size: 20 ]]
			]
	]


	def otherDemogFieldRows = [
			[
					[ object: command, property: "dead", label: "Deceased " ],
					[ object: command, property: "deathDate", label: "Date of death" ]
			]
	]
	
	def placeOfBirth = [
			[
					[ object: command, property: "placeOfBirth", label: "Place Of Birth" ]
			]
	]
	
	def occupation = [
			[
			]		
	]
	
		
	def patientSource1 = [
			[
					[ object: command, property: "entryPoint", label: "Entry Point ", config: [ style: "list", answerTo: entryPointList ] ],
					[ object: command, property: "entrySourceId", label: "Entry source ID" ]
			]		
	] 
	
	
	def addressFieldRows = [
			[
					[ object: command, property: "personAddress.address3", label: "Workplace/university/school address", config: [ type: "textarea",  rows: 2,size: 20 ] ],
					[ object: command, property: "personAddress.address1", label: "Permanent Home Address ", config: [ type: "textarea", rows: 2, size: 20 ] ],
					[ object: command, property: "personAddress.address2", label: "Temporary Address for Treatment", config: [ type: "textarea", rows: 2, size: 20 ] ],
					[ object: command, property: "telephoneContact", label: "Contact Number" ]
			]
	]
	
	def previousTownship = [
			[
					[ object: command, property: "previousTownshipTBNumber", label: "Previous Township TB Number *", config: [  size: 20 ] ],
					[ object: command, property: "township", label: "Township", config: [ style: "search", answerTo: townShipList ] ]
					
			]
			
	]
			
	def previousRegimenDetail = [
			[
					[ object: command, property: "previousRegimenType", label: "Regimen" ],
					[ object: command, property: "previousRegimenStartDate", label: "Start Date"]
					
			]	
		]	
			
	def previousTBOutcomeDetail = [
			[
					[ object: command, property: "previousTBOutcome", label: "Outcome", config: [style:"list",  answerTo: outcome ] ],
					[ object: command, property: "previousTBOutcomeDate", label: "Outcome Date"]
					
			]	
		]	 
		
	def genSampleIdDetail = [
			[
					[ object: command, property: "genSampleId", label: "Sample ID" ]
			]	
		]	 

	def genSpecificationDetail = [
			[
					[ object: command, property: "genSpecificationPlace", label: "Specimen Collection Place" ],
					[ object: command, property: "genSpecificationDate", label: "Date"]
			]	
		]	 
	def genResultDetail = [
			[
					[ object: command, property: "genResult", label: "Xpert MTB/Rif Result" , config: [style:"list",  answerTo: getResultList ]],
					[ object: command, property: "genResultDate", label: "Date"]
			]	
		]	
%>

<form id="edit-patient-form" method="post" action="${ ui.actionLink("kenyaemr", "patient/editPatient", "savePatient") }">
	<% if (command.original) { %>
		<input type="hidden" name="personId" value="${ command.original.id }"/>
	<% } %>

	<div class="ke-panel-content">

		<div class="ke-form-globalerrors" style="display: none"></div>

		<div class="ke-form-instructions">
			<strong>*</strong> indicates a required field
		</div>

		<fieldset>
			<legend>ID Numbers</legend>

			<table>
				<tr>
					<td class="ke-field-label"><b>DR-TB Suspect Number* </b></td>
					<td>${ ui.includeFragment("kenyaui", "widget/field", [ object: command, property: "drTBSuspectNumber" ]) }</td>
				</tr>
				<tr>
					<td class="ke-field-label"><b>Patient ID*</b></td>
					<td><input name="systemPatientId" style="width: 260px" value=${ patientIdentifier} readonly autocomplete="off" ></td>
				</tr>
			</table><br/>
		</fieldset>

		<fieldset>
			<legend>Demographics</legend>

			<% patientNameField.each { %>
			${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			<% } %>
			
			<% fatherNameField.each { %>
			${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			<% } %>
			
			<table>
				<tr>
					<td valign="top">
						<label class="ke-field-label">Birthdate *</label>
						<span class="ke-field-content">
							${ ui.includeFragment("kenyaui", "widget/field", [ id: "patient-birthdate", object: command, property: "birthdate" ]) }
<!--
							<span id="patient-birthdate-estimated">
								<input type="radio" name="birthdateEstimated" value="true" ${ command.birthdateEstimated ? 'checked="checked"' : '' }/> Estimated
								<input type="radio" name="birthdateEstimated" value="false" ${ !command.birthdateEstimated ? 'checked="checked"' : '' }/> Exact
							</span> 
	
-->						&nbsp;&nbsp;&nbsp;
							<span id="from-age-button-placeholder"></span>
						</span>
					</td>
				</tr>
				<tr>
					<td>
						<% if (recordedAsDeceased) { %>
							<div class="ke-warning" style="margin-bottom: 5px">
								<% otherDemogFieldRows.each { %>
									${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
								<% } %>
							</div>
						<% } %>
					</td>			
				</tr>
				<tr>
				<td valign="top">
						<label class="ke-field-label">Gender *</label>
						<span class="ke-field-content">
							<input type="radio" name="gender" value="F" id="gender-F" ${ command.gender == 'F' ? 'checked="checked"' : '' }/> Female
							<input type="radio" name="gender" value="M" id="gender-M" ${ command.gender == 'M' ? 'checked="checked"' : '' }/> Male
							<span id="gender-F-error" class="error" style="display: none"></span>
							<span id="gender-M-error" class="error" style="display: none"></span>
						</span>
					</td>
				</tr>
			</table>
			
			<% nationalId.each { %>
			${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			<% } %>

			
			<% currentTownshipTBNumber.each { %>
			${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			<% } %>


			<% placeOfBirth.each { %>
			${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			<% } %>
			<table>
				<tr>
					<td> 
						<center><label class="ke-field-label">Occupation</label></center>
						<span class="ke-field-content" >
							${ ui.includeFragment("kenyaui", "widget/field",[ id:"occupation", object: command, property: "occupation", config: [ style: "list", answerTo: occupationConcept ] ]) }
						</span>
					</td>
					 <td  id="otherStatus">
						<center><label class="ke-field-label">If other, Please specify</label></center>
						<span class="ke-field-content">
							${ ui.includeFragment("kenyaui", "widget/field",[ id:"otherOccupation",object: command, property: "otherOccupation" ]) }
						</span>	
					</td>
				</tr>
			</table>
			<br />						
			<legend>Address</legend>

			<% occupation.each { %>
			${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			<% } %>
		</fieldset>



		<fieldset>


			<% addressFieldRows.each { %>
				${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			<% } %>

		<div ng-controller="AddresshierarchyCtrl" data-ng-init="init()">
        <table>
        <tr>
        
        <td valign="top">
        <label class="ke-field-label">State / Region</label>
        <span class="ke-field-content">
        <select style="width: 180px;" name="personAddress.stateProvince" ng-model="myState" ng-options="state for state in states track by state" ng-change="stateSelection(myState)"></select>
        </span>
        </td>
        
        <td valign="top">
        <label class="ke-field-label">Township</label>
        <span class="ke-field-content">
        <select style="width: 180px;" name="personAddress.countyDistrict" ng-model="myTownship" ng-options="township for township in townships track by township" ng-change="townshipSelection(myState,myTownship)"></select>
        </span>
        </td>
        
        <td valign="top">
        <label class="ke-field-label">Town / Village</label>
        <span class="ke-field-content">
        <select style="width: 180px;"name="personAddress.cityVillage" ng-model="myVillage" ng-options="village for village in villages track by village"></select>
        </span>
        </td>
     
        </tr>
        </table>
        </div>
		</fieldset>

		<fieldset>
			<legend>Patient Source</legend>

			 <% patientSource1.each { %>
			   ${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			 <% } %>
		</fieldset>

		<fieldset>
			<legend>Previous TB treatment Episodes</legend>
			<table>
				<tr>
					<td valign="top">
						<span class="ke-field-content" >
							<b>Previous History of TB :</b>
							<% if(command.tbHistoryStatus) {%> 
								<input type="radio" name="tbHistoryStatus" value="1065" ${ command.tbHistoryStatus.conceptId == 1065 ? 'checked="checked"' : '' }/> Yes
								<input type="radio" name="tbHistoryStatus" value="1066" ${ command.tbHistoryStatus.conceptId == 1066 ? 'checked="checked"' : '' } /> No	
							<% } else {%>
								<input type="radio" name="tbHistoryStatus" value="1065" /> Yes
								<input type="radio" name="tbHistoryStatus" value="1066"  /> No
							<% } %>
						</span>
						
					</td>
					
					<td valign="top" id="checkInField">
						<span class="ke-field-content">
							<input name="checkInType" id="checkInType" ${ command.checkInType == '1' ? '1' : '0' }/> 
						</span>		
					</td>
				</tr>
			</table>

			<div id="previousTBHistory">
				<% previousTownship.each { %>
				   ${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
				 <% } %>
	 			<table>
					<tr><td style="padding-right:5px">
							<% previousRegimenDetail.each { %>
							   ${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
							 <% } %>
						</td><td>
						<% if(command.previousRegimenStartDateType) {%> 
							<input type="radio" name="previousRegimenStartDateType" value="163545" ${ command.previousRegimenStartDateType.conceptId == 163545 ? 'checked="checked"' : '' } /> Estimated
							<input type="radio" name="previousRegimenStartDateType" value="163546" ${ command.previousRegimenStartDateType.conceptId == 163546 ? 'checked="checked"' : '' } /> Exact
						<% } else {%>
							<input type="radio" name="previousRegimenStartDateType" value="163545" /> Estimated
							<input type="radio" name="previousRegimenStartDateType" value="163546"  /> Exact
						<% } %>
						</td>
					</tr>
				</table>
				
				<% previousTBOutcomeDetail.each { %>
				   ${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
				 <% } %>
			</div>			 
			 
		</fieldset>
		<fieldset>
			<legend>Gene Xpert Test</legend>
			
			<% genSampleIdDetail.each { %>
			   ${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			 <% } %>
			<% genSpecificationDetail.each { %>
			   ${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			 <% } %>
			<% genResultDetail.each { %>
			   ${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			 <% } %>
		</fieldset>
	</div>

	<div class="ke-panel-footer">
		<% if (command.original) { %>
			<button onClick="checkIn(1)" type="submit">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/ok.png") }" /> ${"Save Changes" }
			</button>		
		<% } else {%>
		<!--<button onClick="checkIn(1)" type="submit">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/ok.png") }" /> ${ "Create Patient and Check In" }
			</button>
		-->
			<button onClick="return validateDateOfRegistration();checkIn(0)" type="submit">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/ok.png") }" /> ${ "Create Patient and Check In" }
			</button>
			<input type="text" id="dateOfRegistration" name="dateOfRegistration" placeholder="Date of registration">
			<script type="text/javascript">
                        jq(document).ready(function () {
                            jq('#dateOfRegistration').datepicker({
                                dateFormat: "dd-M-yy", 
                                showAnim: 'blind'
                            });
                        });
                    </script>
		<% } %>
		<% if (config.returnUrl) { %>
			<button type="button" class="cancel-button"><img src="${ ui.resourceLink("kenyaui", "images/glyphs/cancel.png") }" /> Cancel</button>
		<% } %>
	</div>
	
</form>

<!-- You can't nest forms in HTML, so keep the dialog box form down here -->
${ ui.includeFragment("kenyaui", "widget/dialogForm", [
		buttonConfig: [ id: "from-age-button", label: "from age", iconProvider: "kenyaui", icon: "glyphs/calculate.png" ],
		dialogConfig: [ heading: "Calculate Birthdate", width: 40, height: 40 ],
		fields: [
				[ label: "Age in y/m/w/d", formFieldName: "age", class: java.lang.String ],
				[ label: "On date", formFieldName: "now", class: java.util.Date, initialValue: new Date() ]
		],
		fragmentProvider: "kenyaemr",
		fragment: "emrUtils",
		action: "birthdateFromAge",
		onSuccessCallback: "updateBirthdate(data);",
		onOpenCallback: """jQuery('input[name="age"]').focus()""",
		submitLabel: ui.message("general.submit"),
		cancelLabel: ui.message("general.cancel")
]) }

<script type="text/javascript">
	var patientId=${patientId};
	jQuery(document).ready(function(){
		if(patientId==null)
	    { 
	      	jq("#otherStatus").hide();
	      	document.getElementById("previousTBHistory").style.display = 'none';
	    }
	    else
	    {
	    	var stats=${statusother};
	        if(stats==5622)
	        { 
	        	jq("#otherStatus").show();
	        }
	         else
	        {
		        jq("#otherStatus").hide();
	        }
	        
	        var history = ${tbHistory};
	        if(history==1065)
	        { 
	        	  document.getElementById("previousTBHistory").style.display = "";
	        }
	         else
	        {
		          document.getElementById("previousTBHistory").style.display = 'none';
	        }
	        
	        
	     }   

		 document.getElementById('checkInField').style.display='none';
		 	var m_names = new Array("Jan", "Feb", "Mar", 
			"Apr", "May", "Jun", "Jul", "Aug", "Sep", 
			"Oct", "Nov", "Dec");

			var d = new Date();
			var curr_date = d.getDate();
			var curr_month = d.getMonth();
			var curr_year = d.getFullYear();
			var newDate = curr_date + "-" + m_names[curr_month]	+ "-" + curr_year;
			if(document.getElementById('dateOfRegistration')!=null){
				document.getElementById('dateOfRegistration').value=newDate;
			}

		jq('#occupation').on('change', function() 
            {  
			getSelectOption(jq(this).val());
			
		});

		
		 jq('input[type=radio][name=tbHistoryStatus]').change(function() {
	        if (this.value == 1065) {
	                 document.getElementById("previousTBHistory").style.display = "";
	        }
	        else  {
	                  document.getElementById("previousTBHistory").style.display = 'none';
	        }
 		   });
	});
	
    function getSelectOption(elem)
    {   
        
        if(elem== 5622){ 
            document.getElementById("otherStatus").style.display = "";
        }
        
        else
        {
          document.getElementById("otherStatus").style.display = 'none';
        }
    };


	jQuery(function() {
		jQuery('#from-age-button').appendTo(jQuery('#from-age-button-placeholder'));
		
		jQuery('#edit-patient-form .cancel-button').click(function() {
			ui.navigate('${ config.returnUrl }');
		});

		kenyaui.setupAjaxPost('edit-patient-form', {
			onSuccess: function(data) {
				if (data.id) {
					<% if (config.returnUrl) { %>
					ui.navigate('${ config.returnUrl }');
					<% } else { %>
					
					 if (document.getElementById('checkInType').value==1) { 
						ui.navigate('kenyaemr', 'registration/registrationViewPatient', { patientId: data.id });
						} else { 
						ui.navigate('kenyaemr', 'registration/registrationViewPatient', { patientId: data.id });
					 } 
					<% } %>
				} else {
					kenyaui.notifyError('Saving patient was successful, but unexpected response');
				}
			}
		});
	});
	
	
	function checkIn(check){
		document.getElementById('checkInType').value = check;
	}

	function updateBirthdate(data) {
		var birthdate = new Date(data.birthdate);

		kenyaui.setDateField('patient-birthdate', birthdate);
		kenyaui.setRadioField('patient-birthdate-estimated', 'true');
	}
	
	function validateDateOfRegistration() {
	var dateOfRegistration = jQuery("#dateOfRegistration").val();
	if(dateOfRegistration==""){
	alert("Please Enter Date Of Registration");
	return false;
	} 
	}
</script>

<style>

</style>