package net.appuntivari.properties.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import net.appuntivari.properties.util.Config;
import org.apache.log4j.Logger;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class PropertiesViewPortlet extends MVCPortlet {
		 
		private static Logger logger = Logger.getLogger(PropertiesViewPortlet.class);

		private static final String viewPropertiesjsp = "/properties-view/viewProperties.jsp";
		private static final String editValueKeyPropertiesjsp = "/properties-view/editValueKeyProperties.jsp";
		private static final String insertKeyValuePropertiesjsp = "/properties-view/insertKeyValueProperties.jsp";

		
		public void reloadProperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				Config.reload();
				logger.info("reloadProperties - Config.reload()");
		        response.setRenderParameter("jspPage", viewPropertiesjsp);
		}
		
		
		
		public void editValueKeyProperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				logger.info("editValueKeyProperties - "+request.getParameter("keyToEdit"));
				
				request.setAttribute("keyToEdit", request.getParameter("keyToEdit"));
		        response.setRenderParameter("jspPage", editValueKeyPropertiesjsp);
		}
		
		public void updateValueKeyProperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				String parKey = request.getParameter("keyToEdit");
				String parNewValue = request.getParameter("newValueForKey");
				logger.info("updateValueKeyProperties - "+parNewValue);
				
				String oldValue = Config.getProp(parKey);
				
				String oldProp = parKey+"="+oldValue;
				String newProp = parKey+"="+parNewValue;
				
				Config.updateOldPropWithNewProp(oldProp,newProp);
				
				Config.reload();
		        response.setRenderParameter("jspPage", viewPropertiesjsp);
		}
		
		
		public void newProperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				logger.info("newProperties");
		        response.setRenderParameter("jspPage", insertKeyValuePropertiesjsp);
		}
		
		public void insertKeyValueProperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				String parNewKey = request.getParameter("keyNewProp");
				String parNewValue = request.getParameter("valueNewProp");
				logger.info("insertKeyValueProperties - "+parNewKey+"="+parNewValue);
								
				String newProp = parNewKey+"="+parNewValue;
				
				Config.insertNewProp(newProp);
				
				
				Config.reload();
		        response.setRenderParameter("jspPage", viewPropertiesjsp);
		}
		
}
