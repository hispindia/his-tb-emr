<% carePanels.each { carePanel -> %>
${ ui.includeFragment(carePanel.provider, carePanel.path, [ patient: patient, complete: complete ])}
<% } %>
<div class="ke-stack-item">
	<table width="100%" border="0">
		<tr>
			<td width="50%" valign="top">
							${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
			</td>
		</tr>
	</table>
</div>