<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*" %>
<%@page import="java.sql.PreparedStatement"%> 
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Item Wise Expenditure of All Schemes  of Financial Year</title>
<script type="text/javascript">
function report_show_2(sch_sno)
{
	var pyear="2014-2015";
	window.open("../../../../../ame_report?process_code=124&fin_year="+pyear+"&sch_sno="+sch_sno);
	
}
</script>
</head>
<body>
<form name="pms_ame_reports" method="post">
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>

<%
	try 
	{
		String Office_id="0";
		HttpSession session1 = request.getSession(false);
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj=new Controller();
		Connection con=null;
		con=obj.con();
		obj.createStatement(con);
		String	userid = (String) session1.getAttribute("UserId");
		if (userid == null) {
			//response.sendRedirect(request.getContextPath() + "/index.jsp");
			//out.println(e);
		}
		DecimalFormat df=new DecimalFormat("0.00");
		String fin_year=obj.setValue("fin_year",request);
		StringBuffer br=new StringBuffer();
		obj.createStatement(con);
		userid=(String)session.getAttribute("UserId");
		if(userid==null)  
		{
			 	response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		
		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		int region_id=0;
		region_id=Integer.parseInt(Office_id);
		String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		if (Office_id.equals("")) Office_id="0";
		int inp_div=0;
		StringBuffer SQL=new StringBuffer();
		SQL.append(" SELECT a.sch_sno, ");
		SQL.append("  (SELECT sch_name FROM pms_sch_master WHERE a.sch_sno=sch_sno ");
		SQL.append("  ) AS sch_name, ");
		SQL.append("  SUM(a.actual_exp) as actual_exp  ");
		SQL.append(" FROM PMS_AME_TRN_SCH_ACT_EXP_FINYR a ");
		SQL.append(" WHERE  a.FIN_YEAR ='"+fin_year+"' and office_id="+Office_id+" and a.sch_sno<>0 ");
		SQL.append(" GROUP BY a.sch_sno ");
		SQL.append(" ORDER BY a.sch_sno ");  
		String table_column="";
		String table_header="";	
	
		PreparedStatement ps=con.prepareStatement(SQL.toString());
		ResultSet rs=ps.executeQuery();%>
		<table align="center" width="50%" border="1" cellspacing="1" cellpadding="1" style="border-collapse: collapse;border-color: skyblue" >
			<tr>
				<Th colspan="3">&nbsp;</Th>  
			</tr>
			<tr>
				<td align="center" colspan="3">Item Wise Expenditure of All Schemes  of Financial Year <%=fin_year%></td>
			</tr>
			<tr>
				<td align="center" colspan="3"><%=Office_Name %></td>
			</tr>
			<tr>	
				<th>Sl.No</th>
				<th>Scheme Name</th>
				<th width="20%">Amount (Rs.) </th>				
			</tr>
			<%
			int row_=0;
			double net_amt=0.0;
			while(rs.next())
			{
				row_++;
				String sch_name=rs.getString("sch_name");
				String amt=rs.getString("actual_exp");
				String sch_sno=rs.getString("sch_sno");
				net_amt+=Double.parseDouble(amt);
			%>
			<tr>
				<td><%=row_ %></td>
				<td><%=sch_name %></td>
				<td align="right"><a href='javascript:report_show_2(<%=sch_sno%>)'><%=df.format(Double.parseDouble(amt)) %></a></td>  
			</tr>
			<%
				}
			%>
			<tr>
				<th colspan="2" align="right">Total</th>
				<th><%=df.format(net_amt) %></th> 
			</tr>
			<tr>
				<th colspan="3" align="center"><input type="button" value="Exit" onclick="window.close()" /></<th>
			</tr>
		</table>
		<input type="hidden" name="fin_year" id="fin_year" value="<%=fin_year%>">
		
		
	<%
	}catch(Exception e)
	{
		out.println(e);
		//response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
%>
</form>
</body>
</html>