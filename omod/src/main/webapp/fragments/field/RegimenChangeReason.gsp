<%
	config.require("category")
	config.require("reasonType")

	def concept = { uuid -> org.openmrs.api.context.Context.conceptService.getConceptByUuid(uuid).conceptId }
%>
<select id="${ config.id }" name="${ config.formFieldName }" >
	<option value="">Select...</option>
<% if (config.category == "TB" && config.reasonType == "change") { %>
	<option value="${ concept("102AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Toxicity / side effects</option>
	<option value="${ concept("1434AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Pregnancy</option>
	<option value="${ concept("843AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Clinical treatment failure</option>
	<option value="${ concept("159598AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Poor adherence</option>
	<option value="${ concept("5485AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Inpatient care or hospitalization</option>
	<option value="${ concept("1754AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Drugs out of stock</option>
	<option value="${ concept("819AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Patient lacks finance</option>
	<option value="${ concept("127750AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Refusal / patient decision</option>
	<option value="${ concept("160016AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Planned treatment interruption</option>
	<option value="${ concept("1258AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Drug formulation changed</option>
<% } else if (category == "TB" && config.reasonType == "stop") { %>
	<option value="${ concept("102AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Toxicity or side effects</option>
	<option value="${ concept("159874AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Treatment failure</option>
	<option value="${ concept("159598AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Poor adherence</option>
	<option value="${ concept("137793AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Illness or hospitalization</option>
	<option value="${ concept("1754AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Drug out of stock</option>
	<option value="${ concept("dde566ae-e1ef-49c7-a327-287f43e26949") }">Patient's decision to stop</option>
<% } %>
	<option value="${ concept("5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA") }">Other</option>
</select>

<span id="${ config.id }-error" class="error" style="display: none"></span>

<% if (config.parentFormId) { %>
<script type="text/javascript">
	FieldUtils.defaultSubscriptions('${ config.parentFormId }', '${ config.formFieldName }', '${ config.id }');
	jq(function() {
		jq('#${ config.id }').change(function() {
			publish('${ config.parentFormId }/changed');
		});
	});
</script>
<% } %>