<%@ page language="java" import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%
	response.setContentType("text/html");
	response.setHeader("Cache-Control", "no-cache");
	java.sql.Connection con = null;
	Controller obj = new Controller();
	con = obj.con();
	try {
		String Office_id=obj.setValue("off",request);	 
		String Bill_month=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		String Bill_year=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		int Bill_count=obj.getCount("PMS_DCB_TRN_BILL_DMD","where OFFICE_ID="+Office_id+" and BILL_MONTH="+Bill_month+" and BILL_YEAR="+Bill_year);		 	
		response.getWriter().write("text" + "|" + Bill_count);
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
	 
	con.close();
	%>
