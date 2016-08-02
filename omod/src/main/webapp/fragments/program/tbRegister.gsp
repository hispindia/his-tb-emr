
<%
	ui.decorateWith("kenyaui", "panel", [ heading: "NATIONAL TB PROGRAMME MDR-TB Register" ])
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
							<br/><strong>MDR-TB registration number : </strong>
							<br/><strong>Date of Registration : </strong>
							<br/><strong>Name :</strong>
							<br/><strong>Sex :  </strong>
							<br/><strong>Age/DOB : </strong>
							<br/> <strong>Address : </strong>
							<br/><strong>Previous Township TB number : </strong>
							<br/><strong>Registration group :  </strong>
					</td>
	                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
	                		<br/> <% if(mdrTBRegistrationNumber) {%> ${mdrTBRegistrationNumber  }	<% } %>
							<br/>  ${registrationDateVal}
							<br/> ${ patientName }
							<br/> ${ patientGender } 
							<br/> ${ patientAge } / ${ birthDate }
							<br/> <% if(address.address1) { %> ${ address.address1},  <%} %> <% if(address.cityVillage !='?') { %> ${address.cityVillage}, <%} %>
										<% if(address.countyDistrict !='?') { %> ${ address.countyDistrict},  <%} %> <% if(address.stateProvince !='?') { %> ${address.stateProvince} <%} %>
							<br/> <% if(patientWrap.previousTownshipTBNumber) {%> ${patientWrap.previousTownshipTBNumber  }	<% } %>
							<br/> ${ registrationGroup } 
					</td>
				</tr>
	            </table>
			</td>			
			<td width="50%" colspan="3"  valign="top">
				<table width="100%" border="1">
		            <tr bgcolor="#778899">
						<td colspan="3">
							<h4><strong><center>TB/HIV activities</center> </strong></h4>
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
										<td colspan="1"> </td>
										<td colspan="1"><% println  values[5] %> </td>
										<td colspan="1"><% println  values[8] %> </td>
										<td colspan="1"><% println  values[0] %> </td>
									</tr>
									<% } %>
								</table>
							</td>
			             </tr>
			          </table>
				</td> 
			</tr>
            <tr>
  				<td width="50%" colspan="6" valign="top">
	                <table width="100%" border="1">
	                	<tr bgcolor="#778899">
							<td colspan="3">
								<h4><center><strong>MDR TB Treatment (Smear (S) and culture (C) results during treatment)</strong></center></h4>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<table border="1" width="100%">
									<tr >
										<th colspan="1"><strong>Visit</strong></th>
										<th colspan="1"><strong>Visit Date</strong></th>
										<th colspan="1"><strong>Regimen</strong></th>
										<th colspan="1"><strong>Date started</strong></th>
										<th colspan="1"><strong>Sputum Result</strong></th>
										<th colspan="1"><strong>Sputum Date</strong></th>
										<th colspan="1"><strong>Culture Result</strong></th>
										<th colspan="1"><strong>Culture Date</strong></th>
									</tr>
								<% for ( d in smearCultureIndexList ) { %>
								<% def values = d.value.split(",")	%>
									<tr>
										<td colspan="1"><% println  d.key+1  %></td>
										<td colspan="1"><% println  values[0] %> </td>
										<td colspan="1"> </td>
										<td colspan="1"></td>
										<td colspan="1"><% println  values[1] %> </td>
										<td colspan="1"><% println  values[2] %> </td>
										<td colspan="1"><% println  values[3] %> </td>
										<td colspan="1"><% println  values[4] %> </td>
										<td colspan="1"> </td>
									</tr>
									<% } %>
								</table>
							</td>
			             </tr>
			          </table>
				</td> 
			</tr>

		<% /** <tr>
				<td colspan="6" width="50%" valign="top">
				${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
				</td>
			</tr> */%>
		</table>
</div>
	<a id="dlink"  style="display:none;"></a>
	<div> 
	<input type="button" onClick="tableToExcel('table1','MDR-TB Treatment Card','${patientWrap.drTBSuspectNumber}-MDR-TB Treatment Card.xls');"  value="Export as Excel" />
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