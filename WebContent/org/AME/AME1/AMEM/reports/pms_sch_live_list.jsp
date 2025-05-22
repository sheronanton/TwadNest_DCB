<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
  			<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
 
</head>
<body>  
<form action="pms_sch_list.jsp" method="post">
	<%
	String userid="",Office_id="";
	try 
		{
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) 
		{
			userid = "0";
		}
		if (userid == null) 
		{
			response.sendRedirect(request.getContextPath()+ "/index.jsp");
		}
	
	Servlets.PMS.PMS1.DCB.servlets.Controller obj=new Servlets.PMS.PMS1.DCB.servlets.Controller();
	//Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	String  Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	
	%> 
<bR><bR><Br>
<table width="50%" align="center" border="1" cellpadding="5" cellspacing="0" style="padding-bottom: 12em;">
	<tr>
		<th>
				<br><br>
				List of Maintenance Schemes - <%=Office_Name%>
				<br><br>
		</th>
	</tr>
	 
	<tr>   
		<td align="center">
			<br>
			<input type=submit value="DCB Schemes" name="b1">
			<input type=submit value="All Maintenance Schemes" name="b1">     
			<input type=button value="Exit" onclick="window.close()">
			<br>
			<br>
			<br>
			
		</td>
	</tr>
	
	
</table>
</form>
</body>
</html>