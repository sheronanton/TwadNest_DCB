<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <%
 
 try
 { 
 	Controller obj=new Controller();
 	 
 	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

	String con_str = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=e:\\db_mas.MDB";

	Connection	con = DriverManager.getConnection(con_str);
 	 DatabaseMetaData mtdt = con.getMetaData();
 	 ResultSet rs = mtdt.getTables(con.getCatalog(), "%", "%", null);
 	 ResultSetMetaData rsmd = rs.getMetaData();
 	 int numCols = rsmd.getColumnCount();
 	 String combo="<select>";
 	 while (rs.next())
 	 {
 		
        
 	      combo+="<option>"+rs.getString("TABLE_NAME")+"</option>";
 	   }
 	combo+="<select>";
 %>
 <%=combo%>
 
   
 <% }catch(Exception e ) {
	 out.println(e);
	 
 } %>
</body>
</html>