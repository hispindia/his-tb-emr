<%
	ui.decorateWith("kenyaui", "panel", [ heading: "HIV Care" ])

	def dataPoints = []


		if (isHivcount) {
		if (isHivcount=="No")
		{
		
			dataPoints << [ label: "HIV Testing done", value: ui.format(isHivcount)]
		}
		else
		{
		dataPoints << [ label: "HIV Testing done", value: ui.format(isHivcount) ,extra: calculations.hivTestdate.value.obsDatetime]
		}
		}
		else {
			dataPoints << [ label: "HIV Testing done", value: "None" ]
		}
	    if(hivresult)
	    {
	    dataPoints << [ label: "Result", value: ui.format(hivresult)]
	    }
        if(ArtStarted)
	    {
	    if (ArtStarted=="No")
		{
		
			 dataPoints << [ label: "Started on ART", value: ui.format(ArtStarted)]
		}
		else
		{
		 dataPoints << [ label: "Started on ART", value: ui.format(ArtStarted) ,extra: calculations.artstart.value.obsDatetime]
		}
	   
	    }
	      if(RegimeStart)
	    {
	    dataPoints << [ label: "Current Regime", value: ui.format(RegimeStart)]
	    }
         if(CPTStart)
	    { 
	    if(CPTStart=="No")
	    {
	     dataPoints << [ label: "Started on CPT", value: ui.format(CPTStart)]
	    }
	    else
	    {
	     dataPoints << [ label: "Started on CPT", value: ui.format(CPTStart) ,extra: calculations.cptstart.value.obsDatetime]
	    }
	   
	    }
	
	
	
%>


<div class="ke-stack-item">
	<% dataPoints.each { print ui.includeFragment("kenyaui", "widget/dataPoint", it) } %>
</div>
