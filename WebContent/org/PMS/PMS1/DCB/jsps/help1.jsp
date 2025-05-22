<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 800,700 )
			}
			
			</script>
			
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD NEST || NIC HELP</title>
<style type="text/css"> 
ol li
{
	margin-left: 58px;
	padding-left: 20px;
}

UL.check
 {
  list-style-image: url(/LI-markers/checkmark.gif) 
  }


</style>
</head>
<body onload="page_size()">
 <%
 Connection con;
 Controller obj=new Controller();
 try
 {
 	con=obj.con();
 	obj.createStatement(con);
 	String fsno=obj.setValue("fn",request);
 	
 	ResultSet rs=obj.getRS("select * from PMS_DCB_HELP_MAIN where sno="+fsno+" order by  SNO ");
 	%>
 	<table border="1" width="100%" align="center"  cellspacing="0" cellpadding="1" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
 	<%  
 	if (rs.next())
 	{  
 		   
 		String Desc=rs.getString("FORMNAME");
 		
 	
 		 
 		%>
 		<Tr>
 			<td colspan="2" align="center"><a name="<%=fsno%>"></a><%=Desc%></td>
 		</Tr>
 		<tr>
 				<td width="10%"  align="center"><img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></td>
 		 		<td  width="90%">
 		 		<table border="0" width="100%"  cellspacing="0" cellpadding="10" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">  
 					<%
 						try
 						{
 							PreparedStatement ps=con.prepareStatement("select * from PMS_DCB_HELP where FORMSNO="+fsno + " order by ORDERSNO" );
 							ResultSet rs1=ps.executeQuery();
		 					while (rs1.next())
		 	 				{
		 						String Desc1=rs1.getString("HELPTEXT");
		 				%>
		 	 			<Tr> 
		 	 					<td  align="left"><%=Desc1%></td>
		 	 				</Tr>
		 	 			<%
							}
 							rs1.close();
 						%>
 						<%
 							
 						%>
 						</table>
 						<%	
 		}catch(Exception es) { out.println(es);} 
 		
 	}%> 
 	</td></tr>
 	<tr>
					<td colspan="2" align="center"> 
						<input type="Button" value="Exit" onclick="javascript:window.close()">	
					</td>
				</tr>
 <%}catch(Exception es) { out.println(es);}
 	%>
 	</table>	
 
</body>
</html>