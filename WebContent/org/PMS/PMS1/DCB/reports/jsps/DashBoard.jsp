<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.ResourceBundle"  %>
 <%@page import="java.util.Calendar"  %>  
 <%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>DCB Report </title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script> 
<script type="text/javascript">
		function rld_new()
		{  
			 
			var month=document.getElementById("fmonth").value;
			var fin_year=document.getElementById("pyear").value;
		 
			if (fin_year==0)
			{
				alert("Select Financial Year"); 
			}
			else
			{
		 		var qry="../../../../../../ame_report?fin_year="+fin_year+"&pmonth="+month+"&process_code=21";		 	 
 				window.open(qry);
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
		if (month <4)
			  year_incr=0;    
		  else
			  year_incr=1;    
		int prvmonth=obj.prv_month(year,month);
		int prvfinyear=obj.prvfinyear(year,month);
		obj=null;
%>
<table align="center" width="50%" border="1" style="border-collapse: collapse;border-color: skyblue" cellpadding="10">
		<tr>
			<td align="center" colspan="2">
					DCB Report
			</td>
		</tr>
		 <tr>
      		<td><label class="fnt"> Financial Year</label> </td>
	          <Td colspan="2">
	          <!-- <select class="select"  id="pyear" name="pyear"  style="width:120pt;  " onchange='transaction(1,5),transaction(1,3)'> -->
	          <select class="select"  id="pyear" name="pyear"  style="width:120pt;"   style="width: 110pt;">
			  <option value="0">-Select Year-</option>
			  <%
			  for (int j=year-5;j<=year+year_incr;j++)  
			  {
				  if ((j-1)==prvfinyear) 
				  {
			  	  %>
			  		<option value="<%=j-1%>" selected="selected"><%=j-1%>-<%=j%></option>
			  	  <%}
				  else
				  {
					  %>
				  		<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
				  	  <%    
				  }
			  } %>
			  </select> 
			  </tD>
			  </tr>
				<tr>  
					<td class="tdText">Month</td><td> 
					<select class="select"  id="fmonth" name="fmonth"   style="width: 110pt;">
					<option value="0">-Select Month-</option>
			  		<%
			  		for (int j=1;j<=12;j++)
			  		{	
			  		 if (prvmonth==j) {
			   		 %>
			  	  	<option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
			   		 <%}else { %>
			   		 <option value="<%=j%>"><%=monthArr[j]%></option>
			   		 <%
			   		 	}
			  		 } %>			    
			 </select> 
			</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="View"  class="btn" onclick="rld_new()"/>
					<input type="button" value="Exit"  class="btn" onclick="window.close()"/>
				</td>	
			</tr>
</table>
</body>
</html>