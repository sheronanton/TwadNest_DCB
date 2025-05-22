<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tariff rate Details  </title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<style type="text/css">
	.ft
	{
		font-size: small;
	}  
</style>
<script type="text/javascript">
	function load_new( )
	{
		var pmonth=document.getElementById("pmonth").value; 
		var pyear=document.getElementById("pyear").value;		 
		if (pmonth==0 || pyear==0)
		{
			alert("Select Month and Year..");			
		}
		else
		{
			window.open("md_abstract_stypewise.jsp?pmonth="+pmonth+"&pyear="+pyear);
		}
		
	}
	function load_new2( )
	{
		var pmonth=document.getElementById("pmonth").value; 
		var pyear=document.getElementById("pyear").value;		 
		if (pmonth==0 || pyear==0)
		{
			alert("Select Month and Year..");			
		}
		else     
		{
			window.parent.frames[2].location.href="about:blank";
			window.parent.frames[3].location.href="about:blank";
			window.parent.frames[4].location.href="about:blank";
			window.parent.frames[1].location.href ="md_new_abs_ctypewise_mir2.jsp?pmonth="+pmonth+"&pyear="+pyear;
			// 	window.open("md_new_abs_ctypewise.jsp?pmonth="+pmonth+"&pyear="+pyear);
			
		}
	} 
</script>  
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
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
int prv_month=month-1;
int prv_year=year;
if (prv_month==12)  prv_year=year-1;
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
<table class="ft" border="1" width="62%" cellpadding="5" cellspacing="0" style="border-collapse: collapse;"  align="center">
		<tr class="tdH">   
			<td  colspan="3" align="center">Traiff rate Details -  Report</td>
		</tr> 
		<tr>
			<td>Month And Year</td>
			<td><select class="select" id="pmonth" name="pmonth"  style="width:80pt" onchange="report_period_verify(this,document.getElementById('pyear'))" style="width: 80pt;">
		  		<option value="0">Select</option>
   				<%	for (int j=1;j<=12;j++)
   				{
   				if (j==prv_month) { %>			
			    <option value="<%=j%>" selected="selected"><%=cmonth[j]%></option>
			  	<% }  else { %>   		  		
				    <option value="<%=j%>" ><%=cmonth[j]%></option>
				<% }  
   				} %>
			 </select>
			<select class="select"  id="pyear" name="pyear"  style="width:80pt" onchange="report_period_verify(document.getElementById('pmonth'),this)" >
   			 <option value="0">Select</option>
			  <%
			  for (int i=year-1;i<=year;i++)
			  {
			   if (i==prv_year) { %>
			  <option value="<%=i%>" selected="selected"><%=i%></option>
			 	<% }  else { %> <option value="<%=i%>"><%=i%></option>
			  <%} 
			   }%>
			  </select><input type="button" value="Submit" onclick="load_new2()"> </tD>
			  <td align="right"><input type="button" value="Exit" onclick="parent.window.close()"></td>
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