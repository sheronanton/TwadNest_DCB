<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quantity pumped to the Private beneficiaries</title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>

<script type="text/javascript">
function rld_new6() 
{
	var year=document.getElementById("pyear").value; 
	var smonth=document.getElementById("pmonth").value;
	var option=document.getElementById("rtype").value;  
	var rpt=81;
		window.open("../../../../../../PMS_DCB_HO_DIST_WISE?month="+smonth+"&splflag="+option+"&year="+year+"&ref_sno="+(parseInt(rpt)+20));
} 
function rld_new7() 
{
	var year=document.getElementById("pyear").value; 
	var smonth=document.getElementById("pmonth").value;
	var option=document.getElementById("rtype").value;    
	var rpt=82;
		window.open("../../../../../../PMS_DCB_HO_DIST_WISE?month="+smonth+"&splflag="+option+"&year="+year+"&ref_sno="+(parseInt(rpt)+20));
} 
</script>  
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
</head>
<body>

<form action="" method="post"> 
<%
String  Office_name="",userid="",Office_id=""  ;
Controller obj=new Controller();
Connection con=null;
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) + 1;
int year = cal.get(Calendar.YEAR);
try
{
	con=obj.con();
	con=obj.con();   
	obj.createStatement(con);
	userid=(String)session.getAttribute("UserId");
	if(userid==null)  
	{
	  response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
	String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};

%>
<table border="1" width="55%" cellpadding="10" cellspacing="0" style="border-collapse: collapse;"  align="center">
		<tr  bgcolor="#3080A0">
			<td  colspan="4" align="center"><font color=white>Quantity pumped to the Private beneficiaries <Br><%=Office_name %></font></td>
		</tr> 
		<tr>
			<td width="5%">Year </td> 
			<td width="5%">
			 <select class="select"  id="pyear" name="pyear"  style="width:80pt" onchange="report_period_verify(document.getElementById('pmonth'),this)" >
   				<option value="0">Select</option>
			  <%
			  for (int i=year-6;i<=year;i++)
			  {
			   %>
			  <option value="<%=i%>"><%=i%></option>
			  <%} %>
			  </select> &nbsp; &nbsp; &nbsp; 
			 </td></tr><tr><td width="5%"> Month </td><td width="5%"><select class="select" id="pmonth" name="pmonth"  style="width:80pt" onchange="report_period_verify(this,document.getElementById('pyear'))">
		  		<option value="0">Select</option>
   				<%
			  		for (int j=1;j<=12;j++)
			  		{
			   	%>
			    <option value="<%=j%>"><%=cmonth[j]%></option>
			  	<%} %>
			 </select>
			  
			  </td>
  		 </tr>
  		 <tr><Td><label class="lbl">Select Report Type</label> </Td>
       		 	<td>
       			<select id="rtype" name="rtype" class='select'>
						<option value="0">Select </option>							
							<option value="1" selected="selected">  PDF </option>
							<option value="2">  Excel </option>
							<option value="3">  HTML </option>    
				</select></td> 
       			</tr>
  		 <tr>  
  		 	<td colspan="4" align="center">
  		 		<input type="button" value="District Wise" onclick="rld_new6()">
  		 		<input type="button" value="Financial Year Report" onclick="rld_new7()">
  		 		<input type="button"  style="color: red;font-weight: bold"  value="Close" onclick="javascript:window.close()">
  		 	</td>
  		 </tr>
	</table>
<%
}catch(Exception e)
{
}
%>
</form>
</body>
</html>