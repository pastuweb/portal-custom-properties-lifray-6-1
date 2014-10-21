<%@include file="/init.jsp" %>
<%@include file="/propertiesnh-common/header.jsp" %>
		
		
    <portlet:actionURL name="updateValueKeyproperties" var="updateValueKeypropertiesURL" windowState="maximized" />
	<%
	String keyToEdit = (String)request.getAttribute("keyToEdit");
	%>
	<div style="text-align:center;">
		<h2 style="color:#FF0000;">
			Modifica Propriet&agrave; esistente
		</h2>
	</div>
	<br>
	<div>

	    <div>
			<form action="<%= updateValueKeypropertiesURL.toString() %>" id="updateValueKeyPropertiesForm" method="post">
			    Chiave: <strong><%=keyToEdit %></strong><br>
			    <input type="hidden" value="<%=keyToEdit %>" name="keyToEdit" />
			    <input type="text" value="<%=Config.getProp(keyToEdit) %>" name="newValueForKey" /><br><br>
			    <input type="submit" value="Aggiorna Proprietà" name="updateValueKeyPropertiesButton" />
			</form>
		</div>
	    <br>
	    <br> 
	    <br>
    	<portlet:renderURL var="indietroURL">
    			<portlet:param name="jspPage" value="/propertiesnh-view/viewProperties.jsp"/>
    	</portlet:renderURL>
	    <aui:button value="Indietro" onClick="<%=indietroURL.toString() %>"/>
	    
	</div>

 <%@include file="/propertiesnh-common/footer.jsp" %> 