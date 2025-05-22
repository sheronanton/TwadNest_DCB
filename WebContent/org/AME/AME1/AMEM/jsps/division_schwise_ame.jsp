<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Calendar" %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>

<%@page import="java.sql.Connection"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
 
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/AME Entry Status Monitoring.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
<title>AME Entry Status Monitoring - Scheme Wise AME</title>
</head>
<body>
			<form>
			<%
			try 
			{  
			String office_id=request.getParameter("office_id");
			String fmonth=request.getParameter("pmonth");
			String pyear=request.getParameter("fin_year");
			Controller obj=new Controller();
			Connection con=obj.con();
			obj.createStatement(con);
			
			%>
			<table id="table1"  border="0" align="center"   cellspacing="1" cellpadding="1" width="60%">
				<tr>
					<th colspan=9>List of Maintenance Schemes with Estimate for the Financial Year  - &nbsp;<%=pyear%> </th> 
				</tr>
				<tr>
					<th colspan=9><%=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+office_id+ "  ")%> </th> 
				</tr>
				<tbody id="tbody1" align="center" valign="top"></tbody>
				<tr>
      				<th colspan="9" align="center" ><input type="button" value="Exit" onclick="javascript:window.close()"/></th>
      			</tr> 		 			 
			</table> 
			</form>			 
			 <input type="hidden" id='reg_id' value="<%=office_id%>"/>
			 <input type="hidden" id='fmonth' value="<%=fmonth%>"/>
			 <input type="hidden" id='pyear' value="<%=pyear%>"/>
			 <input type="hidden" id="pr_status" name="pr_status" value="1"/>
			 <script type="text/javascript">
			 		filter(1,3,<%=office_id%>)   
			 </script>
			 <%
			}
			catch(Exception e)
			{
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			%>
</body>
</html>