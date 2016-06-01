<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Search for a Patient" ])

	ui.includeJavascript("kenyaemr", "controllers/patient.js")

	def defaultWhich = config.defaultWhich ?: "all"

	def id = config.id ?: ui.generateId();
%>
<form id="${ id }" ng-controller="PatientSearchForm2" ng-init="init('${ defaultWhich }')"  >
	<label  class="ke-field-label">Which patients</label>
	<span class="ke-field-content">
		<input type="radio" ng-model="which" ng-change="updateSearch()" value="all" /> All
		&nbsp;&nbsp;
		<input type="radio" ng-model="which" ng-change="updateSearch()" value="checked-in" /> Only Checked In
	</span>

	<label class="ke-field-label">Patient ID or Name (3 chars min)</label>
	<span class="ke-field-content">
		<input type="text" name="query" ng-model="query" ng-change="updateSearch()" style="width: 260px" />
	</span>
	
	<label class="ke-field-label">Scheduled Date</label>
	<span class="ke-field-content">
		<input type="text" id="date" name="date" ng-model="date" ng-change="updateSearch()" style="width: 260px" />
		<script type="text/javascript">
                        jQuery(document).ready(function () {
                            jQuery('#date').datepicker({
                                showAnim: 'blind'
                            });
                        });
                    </script>
	</span>

	<label class="ke-field-label">Township</label>
	<span class="ke-field-content">
		<input type="text" id="township" name="township" ng-model="township" ng-change="updateSearch()" style="width: 260px" />
	</span>
</form>

<script type="text/javascript">
jQuery(document).ready(function(){

});
	
</script>