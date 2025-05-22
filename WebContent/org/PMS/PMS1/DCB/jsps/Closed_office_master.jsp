<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
      <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
	<script src="jquery-3.6.0.js" type="text/javascript"></script> 
	
<title>Closed Office - New Office Mapping</title>
<script type="text/javascript" src="../scripts/Office_Shift_new.js">
</script>
</head>
<body>
<br><br>

<table align="center"  cellpadding="2" border="2" width="80%">
<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>
	<font color="black" size="3">Closed Office - New Office Mapping</font></b></td>
</tr>
<tr  align="left" >
<td>Enter Closed Office Id : </td>
<td><input type="text" id="closed_off_id" size="4" maxlength="4"  onblur="getdata6()"></td>
<td>  Office Name  : </td>
<td><b id="closed_off_name"></b></td>
<td>  Office Status : </td>
<td><b id="closed_off_status"></b></td>
</tr>
<tr  align="left" >
<td>Enter New Office Id : </td>
<td><input type="text" id="New_off_id" size="4" maxlength="4" onblur="getdata5()"></td>
<td> Office Name  : </td>
<td><b id="New_off_name"></b></td>
<td> Office Status : </td>
<td><b id="New_off_status"></b></td>
</tr>
<tr  align="left" >
<td>Enter w.e.f Month : </td>
<td><input type="text" id="month" size="2" maxlength="2" ></td>
<td>Enter w.e.f year : </td>
<td><input type="text" id="year" size="4" maxlength="4" ></td>
</tr>


<tr bgcolor="skyblue" align="center">
	<td bgcolor="skyblue" colspan="6" align="center" class="tdText"><b><font color="#7610ba" size="2"> ** CR - Created ,  CL - Closed ,  CN / NC -  Converted  , AT  -  Attached  ,  RD  - Redeployed </font></b></td>
</tr>
<tr  bgcolor="skyblue" align="center">
<td colspan="10">
<button class="sa" onclick="insert_closed()" >Submit</button>
  <button onclick="window.close();" class="sa"><font color="red" >Exit</font></button><br>
</td>
</tr>
</table>
  
</body>
</html>
