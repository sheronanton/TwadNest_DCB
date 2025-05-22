<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="Servlets.PMS.PMS1.DCB.servlets.*,java.util.*"%>
<%@page import="java.sql.*"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<% 

	Controller obj=new Controller();
	ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	String ConnectionString = "";
	String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
	String strdsn = rs1.getString("Config.DSN");
	String strhostname = rs1.getString("Config.HOST_NAME");
	String strportno = rs1.getString("Config.PORT_NUMBER");
	String strsid = rs1.getString("Config.SID");
	String strdbusername = rs1.getString("Config.USER_NAME");
	String strdbpassword = rs1.getString("Config.PASSWORD");
//	ConnectionString = strdsn.trim()+"@"+strhostname.trim()+":"+strportno.trim()+":"+strsid.trim();
 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
	ConnectionPool c_p=new ConnectionPool(strDriver,ConnectionString,strdbusername,strdbpassword,0,500,true);
	java.sql.Connection con=c_p.getConnection();		
	  
	 
	Statement ps=con.createStatement();
	
	  

%>

</body>
</html>