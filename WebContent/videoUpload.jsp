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
 
%>
<table align="center" width="85%" border="1" cellpadding="5">

 
<tr  class = "tdH">
	<td colspan="2" align="center">Upload Design and Pumped Quantity - Month Wise  </td>
</tr>
  
			
			 <tr>
<td colspan="2">
  Select Excel File <input type="file" id="filename" name="filename" >
  <input type="submit" value="Upload">
   
    <input type="button" value="Exit" onclick="javascript:window.close()">
  
 
 </td>  
</tr> 
 
 
</table>
 

 </form>
</body>
</html>