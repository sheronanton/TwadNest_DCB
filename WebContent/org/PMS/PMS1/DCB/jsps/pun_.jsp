<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD Nest II | Urban Master Status</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
 </head>
<body>

<%
Controller obj=null;
Connection con;
try
{ 
		  obj=new  Controller();
		  con=obj.con();
		  obj.createStatement(con);
		  
		  String cb=obj.combo_str("COM_MST_URBAN_LB","URBANLB_NAME","URBANLB_SNO"," order by URBANLB_NAME","ursno","class=select onChange=urban_details()"); 
%>
	<table align="center" width="75%" cellpadding="2">
	<tr  bgcolor="skyblue">
	 <td colspan="2" align="center" class="tdText">Urban Master Status</td>
	</tr>
	<tr>
	<td class="tdText">Urban LB</td><td><%=cb%></td>
	</tr>
	<tr >
	<td align="center" id='idtab' colspan="2"> 
	
	</td>
	</tr>
	<tr  >
	 <td colspan="2" align="center"><input type=button onclick="window.close()" value="Exit" class="fb2"></td>
	</tr> 
	<tr  bgcolor="skyblue">
	 <td colspan="2" align="center" class="tdText">&nbsp;</td>
	</tr>
	</table>
		 
		  
<%
  		  	  
		  
  	
}catch(Exception e) {  }

%>
</body>
</html>