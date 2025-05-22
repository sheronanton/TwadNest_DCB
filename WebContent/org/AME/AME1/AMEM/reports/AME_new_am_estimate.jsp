<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.ResourceBundle"  %>
<%@page import="java.util.Calendar"  %>  
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>

<html>
<head>
<title>AME - Monitoring </title>
<script type="text/javascript">  
		function rld_new()
		{   
			 
			var month=document.getElementById("fmonth").value;
			var fin_year=document.getElementById("pyear").value;
			if (fin_year==0) {
				alert("Select Financial year"); }  
			else
				{
		//	var rid=document.getElementById("reg_id").value;
			var pr=201;
			var qry="../../../../../ame_report?fin_year="+fin_year+"&pmonth="+month+"&process_code="+pr;
		 	 
 			 window.open(qry);
				}      
		}
		
</script>
</head>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<body>
<%		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj = new Controller();
		Connection con=obj.con();
		obj.createStatement(con);
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		
		String reg_id=obj.setValue("reg_id",request);
		String reg=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID","reg_id",reg_id,"");
		  int year_incr=0;
		  if (month <4)
			  year_incr=0;    
		  else
			  year_incr=1; 
%>  
<form>
<table align="center" width="50%" border="1" style="border-collapse: collapse;border-color: skyblue" cellpadding="10">
		<tr>
			<th align="center" colspan="2">
					AME - Monitoring
			</th>
		</tr>
		 <tr>
      		<td><label class="fnt"> Financial Year</label> </td>
	          <Td colspan="2">
	          <!-- <select class="select"  id="pyear" name="pyear"  style="width:120pt;  " onchange='transaction(1,5),transaction(1,3)'> -->
	          <select class="select"  id="pyear" name="pyear"  style="width:120pt;  ">
			  <option value="0">Select</option>
			  <%
			  for (int i=2010;i<year+1;i++)
			  {
			   %>
			  <option value="<%=i%>"><%=i%>-<%=i+1%></option> 
			  <%} %>
			  </select> 
			  </tD>
			  </tr>
				<tr>    
					<td class="tdText">Month</td><td> 
					<select class="select"  id="fmonth" name="fmonth">
					<option value="0">Select</option>
			  		<%
			  		for (int j=1;j<=12;j++)
			  		{
			  			if (j==month) {
			   		%><option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
			   		 <% } else {%>
			   			<option value="<%=j%>"><%=monthArr[j]%></option>
			   		<% }
			  		}  %>  
			 </select> 
			</td>
			</tr>
			
			<tr>
				<th colspan="2" align="center">
					<input type="button" value="View"  class="btn" onclick="rld_new()"/>
					<input type="button" value="Exit"  class="btn" onclick="window.close()"/>
					<a href="../jsps/AME_Monitoring.jsp">HTML Report</a>  
				</th>	
			</tr>
</table>
</form>
</body>
</html>