<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/* 
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 28/01/2013
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 		  Type
 *-----------------------------------------------------------------------------
 * 28/01/2013	District wise Report in HO				Panneer Selvam.k	N
 *-----------------------------------------------------------------------------
 */


%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>District wise Due Report</title>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
		function rld_new()
		{
			var year=document.getElementById("year").value; 
			var smonth=document.getElementById("smonth").value;
			var rpt=document.getElementById("rpt").value;
			window.open("../../../../../../PMS_DCB_HO_DIST_WISE?month="+smonth+"&year="+year+"&ref_sno="+(parseInt(rpt)+20));    
		}
</script>  
</head>
<%@page import="java.util.Calendar"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<body>
<%
	String new_cond=Controller.new_cond;
	Controller obj = new Controller();
	Calendar cal = Calendar.getInstance();
	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
	String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
%>
<table align="center" border="1" bordercolor="skyblue" style="border-collapse: collapse;" cellpadding="10" cellspacing="0">
		<tr>
			<td colspan="2" align="center"> District Wise Water Charges Due</td>
		</tr>
		<tr>
			<td>Month</td>
			<td>  
			<select class="select" id="smonth" name="smonth" onchange="month_process_view()" style="width: 110pt;"  >
					<option value="0">-Month-</option>
				 	<%
				 		for (int j = 1; j <=12  ; j++) 
				 		{
					%>
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
					<% }  %>
				</select>
			 </td>
		</tr>
		<tr>
			<td> Year </td>
			<td> 	
					 <select class="select" id="year" name="year" style="width: 110pt;"  >
				        <option value="0">Select Year</option>			 
					<% 	for (int i = year-1; i <=year; i++) { 
													%>
						<option value="<%=i%>"> <%=i%></option>
					<% 	} %>     
					</select>
			</td>
		</tr>
		<tr>
				<td>Reports <i><strong><font color="red"><b>New</b></font></strong></i></td>
				<td>
						<select id="rpt" name="rpt" class='select'>
						  <option value="0">Select</option>
						    <option value="115">Abstract Of Water Charges Due</option>
						    <option value="116">Abstract Of Water Charges Due - In Lakhs</option>
						    <option value="117">Abstract Of Water Charges Due - In Actual</option>
						    <option value="0">-----------------------</option>
							<option value="1">Corporation  </option>
							<option value="2">Municipalities</option>
							<option value="3">Rural Town Panchayat</option>
							<option value="4">Urban Town Panchayat</option> 
							<option value="5">Village Panchayat</option>
							<option value="0">-----------------------</option>
							<option value="11">Corporation - In Lakhs </option>
							<option value="12">Municipalities - In Lakhs</option>
							<option value="13">Rural Town Panchayat - In Lakhs</option>
							<option value="14">Urban Town Panchayat- In Lakhs</option> 
							<option value="15">Village Panchayat - In Lakhs</option>
							<option value="0">-----------------------</option>
							<option value="21">Corporation - In Actual </option>
							<option value="22">Municipalities - In Actual</option>
							<option value="23">Rural Town Panchayat - In Actual</option>
							<option value="24">Urban Town Panchayat - In Actual</option> 
							<option value="25">Village Panchayat - In Actual</option>				
						</select>
						<input  class="fb2"  type="button" value="Print" onclick="rld_new()" id="b1" >
						</td>
						</tr>
</table>
</body>
</html>