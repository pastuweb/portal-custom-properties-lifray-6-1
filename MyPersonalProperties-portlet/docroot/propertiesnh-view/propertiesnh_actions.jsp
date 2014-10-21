<%@include file="/init.jsp" %>

<%
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	String keyProp = (String) row.getObject();
%>


<liferay-ui:icon-menu>
	
	
	<portlet:actionURL name="editValueKeyProperties" var="editValueKeyPropertiesURL">
      <portlet:param name="keyToEdit" value="<%= keyProp %>" />
    </portlet:actionURL>

    <liferay-ui:icon image="edit" message="Edit" url="<%= editValueKeyPropertiesURL.toString() %>" />

</liferay-ui:icon-menu>

