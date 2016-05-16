<%
	ui.decorateWith("kenyaui", "panel", [ heading: "MDR-TB Treatment Card (MDR-TB FORM 01)" ])
%>

<% if (config.complete) { %>
<div class="ke-stack-item">
	<div class="widget-content">
		<table id="table1" width="100%" border="1">
        <tr>
        <td  valign="top" width="50%" colspan="3">
	        <table width="100%" border="1">
				<tr bgcolor="#778899">
					<td colspan="3">
						<h4><strong><center>Patient Identification</center> </strong></h4>
					</td>
				</tr>
				<tr>
					<td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
							<br/><strong>Name :</strong>
							<br/><strong>Sex :  </strong>
							<br/><strong>Age/DOB : </strong>
							<br/><strong>Registration group :  </strong>
							<br/><strong>MDR-TB registration number : </strong>
							<br/><strong>Date of Registration : </strong>
							<br/> <strong>Current Township TB number : </strong>
							
					</td>
	                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
	                	
							<br/> ${ patientName }
							<br/> ${ patientGender } 
							<br/> ${ patientAge } / ${ birthDate }
							<br/> ${ registrationGroup } 
							<br/> <% if(mdrTBRegistrationNumber) {%> ${mdrTBRegistrationNumber  }	<% } %>
							<br/>  ${registrationDateVal}
							<br/>  ${patientWrap.currentTownshipTBNumber}
					</td>
				</tr>
	            </table>
			</td>			<!--========================================================================================================================================== -->
			<td width="50%" colspan="3"  valign="top">
	            <table width="100%" border="1">
	            <tr bgcolor="#778899">
					<td colspan="3">
						<h4><strong><center>Previous tuberculosis treatment episodes</center> </strong></h4>
					</td>
				</tr>
	            <tr>
					<td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
							<br/><strong>Previous Township TB number : </strong>
							<br/><strong>Township</strong>
							<br/><strong>Site : </strong>
							<br/><strong>If extra pulmonary, SITE :  </strong>
							<br/><strong>Regimen :  </strong>
							<br/><strong>Start date : </strong>
							<br/><strong>Outcome : </strong>
					</td>
	                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
	                	
							<br/> ${ patientWrap.previousTownshipTBNumber }
							<br/> ${ townshipVal } 
							<br/> ${ tbDiseaseClasificationVal }
							<br/> ${ tbSiteVal } 
							<br/> <% if(patientWrap.previousRegimenType) {%> ${patientWrap.previousRegimenType  }	<% } %>
							<br/>  <% if(patientWrap.previousRegimenStartDate) {%> ${patientWrap.previousRegimenStartDate  } (${regimenStartDateTypeVal})	<% } %>
							<br/>  ${outcomeVal}
					</td>
				</tr>
	            
            </table>
            </td>
            </tr>
            
            <tr>
				<td width="50%" colspan="3"  valign="top">
					<table width="100%" border="1">
			            <tr bgcolor="#778899">
							<td colspan="3">
								<h4><strong><center>Drug abbreviations</center> </strong></h4>
							</td>
						</tr>
			            <tr>
							<td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
									<br/> <strong>Address : </strong>
									<br/><strong> Treatment centre : </strong>
									<br/><strong>Name of DOT provider</strong>
									<br/><strong>DOT supervisor : </strong>
									<br/><strong>Contact of MDR-TB case : </strong>
							</td>
			                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
									<br/> <% if(address.address1) { %> ${ address.address1},  <%} %> <% if(address.cityVillage !='?') { %> ${address.cityVillage}, <%} %>
										<% if(address.countyDistrict !='?') { %> ${ address.countyDistrict},  <%} %> <% if(address.stateProvince !='?') { %> ${address.stateProvince} <%} %>
									<br/> ${ systemLocation }
									<br/> ${ dotProviderVal } 
									<br/> ${ supervisorVal }
									<br/> ${contactCaseVal}
							</td>
						</tr>
		            </table>
				</td>
				<td width="50%" colspan="3"  valign="top">
										<table width="100%" border="1">
			            <tr bgcolor="#778899">
							<td colspan="3">
								<h4><strong><center>HIV Information</center> </strong></h4>
							</td>
						</tr>
			            <tr>
							<td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
									<br/><strong>HIV test : </strong>
									<br/><strong>Date of test </strong>
									<br/><strong>Results : </strong>
									<br/><strong>Started on ART :  </strong>
									<br/><strong>Started on CPT :  </strong>
							</td>
			                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
			                	
									<br/> ${ hivTestVal }
									<br/> ${ hivTestDateVal } 
									<br/> ${ hivTestResultVal }
									<br/> <% if(artStartedVal){%> ${ artStartedVal } <%}%> <% if(artStartDateVal){%>  (${artStartDateVal})<%}%> 
									<br/><% if(cptStartedVal){%> ${ cptStartedVal } <%}%> <% if(cptStartedVal){%>  (${cptStartDateVal})<%}%> 
							</td>
						</tr>
			            
		            </table>
				</td>
			</tr>
            <tr>
				<td width="32%" valign="top" colspan="2">
					<table width="100%" border="1">
						<tr>
							<td colspan="2" class="tbheader" bgcolor="#778899">
								<center><strong>Drug History</strong></center>
							</td>
						</tr>
						<tr>
							<td>
								<% if(artReceivedVal) { %>
								<strong>ART received :</strong></td><td>  ${artReceivedVal}</td>
								</tr>
								<% } %>
								<%if(artReceivedTypeValue) {%>  
								<tr>
									<td><strong>If Yes :</strong></td><td>  ${artReceivedTypeValue}</td>
								</tr>
								<% } %>
								<% if(artReceivedPlaceValue) {  %>
								<tr>
									<td><strong>Place :</strong></td><td>  ${artReceivedPlaceValue}</td>

								</tr>
								<% } %>
								<% if(drugStartDateVal) {  %>								
								<tr>
									<td><strong>Start Date :</strong></td><td> ${drugStartDateVal}</br></td>
								</tr>
								<% } %>
								<% if(drugDurationVal) {  %>
								<tr>
									<td><strong>Duration :</strong></td><td>  ${drugDurationVal} Months</br></td>
								</tr>
								<% } %>
								<%  if(drugNameVal) {  %>								
								<tr>
									<td><strong>Drug Regimen :</strong></td><td>  ${drugNameVal}</td>
								</tr>
								<% }  %>								
							</td>
						</tr>
						
						
					
					
					</table>
				</td>
                <td width="32%" colspan="2" valign="top">
<table width="100%" border="1" height="100%">
 <tr>
							<td colspan="2" class="tbheader" bgcolor="#778899">
								<center><strong>Personal History</strong></center>
							</td>
						</tr>
<tr><td colspan="1" style="padding-left:5px;"><strong><% if(listAllRiskFactor) { %><br/>Risk Factor : 	<% } %></strong></td><td colspan="1" style="padding-left:5px;"><% if(listAllRiskFactor) { %><br/>	${listAllRiskFactor}<% } %></td></tr>
<tr><td colspan="1" style="padding-left:5px;"><strong><% if(literate) { %><br/> Literate : <%} %>  </strong></td><td colspan="1" style="padding-left:5px;"><% if(literate) { %><br/> ${ literate}  <%} %></td></tr>
<tr><td colspan="1" style="padding-left:5px;"><strong><% if(employed) { %><br/> Employed : <%} %>  </strong></td><td colspan="1" style="padding-left:5px;"><% if(employed) { %><br/> ${employed}  <%} %></td></tr>
</table></td>
               <td width="32%" colspan="2">
                <table width="100%" border="1">
                <tr>
							<td colspan="2" class="tbheader" bgcolor="#778899">
								<center><strong>Obstetric History</strong></center>
							</td>
						</tr>
                        <tr>
							<td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%"><strong><% if(pregStatusVal) { %> Pregnancy :<% } %>  </strong></td>
                            <td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%"><% if(pregStatusVal) { %> ${pregStatusVal} <% } %></td>
                            </tr>
                            <tr>
							<td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%"><strong><% if(eddVal) { %>EDD : <% } %></strong></td>
                            <td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%"><% if(eddVal) { %> ${eddVal} <% } %></td>
                            </tr>
                            <tr>
							<td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%"><strong><% if(ancNumberVal) { %>ANC No. :<% } %></strong> </td>
                            <td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%"><% if(ancNumberVal) { %> ${ancNumberVal}<% } %></td>
                            </tr>
                           
                        
						<tr >
							<td colspan="2" class="tbheader" bgcolor="#778899">
								<center><strong>Exposed Infant Follow Up</strong></center>
							</td>
						</tr> 
						<tr>
							<td colspan="2">
								<table border="1" width="100%">
									<tr >
										<th colspan="1"><strong>Exposed-Infant Name/No</strong></th>
										<th colspan="1"><strong>CPT started date</strong></th>
									</tr>
									<% for ( d in infantList ) { %>
									<% def values = d.value.split(",")	%>
										<tr>
											<td colspan="1"><% println  values[0] %> </td>
											<td colspan="1"><% println  values[1] %> </td>
										</tr>
									<% } %>
								</table>
							</td>
                            </tr>
                        </table>
</td> 
	
</tr>
<tr>
<td valign="top" width="100" colspan="6">
<table border="1" width="100%">
			<tr bgcolor="#778899">
				<td colspan="6" >
					<h4><strong><center>Pre-ART details</center></strong></h4>
				</td>
			</tr>	
			<tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
                <strong>ART start date (transfered to ART register) :</strong>
                </td>
                <td colspan="4" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
                 ${artInitiationDate}
                </td>
                </tr>
                <tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
                <strong>End of follow up Date :</strong>
                </td>
                <td colspan="4" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
                 ${dataPlaceVal}
                </td>
                </tr>
                <tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
                <strong>End of follow up Reason :</strong> 
                </td>
                <td colspan="4" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
                ${programDiscontinuationReasonVal}
                </td>
                </tr>
					
			</table>
            </td>
            </tr>		
			<tr>
				<td colspan="6" width="50%" valign="top">
					${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
				</td>
			</tr>
		</table>
	</div>
	<a id="dlink"  style="display:none;"></a>
	<div> 
	<input type="button" onClick="tableToExcel('table1','PRE ART Register','${patientWrap.drTBSuspectNumber}-PRE ART Register.xls');"  value="Export as Excel" />
	<button onclick="ui.navigate('${returnUrl}')"><b>Back</b></button>
	</div>
	
</div>
<% } %>


<script type="text/javascript">
var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table >{table}</table></body></html>'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function(s, c) { return s.replace(/{(\\w+)}/g, function(m, p) { return c[p]; }) }
  return function(table, name, filename) {
    if (!table.nodeType) table = document.getElementById(table)
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
    
            document.getElementById("dlink").href = uri + base64(format(template, ctx));
            document.getElementById("dlink").download = filename;
            document.getElementById("dlink").click();
    
  }

})()
</script>