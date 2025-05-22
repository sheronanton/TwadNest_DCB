<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
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
 		
		
 		Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",
 				"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
 						+ userid + "')");
 		if (Office_id.equals(""))
 			Office_id = "0";
 		Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	");
 		
 		smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID=" + Office_id), 1);
 		
 		syear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
		
 		String qry= " SELECT ben.BENEFICIARY_NAME as BENEFICIARY_NAME,"
 			 	   +" ty.BEN_TYPE_DESC as  BEN_TYPE_DESC"
 			 	   +"	FROM "
 			   	   +" ( SELECT BENEFICIARY_SNO, "
 			       +" BENEFICIARY_TYPE_ID, "
 			       +" BENEFICIARY_NAME, "
 			       +" office_id "
 			       +" FROM PMS_DCB_MST_BENEFICIARY "
 			       +" WHERE "+new_cond+" office_id          = "+Office_id;
 			
 			   	      
 		table_column = "BENEFICIARY_NAME,BEN_TYPE_DESC";	   	   
 		table_header = "Beneficiary,Beneficiary Type";
 		String table_td_set = ",,align=center width='5%'";
 		table_heading = "Beneficiary Pumping Return  Status - "+ Office_Name + "";
 		String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);

 		con.close();
}catch(Exception e) {}

%>

		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table      id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan=2><center><%=table_heading%></center></td>
				</tr> 
				
				<tr>
					<td class="tdText">Month&nbsp;&nbsp;:&nbsp;&nbsp;<%=obj.month_val(smonth)%></td>
					<td class="tdText" align="right">Year&nbsp;&nbsp;:&nbsp;&nbsp;<%=syear%></td>
				</tr>
				 
				<Tr>
					<td colspan=2><%=html%></td>  
				</Tr>
				<tr>
					<td colspan=2 align="center"><input class="fb2" type="button" id="" value="Print" style="font-style: italic;font-size:7;color:green;font-weight: bold"  onclick="callReport()"/> <input class="fb2" style="font-style: italic;font-size:7;color:red;font-weight: bold"  type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
			 	 
		</Table>
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 




</body>
</html>
 