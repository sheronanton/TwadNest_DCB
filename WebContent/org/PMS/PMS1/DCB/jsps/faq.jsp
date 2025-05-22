<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD :: DCB FAQ</title>
</head>
<body>
 
 <%
 	Controller obj=new Controller();
 	Connection con=obj.con();
 	obj.createStatement(con);
 	ResultSet rs=obj.getRS("select * from pms_dcb_faq order by display_order");
 	
 	       
 %>   
 <table width="75%" align="center" border=0 bgcolor="#99cccc" cellpadding="0" cellspacing="0">  
 	<tr> 	<td valign="top"><img src="../../../../../images/dcbbutton.jpg" height="35"></td>
 			<td width="30%" align="right">&nbsp;&nbsp;&nbsp;<img border="0" src="../../../../../images/DCBfaq.jpg" height="55"/><br>Frequently Asked Questions </td>
 	</tr>    
   
 </table>    
  	<table width="75%" align="center" border=0 bgcolor="#99cccc" cellpadding="0" cellspacing="0">  
 	<tr><td>&nbsp;</td></tr>
 	<tr>  
 	<td width="100%">
 						<table bgcolor="#99cccc"   id="entred_data" align=left width="100%" border=0 cellspacing=0 cellpadding=5>
						<%  
							int i=0;      
							while (rs.next())
							{
								i++;  
						%>				
						
						 	<%
						 		out.println("<tr><td width='5%'>"+i+".</td><td><span style='font-family:&quot;Arial&quot;,&quot;sans-serif&quot;'>"+rs.getString("QUESTION")+"</span></td></tr>");
						 		out.println("<tr  bgcolor='white'><td width='5%'>&nbsp;</td><td> <font face='Korinna BT' >"+rs.getString("ANSWER")+"</td></tr>");
						 	%>
						 
						<%} %>
	         			</table>
				   
  	</td> 
  	</tr>
  	<tr>
  			<td align="center"><img  width="80" height="50"  src="../../../../../images/exit_new.jpg"  onclick="window.close()"  ></td>
  	</tr>
  	</table>
 
 
</body>
</html>