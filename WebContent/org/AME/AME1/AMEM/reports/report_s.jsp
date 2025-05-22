<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
		function rld_new()
		{
			var year=document.getElementById("year").value; 
			var smonth=document.getElementById("month").value;
			var pr_type=document.getElementById("pr_type").value;;
			var rpt=50;
			window.open("../../../../../PMS_DCB_HO_DIST_WISE?month="+smonth+"&splflag="+pr_type+"&year="+year+"&ref_sno="+(parseInt(rpt)+20));
			    
		}
</script>
</head>
<body>    

<%
String month=request.getParameter("smonth");
String year=request.getParameter("year");
String pr_type=request.getParameter("pr_type");

%>
<input type="hidden" id="month" name="month" value="<%=month%>">
<input type="hidden" id="year" name="year" value="<%=year%>">
<input type="hidden" id="pr_type" name="pr_type" value="<%=pr_type%>">
<script type="text/javascript">
rld_new();  
</script>
</body>
</html>