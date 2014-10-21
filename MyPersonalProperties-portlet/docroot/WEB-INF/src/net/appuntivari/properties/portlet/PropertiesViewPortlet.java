package net.appuntivari.properties.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.apache.log4j.Logger;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.bridges.mvc.MVCPortlet;

import net.appuntivari.properties.util.Config;

public class PropertiesViewPortlet extends MVCPortlet {
		 
		private static Logger logger = Logger.getLogger(PropertiesViewPortlet.class);

		private static final String viewPropertiesJSP = "/propertiesnh-view/viewProperties.jsp";
		private static final String editValueKeyPropertiesJSP = "/propertiesnh-view/editValueKeyProperties.jsp";
		private static final String insertKeyValuePropertiesJSP = "/propertiesnh-view/insertKeyValueProperties.jsp";

		
		public void reloadNHproperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				Config.reload();
				logger.info("reloadNHproperties - Config.reload()");
		        response.setRenderParameter("jspPage", viewPropertiesJSP);
		}
		
		
		
		public void editValueKeyNHproperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				logger.info("editValueKeyNHproperties - "+request.getParameter("keyToEdit"));
				
				request.setAttribute("keyToEdit", request.getParameter("keyToEdit"));
		        response.setRenderParameter("jspPage", editValueKeyPropertiesJSP);
		}
		
		public void updateValueKeyNHproperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				String parKey = request.getParameter("keyToEdit");
				String parNewValue = request.getParameter("newValueForKey");
				logger.info("updateValueKeyNHproperties - "+parNewValue);
				
				String oldValue = Config.getProp(parKey);
				
				String oldProp = parKey+"="+oldValue;
				String newProp = parKey+"="+parNewValue;
				
				Config.updateOldPropWithNewProp(oldProp,newProp);
				
				Config.reload();
		        response.setRenderParameter("jspPage", viewPropertiesJSP);
		}
		
		
		public void newNHproperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				logger.info("newNHproperties");
		        response.setRenderParameter("jspPage", insertKeyValuePropertiesJSP);
		}
		
		public void insertKeyValueNHproperties(ActionRequest request, ActionResponse response)
		        throws Exception {

				String parNewKey = request.getParameter("keyNewProp");
				String parNewValue = request.getParameter("valueNewProp");
				logger.info("insertKeyValueNHproperties - "+parNewKey+"="+parNewValue);
								
				String newProp = parNewKey+"="+parNewValue;
				
				Config.insertNewProp(newProp);
				
				
				Config.reload();
		        response.setRenderParameter("jspPage", viewPropertiesJSP);
		}
		
		
}
