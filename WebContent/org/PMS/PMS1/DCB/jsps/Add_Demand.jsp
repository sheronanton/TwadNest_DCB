
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="java.util.Calendar" %>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
      <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/Office_Shift_new.js"></script>
	
 
</head>
<body>
<%
Controller obj=null;
Connection con;
obj=new Controller();
con=obj.con();
obj.createStatement(con);
HttpSession session = request.getSession();
String userid=(String)session.getAttribute("UserId");
String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");

%>
<br><input type="text" id="user_id"  name="user_id" size="4" readonly hidden  value="<%=(String)session.getAttribute("UserId") %>">
<br><input type="text" id="off_id"  name="off_id" size="4" readonly  hidden  value="<%=Office_id %>">
<br><input type="text" id="val11"  name="val11" size="4" readonly hidden >
<br><input type="text" id="val22"  name="val22" size="4" readonly hidden >
<br>
 
<table align="center"  cellpadding="2" border="2">
<tr bgcolor="skyblue" align="center">
	<td colspan="8" align="center" class="tdText"><b>Add Demand for --<%=Office_Name%></b></td>
</tr>

<tr class="tdText" align="center">
<td>Enter the month &nbsp;&nbsp;
</td>

<td>
<input type="text" id="month" name="month" size="2" maxlength="2" >
</td>
<tr class="tdText" align="center">
<td>Enter the Year &nbsp;&nbsp;
</td>
<td>
<input type="text" id="year" name="year" size="4" maxlength="4" onblur="getval11(),getval22()" >
</td>
</tr>
<tr>
<td>Previous month adj.demand : <b id="val1"></b>
</td>
<td>Present month adj.demand : <b id="val2"></b>
</td>
</tr>
<tr class="tdText" align="center">
<td colspan="2">
<input type="button" value="Do not use it"  onclick="Add_Demand()" class="sa">
<input type="button" value="Exit"  onclick="window.close();" class="sa">
</td>
</tr>
</table>

<center><font color="red" size="6" ><b id="val3"></b></font></center>

</body>
</html>