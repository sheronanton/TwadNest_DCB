<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@page import="java.util.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
 <%
	String userid="",Office_id="",Office_Name="",smonth="",syear="",table_column="";
	String table_header="",table_heading="",html="";
	Controller obj=null;
	Connection con;
try {
 	
		String new_cond=Controller.new_cond;
		obj = new Controller();
		con = obj.con();
		obj.createStatement(con);
			  
		try {
 			userid = (String) session.getAttribute("UserId");
 		} catch (Exception e) {
 			userid = "0";
 		}
 		if (userid == null) {
 			response.sendRedirect(request.getContextPath()+ "/index.jsp");
 		}
 		Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		String fun=obj.setValue("fun",request);
 		String oid=obj.setValue("oid",request);
 		
 		 		
 		Controller.condition(1,Office_id);
 		
 		
 		
		String Ben_Met_Not_Avl=obj.meter_not_for_ben;  
		
		if (!oid.trim().equalsIgnoreCase("0"))
 			Office_id=oid;
 		
 		String cond="",addcolumn="",addhead="",addcolumn1="";
 		String head="",addcond="",addset="";
		
		String qry="SELECT a.METRE_SNO, "+
					" a.BENEFICIARY_SNO, "+
			  	" a.OFFICE_ID, "+
			  " a.SCHEME_SNO as ssno,a.METRE_LOCATION  as loc,"+
			  " b.beneficiary_name as name,c.ben_type_desc,d.SCH_NAME as sch  "+
			  " FROM PMS_DCB_MST_BENEFICIARY_METRE a, "+
			" PMS_DCB_MST_BENEFICIARY b,PMS_DCB_BEN_TYPE c  ,PMS_SCH_MASTER d "+ 
			  " WHERE a.office_id =5101 "+
			" AND a.METER_STATUS='L' "+
				" AND b.office_id   =a.office_id "+
			" AND b.office_id   =5101 "+
			" AND b.status      ='L' "+
				" and b.beneficiary_sno=a.beneficiary_sno "+ 
			" and c.ben_type_id= b.beneficiary_type_id and c.ben_type_id=a.beneficiary_type_id and d.sch_sno= a.scheme_sno  "+
			" order by b.beneficiary_type_id, b.beneficiary_name " ;
 		
		
			ResultSet rs=obj.getRS(qry);
				
		%>
		
				<table>
					<tr> <td> </td></tr>
					<% 
					int row=0;
					while (rs.next())
					{
						
					int c=obj.getCount("PMS_DCB_OB_YEARLY"," where BENEFICIARY_SNO="+rs.getString("BENEFICIARY_SNO")+" and FIN_YEAR=2011 and  MONTH=10 and OFFICE_ID=5101 and SCH_SNO="+rs.getString("ssno"));
					if (c==0)
					{
						row++;
					%>
					<tr> 
						<td><%=row%> </td>
						<td><%=rs.getString("name")%> </td>	
						<td><%=rs.getString("name")%> </td>
						<td><%=rs.getString("loc")%> </td>
						<td><%=rs.getString("sch")%> </td> 
						<td><%=c%> </td>
					</tr>	
						  
					<%}
					
					} %>
				</table>
		
		
		<%
		
		
		
				
		
		
		
		
 		
}catch(Exception e) {}
 %>
</body>
</html>