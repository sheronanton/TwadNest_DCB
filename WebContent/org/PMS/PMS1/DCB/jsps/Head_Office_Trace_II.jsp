<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="dcb.reports.Dcb_Collection_Difference"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB Data Freeze</title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
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
	try
	{
	  con=obj.con();
	  obj.createStatement(con);
	  userid=(String)session.getAttribute("UserId");			
	  if(userid==null)
	  {
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
	  }
	}catch(Exception e) {}
	String pmonth=obj.setValue("month",request);
	String pyear=obj.setValue("year",request);
	String sub=obj.setValue("store",request);
	
	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
	String acc_unit_id=obj.getValue("PMS_DCB_COM_OFFICE","ACCOUNTING_UNIT_ID"," where office_id="+Office_id);
	if (Office_id.equals("")) Office_id="0";
		 
	Dcb_Collection_Difference obj_diff=new Dcb_Collection_Difference();
	
	obj_diff.setProcessmonth(Integer.parseInt(pmonth));
	obj_diff.setProcessyear(Integer.parseInt(pyear));
	obj_diff.setList();
	ArrayList<Dcb_Collection_Difference> obj_value=obj_diff.getList(); 
	
	
	
%>
<form action="Head_Office_Trace.jsp" method="get">


<table  class="fnt"  align="center" border=1 width="50%"  bordercolor="#00FFFF"  >
	<tr> 
		<td colspan="2" align="center">  
			DCB Data Freeze
		</td>
		</tr> 
    <Tr>
    <td>
	<%
	out.println("<table border='1' width='100%'>");
	out.println("<tr><td>Sl.No</td><td>Office  Name </td><td align='center'>Diffenernce Amount</td></tr>");
	Iterator er=obj_value.iterator();
	int row=0;
	while (er.hasNext())
	{
		
		Dcb_Collection_Difference ds=(Dcb_Collection_Difference)er.next();
		try
		{
			out.println("<tr><td>"+(++row)+"</td><td>"+ds.getOfficename()+"</td><td align='right'>"+ds.getCollection()+"</td></tr>");
		}catch(Exception e) 
		{
			out.println(e); 
		}
	}
	out.println("</table>");
	%>
</td>
</Tr>
</table>
</form>
</body>
</html>