<?xml version="1.0" encoding="ISO-8859-1" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.util.Calendar"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>ANNEXURE REPORT </title>
 			 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/transcat.js"></script>
<script type="text/javascript" src="../scripts/reports.js"></script>

	<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 700,700 );  
			}
			 
			</script>
</head>
<body onload="page_size() ">
<%
	HttpSession session1 = request.getSession(false);
	String userid = (String) session1.getAttribute("UserId");
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);

	Controller obj = new Controller();
	if (userid == null) {

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");

	if (Office_id.equals(""))
		Office_id = "0";
	String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");


	String[] monthArr = { "Select", "January", "February", "March","April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
%>
<table width="100%" class="table" align="center" border=1 cellpadding="5">
	<tr>
		<td colspan="3" align="center" class="tdH">Monthly Scheme Expenditure Summary </td>
	</tr>
	<tr>
		<td width="25%"><label class="lbl">Division Office Name :</label></td>
		<td align="left" colspan="2"><label class="lbl"><%=Office_Name%></label></td>
	</tr>
	
	<tr>
		<td><label class="lbl">Financial Year</label></td>


		<Td colspan="2"><select class="select" id="pyear" name="pyear"
			style="width: 80pt;"  >
			<option value="0">Select</option>
			<%
				for (int i = 2010; i <= year ; i++) {
			%>
			<option value="<%=i%>-<%=i + 1%>"><%=i%>-<%=i + 1%></option>


  
 
			<%
				}
			%>
		</select></tD>
		
	</tr>
	   <tr>  
			  	 <td><label class="lbl">  Month </label></td>	  	          
			 	 <td colspan="2" width="63%"><select class="select" id="pmonth" name="pmonth"  style="width:75pt;"   >
			  					 <option value="0">Select</option>
			  				  	<%for (int j=1;j<=12;j++){%>
			    				<option value="<%=j%>"><%=monthArr[j]%></option>
			    				<%} %>  
			 				</select>   
			 	 </td> 
			 	
			 </tr>
	<tr><td colspan="3" align="left" valign="baseline"> 
		<a href="javascript:report(14)">ANNEXURE I - Division Expenditure</a> <br>
		<a href="javascript:report(11)">ANNEXURE II - Scheme Expenditure</a> <br>
		<a href="javascript:report(12)">ANNEXURE III - Month Expenditure CWSS </a><br>
		<a href="javascript:report(13)">ANNEXURE III(a) - Month Upto Expenditure CWSS</a><br>
		<a href="javascript:report(15)">ANNEXURE IV - Month Expenditure WSS</a><br>
		<a href="javascript:report(16)">ANNEXURE IV(a) - Month Upto Expenditure WSS</a><br>
		<a href="javascript:report(17)">ANNEXURE V - Fund Receipt</a><br>
		<a href="javascript:window.close()">Close</a><br>
	
		 <a href="../reports/headwise_exp.jsp">New Report</a>
		  
			</td></tr>

</table>
</body>
</html>
