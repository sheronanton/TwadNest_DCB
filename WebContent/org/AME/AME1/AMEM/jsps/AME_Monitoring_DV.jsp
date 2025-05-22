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
		function process(a)
		{
			var fin_year=document.getElementById("pyear").value;
			var pmonth=document.getElementById("fmonth").value;
			window.open("division_schwise_ame_DV.jsp?office_id="+a+"&pmonth="+pmonth+"&fin_year="+fin_year, "_blank", "toolbar=yes, scrollbars=yes, resizable=yes, top=500, left=500, width=400, height=400");
			
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
		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
				"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
						+ userid + "')");
%>
  
<body>  
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
      			<th colspan="3" align="center" >&nbsp; <input type="button" value="View" onclick="process(<%=Office_id%>)"><input type="button" value="Exit" onclick="javascript:window.close()"/></th>
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