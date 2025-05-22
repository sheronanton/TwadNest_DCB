<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Know your Beneficiary Type</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/knowyourben.js"></script>
 <style type="text/css">
 table
 {
 	border-collapse: collapse;
 	
 
 }
 th
 {
 background-color: #0aa;  
 }
 .tr
 {
 	background-color:rgb(255,200,200);
 
 }
 
.box1
{
	height:520px;   
	background: transparent no-repeat;	
	font-size: 12pt; 
	font-family: Tahoma, Arial, Helvetica, sans-serif;		
	text-align:left;			
	position: absolute;
	top:18px;	
	left: 1170px;	
	border :2em; 	
	box-shadow: 10px 10px 5px;;  
	
}
.box 
{
	font-size: 10pt; 
	font-family: Tahoma, Arial, Helvetica, sans-serif;		
	text-align:left;
	left: 1180px;	
}
</style> 
</head>  
<body> 
<%     
		Connection con,con2;
		Controller obj=new Controller();
		Controller obj2=new Controller();
		String BEN_TYPE_ID="",Office_name="",userid="",msg="",Office_id="";
		try
		{
			
			con=obj.con();
			con2=obj2.con(); 
			obj.createStatement(con);
			userid=(String)session.getAttribute("UserId");
			if(userid==null)  
			{
			 	 response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE ) order by BEN_TYPE_ID","BEN_TYPE_ID","","class='box' onchange='request_process(1)'" );
			msg=obj.isNull(obj.setValue("msg",request),4);          
		    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		
		    if (Office_id.equals("")) Office_id="0"; 
		    
		}catch(Exception e)
		{   
			 	out.println(e);
		}
%>
<table align="left" width="85%" border="1" cellpadding="0" cellspacing="0">
<tr>
<td colspan=5 align="center">
	Information about Beneficiary
</td>
</tr>
<tr>
<td colspan=5 >
<table align="left" width="100%" border="0" cellpadding="2" cellspacing="0">
	 
	<tr >
		<td>Beneficiary Type</td>
		<td><%=BEN_TYPE_ID%></td>
		<td>Beneficiary Name Like &nbsp;&nbsp;<input type="text" id="startname" name="startname" onkeyup="request_process(2)"></td> 
		<td>&nbsp;
		<div  class='box1' >
			<select id="ben" multiple="multiple" onchange="request_process(3)"></select>
		</div>  
		</td>		
	</tr>
	<tr bgcolor="#eeaabb"> 
		<td  colspan=6>Basic Information </td>		
	</tr>
	<tr> 
		<td colspan=6>
			<div id="bas1"></div>
		</td>
	</tr>
</table>
</td>
</tr>
</table>
</body>
</html>