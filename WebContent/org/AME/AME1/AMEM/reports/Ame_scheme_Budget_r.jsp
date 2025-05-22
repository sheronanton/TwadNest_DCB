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
<script type="text/javascript">

	function reload(region_office_id, pyear)
	{
		 window.open("Ame_scheme_Budget_c.jsp?flag=C&region_office_id="+region_office_id+"&pyear="+pyear);
	  
	}

</script> 
</head>
<body>
 

<form name="Performance">
<% 
		HttpSession session1 = request.getSession(false);
		String userid = (String) session1.getAttribute("UserId");		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		
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
		String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");
		
		  flag=obj.setValue("flag",request);
 
		 
		int row=0;
		%>
		<div id='dataid'>
			<table border="1" align="center" >
			<tr>
				<td align="center" colspan="3">
				<center><pre>Tamil Nadu Water Supply and Drainage Board <br>Head Office, Chennai
					</pre></center>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="3"> Budget Estimate  - <%=pyear%></td>
			</tr>			
			<tr>
				<td align="center"  colspan="3"> </td>
			</tr>
			<tr>
				<td align="center" >Sl.No</td>
				<td align="center" width='20%'>Region Name</td>
				<td align="center" >Budget Estimate Amount(Rs.
		in lakhs)</td>
			</tr>
			<%
			double total_=0.0f,amt=0.0;
			String query=" select distinct region_office_id as region_office_id,region_name from pms_dcb_reg_cir_div_sch order by region_office_id";
			ResultSet rs=obj.getRS(query);
			while(rs.next())
			{
				String reg_id=rs.getString("region_office_id");
				StringBuffer qry=new StringBuffer();	 
				out.println("<tr><td align='center' width='5%'>"+(++row)+"</td><td align='left'  width='45%'><a href=javascript:reload("+reg_id+",'"+pyear+"')>"+obj.isNull(rs.getString("region_name"),1)+"</td>");
				
				
				qry.append(" select sum(amt.bdamt) as bdamt,amt.region_id as region_id,(select office_name from com_mst_all_offices_view   where office_id=amt.region_id) as region_name from "); 
				qry.append(" ( SELECT SUM(a.budget_est_amt) as bdamt,a.office_id,(SELECT region_office_id FROM com_mst_all_offices_view ");
				qry.append(" WHERE office_id=a.office_id ) as region_id	FROM pms_ame_trn_budget a WHERE fin_year='"+pyear+"'" );
				qry.append("  GROUP BY office_id )amt where region_id="+reg_id+" group by amt.region_id");							 
				
				PreparedStatement ps_obj=con.prepareStatement(qry.toString());
				ResultSet rs_new=ps_obj.executeQuery();
				if(rs_new.next()) 
				{
					amt=Double.parseDouble(obj.isNull(rs_new.getString("bdamt"),1));
					total_+=amt;
					out.println("<td align='right' width='45%'>"+df.format(amt)+"</td>");
					//out.println("<td align='right' width='45%'>"+obj.isNull(rs_new.getString("bdamt"),1)+"</td>");
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