package Servlets.PMS.PMS1.DCB.Impl;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;  
import org.apache.struts.action.ActionForward;
import org.apache.struts.util.LabelValueBean;

    
public class TypeAction extends DispatchAction {

	 private final static String SUCCESS = "success";
	 public ActionForward populate(ActionMapping mapping, ActionForm  form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {

	        ArrayList typeList = new ArrayList();
	          
	        TypeForm inputForm = (TypeForm) form;   
  
	        typeList.add(new TypeData("1", "USA"));
	        typeList.add(new TypeData("2", "Canada"));
	        typeList.add(new TypeData("3", "Mexico"));  
			
	        return mapping.findForward(SUCCESS);
	 }
}
