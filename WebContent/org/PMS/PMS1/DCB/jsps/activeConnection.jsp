<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<Table width="75%" align="center">
<tr>
	<td>Sl.No</td>
	<td>User ID </td>
</tr>
		
<%
	Controller obj=new  Controller();
	String qry="select * from sec_mst_users_login_history where to_char(logged_in_time,'dd/mm/yyyy')=to_char(clock_timestamp(),'dd/mm/yyyy') and logged_out_time is null";
	out.println(qry);
	Connection con=obj.con();  
	obj.createStatement(con);
	int r=0;
	ResultSet rs =obj.getRS(qry);
	while (rs.next())
	{%>
	<tr>
		<td><%=++r%></td>			
		<td><%=rs.getString("User_ID")%></td>
	</tr>	 
	<%  
	} 
	for (Enumeration e = session.getAttributeNames(); e.hasMoreElements(); )
	{     
	    String attribName = (String) e.nextElement();
	    Object attribValue = session.getAttribute(attribName);
	
	%><%= attribName %> - <%= attribValue %>
	<% } %>
	</Table>  
</body>
</html>