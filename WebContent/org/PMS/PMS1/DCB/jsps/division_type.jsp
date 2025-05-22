<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
</head>
<body onload="data_show_dcb('show',2,'bentype')">
<select class="select" id="bentype" name="bentype" onchange="grid_show(3,'data','ben_data','ben_body' ,'bentype'),flash()"></select>
</body>
</html>