<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*,Servlets.AME.AME1.AMEM.servlets.Controller,Servlets.AME.AME1.AMEM.Bean.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.PreparedStatement"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Monthly Scheme Expenditure Summary</title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
 
</head>
<body>
 

<form name="Performance">
<% 
		HttpSession session1 = request.getSession(false);
		String userid = (String) session1.getAttribute("UserId");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int day = cal.get(java.util.Calendar.DATE);
		int month = cal.get(java.util.Calendar.MONTH) + 1;
		int year = cal.get(java.util.Calendar.YEAR);
		String html="",datahtml="";	
		Controller obj = new Controller();
		Controller obj_data = new Controller();
		
		 
		String pyear=obj.setValue("pyear",request);
 		
		Connection con=obj.con();
		DecimalFormat df=new DecimalFormat("0.00");
		
		obj.createStatement(con);
		obj_data.createStatement(con);
		if (userid == null) {
		
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		
		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		String flag="R";
		try
		{
		if (Office_id.equals(""))
			Office_id = "0";
		String Office_Name ="";
		
		 
			Office_id=obj.setValue("office_id",request);
			Office_Name+= obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + obj.setValue("region_office_id",request) + "  ");
			Office_Name+="->"+obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + obj.setValue("circle_office_id",request) + "  ")+"-->";
			Office_Name+=obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + obj.setValue("office_id",request) + "  ");
		 
		
		  flag=obj.setValue("flag",request);
		int row=0;
		%>
		<div id='dataid'>
			<table border="1" align="center" class="table" width="50%">
			<tr>
				<td align="center" colspan="3">
					<center><pre>Tamil Nadu Water Supply and Drainage Board <br>Head Office, Chennai
					</pre></center>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="3"> Budget Estimate - <%=pyear%></td>
			</tr>			
			<tr>
					<td align="left" colspan="3"><%=Office_Name%></td>							 
			</tr>
			<tr>
				<td align="center" >Sl.No</td>
				<td align="center" width='20%'>Scheme Name</td>
				<td align="center" >Budget Estimate Amount(Rs.
		in lakhs) </td>
			</tr>
			<%
			double total_=0.0f,amt=0.0;
			String query=" select distinct sch_sno, sch_name from pms_dcb_reg_cir_div_sch where office_id="+obj.setValue("office_id",request)+" order by sch_sno ";
			ResultSet rs=obj.getRS(query);
			while(rs.next())
			{ 
				String sch_sno=rs.getString("sch_sno");
				StringBuffer qry=new StringBuffer();	 
				out.println("<tr><td align='center' width='5%'>"+(++row)+"</td><td align='left'  width='45%'>"+obj.isNull(rs.getString("sch_name"),1)+"</td>");
				
				
				qry.append(" select budget_est_amt as bdamt from pms_ame_trn_budget where office_id="+obj.setValue("office_id",request)+" and fin_year='"+pyear+"' and sch_sno="+sch_sno);							 							 
				PreparedStatement ps_obj=con.prepareStatement(qry.toString());
				ResultSet rs_new=ps_obj.executeQuery();
				if(rs_new.next()) 
				{
					amt=Double.parseDouble(obj.isNull(rs_new.getString("bdamt"),1));
					total_+=amt;
					out.println("<td align='right' width='45%'>"+df.format(amt)+"</td>");
				}
				else
				{
					out.println("<td align='right' width='45%'>0.00</td>");
				}
				out.println("</tr>");
				rs_new=null;
				ps_obj=null;
				
			 } 
			 %>
			  <tr bgcolor="lightgray">   
			 	<td colspan="2" align="right"><b>Total</b></td><td align="right">  
			 	<b><%=df.format(total_)%></b></td>
			 	</tr>  
			 <tr>  
			 	<td colspan="3" align="center">  
			 	<div class="img"><a href="javascript:document.title = 'Budget Estimate'; window.print();" >Print	</a></div>
			 	</td>
			 </tr>
		 </table>
		 </div>
		<%
	 
		
		}catch(Exception e)
		{
			out.println(e);
			
		} 
		
	%>
	 
	 
</form>
</body>
</html>