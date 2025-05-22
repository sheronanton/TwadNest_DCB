	<%@page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
	<%@page import="java.util.*,java.sql.*"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script type="text/javascript" src="../scripts/single_done_delete.js"></script>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<title>Beneficiary Delete </title>
	</head>
	<%
		String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int  month = cal.get(Calendar.MONTH) + 1;
		int  year = cal.get(Calendar.YEAR);
	%>
	<body onload="delete_fun(1);delete_fun(3)">
	<table align="center" width="75%" height="" border="1" style="border-collapse: collapse;border-color: purple;">
		 
			<tr>
				<td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beneficiary Type</td>
				<td><select id="btype" name="btype" onchange='delete_fun(4)'></select></td>
			</tr>
			<tr> 
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;District</td>
				<td> <select id="District" name="District" onchange="delete_fun(2);delete_fun(4)"></select>
				Block : <select id="block" name="block" onchange="delete_fun(4)"></select></td>
			</tr>
			
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beneficiary Name</td>
				<td><select id="ben_name" name="ben_name" ></select></td>
			</tr>
			<tr> 
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Delete (w.e.f)</td>
				<td> 
						<select class="select"  id="fmonth" name="fmonth">
						<option value="0">-Select Month-</option>
				  		<%
				  		for (int j=1;j<=12;j++)
				  		{
				   		%>
				 	   <option value="<%=j%>"><%=cmonth[j]%></option>
				 	   <% } %>
				 	   </select>
				 	   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select class="select"  id="fyear" name="fyear">
				   			<option value="0">-Select Year-</option>
				  			<%
				  			for (int i=year-2;i<=year;i++)
							 { 
							%><option value="<%=i%>"><%=i%></option> 
							<% } %>
							</select>&nbsp;&nbsp;&nbsp;&nbsp;<input type=button value="GO" onclick="delete_fun(5)">
				</td>
			</tr>
		</table>
		<table align="center" width="75%" height="" border="1" >
			<tr>
				<td id="data"></td>
			</tr>
			<tr>
				<td id="data2"></td>
			</tr>
		</table>
	</body>
	</html>