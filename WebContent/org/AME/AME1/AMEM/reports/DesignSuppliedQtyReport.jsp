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
<%@ page import="Servlets.AME.AME1.AMEM.servlets.DataUploadAmeCollection,java.sql.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Financial Year  Design and Actual Supply - AME </title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>

<script type="text/javascript">
		function rld_new()
		{
			var year=document.getElementById("year").value; 
			if (year!=0)
			{
			var pr_type=document.getElementById("pr_type").value;    
			var rpt=60;     
		
			window.open("../../../../../PMS_DCB_HO_DIST_WISE?year=0&month=0&splflag="+pr_type+"&fin_year="+year+"&ref_sno="+(parseInt(rpt)+20));
			}else
			{
				alert("Select Financial year ");
				
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
			
		 
		 
		//con.close();
		con = null;  
String sel="",sely="";
int year_incr=0;
if (month <4)
	  year_incr=0;    
else
	  year_incr=1; 
int prvfinyear=obj.prvfinyear(year,month); 
%>
<form action="" method="get">
<table align="center" border="1" bordercolor="skyblue" style="width: 610pt;border-collapse: collapse;" cellpadding="10" cellspacing="0">
		<tr>
			<th colspan="2" align="center"  class = "tdH">Financial Year  Design and Actual Supply  </th>
		</tr>
		  
		<tr>
			<td> Financial Year  </td>
			<td> 	
					 <select class="select" id="year" name="year" style="width: 130pt;"  >
				        <option value="0">Select</option>			 
						 <%
                		 for (int j=year-1;j<=year+year_incr;j++)
                		 { 
                			 if (j-1==prvfinyear)
                			 {
                		    %>
                			<option value="<%=j-1%>-<%=j%>" selected><%=j-1%>-<%=j%></option>
                			<%  
                			 }else{
                				 %>
                     			<option value="<%=j-1%>-<%=j%>"><%=j-1%>-<%=j%></option>
                     			<% 
                			 }
                		 } %>  
					</select>
			</td>
		</tr>
		 <tr>
		 	<td> Report Type </td>
 		<td >
						<select id="pr_type" class="select">
						<option value="0">Select </option>
						<option value="1"> PDF </option>
						<option value="2"> EXCEL  </option>
						</select>
					</td>
		</tr>
		<tr>   
			<th colspan="2" align="center">
			<input type="button" value="Report"   onclick="rld_new()">
			<input type="reset" value="Exit" onclick= "javascript:self.close()" />  </th>
		</tr>
		
		</table>
</form>
<%
}catch(Exception e ) {}
%>
</body>
</html>