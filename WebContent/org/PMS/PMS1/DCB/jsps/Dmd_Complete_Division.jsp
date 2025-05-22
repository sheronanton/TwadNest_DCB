<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Iterator"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@page import="java.sql.*" %> 
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
		Controller obj=new Controller();		 
		Connection con=null;
		String userid="",Office_id="";
		try
		{
			con=obj.con();
			obj.createStatement(con);
			userid=(String)session.getAttribute("UserId");
			
			if(userid==null)
			{
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
				Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				if (Office_id.equals("")) Office_id="0";
				ArrayList sr=obj.Records1("select BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where status='L' and  OFFICE_ID="+Office_id,con); 
			 	Iterator er=sr.iterator();
			
			while (er.hasNext())
			{
				
				String s=(String)er.next();
				out.println(s+"<Br>");
			}
			 
			
		}catch(Exception e) {
			out.println(e);
			
		}
		con.close();
		 
%>
</body>
</html>
 