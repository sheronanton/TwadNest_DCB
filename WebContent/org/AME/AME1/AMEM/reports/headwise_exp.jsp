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
<title>Head Wise Expenditure - AME </title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script> 
<script type="text/javascript">
function check()
{
	var year=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var res=month_year_check(smonth,year);
 
	if (res==0)
	{
		return true;
	}else 
	{
		return false;  
	}
}
		function rld_new()
		{
			var year=document.getElementById("year").value; 
			var smonth=document.getElementById("smonth").value;
			var res=month_year_check(smonth,year);      
			if (res!=0)
			{
				var pr_type=2;
				var rpt=50;
				window.open("../../../../../PMS_DCB_HO_DIST_WISE?month="+smonth+"&splflag="+pr_type+"&year="+year+"&ref_sno="+(parseInt(rpt)+20));
			}    
		}
		function rld_new2()      
		{
			var year=document.getElementById("year").value; 
			var smonth=document.getElementById("smonth").value;
			var rpt=50;
			var res=month_year_check(smonth,year);      
			if (res!=0)
			{
				window.open("Performance_entry_report.jsp?month="+smonth+"&year="+year);
			}      
		}
</script>
</head>
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
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
			
		String but=obj.setValue("Report",request);
		String selyear1=obj.setValue("year",request);
		String selmonth1=obj.setValue("smonth",request);
		String pr_type=obj.setValue("pr_type",request);
		int r=0;
		if (but.equalsIgnoreCase("Report"))   
		{
			try   
			{ 
				DataUploadAmeCollection d=new DataUploadAmeCollection();
		 		d.data_upload( Integer.parseInt(selyear1),Integer.parseInt(selmonth1),Integer.parseInt(Office_id) );
		 	    r=1;
			}catch(Exception e)  
			{
				out.println(e);
				
			}    
		}  
		if (but.equalsIgnoreCase("Report") && r==1)    
		{
			 response.sendRedirect("./report_s.jsp?smonth="+selmonth1+"&year="+selyear1+"&pr_type="+pr_type);  
		}
		else  
		{    
			   
			
		}
		//con.close();
		con = null;  
		int prvmonth=obj.prv_month(year,month);
		int prvyear=obj.prv_year(year,month);
String sel="",sely="";
%>  
<form action="./headwise_exp.jsp" method="get" onsubmit="return check()">

<table align="center" border="1" bordercolor="skyblue" style="width: 310pt;border-collapse: collapse;" cellpadding="10" cellspacing="0">
		<tr>
			<th colspan="2" align="center"> Head Wise Expenditure </th>
		</tr>
		<tr>
			<td>Month</td>
			<td>  
			<select class="select" id="smonth" name="smonth" style="width: 110pt;" onchange="report_period_verify(this,document.getElementById('year'))" >
					<option value="0">Select</option>
				 	<%
				 		for (int j = 1; j <=12  ; j++) 
				 		{
				 			if (prvmonth==j) sel="selected"; else sel="";
					%>
						<option value="<%=monthArrv[j]%>" <%=sel%> ><%=monthArr[j]%></option>
					<% }  %>
				</select>
			 </td>
		</tr>  
		<tr>
			<td> Year </td>
			<td> 	
					 <select class="select" id="year" name="year" style="width: 110pt;" onchange="report_period_verify(document.getElementById('smonth'),this)" >
				        <option value="0">Select</option>			 
					<% 	for (int i = year-1; i <=year; i++) 
					{ 
						if (prvyear==i) sely="selected";
					%>
						<option value="<%=i%>" <%=sely%> > <%=i%></option>
					<% 	} %>     
					</select>
			</td>
		</tr>
		 <tr>
		 	<td> Report Type </td>
 		<td >
						<select id="pr_type" name="pr_type" class="select">
						<option value="0">Select Print Format   </option>
						<option value="1">PDF </option>
						<option value="2">EXCEL  </option>
						</select>
					</td> 
		</tr>
		<tr>    
			<th colspan="2" align="center">
			<input type="submit" name="Report" value="Report">
			<input type="reset" value="Exit" onclick= "javascript:self.close()" />  </th>
		</tr> 
		</table>
<%
}catch(Exception e ) {}
%>
</form>
</body>
</html>