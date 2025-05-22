<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand,Collection and Balance Statement</title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<form action="MD_abstract_2.jsp" method="post"> 
<%
String  Office_name="",userid="",Office_id=""  ;
Controller obj=new Controller();
Connection con=null;
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) + 1;
int year = cal.get(Calendar.YEAR);
try
{
	con=obj.con();
	con=obj.con();   
	obj.createStatement(con);
	userid=(String)session.getAttribute("UserId");
	if(userid==null)  
	{
	  response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
	String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	MD_Abstract obj_abs=new MD_Abstract();
	 
%>
	<div>
	<table border=1 width="50%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
	<tr>
		<td>
			Sl.No
		</td>
		<td>
			Beneficiary Type
		</td>
		<td>
			Opening Balance
		</td>
		<td>
			Demand Current month
		</td>		
		<td>
			Collection
		</td>
		<td>
			Balance
		</td>
	</tr>				 
  		 <% 
  			/*Iterator as=ar.iterator();
  		     int i=0;
  		     int j=0;
  			while (as.hasNext() )  
			{
  				i++; 
  				
  				if (i%5==1)
  				{  
  					j++;
  					out.println("<tr>");
  					out.println("<td align='center'>"+(j)+"</td>");  
  				}
  				String ds=as.next().toString();  				
  				out.println(ds);
  				if (i%5==0)
  				out.println("</tr>");
			}*/
  		 %>
  		 
			
	</table>
	<div>
<%
}catch(Exception e)
{
	

}
%>
</form>
</body>
</html>