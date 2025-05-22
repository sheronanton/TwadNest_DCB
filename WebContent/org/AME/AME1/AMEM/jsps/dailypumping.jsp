<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.Date,java.util.Calendar,java.text.DateFormat,java.text.SimpleDateFormat"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD-NEST : DAILY PUMPING</title>
</head>
<body>
 <%
 		try
 		{
 			Controller obj=new Controller(); 			
 			Connection con=obj.con();
 			String	userid = (String) session.getAttribute("UserId");
 			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
 			String YEAR=obj.getValue("PMS_DCB_SETTING","YEAR","where office_id="+Office_id);
 			String MONTH=obj.getValue("PMS_DCB_SETTING","MONTH","where office_id="+Office_id);
 			int startdate= Integer.parseInt(obj.getValue("PMS_DCB_SETTING","STARTDATE","where office_id="+Office_id));
 			int enddate=Integer.parseInt(obj.getValue("PMS_DCB_SETTING","ENDDATE","where office_id="+Office_id));
 			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
 			Date d1=null;
 			Date d2=null;
 			String s1="";
 			String s2="";
 			int days=0;
 			try { 
 				d1= df.parse(startdate+"/"+(Integer.parseInt(MONTH)-1)+"/"+(Integer.parseInt(YEAR)));
 				d2=df.parse(enddate+"/"+(Integer.parseInt(MONTH))+"/"+(Integer.parseInt(YEAR)));
 				s1 = df.format(df.parse(startdate+"/"+(Integer.parseInt(MONTH)-1)+"/"+(Integer.parseInt(YEAR))));
 				s2 = df.format(df.parse(enddate+"/"+(Integer.parseInt(MONTH))+"/"+(Integer.parseInt(YEAR))));
 				days=obj.daysBetween(d1,d2);
 				} catch (Exception e) 
		       {
 				     e.printStackTrace();
 			   }
 			Calendar calendar = Calendar.getInstance();
 			int weekday = calendar.get(Calendar.DAY_OF_WEEK);
 			int day = calendar.get(Calendar.DAY_OF_MONTH);
 			    String dayofmonth=obj.month_val2(Integer.parseInt(MONTH));
 			    String set="";
 			%>
 			<table align="center" width="50%" border="1">
 				<tr>
 					<td colspan="2" align="center"> Daily Pumping Entry</td>
 				</tr>
 				<tr>
 					<td width="50%">Day of Month </td> <td>Qty</td>
 				</tr>
 				<%
 				int j=0;
 					for (int i=startdate;i<(days+enddate);i++)
 					{   
 						
 						if (i > Integer.parseInt(dayofmonth))
 						{
 							  
 						j=(enddate+1)-((days+enddate)-i);
 						}
 						else
 						{
 							j=i;
 						}
 						if (day==j)
 							set="";
 							else	
 			 			   	set="readonly='readonly'";
 				%>
 				<tr>
 					<td><%=j%></td> <td><input type="text"  <%=set%>></td>
 				</tr>
 				<% } %>
 			</table>
 			<%
 		}catch(Exception e) {}
 %>
</body>
</html>