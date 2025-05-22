<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/Traiff_Revised_report.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
String BEN_TYPE_ID="",Office_name="",userid="",Office_id="",combostr="";
Controller obj=new Controller();
String combo1="";
Connection con=null;
try
{
	con=obj.con();
	con=obj.con();   
	obj.createStatement(con);
	userid=(String)session.getAttribute("UserId");
	if(userid==null)  
	{
	  response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
 
    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);

     


%>

<%
}catch(Exception e)
{
	

}
%>
<table align="center" width="75%" cellpadding="10" cellspacing="0" border=1 bordercolor="darkgray">
 <tr bgcolor="skyblue">
 		<td colspan="2" align="center"><%=Office_name%></td>
 </tr>
  
 <tr>
     	<Td>
     		Report Type
     	</Td> 
     	<td  >
       	<select id="type" name="type" class='select'>
       						<option value="0" selected>Select Report Type</option>
							<option value="18"> RURAL - VP </option>
							<option value="19"> RURAL - RTP </option>  
							<option value="20"> PRIVATE </option>
							<option value="21"> RTP </option>
							<option value="22"> RTP </option>
		</select>
		<input type="button" value="All Division" onclick="tariff_revised_type_wiserpt()"> </td>     		
     </tr>
      <tr>
     	 
     	<td colspan="2">
     	       	<select id="rtype" name="rtype" class='select'>
       						<option value="0" selected>Select Report Type</option>
							<option value="2">  Excel </option>
							<option value="1">  PDF </option>  
		</select></td>     		
     </tr>
     </table>
</body>
</html>