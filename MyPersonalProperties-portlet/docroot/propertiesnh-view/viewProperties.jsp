<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@include file="/init.jsp" %>
<%@include file="/properties-common/header.jsp" %>
		
    <portlet:actionURL name="reloadProperties" var="reloadPropertiesURL" windowState="maximized" />
    <portlet:actionURL name="newProperties" var="newPropertiesURL" windowState="maximized" />
    
	<%
	long remote_userid =  PrincipalThreadLocal.getUserId();
	%>
	<h2 style="color:#FF0000;">
		Properties (<span style="color:#0015ff">my-personal-properties.properties</span>)
	</h2>
	<br>
	<div>    
	    
	    <%
	    List<String> tempResults2 = Config.getAllKeyProperties();
	    %>
	    
	    <liferay-ui:search-container emptyResultsMessage="utente-non-presenti" delta="20">
	
	    <liferay-ui:search-container-results 
	    	results="<%=ListUtil.subList(tempResults2, searchContainer.getStart(), searchContainer.getEnd())%>" 
	    	total="${tempResults2.size()}"
	    	resultsVar="listProperties">
	    
	    </liferay-ui:search-container-results>
	
	    <liferay-ui:search-container-row  className="String" modelVar="itemNhProp" >
	
	      <liferay-ui:search-container-column-text
	          name="Chiave"
	          value="<%=itemNhProp %>" />
	      <liferay-ui:search-container-column-text
	          name="Valore"
	          value="<%=Config.getProp(itemNhProp.toString()) %>" />
	      
	      <liferay-ui:search-container-column-jsp
	          path="/properties-view/properties_actions.jsp"
	          align="right" />	
	
	    </liferay-ui:search-container-row>
	
	    <liferay-ui:search-iterator />
	
	  </liferay-ui:search-container>
	   
	  <div class="portlet-msg-alert">Per alcune PROPERTIES cè bisogno del riavvio dell'Applciation Server</div>
	  	    
	  	    
	    <br><br>
	    <div style="float:left;">
			<form action="<%= reloadPropertiesURL.toString() %>" id="reloadPropertiesForm" method="post">
			    <input type="submit" value="ReLoad TUTTE le Proprietà" name="reloadPropertiesButton" style="background-color:#000000;color:#FF0000" />
			</form>
		</div>
		<div style="float:left;margin-left:20px;">
			<form action="<%= newPropertiesURL.toString() %>" id="newPropertiesForm" method="post">
			    <input type="submit" value="Inserisci una nuova Properietà" name="newPropertiesButton"/>
			</form>
		</div>
		<div style="clear:left;"></div>
	    
	</div>

 <%@include file="/properties-common/footer.jsp" %> 