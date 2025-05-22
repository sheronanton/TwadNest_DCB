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

<%@page import="Servlets.AME.AME1.AMEM.servlets.*"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>District wise Due Report</title>
<script type="text/javascript">
		function rld_new()
		{
			var year=document.getElementById("year").value; 
			var smonth=document.getElementById("smonth").value;
			var rpt=document.getElementById("rpt").value;
			window.open("../../../../../../PMS_DCB_HO_DIST_WISE?month="+smonth+"&year="+year+"&ref_sno="+(parseInt(rpt)+20));    
		}
</script>
</head>
<body>
<form action="headwise_exp.jsp" method="get">
<%
try
{
	Controller obj = new Controller();
	Calendar cal = Calendar.getInstance();
	java.sql.Connection con=obj.con();
	obj.createStatement(con);
	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
	String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	int day = cal.get(Calendar.DATE);
	 int year1=cal.get(Calendar.YEAR);
	 String month=obj.setValue("smonth",request);
	 String year=obj.setValue("year",request);
	try{ 
	 if (month!="0" && year!="0")
	 {
		DataUploadAmeCollection d=new DataUploadAmeCollection();
		d.data_upload(
			Integer.parseInt(month),	  
			Integer.parseInt(year),5086
		) ;
		 
	 }
	}catch(Exception e) {}

%>
<table align="center" border="1" bordercolor="skyblue" style="border-collapse: collapse;" cellpadding="10" cellspacing="0">
		<tr>
			<td colspan="2" align="center"> District Wise Water Charges Due</td>
		</tr>
		<tr>
			<td>Month</td>
			<td>  
			<select class="select" id="smonth" name="smonth" onchange="month_process_view()" style="width: 110pt;"  >
					<option value="0">-Month-</option>
				 	<%
				 		for (int j = 1; j <=12  ; j++) 
				 		{
					%>
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
					<% }  %>
				</select>
			 </td>
		</tr>
		<tr>
			<td> Year </td>
			<td> 	
					 <select class="select" id="year" name="year" style="width: 110pt;"  >
				        <option value="0">Select Year</option>			 
					<% 	for (int i = year1-1; i <=year1; i++) { 
													%>
						<option value="<%=i%>"> <%=i%></option>
					<% 	} %>     
					</select>
			</td>
		</tr>
		<tr>
			<td><input type="submit" value="Report"> </td>
		</tr>
		</table>

<%
}catch(Exception e ) {}
%>
</form>
</body>
</html>