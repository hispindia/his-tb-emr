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
							<br/>  <% if(patientWrap.currentTownshipTBNumber) {%> ${patientWrap.currentTownshipTBNumber  }	<% } %>
					</td>
				</tr>
	            </table>
			</td>			
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
	                	
							<br/> <% if(patientWrap.previousTownshipTBNumber) {%> ${patientWrap.previousTownshipTBNumber  }	<% } %>
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
								<h4><strong><center>Treatment related details</center> </strong></h4>
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
  				<td width="50%" colspan="3" valign="top">
	                <table width="100%" border="1">
	                	<tr bgcolor="#778899">
							<td colspan="3">
								<h4><center><strong>District/Township MDR-TB Committee recommendation</strong></center></h4>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<table border="1" width="100%">
									<tr >
										<th colspan="1"><strong>Date</strong></th>
										<th colspan="1"><strong>Decision</strong></th>
										<th colspan="1"><strong>Next Date</strong></th>
									</tr>
								<% for ( d in recommendationIndexList ) { %>
								<% def values = d.value.split(",")	%>
									<tr>
										<td colspan="1"><% println  values[0] %> </td>
										<td colspan="1"><% println  values[1] %> </td>
										<td colspan="1"><% println  values[2] %> </td>
									</tr>
									<% } %>
								</table>
							</td>
			             </tr>
			          </table>
				</td> 
			    <td width="50%" colspan="3" valign="top">
				    <table width="100%" border="1">
				            <tr bgcolor="#778899">
								<td colspan="3">
									<h4><strong><center>Outcome Details</center> </strong></h4>
								</td>
							</tr>
				            <tr>
								<td colspan="1" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
										<br/><strong>Outcome : </strong>
										<br/><strong>Date : </strong>
								</td>
				                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
				                	
										<br/> ${ tbOutcomeVal }
										<br/> ${ tbOutcomeDateVal } 
								</td>
							</tr>
			            </table>
			    
			    
				</td>
			</tr>
			<tr bgcolor="#778899">
				<td colspan="6">
					<h4><strong><center>Daignosis</center> </strong></h4>
				</td>
			</tr>
			<tr>
				<td valign="top"  colspan="6">
					${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
				</td>
			</tr>
			
			<tr>
  				<td width="50%" colspan="6" valign="top">
	                <table width="100%" border="1">
	                	<tr bgcolor="#778899">
							<td colspan="3">
								<h4><center><strong>DST Result</strong></center></h4>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<table border="1" width="100%">
									<tr >
										<th colspan="1"><strong>Drug</strong></th>
										<th colspan="1"><strong>R</strong></th>
										<th colspan="1"><strong>H</strong></th>
										<th colspan="1"><strong>E</strong></th>
										<th colspan="1"><strong>S</strong></th>
										<th colspan="1"><strong>Km</strong></th>
										<th colspan="1"><strong>Cm</strong></th>
										<th colspan="1"><strong>Fq</strong></th>
										<th colspan="1"><strong>Pto/Eto</strong></th>
										<th colspan="1"><strong>Other</strong></th>
										<th colspan="1"><strong>Date of DST</strong></th>
										<th colspan="1"><strong>Culture Number</strong></th>
									</tr>
								<% for ( d in cultureDstList ) { %>
								<% def values = d.value.split(",")	%>
									<tr>
										<td colspan="1"><% println  d.key+1  %></td>
										<td colspan="1"><% println  values[3] %> </td>
										<td colspan="1"><% println  values[2] %> </td>
										<td colspan="1"><% println  values[4] %> </td>
										<td colspan="1"><% println  values[1] %> </td>
										<td colspan="1"><% println  values[7] %> </td>
										<td colspan="1"><% println  values[6] %> </td>
										<td colspan="1"><% println  values[10] %> </td>
										<td colspan="1"><% println  values[5] %> </td>
										<td colspan="1"><% println  values[8] %> </td>
										<td colspan="1"><% println  values[0] %> </td>
										<td colspan="1"><% println  values[9] %> </td>
									</tr>
									<% } %>
								</table>
							</td>
			             </tr>
			          </table>
				</td> 
			</tr>
			
			

			<tr>
  				<td width="100%" colspan="6" valign="top">
	                <table width="100%" border="1">
	                	<tr bgcolor="#778899">
							<td colspan="6">
								<h4><center><strong>TB Regimen</strong></center></h4>
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<table border="1" width="100%">
									<tr>
										<th colspan="1"><strong>Date</strong></th>
										<th colspan="1"><strong>H</strong></th>
										<th colspan="1"><strong>R</strong></th>
										<th colspan="1"><strong>Z</strong></th>
										<th colspan="1"><strong>E</strong></th>
										<th colspan="1"><strong>S</strong></th>
										<th colspan="1"><strong>Km</strong></th>
										<th colspan="1"><strong>Am</strong></th>
										<th colspan="1"><strong>Cm</strong></th>
										<th colspan="1"><strong>FQ</strong></th>
										<th colspan="1"><strong>Pto/Eto</strong></th>
										<th colspan="1"><strong>Cs</strong></th>
										<th colspan="1"><strong>PAS</strong></th>
									</tr>
									<% for ( rList in regimenList ) {  %>
										<% def values = rList.value.split (",") %>	
										<tr>
											<td><% println  values[0] %> </td>
											<td><% println  values[1] %> </td>
											<td><% println  values[2] %> </td>
											<td><% println  values[3] %> </td>
											<td><% println  values[4] %> </td>
											<td><% println  values[5] %> </td>
											<td><% println  values[6] %> </td>
											<td><% println  values[7] %> </td>
											<td><% println  values[8] %> </td>
											<td><% println  values[9] %> </td>
											<td><% println  values[10] %> </td>
											<td><% println  values[11] %> </td>
											<td><% println  values[12] %> </td>
										</tr>
									<% } %>		
								</table>
							</td>
			             </tr>
			          </table>
				</td> 
			</tr>
		</table>
</div>
	<a id="dlink"  style="display:none;"></a>
	<div> 
	<input type="button" onClick="tableToExcel('table1','TREATMENT CARD','${patientWrap.drTBSuspectNumber}-TREATMENT CARD.xls');"  value="Export as Excel" />
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