<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
	function ds_ch()
	{  
		var m=document.getElementById("pmonth").value;
		var y=document.getElementById("pyear").value;
		var sm=document.getElementById("sm").value;
		var sy=document.getElementById("sy").value;
		 
	}
 
</script>   

<title>Rectification</title> 
</head>
<body>
	<form name="rect">
	
		<%
		java.util.Calendar cal = java.util.Calendar.getInstance();
		Controller obj=new Controller();
		int day = cal.get(java.util.Calendar.DATE);
		int month = cal.get(java.util.Calendar.MONTH) + 1;
		int year = cal.get(java.util.Calendar.YEAR);
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		String ben_value= obj.setValue("ben_value",request);
		String selsno= obj.setValue("selsno",request);
		String row= obj.setValue("row",request);
		String mtype= obj.setValue("mtype",request);
		%> 
		<input type="hidden" id="sm" name="sm" value="<%=month%>">
		<input type="hidden" id="sy" name="sy" value="<%=year%>">
		<table class="fb2"  align="center" width="100%" border=1 cellpadding="7" cellspacing="0" bordercolor="#BDB76B" >
		<tr>
			<td align="center" colspan="2"> Rectification Quantity </td>
		</tr>
		<tr  class="tdH">
			<td> Meter Type </td>  
			<td> <%=mtype%></td>
		</tr>  
		<tr>  
	          <td> <font color="#0000A0"> Month And Year </font> </td> 
				  <Td>
				  <select class="select" id="pmonth" name="pmonth"  style="width:70pt;"   >
					  <option value="0">- Month-</option>
					   <%
					  	for (int j=1;j<=12;j++)
					  	{  
					   %>
					   <option value="<%=j%>"><%=monthArr[j]%></option>
					  <%} %>			  
					 </select>
					  <select class="select"  id="pyear" name="pyear"  style="width:50pt;"  onchange="ds_ch()">
					   <option value="0">-Year-</option>
					  <%
					  for (int i=2011;i<=year;i++)
					  {
					   %>
					  <option value="<%=i%>"><%=i%></option>
					  <%} %>
				  </select>
				  </td>		  
		</tr>
		<tr> 
			<td><font color="#0000A0"> Rectification Qty in <b><%=mtype%></b></font></td>
			<td>
				<input type="text" id="qty" name="qty" onkeyup="isInteger(this,9,event)">
			</td> 
		</tr>
		<tr>
			<td align="center" colspan="3">
				<input type=button value="Submit" onclick="wc_cal_prv(<%=ben_value%>,<%=selsno%>,<%=row%>)">
				<input type="button" value="Exit" onclick="window.close()">
			</td>
		</tr>
		</table>
	</form>

</body>
</html>