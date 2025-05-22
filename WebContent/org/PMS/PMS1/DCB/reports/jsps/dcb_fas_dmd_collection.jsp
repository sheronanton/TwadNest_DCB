<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@page import="java.util.Calendar"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>DCB and FAS  Checklist</title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function report(a)
{
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value;
	var rtype=document.getElementById("rtype").value;
	var res=month_year_check(month,year);
	if (a==1)
	{  
		  
		var src="../../../../../../Water_Charges_Report?option="+rtype+"&month="+month+"&year="+year+"&command=dcb_fas_dmd_collection";
		window.open(src);
	}  
	else
	{
		if (res!=1)
		{
			 //document.getElementById("rtype").value;
			var src="../../../../../../dcb_statement_new?option="+rtype+"&fmonth="+month+"&fyear="+year+"&process_code=21";
			window.open(src);
		}  
	}
}
		
</script>
</head>
<body>
<%		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		int year_incr=0;
		if (month <4)
			  year_incr=0;    
		  else
			  year_incr=1;    
		int prv_month=0;
		  if (month==1) prv_month=12;
		  else
		  prv_month=month-1;   
		  int prv_year=year;
		  if (prv_month==12)  prv_year=year-1; 
%>
<table align="center" width="50%" border="1"
	style="border-collapse: collapse; border-color: skyblue"
	cellpadding="10">
	<tr >
		<td align="center" colspan="2">DCB and FAS  Checklist</td>
	</tr>
	 <tr><td>&nbsp;Year</td>
	            <td><select id="year" name="year"  tabindex="5" onchange="report_period_verify(document.getElementById('month'),this)">
                <option value="0" selected="selected">Select</option>
                <%
                for (int j=year-2;j<=year;j++)
                {
                	if (j==prv_year) 
    		   		{
    		   		%>
    		   		<option value="<%=j%>" selected="selected"><%=j%></option>
    		  		<% } else {	%> <option value="<%=j%>"><%=j%></option><%}
                 } %>  
    </select> </td>
		</tr>
		 <tr> 
			<td width="20%"  align="left">&nbsp;Month</td>
			<td>
			 <select name="month" id="month" onchange="report_period_verify(this,document.getElementById('year'))">
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++) {
	         		  if (i==prv_month) { 
					   		%>  <option value="<%=i%>" selected="selected"><%=amonth[i]%></option><% }  else { %>
						    	<option value="<%=i%>"><%=amonth[i]%></option> <% }
	         	%>
	         		 
	         	<%} %>
	          </select>
	         </td>
	       </tr>
		  <tr>  
	<tr>
		<td colspan="2" align="left"><select id="rtype" name="rtype"
			class='select'>
			<option value="0">Select</option>
			<option value="1" selected="selected">PDF</option>
			<option value="2">Excel</option>
			<option value="3">HTML</option>
		</select></td>
	</tr>

	<tr>
		<td colspan="2" align="center"><input type=Button value="View" onclick="report(1)">
		<input type="button" value="Exit" class="btn" onclick="window.close()" />
		</td>
	</tr>
</table>
</body>
</html>