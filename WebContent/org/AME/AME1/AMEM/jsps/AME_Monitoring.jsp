<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Calendar" %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/AME Entry Status Monitoring.js"></script>
<script type="text/javascript">
		function process(a,b,c)
		{
			window.open("division_schwise_ame_DV.jsp?office_id="+a+"&pmonth="+b+"&fin_year="+c, "_blank", "toolbar=yes, scrollbars=yes, resizable=yes, top=500, left=500, width=400, height=400");
			
		}
</script> 
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
<title>AME Entry Status Monitoring</title>
</head>
<%
	try 
	{  
		HttpSession session1 = request.getSession(false);
		String	userid = (String) session1.getAttribute("UserId");
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj=new Controller();
		if (userid == null) {

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String sch_sno=obj.setValue("sch_sno",request);
		int year_incr=0;
		if (month <4)
		  year_incr=0;    
		else 
		  year_incr=1;
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

%>
  
<body onload="flash()">  
			<table align="center" border="0" align="center" width="95%"  cellspacing="1" cellpadding="1" >
			<tr>
      			<th colspan="3" align="center" ><label class="fnt">AME Entry Status Monitoring</label><label id='msg'></label> </th>
      		</tr> 
			<tr>
      		<td><label class="fnt"> Financial Year</label> </td>
	        <Td colspan="2">	        
	          		<select class="select"  id="pyear" name="pyear"  style="width:120pt;" >
			  		<option value="0">Select</option>
			  		<%
			  			for (int i=2014;i<year+1;i++)
			  			{
			   		%>
			  		<option value=<%=i%>-<%=i+1%>><%=i%>-<%=i+1%></option> 
			  		<%  } %>
			  		</select> 
			  </td>
			  </tr>
				<tr>    
					<td class="tdText">Month</td><td> 
					<select class="select"  id="fmonth" name="fmonth" onchange="filter(1,1,0)">
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
			 </select> &nbsp; <input type="button" value="Go" onclick="filter(1,1,0)">
			</td>
			</tr>
			
			<tr>
			<td colspan=3>
				
				<table id="table1"  border="1" align="center" width="100%"  cellspacing="1" cellpadding="1" style="border-color: black">
				<Tr  >
					<Td colspan="2" id='td1'>&nbsp;</Td>
					<Td colspan="7" align="center" id='td2'>&nbsp;</Td>
					<Td colspan="4" align="center" id='td3'>&nbsp;</Td>
				</Tr>
			 	<tbody id="tbody1" align="center" valign="top"/>		 			 
				</table>  
			</td>
			</tr>
			<tr><td colspan=3>
				<table id="table2"  border="1" align="center" width="100%"  cellspacing="1" cellpadding="1">
				<tr>
					<td id='td1_new' colspan=13>&nbsp; </td> 
				</tr>
					<tbody id="tbody2" align="center" valign="top"/>		 			 
				</table>
				</td>
			</tr>
			<tr>
      			<th colspan="3" align="center" ><input type="button" value="Exit" onclick="javascript:window.close()"/></th>
      		</tr> 
			</table> 
	<%
	}
	catch(Exception e)
	{
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	%>
	<input type="hidden" id="pr_status" name="pr_status" value="1"/>
	<a href="../reports/ame_new_reports.jsp"></a>
</body>
</html>