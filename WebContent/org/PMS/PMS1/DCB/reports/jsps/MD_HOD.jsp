<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand,Collection and Balance Statement  </title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>  
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
		//	window.parent.frames[1].location.href ="md_new_abs_ctypewise_mir.jsp?pmonth="+pmonth+"&pyear="+pyear;
			 	window.open("md_new_abs_ctypewise.jsp?pmonth="+pmonth+"&pyear="+pyear);
		}
	} 
	function load_new3( )
	{
		var pmonth=document.getElementById("pmonth").value; 
		var pyear=document.getElementById("pyear").value;		 
		if (pmonth==0 || pyear==0)
		{
			alert("Select Month and Year..");			
		}
		else  
		{
		//	window.parent.frames[1].location.href ="md_new_abs_ctypewise_mir.jsp?pmonth="+pmonth+"&pyear="+pyear;
			 	window.open("md_new_abs_ctypewise_rev.jsp?pmonth="+pmonth+"&pyear="+pyear);
		}
	} 
</script>  
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
int prv_month=0;
if (month==1) prv_month=12;
else
prv_month=month-1;
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
<table border="1" width="62%" cellpadding="10" cellspacing="0" style="border-collapse: collapse;"  align="center">
		<tr bgcolor="#3080A0">
			<td  colspan="2" align="center"><font color=white>Demand,Collection and Balance Statement <Br> Abstract Report </font></td>
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
			  for (int i=year-2;i<=year;i++)
			  {
			   if (i==prv_year) { %>
			  <option value="<%=i%>" selected="selected"><%=i%></option>
			 	<% }  else { %> <option value="<%=i%>"><%=i%></option>
			  <%} 
			   }%>
			  </select> </tD>
  		 </tr>  		 
  		 <tr> 
  		 	<td colspan="2" align="center">
  		 		<input type="button" value="Submit" onclick="load_new2()"> <input type="hidden" value="Revised Report" onclick="load_new3()"> 
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