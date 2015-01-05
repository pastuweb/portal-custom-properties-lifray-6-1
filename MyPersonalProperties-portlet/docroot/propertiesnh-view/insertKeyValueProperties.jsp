<%@include file="/init.jsp" %>
<%@include file="/properties-common/header.jsp" %>
		
    <portlet:actionURL name="insertKeyValueProperties" var="insertKeyValuePropertiesURL" windowState="maximized" />
	
	<div style="text-align:center;">
		<h2 style="color:#FF0000;">
			Inserimento Nuova Properiet&agrave;
		</h2>
	</div>
	<br>
	<div>
	    <div>
			<form action="<%= insertKeyValuePropertiesURL.toString() %>" id="insertKeyValuePropertiesForm" method="post">
			    Chiave:<br>
			    <input type="text" value="" name="keyNewProp" /><br>
			    Valore:<br>
			    <input type="text" value="" name="valueNewProp" /><br><br>
			    <input type="submit" value="Salva" name="insertKeyValuePropertiesButton" />
			</form>
		</div>
	    <br>
	    <br> 
	    <br>
    	<portlet:renderURL var="indietroURL">
    			<portlet:param name="jspPage" value="/properties-view/viewProperties.jsp"/>
    	</portlet:renderURL>
	    <aui:button value="Indietro" onClick="<%=indietroURL.toString() %>"/>
	</div>

 <%@include file="/properties-common/footer.jsp" %> 