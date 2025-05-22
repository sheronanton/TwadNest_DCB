<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.*,Servlets.Security.classes.UserProfile" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Reviews</title>
  <link href="../../../../../css/common.css" rel="stylesheet" media="screen"/>
</head>
<body>
<%
	 
	String userid="0",Office_id="",Office_Name="";
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Connection con=null;
	java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
	String pmonth="0",pyear="0";
	try
	{
	  con=obj.con();
	  obj.createStatement(con);
	  userid=(String)session.getAttribute("UserId");			
	  if(userid==null)
	  {
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
	  }
	
	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
 
 		UserProfile up = null;
		up = (UserProfile) session.getAttribute("UserProfile");
 		String but=""; 		
 		but=obj.setValue("Add",request);
 		String b1=obj.setValue("b1",request);
 		int row=0;
 		if (but.equals("Submit"))
 		{
  						String desc=obj.setValue("desc",request);
 
						java.util.Hashtable ht = new Hashtable();
											
						if (Integer.parseInt(b1)==1)					
						ht.put("SUGGESTION",    "'"+desc + "'");
						else if (Integer.parseInt(b1)==2)
						ht.put("ISSUE",    "'"+desc + "'");
						else if (Integer.parseInt(b1)==3)
						ht.put("USEFUL_MESSAGE",   "'"+desc + "'");
						ht.put("OFFICE_ID", Office_id);
						
						ht.put("CONTACT_NAME",   "'" + up.getEmployeeName() + "'");
						ht.put("CONTACT_NO", "0");
						ht.put("UPDATED_BY_USER_ID", "'" + userid + "'");
						ht.put("UPDATED_DATE", "clock_timestamp()");
						row=obj.recordSave(ht ,"PMS_DCB_HELP_REPORT ", con);
						
 		}
 		if (row>0)
 			out.println("<center><a href='user_review.jsp'>Successfully Stored</a></center>");
 		
   
	}catch(Exception e)
	{
		out.println(e);  
	}

 %>
</body>
</html>