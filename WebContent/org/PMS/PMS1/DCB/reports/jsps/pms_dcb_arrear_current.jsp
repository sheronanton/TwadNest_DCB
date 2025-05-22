<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/pms_dcb_arrear_current.js"></script>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<title>PMS_DCB_BEN_WISE</title>
</head>
<body>
<%
		String combo1=""; 
		Controller obj=null;
		Connection con;
		try
		{
		 	String userid=(String)session.getAttribute("UserId");
			if(userid==null)
			{ 
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			} 
			obj=new Controller();
			con=obj.con();
		 	obj.createStatement(con);
		 	String cb=obj.combo_str("PMS_DCB_MIS_REPORT_TITLE","REPORT_HEADING","REPORT_REF"," order by REPORT_REF","rpt","","class=select style='width:290px' ");  
		 	Calendar cal = Calendar.getInstance();
		 	int day = cal.get(Calendar.DATE);
		 	int month = cal.get(Calendar.MONTH) + 1;
		 	int year = cal.get(Calendar.YEAR);  
		 	int prv_month=month-1;
			int prv_year=year;
			if (prv_month==12)  prv_year=year-1;
			String Region_id="0";		 
		 	String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		 	String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
		 	String disable="";
		 	if (Integer.parseInt(Office_id)!=5000)
		 		disable=" disabled=disabled";
		 	int prvmonth=obj.prv_month(year,month);		
%>
<table align="center" width="75%" cellpadding="2">
	<tr bgcolor="skyblue">
		 <td colspan="2" align="center" class="tdText"><b>Head Office Report</b></td>
	</tr>
	<tr class="tdText">
	 	<td><label id="benname" >Year</label></td>
		<td colspan="2">
		 	<select id="year" tabindex="5" style="width:100pt" class="select" onchange="report_period_verify(document.getElementById('selmonth'),this)">
		 	<option value="0" selected="selected">- - - Select - - -</option>
		 	<%
		 	
		 	for (int j=year-6;j<=year;j++)
		 	{  
		 	 if (j==prv_year) { %>
			  <option value="<%=j%>" selected="selected"><%=j%></option>
			 	<% }  else { %> <option value="<%=j%>"><%=j%></option>
			  <%} } %>
		 </select>
		 </td>
	 </tr>
	 <tr class="tdText"> 
	 	<td><label id="Month" >Month</label></td>
		 <td>
			 <select id="selmonth" style="width: 100pt" class="select" onchange="report_period_verify(this,document.getElementById('year'))">
			 <option value="0" selected="selected" >- - - Select - - -</option>
			 <%
			 	String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
			 	for (int i=1;i<=12;i++) 
			 	{
			 		if (i==prvmonth) { %>			
				    <option value="<%=i%>" selected="selected"><%=amonth[i]%></option>
				  	<% }  else { %>   		  		
					    <option value="<%=i%>" ><%=amonth[i]%></option>
					<% }
			 	}
			 	%>
			  
			 </select>
		 </td>
	 </tr>
	 <tr>
	 	<td>Report Title</td>
	 	<td>
	 		<select id="reporttype" name="reporttype">
	 			<option value="0">Select</option>
	 			<option value="1">Abstract</option>
	 			<option value="2">Details</option>
	 			<option value="3">District Wise</option>
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
	<tr bgcolor="skyblue" >
	 <td colspan="2" align="center"><input type="button" onclick="report_show()" value="Print" style="font-style: italic;font-size:7;color:green;font-weight: bold" class="fb2">&nbsp;&nbsp;<input type="button" onclick="window.close()" value="Exit" style="font-style: italic;font-size:7;color:red;font-weight: bold" class="fb2"><img src="../../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('../../jsps/help1.jsp?fn=67','mywindow','width=600,height=400,scrollbars=yes')"></td>
	</tr>
</table>

<%
		}catch(Exception e) {}
%>
 
</body>
</html>