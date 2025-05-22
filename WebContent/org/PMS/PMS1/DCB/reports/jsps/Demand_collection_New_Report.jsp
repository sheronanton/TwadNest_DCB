<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="java.sql.Connection"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand and Collection for selected period </title>
 <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 <script type="text/javascript">
		function rld()
		{
			var pyear=document.getElementById("pyear").value;
			var pmonth=document.getElementById("pmonth").value;
			var pmonth1=document.getElementById("pmonth1").value;

			window.open("../../../../../../Water_Charges_Report?command=FAS_DCB_DMD&pyear="+pyear+"&pmonth="+pmonth+"&pmonth1="+pmonth1);
			
			/*if (parseInt(pmonth1) >= parseInt(pmonth))	
			{
				
			}
			else
			{  
				alert(" To Month must be greater than From month");
			}*/	 
		}
		function rld1()
		{
			var pyear=document.getElementById("pyear").value;
			var pmonth=document.getElementById("pmonth").value;
			var pmonth1=document.getElementById("pmonth1").value;
			var ben=document.getElementById("ben").value;
			if (parseInt(pmonth1) >= parseInt(pmonth))	
			{
				
					window.open("../../../../../../Water_Charges_Report?bensno="+ben+"&command=FAS_DCB_DMD_BEN&pyear="+pyear+"&pmonth="+pmonth+"&pmonth1="+pmonth1);
			}
			else
			{  
				alert(" To Month must be greater than From month");
				alert(" For complete financial Year Reports,   two split reports can be taken. \n Example  2017-18 . April - Dec  2017  then Jan-Mar 2018");
			}	 
		}
		 
</script>  
</head>
<body>
<%
		Connection con=null;String Schemestr="",Office_id="0",Office_Name="0";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
try
{
		HttpSession session1 = request.getSession(false);
		String userid = (String) session1.getAttribute("UserId");
		Controller obj = new Controller();
		con=obj.con();
		obj.createStatement(con);
		if (userid == null) {
		 response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		// Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		String cyear="";
		  Schemestr=obj.combo_str("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","BENEFICIARY_SNO","where office_id="+Office_id+" and status='L' order by  BENEFICIARY_NAME asc","ben","  style='width: 150;font-size: 0.7em; color: blue;' class='select'  ");

		String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
		String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		int year_incr=0;
		if (month <4)
			  year_incr=0;    
		else
			  year_incr=1;
		int prvfinyear=obj.prvfinyear(year,month);
		//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID  from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
%>
	<table align="center" width="70%" border=1 class="table" cellpadding="10" cellspacing="0" style="vertical-align: top;">
		<tr class="tdH">
			<td colspan="4" align="center" class="tdH"> Demand And Collection For Selected Period - <%=Office_Name%></td></tr>
		<tr>
			<td><font color=blue>Year </font>:&nbsp;&nbsp;&nbsp;&nbsp;<select class="select" id="pyear" name="pyear" style="width: 80pt;" onchange="report_period_verify(document.getElementById('pmonth1'),this)">
					<option value="0">-Select Year-</option>			 
					<% 	
					for (int i=year;i>=2012;i--)
					// for (int i = year-6; i <= year;i++) 
					
					{ %>
						<option value="<%=i%>"><%=i%></option> 
					<% 	} %>
					</select> </td>
			<td>Month From :
				<select class="select" id="pmonth" name="pmonth" onchange="report_period_verify(this,document.getElementById('pyear'))" style="width: 80pt;"  >
				<option value="0">-Select-</option>
					<%  for (int j = 1; j <=12  ; j++) {
						  
						%>
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
						<%} %>
				 
				</select>
			 To : 
				<select class="select" id="pmonth1" name="pmonth1" onchange="report_period_verify(this,document.getElementById('pyear'))" style="width: 80pt;"  >
				<option value="0">-Select-</option>
					<%  for (int j = 1; j <=12  ; j++) {
						   
						%>
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
						<%} %>
				 
				</select> 
				</td> 
			<td>
			<input  type="button" value="REPORT" class="fb2"   onclick="rld()">
			</td>   
	</tr>
	<Tr> 
			<td>   
				<font color=blue>Beneficiary Name</font>
			</td>
			<td>
				<%=Schemestr%>
			</td>
			   
			<td>
				<input  type="button" value="REPORT" class="fb2"   onclick="rld1()">
			</td>
			
	</Tr>
	 
	<tr>
		<td colspan="4" align="center"> 
			
			<input type="button" value="EXIT" class="fb2"  onclick="window.close()"> </td>
	 
	</tr>
	</table>
	
<%
}catch(Exception e) { 
	out.println(e);
	
}
%>


</body>
</html>