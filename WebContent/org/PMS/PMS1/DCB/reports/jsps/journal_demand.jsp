<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%>
<%@page import="java.sql.Connection"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Journal Difference  </title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
 
 
 <%
 
	String userid="",Office_id="",Office_Name="",smonth="",syear="",table_column="";
	String table_header="",table_heading="",html="";
	Controller obj=null;
	Connection con;
try {
	
	String new_cond=Controller.new_cond;
		  obj = new Controller();
		con = obj.con();
		obj.createStatement(con);
			  
		try {
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) {
			userid = "0";
		}
		if (userid == null) {
			response.sendRedirect(request.getContextPath()+ "/index.jsp");
		}
		Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",
				"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
						+ userid + "')");
			smonth=obj.setValue("smonth",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
			syear=obj.setValue("syear",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
			Hashtable ht = new Hashtable();
			Hashtable condht = new Hashtable();
			ht.put("JOURNAL_FREEZE", "'Y'");
			condht.put("OFFICE_ID", Office_id);
			condht.put("CASHBOOK_MONTH", smonth);
			condht.put("CASHBOOK_YEAR", syear);		
			int r=obj.recordSave(ht,condht, "PMS_DCB_FREEZE_RECEIPT ",con);
			con.close();
			con=null;
			if (r >0 )
			{
  			%>
  			<script type="text/javascript">
  			alert('Succeefully Verified..')
  			window.close();
  			</script>
  			<%
  				
			}
}catch(Exception e) 
{
		out.println(e);
} 
  %>
</body>
</html>