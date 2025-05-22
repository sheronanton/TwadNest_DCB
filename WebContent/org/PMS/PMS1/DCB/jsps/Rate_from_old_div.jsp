<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<%@page import="java.util.*,java.sql.*"%> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/Scheme_Change.js"></script>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
</head>
<body onload="">
<%
String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
Calendar cal = Calendar.getInstance();
String inp_month="",inp_year="";
int month = 0;//cal.get(Calendar.MONTH) + 1;
int year = cal.get(Calendar.YEAR);
Connection con;
String BEN_TYPE_ID="",Office_name="",userid="",Office_id="",combostr="";
Controller obj=new Controller();
Controller obj1=new Controller();

try
{
	
	con=obj.con();
	con=obj.con();
	obj.createStatement(con);
	obj1.createStatement(con);
	 
	userid=(String)session.getAttribute("UserId");

	if(userid==null)  
	{
	 	//response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
 
    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);

    if (Office_id.equals("")) Office_id="0"; 
	BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")","BEN_TYPE_ID",""," style='width: 200;font-size: 0.7em; color: maroon;' onchange=sch_load(2,'')" );
	  
	combostr=obj.combo_str("com_mst_all_offices_view","OFFICE_NAME","OFFICE_ID","where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP ) and OFFICE_LEVEL_ID='DN'","DV"," onchange=rate_fetch_ben_Load() style='width: 200;font-size: 0.7em; color: maroon;'");
	}catch(Exception e) {}
   
	ResultSet rs=obj1.getRS("select BENEFICIARY_NAME,BENEFICIARY_SNO,OFFICE_ID,TARIFF_MODE,OLD_OFFICE_ID,OLD_BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and Old_OFFICE_ID <> null ");
%>
 <table border="1" width="50%" align="center" class="fb2">
 		<tr>
 				<td align="center" colspan="9">Tariff Slab Rate Transfer</td>
 		</tr>
 	 	<tr>
 				<td align="center" colspan="9"><%=Office_name%></td>
 		</tr>
 	 
 			<tr>
 				<td> Transfered From </td>
 				<td colspan="8"><%=combostr%></td> 
 		</tr>	
 		 
 	<tr>	
 			<td align="center" width='10%'>Sl.No</td>
 			<td align="center">Beneficiary  Name</td>
 			<td align="center" width='10%'>Accept</td>
 			<td align="center" width='10%'>Process</td>
 			 
 		 
 	</tr>
 	 <tbody id="ben_load_" name="ben_load_"></tbody>
 	<tr>
 		<td colspan="10"> 
 			<input type="button" value="Exit" style="background-color: skyblue" onclick="window.close()">
 		    
 		</td>
 	</tr>
 	  
 </table>
  <input type="hidden" id="totalrec" name="totalrec" />
</body>
</html>