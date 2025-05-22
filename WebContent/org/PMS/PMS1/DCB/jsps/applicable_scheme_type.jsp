<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.Connection"%>
	<%@page import="java.util.Calendar"%><html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DCB Applicable Scheme Type</title>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/applicable_scheme_type.js"></script>
	</head>
	<body onload="call_process(1);call_process(5);">
<%
		try 
		{  
			int Office_id=0; 
			String Office_name="";
			Calendar cal=Calendar.getInstance();	
			int day=cal.get(Calendar.DATE);
			int month=cal.get(Calendar.MONTH)+1;
			int year=cal.get(Calendar.YEAR);
			Controller obj=new Controller();
			Connection con=null; 
%> 
	<table align="center" width="65%" height="" border="1" cellpadding="10" style="border-collapse: collapse;border-color: purple;"> 
		<tr style="background-color: #F5BCA9">
			<td align="center" colspan="2">
				DCB Applicable Scheme Type
			</td>
		</tr>
		<tr>
			<td width="50%">Scheme Type is :</td>
			<td><select id="stype" name="stype"></select></td>
		</tr>
		 
	 <tr>
			<td colspan="2" align="center"> 
				<input type=button value="Submit" name="sub" id="sub"  onclick="call_process(2)" >				
				<input type="button" value="Exit" onclick="javascript:window.close()"> 
			</td>
		</tr>
	</table><br>  
	<table align="center" width="65%" height="" border="1"  style="border-collapse: collapse;border-color: purple"> 
				<tbody id='tbody'>
					<table id='ttable' border=1></table>
				</tbody>
	</table>
<%
		}catch(Exception e) 
		{
		}
%>
</body>
</html>