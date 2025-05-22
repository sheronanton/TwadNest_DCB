<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pumped Qty and Demand Statement  </title>
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
		var today = new Date();
		var date = today.getDate();	
		 var chyear = today.getFullYear(); 
		 var prmonth = today.getMonth(); 
		 prmonth++;
		var Lmonth=3;
		var pyear=document.getElementById("finyear").value;	 
//		alert("pyear is --> "+ pyear)
//		alert("prmonth is --> "+ prmonth)
		
		var Lyear=pyear;
		
		if(chyear==pyear)
				{
					Lmonth=prmonth;
					Lyear=pyear;
				}else
					{
					Lmonth=3;
					Lyear++;
					}
	//	alert("Lyear is --> "+ Lyear)
	//	alert("pyear is --> "+ pyear)
	//	var pmonth=document.getElementById("pmonth").value; 
	//	var pyear=document.getElementById("pyear").value;		 
		if (Lyear==0 || pyear==0)
		{
			alert("Select Month and Year..");			
		}
		else     
		{
			window.parent.frames[2].location.href="about:blank";
			window.parent.frames[3].location.href="about:blank";
			window.parent.frames[4].location.href="about:blank";
			window.parent.frames[1].location.href ="HO_SUP_DMD_REPORT1.jsp?Lyear="+Lyear+"&pyear="+pyear;
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

int year_incr=0;
if (month <4)
	  //year_incr=1;  
year_incr=0;  
else
	 year_incr=1;
    // year_incr=2;  
int prvfinyear=obj.prvfinyear(year,month);
int prvmonth=obj.prv_month(year,month);

//int prv_month=month-1;
//int prv_year=year;
//if (prv_month==12)  prv_year=year-1;
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
			<td  colspan="3" align="center">Pumped Qty and Demand Statement - Full Report</td>
		</tr> 
		<tr>
			<td>Financial Year</td>
			<td>
			
			<select id="finyear" class="select"  tabindex="5" style="width:120pt"  >
                	<option value="0" selected="selected">Select</option>
                	<%                	
                	for (int j=year-6;j<=year+year_incr;j++)
                	{
                		if (j-1==prvfinyear)
                		{
                	%>
                	<option value="<%=j-1%>" selected="selected"><%=j-1%>-<%=j%></option>
                	<%
                		}
                		else
                		{
                			%>
                        	<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
                        	<%		
                		}
                		 
                	}
                	%>               	
                 </select>
			
			
			<input type="button" value="Submit" onclick="load_new2()"> </tD>
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