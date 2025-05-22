<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen">
  <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
</head>
<body onload="clickIE4()">
<%@ taglib uri="/struts-tags" prefix="s" %>  
<h3>All Records:</h3>  
		<s:iterator  value="list">  
		<fieldset>  
		<s:property value="ben_name"/><br/>  
		<s:property value="cls_wc_charges"/><br/>  
		<s:property value="cls_maint_charges"/><br/>  
		<s:property value="cls_int_charges"/><br/>
		<s:property value="cls_yester_year"/><br/>    
		</fieldset>  
		</s:iterator>
		  <a href="viewdetails">viewdetails</a>
</body>
</html>