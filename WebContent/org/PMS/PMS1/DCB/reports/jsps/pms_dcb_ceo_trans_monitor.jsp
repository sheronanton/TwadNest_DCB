<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Calendar"  %> 
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>DIVISION DCB TRANASACTION  </title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<link href='../../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
		function rld_new()
		{  
			 
			var month=document.getElementById("fmonth").value;
			var year=document.getElementById("pyear").value;
			var res=month_year_check(month,year);	 
			 
			if (res!=1)
			{	
				var qry="../../../../../../Bill_Demand?fyear="+year+"&fmonth="+month+"&command=record_pdf&process_code=4";		 	 
 				window.open(qry);
			}  else
			{
				alert("select month and year.....")
			}    
		}
		
</script>
</head>
<body>
<%		Calendar cal = Calendar.getInstance();
		Controller obj=new Controller();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		int year_incr=0;
		if (month < 3)
			  year_incr=0;    
		  else
		  year_incr=1;
		int prvmonth=obj.prv_month(year,month);
		int prvyear=obj.prv_year(year,month);
		obj=null;
%>
<table border="1" width="62%" cellpadding="10" cellspacing="0" style="border-collapse: collapse;"  align="center">
		<tr  bgcolor="#3080A0">

			<td align="center" colspan="2">
					<b><font color=white>Division DCB Transaction Statistics</font></b>   
			</td>
		</tr>
		 <tr>
      		<td><label class="fnt">   Year</label> </td>
	          <Td colspan="2">
	          <!-- <select class="select"  id="pyear" name="pyear"  style="width:120pt;  " onchange='transaction(1,5),transaction(1,3)'> -->
	          <select class="select"  id="pyear" name="pyear"  style="width:120pt;"   style="width: 110pt;" onchange="report_period_verify(document.getElementById('fmonth'),this)" >
			  <option value="0">-Select Year-</option>
			  <%
			  for (int j=year-1;j<=year;j++)  
			  {
				  System.out.println(prvyear);
			   %>
				<option value="<%=j%>" <% if (j==prvyear) {%>selected<%} %>><%=j%></option>
			  <%} %>
			  </select> 
			  </tD>
			  </tr>
				<tr>  
					<td class="tdText"><label class="fnt">Month</label></td><td> 
					<select class="select"  id="fmonth" name="fmonth"   style="width: 110pt;" onchange="report_period_verify(this,document.getElementById('pyear'))">
					<option value="0">-Select Month-</option>
			  		<%
			  		for (int j=1;j<=12;j++)
			  		{	  
			  			if (prvmonth==j)
			  			{%><option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
						<%}else { %>
						<option value="<%=j%>"><%=monthArr[j]%></option>
						<%	}  		 } %>			    
			 </select> 
			</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="View"  class="btn" onclick="rld_new()"/>
						<input type="button"  style="color: red;font-weight: bold"  value="Close" onclick="javascript:window.close()">
				</td>	
			</tr>
</table>
</body>
</html>