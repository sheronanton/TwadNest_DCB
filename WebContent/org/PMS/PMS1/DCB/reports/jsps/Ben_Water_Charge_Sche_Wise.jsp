<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Beneficiary Water Charge Scheme Wise  </title>
<link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
		function rld()    
		{
			var pyear=document.getElementById("pyear").value;
			var pmonth=document.getElementById("pmonth").value;
		 	var pyear1=pyear;
			var pmonth1=document.getElementById("pmonth1").value;
			var res=month_year_check(pmonth,pyear);
			if (res!=1 && pmonth1!=0)
			{
				window.open("../../../../../../Water_Charges_Report?command=WCBenScheme&pyear="+pyear+"&pmonth="+pmonth+"&pyear1="+pyear1+"&pmonth1="+pmonth1);
			}	
		}
</script>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
</head>
<%
		String Office_id="0",Office_Name="0";
		Connection con=null;
		HttpSession session1 = request.getSession(false);
		String userid = (String) session1.getAttribute("UserId");
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj = new Controller();
		con=obj.con();
		if (userid == null) {
		
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
		String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		String cyear="";
		//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID  from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
%>
<body>
 			<table align="center" width="50%" border=1   cellpadding="12" cellspacing="0" style="vertical-align: top;">
		<tr>
			<td colspan="2" align="center" class="tdH"> Beneficiary Water Charge Scheme Wise - <%=Office_Name %> </td></tr>
		 
		 <tr>  
			<td>  Year :</td><td>  
					<select  id="pyear" name="pyear" style="width: 80pt;" onchange="report_period_verify(document.getElementById('pmonth'),this)">
					<option value="0">-Year-</option>  			 
					<%
					for (int i=year;i>=2015;i--)
		//			for (int i = year-6; i <= year; i++)
					{ %>
						<option value="<%=i%>"><%=i%></option>  
					<% 	} %>
					</select>
			</td>      
		 </tr>  
	<tr>           
			
			<td>From Month :</td><td>
				<select id="pmonth" name="pmonth"  style="width: 80pt;" onchange="report_period_verify(this,document.getElementById('pyear'))">
				<option value="0">-Month-</option>  
					<%  for (int j = 1; j <=12  ; j++) {
		 				      
						%>
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
						<%} %>
				 
				</select>
			 &nbsp;&nbsp;&nbsp;To Month :
				<select  id="pmonth1" name="pmonth1"   style="width: 80pt;" onchange="report_period_verify(this,document.getElementById('pyear'))">
				<option value="0">-Month-</option>
					<%  for (int j = 1; j <=12  ; j++) {   
					 %>
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
						<%} %>
				 
				</select>
			</td>
	</tr>
	<tr>
			<td align="center" colspan="2">
			<input  type="button" value="REPORT" class="fb2"   onclick="rld()">
			<input type="button" value="EXIT" class="fb2"  onclick="window.close()"> </td>
		</tr>
</table>
</body>
</html>