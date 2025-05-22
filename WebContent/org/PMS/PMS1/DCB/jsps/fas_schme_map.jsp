<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<%@page import="java.util.*,java.sql.*"%> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<title>FAS-PMS Scheme Mapping Status | TWAD Nest - Phase II</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
</head>
<%
	 
	String userid = "0", Office_id = "", Office_Name = "" ;
	Connection con;
 
	ResultSet rs = null;
	Controller obj = null;
	try {
		obj = new Controller();
		con = obj.con();
		obj.createStatement(con);
		try {
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) {
			userid = "0";
		}
		if (userid == null) {
			response.sendRedirect(request.getContextPath()
					+ "/index.jsp");
		}
		Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING",
				"OFFICE_ID",
				"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
						+ userid + "')");
		if (Office_id.equals(""))
			Office_id = "0";
		Office_Name = obj.getValue("com_mst_all_offices_view",
				"OFFICE_NAME", "where OFFICE_ID=" + Office_id + " ");
		 
		String qry = " select "
				+ " PROJECT_ID, "
				+ " PROJECT_NAME,"
				+ " CASE WHEN  SCH_SNO is null THEN 0 ELSE sch_sno END as sch_sno, "
				+ " CASE WHEN  SCH_NAME is null THEN '-' ELSE SCH_NAME END as SCH_NAME, "
				+ " COMP_SNO, "
				+ " COMP_DESC, "
				+ " PROJ_OR_COMP,CASE WHEN  STATUS='L' THEN 'Live' ELSE 'Cancel' END as liveSTATUS,TO_CHAR(UPDATED_DATE , 'DD/MM/YYYY') as UPDATED_DATE, "
				+ " CASE WHEN  SCH_SNO is null or (SCH_SNO=0) THEN 'Scheme Not Mapped' ELSE 'Scheme Mapped' END as status "
				+ " from  " + " PMS_MST_PROJECTS_VIEW "
				+ " where OFFICE_ID=" + Office_id + "   "
				+ " order by PROJECT_ID,status		";

		 
		rs = obj.getRS(qry);
%>
<link href="../../../../../css/txtbox.css" rel="stylesheet"
	media="screen" />
<body>
<form >

<table cellpadding="5" cellspacing="0" width="85%" align="center" border=1 style="border-collapse: collapse;">
	<tr>
		<td align="center" colspan="8">FAS-PMS Scheme Mapping Status 
		<%=Office_Name%>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="5" bgcolor="lightyellow">FAS</td>
		<td align="center" colspan="3" bgcolor="skyblue">PMS</td>
	</tr>
	<tr>
		<td class="fonttd" align="center"><b>Sl.No</b></td>
		<td class="fonttd" align="center"><b>Project ID</b></td>
		<td class="fonttd" align="center"><b>Project Name</b></td>
		<Td class="fonttd" align="center"><b>Status</b></Td>
		<td class="fonttd" align="center"><b>Last Modified Date</b></td>
		<td class="fonttd" align="center"><b>Scheme No</b></td>
		<td class="fonttd" align="center"><b>Scheme Name</b></td>
		<Td class="fonttd" align="center"><b>Mapping Status</b></Td>
 		
	</tr>

	<%
		int r = 0;
			int ssno = 0;
			String var = "";
			while (rs.next()) {

				ssno = rs.getInt("sch_sno");

				if (ssno == 0)
					var = "color=red";
				else
					var = "color=green";
				r++;
				String lddate=rs.getString("UPDATED_DATE");
				if (lddate==null) lddate="";
	%>
	<tr>
		<td class="fonttd" align="center"><%=r%></td>
		<td class="fonttd" align="center"><%=rs.getString("PROJECT_ID")%></td>
		<td class="fonttd"><%=rs.getString("PROJECT_NAME")%></td>
		<td class="fonttd" align="center"><%=rs.getString("liveSTATUS")%></td>
		<td class="fonttd" align="center"><%=lddate%></td>
		
		<td class="fonttd" align="center"><%=(ssno!=0)?ssno:""%></td>				
		<td class="fonttd" width='20%'><%=rs.getString("SCH_NAME")%></td>
		<td class="fonttd" width='20%'><font <%=var%>><%=rs.getString("status")%></font></td>
 		

	</tr>
	<%
		}
	%>
	<tr>
		<td colspan="8" align="center"><input type="button" value="Exit"  onclick ="window.close()" /></td>
	</tr>

</table>

</form>
<%
	con.close();
	} catch (Exception e) {
		out.println(e);
	}
%>
</body>
</html>