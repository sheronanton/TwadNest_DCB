<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/* 
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 28/01/2013
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 		  Type
 *-----------------------------------------------------------------------------
 * 28/01/2013	District wise Report in HO				Panneer Selvam.k	N
 *-----------------------------------------------------------------------------
 */


%>
<%@ page import="java.sql.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Schemewise Quantity -- Design,Pumped,Supply </title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function report_new( )
{
		var pmonth=document.getElementById("smonth").value;
		var pyear1=document.getElementById("year").value;
		var res=month_year_check(pmonth,pyear1);   
		if (res!=1)  
		{  
			var option =document.getElementById("pr_type").value;
			window.open("../../../../../ame_report?option="+option+"&process_code=29&pmonth="+pmonth+"&pyear="+pyear1);
		}
	  
	}
 
</script>  
</head>
<body>   
<%
try
{
	Calendar cal = Calendar.getInstance();
	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
	String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Connection con=obj.con();
	obj.createStatement(con);
	String	userid = (String) session.getAttribute("UserId");
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals("")) Office_id="0";
	System.out.println("Test");	
	con = null;  
	String sel="",sely="";
	
	int prvyear=obj.prv_year(year,month);
	int prvmonth=obj.prv_month(year,month); 
	 
%>
<form action="" method="get">

<table align="center" border="1" bordercolor="skyblue" style="width: 610pt;border-collapse: collapse;" cellpadding="10" cellspacing="0">
		<tr>
			<th colspan="2" align="center"  class = "tdH">Scheme wise Quantity <br>  Design,Pumped and Supply</th>
		</tr> 
		<tr>
			<td>   Year  </td>
			<td> 	
					 <select class="select" id="year" name="year" style="width: 130pt;"  onchange="report_period_verify(document.getElementById('smonth'),this)">
				        <option value="0">Select</option>			 
					<%
					for (int i = year-6; i <=year; i++) 
					{
						if (prvyear!=i)
						{
						%>
						<option value="<%=i%>"><%=i%></option>
						<% } else { %>
						<option value="<%=i%>" selected><%=i%></option>
						<% } %>
					<% 	} %>     
					</select>
			</td>
		</tr>
	
		 <tr>
		 	<td>Month </td>  
		 	<td>
		 	 <div><select class="select" id="smonth" name="smonth"  style="width: 110pt;"   onchange="report_period_verify(this,document.getElementById('year'))">
				<option value="0">Select</option>
				 	<%  for (int j = 1; j <=12  ; j++) {
				 		if (prvmonth!=j) {
						%>  
						<option value="<%=j%>"><%=monthArr[j]%></option>
						<% } else {
						%>
						<option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
						<%}		
						 }%>
				</select></div>
		 	</td>  
		 </tr>
		
		<tr>
		 	<td> Report Type </td>
 		<td >
						<select id="pr_type" class="select">
						<option value="0"> Select </option> 
						<option value="1"> PDF </option>
						<option value="2"> EXCEL  </option>
						</select>
					</td>
		</tr>
			 
		<tr>     
			<th colspan="2" align="center">
			<input type="button" value="Report"   onclick="report_new()">
			<input type="reset" value="Exit" onclick= "javascript:self.close()" />  </th>
		</tr>
		</table>
		</form>  
<%
}catch(Exception e ) 
{
System.out.println(e);	
}
%>
</body>
</html>