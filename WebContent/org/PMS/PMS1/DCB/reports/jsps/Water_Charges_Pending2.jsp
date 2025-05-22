<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Calendar"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Water Charges Pending </title>
<script type="text/javascript" src="../scripts/water_charges_Pending2.js"></script>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script src="../../angular.min.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 <script type="text/javascript"></script>
</head>
<body> 
<form action="">
<% 
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		//out.println("year"+year);
		int month=c.get(Calendar.MONTH)+1;
		//out.println("monthhhhhhhhhhhhhhhhhhhhhhhhhhhh"+month);
		int day=c.get(Calendar.DATE);
%>
<table align="center" width="70%" cellpadding="2">
	<tr bgcolor="skyblue">
	 <td colspan="6" align="center" class="tdText"><b> Water Charges Pending </b></td>
	 </tr>
	<tr class="tdText">
	 	<td> 
	 		<label id="benname" >Year</label>
		</td>
	<td colspan="6">
	 	<select id="year" tabindex="5" style="width:100pt" class="select" onchange="report_period_verify(document.getElementById('selmonth'),this)">
	 	<option value="0" selected="selected">- - - Select - - -</option>
	 	<%
	 	
	 	for (int j=2010;j<=year;j++)
	 	{
	 	if (year==j){ 	%><option value="<%=j%>" selected="selected"><%=j%></option> <% } else { %>
	 		<option value="<%=j%>"><%=j%></option><% } %>	 	
	 	<%} %>
	 </select>
	 </td>
	 </tr>
	  
	 <tr class="tdText"> 
	 	<td><label id="Month" >Month</label></td>
	 <td>
		 <select id="selmonth" style="width: 100pt" class="select" onchange="report_period_verify(this,document.getElementById('year'))">
		 <option value="0" selected="selected" >- - - Select - - -</option>
		 <%
		 	int current_month=0;
		 	String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
		 	for (int i=1;i<=12;i++) 
		 	{
		 		if (month==i){ 
		 			current_month=month; 
		 		}
		 		
		 	   if (current_month==i)
		 	   {
		 	%><option value="<%=i%>"  selected="selected"><%=amonth[i]%></option>
		 		<% }else{ %>
		 		<option value="<%=i%>" ><%=amonth[i]%></option>
		 	<%
		 	}
		 	} %>
		 </select>
	 </td>
	 
	  
	 </tr>
	 <tr>
	 	<td>Report Title</td>
	 	<td>
	 		<select id="reporttype" name="reporttype">
	 			<option value="0">Select</option>
	 			<option value="7">Over All Abstract </option>  
	 			<option value="1">Corporation</option>  
	 			<option value="2">Municipality Detail</option>  
	 			<option value="3">Municipality Abstract</option>  
	 			<option value="4">Town Panchayat</option>  
	 			<option value="5">Village Panchayat</option>  
	 			<option value="6">Private</option>
	  		</select>
	 		
	 		
	 	</td>
	 </tr>
	  <tr> 
		<td>Bill Generated </td>   
		<td>
			<select id="option_new" name="option_new">
				<option value="1">Yes</option>
				<option value="2">NO</option>
				
			</select>
		 
		</td> 
		
	</tr>
	 <tr> 
		<td>Report Type</td>   
		<td>
			<select id="option" name="option">
				<option value="1">PDF</option>
				<option value="2">Excel</option>
				
			</select>
		 
		</td> 
	</tr>
	<tr class="tdText"> <td>Report in</td>
	 	<td>
	 		<select id="ftype" name="ftype"  style="width: 100pt" class="select" >
	 			<option value="3" >Actual</option>			
	 			<option value="2"  selected="selected">Lakhs</option>
	 			<option value="1">Crores</option>
	 		</select>
	 	</td>
	 </tr>
	<!-- <Tr><Td colspan="2" ><a href="sch_master_report.jsp">Report Condition</a></Td></Tr>-->
	<tr bgcolor="skyblue" >
	 <td colspan="6" align="center"><input type="button" onclick="report_show()" value="Print" style="font-style: italic;font-size:7;color:green;font-weight: bold" class="fb2">&nbsp;&nbsp;<input type="button" onclick="window.close()" value="Exit" style="font-style: italic;font-size:7;color:red;font-weight: bold" class="fb2"><img src="../../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('../../jsps/help1.jsp?fn=67','mywindow','width=600,height=400,scrollbars=yes')">
	 
	  <input type="button" onclick="check()" value="Click if null" style="font-style: italic;font-size:7;color:green;font-weight: bold" class="fb2">
	 </td>
	
	</tr>
</table>


</form>
</body>
</html>