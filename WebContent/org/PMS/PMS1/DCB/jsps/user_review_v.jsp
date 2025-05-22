<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.*,Servlets.Security.classes.UserProfile" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Reviews</title>
  <link href="../../../../../css/common.css" rel="stylesheet" media="screen"/>
</head>
<body>

<%
	 
	String userid="0",Office_id="",Office_Name="";
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Connection con=null;
	java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
	String pmonth="0",pyear="0";
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
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");

%>
<form action="user_review_a.jsp" method="post" >
  
 
 		<table align="center" width="74%" border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse">
 		<tr>
 			<td style="color: green"><b>Comments</b></td>
 			<td align="right" style="color: red"><b><a href='javascript:window.close()'>Exit</b></td>
 		</tr>
 		</table><br>	
		<center> <div id='container'>
		 <%	
 		String html ="";    
 		UserProfile up = null; 
		up = (UserProfile) session.getAttribute("UserProfile");
 		 String msg="",sdate="",CLIENTIP="";
 		ResultSet rs=obj.getRS("select office_id,contact_name,useful_message,suggestion,issue,to_char(updated_date,'dd/MM/yyyy') as udate,UPDATED_DATE,clientip from PMS_DCB_HELP_REPORT order by UPDATED_DATE desc");
 		html="";
 		while(rs.next())
 		{
 		  msg=obj.isNull(rs.getString("ISSUE"),1);
 		  if (msg.equals("0")) 		  
 		  msg=obj.isNull(rs.getString("USEFUL_MESSAGE"),1);
 		  
 		  if (msg.equals("0")) 		  
 		  msg=obj.isNull(rs.getString("SUGGESTION"),1);
 		  
 		  sdate=rs.getString("udate");
 		 CLIENTIP=rs.getString("clientip");
 		// out.println(CLIENTIP);
 		 if(CLIENTIP==CLIENTIP){
 			html+="<p id='rcorners6'> posted by : &nbsp;"+obj.getValue("com_mst_all_offices_view","office_name", " where office_id="+rs.getString("OFFICE_ID"))+"&nbsp#"+CLIENTIP+" on "+sdate+" </p> ";
		  html+="<p id='rcorners5'> &nbsp;"+msg+"</p>";}
 		
 		//else
 		 
 		//  html+="<p id='rcorners6'> posted by : &nbsp;"+obj.getValue("com_mst_all_offices_view","office_name", " where office_id="+rs.getString("OFFICE_ID"))+" on "+sdate+" </p> ";
 		//  html+="<p id='rcorners5'> &nbsp;"+msg+"</p>";
 		
 		}
 		out.println(html+"");   
 		con.close();
  %>   
  
 </div></center>
</form>

<%
	}catch(Exception e)
	{
		out.println(e);  
	}

 %>

</body>
</html>