<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.Connection"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of Journal Types</title>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>  
<body>
<%
	Controller obj=new Controller();  
	Connection con=obj.con();
	obj.createStatement(con);
	
	obj = new Controller();
	con = obj.con();
	obj.createStatement(con);
	String userid="";	  
	try {
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) {
			userid = "0";
		}
		if (userid == null) {
			response.sendRedirect(request.getContextPath()+ "/index.jsp");
		}
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	String qry="";
	
	if (Office_id.equalsIgnoreCase("5000")) 
	qry="select distinct journal_type_code,journal_type_desc,case when display_restricted='Y' then '<font color=red size=3>Yes</font>' else '<font color=green size=3>No</font>' end as display_restricted from  PMS_DCB_APPLICABLE_JOU_TYPE order by journal_type_code ";
	else
	qry="select distinct journal_type_code,journal_type_desc  from  PMS_DCB_APPLICABLE_JOU_TYPE order by journal_type_code ";
	String 	table_column="",table_header="",table_td_set="",table_heading="",tab="";
	if (Office_id.equalsIgnoreCase("5000"))
	{
	  table_column="journal_type_code,journal_type_desc,display_restricted"; 
	  table_header="Journal Type Code,Journal Type Description,Display Restricted in FAS Module ";
	  table_td_set=" align=center width='10%',   align=left width='50%' ,align=center width='10%'"; 
	  table_heading="List of Journal Types ";    
	  tab="cellspacing='0' cellpadding='5' width='100%' style='BORDER-COLLAPSE: collapse' border='0' borderColor='#92c2d8'";
	}else
	{
		  table_column="journal_type_code,journal_type_desc"; 
		  table_header="Journal Type Code,Journal Type Description";
		  table_td_set=" align=center width='10%',   align=left width='50%'"; 
		  table_heading="List of Journal Types ";    
		  tab="cellspacing='0' cellpadding='5' width='100%' style='BORDER-COLLAPSE: collapse' border='0' borderColor='#92c2d8'";
	}
	String html=obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);
	con.close();
	
	
%>
<table width="70%" align="center" border="1" style="border-collapse: collapse;border-color: skyblue" cellpadding="5">
	<tr style="background-color:skyblue">
		<td align="center"><%=table_heading%></td>
	</tr>
	<tr>
		<td><%out.println(html); %></td>
	</tr>
	<tr style="background-color:skyblue">   
		<td align="center"><input type=button onclick="window.close()" value="Exit"></td>
	</tr>
</table>
</body>
</html>