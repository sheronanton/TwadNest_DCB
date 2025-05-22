<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="Servlets.AME.AME1.AMEM.servlets.*,java.util.*,java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
try
{
	Calendar cal = Calendar.getInstance();
	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
	String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Connection con=obj.con();
	obj.createStatement(con);
	Statement sd=con.createStatement();
	ResultSet rs=sd.executeQuery("select * from PMS_DCB_LEDGER_ACTUAL where month=4 and year=2015");
	ResultSetMetaData rsd=rs.getMetaData();
	
}catch(Exception e)
{
	
}

%>
</body>
</html>