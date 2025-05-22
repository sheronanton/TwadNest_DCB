<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*,java.io.*"%>
<%@page import="java.text.DecimalFormat"%><%@page import="java.util.Calendar" %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.excel2"%><html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"  %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />

<title> </title>
<script type="text/javascript" src="./serv.js"></script>

<script type="text/javascript">
	function del()
	{ 
		document.location.href="excelUpload.jsp?del=delete";
	}
	
</script>
</head>
<body>    
<form action="vupload.jsp" method="POST" enctype="multipart/form-data">
<%
String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="",html="";
 Controller obj=new Controller(); 
 Connection con=obj.con();
 obj.createStatement(con);
 Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
 DecimalFormat df=new DecimalFormat();      
  
 String delete=obj.setValue("del",request);
 
 userid = (String) session.getAttribute("UserId");
 if (userid==null) userid="0";
   
Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
if (Office_id.equals("")) Office_id="0";
  Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
	String msg=obj.isNull(obj.setValue("msg",request),3);
	if (msg.equals("0")) msg="";
%>
<table align="center" width="85%" border="1" cellpadding="5">

<tr  class = "tdH">
	<td colspan="2" align="center"><%=Office_Name%></td>
</tr>
<tr  class = "tdH">
	<td colspan="2" align="center">Upload Design and Pumped Quantity - Month Wise  </td>
</tr>
  
			
			 <tr>
<td colspan="2">
  Select Excel File <input type="file" id="filename" name="filename" >
  <input type="submit" value="Upload">
  <input type="button" value="Delete" onclick="del()">
    <input type="button" value="Exit" onclick="javascript:window.close()">
  
 
 </td>  
</tr> 
 
 
</table>
 

 </form>
</body>
</html>