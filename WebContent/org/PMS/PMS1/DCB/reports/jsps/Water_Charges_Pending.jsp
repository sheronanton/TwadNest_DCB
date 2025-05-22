<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Calendar"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Water Charges Pending </title>
<script type="text/javascript" src="../scripts/water_charges_Pending.js"></script>
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
	 
	 <td>
	 	District   &nbsp;&nbsp;&nbsp;&nbsp;
			<select id=dis name="dis">
				<option value="31">Ariyalur</option>
				<option value="10">Coimbatore</option>
				<option value="6">Cuddalore</option>
				<option value="4">Dharmapuri</option>
				<option value="11">Dindugul</option>
				<option value="8">Erode</option>
				<option value="2">Kancheepuram</option>
				<option value="21">Kanyakumari</option>
				<option value="25">Karur</option>
				<option value="30">Krishnagiri</option>
				<option value="16">Madurai</option>
				<option value="22">Nagapattinam</option>
				<option value="27">Namakkal</option>
				<option value="9">Nilgiris</option>
				<option value="24">Perambalur</option>
				<option value="14">Pudukkottai</option>
				<option value="18">Ramanathapuram</option>
				<option value="7">Salem</option>
				<option value="15">Sivagangai</option>
				<option value="13">Thanjavur</option>
				<option value="28">Theni</option>
				<option value="29">Thiruvarur</option>
				<option value="12">Tiruchirapalli</option>
				<option value="20">Tirunelveli</option>
				<option value="32">Tiruppur</option>
				<option value="5">Tiruvannamalai</option>
				<option value="26">Tiruvellore</option>
				<option value="19">Tuticorin</option>
				<option value="3">Vellore</option>
				<option value="23">Villupuram</option>
				<option value="17">Virudhunagar</option>
				<option value="37">Kallakurchi</option>
				<option value="38">Thenkasi</option>
				<option value="39">Chengalpattu</option>
				<option value="40">Ranipet</option>
				<option value="42">Tirupathur</option>
				<option value="41">Mayiladuthurai</option>
				
							
			</select>
			<input type="button" onclick="report_show1()" value="Districtwise Watercharges Due Abstract" style="font-style: italic;font-size:7;color:green;font-weight: bold" class="fb2">
		</td>
			       
	 
	 
	 
	 </tr>
	 <tr>
	 	<td>Report Title</td>
	 	<td>
	 		<select id="reporttype" name="reporttype">
	 			<option value="0">Select</option>
	 			<!--<option value="1">HOD Abstract</option>
	 			--><option value="2">Over All Abstract</option>
	 			<option value="30">Over All Abstract New</option>
	 			<option value="31">Over All Abstract Bentypewise</option>
	 			<option value="3">CMA Abstract</option>
	 			<option value="4">DTP Abstract</option>
	 			<option value="5">DRD Abstract </option>
	 			<option value="6">CMA Report - Corporation</option>
	 			<option value="7">CMA Report - Municipality District(both) </option>
	 			<option value="15">CMA Report - Municipality III Grade Details</option>
	 			<option value="8">CMA Report - Municipality Details(no 3rd grade)</option>
	 			<option value="9">DTP Report - Town Panchayats</option>
	 			<option value="10">DTP Report - Town Panchayats Block Wise</option>
	 	<!--	<option value="11">DTP Report -RTP Detail</option>  -->
	 			<option value="12">DRD- Village Panchayats</option>
	 			<option value="14">DRD- Village Panchayats Block Wise</option>
	 			<option value="18">Private Abstract</option>  
	 			<option value="16">Private Details</option>  
	 			<option value="17">Schemewise Details</option>  
	 			<option value="19">Officewise Abstract</option> 
	 			<option value="31">Beneficiary type</option> 
	 			<option value="20">Corporation</option>  
	 			<option value="21">Municipality Detail</option>  
	 			<option value="29">Municipality Abstract</option>  
	 			<option value="22">Town Panchayat</option>  
	 			<option value="23">Village Panchayat</option> 
	 			<option value="32">Village Panchayat Detailed</option>  
	 			<option value="28">Private</option>
	 	<!--		<option value="24">Village Panchayat New</option>  -->
	 			<option value="25">Districtwise Report</option>  
	 			<option value="26">Divisionwise Demand and Collection</option> 
	 			<option value="27">Region Officewise WC</option> 
	 			<option value="33">Region Officewise WC NEW</option> 
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
		<td>Include present month Demand &nbsp;&nbsp;&nbsp;&nbsp;
			<select id="option_new1" name="option_new1">
				<option value="1">NO</option>
				<option value="2">Yes</option>
				
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