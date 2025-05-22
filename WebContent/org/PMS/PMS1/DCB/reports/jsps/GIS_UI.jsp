<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GIS Abstract</title>
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
			 	window.open("GIS_Ben_Types.jsp?pmonth="+pmonth+"&pyear="+pyear);
		}
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
			<td  colspan="4" align="center"><font color=white>GIS Abstract</font></td>
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
			 </td><td width="5%"> Month </td><td width="5%"><select class="select" id="pmonth" name="pmonth"  style="width:80pt" onchange="report_period_verify(this,document.getElementById('pyear'))">
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
  		 
  		 <tr> 
  		 	<td colspan="4" align="center">
  		 		<input type="button" value="Submit" onclick="load_new2()">
  		 		<input type="button"  style="color: red;font-weight: bold"  value="Close" onclick="javascript:window.close()">
  		 	</td>
  		 </tr>
	</table><A href="Ben_Wise_Pumping_qty.jsp">.</A>
<%
}catch(Exception e)
{
}
%>
</form>
</body>
</html>