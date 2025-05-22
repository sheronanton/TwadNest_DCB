<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@page import="java.util.Calendar" %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title> Budget Estimate Amount</title>
<script type="text/javascript"> 
function page_size()
{
				window.resizeTo( 700,700 )
}
</script>
</head>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/transcat.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
 				<script src="../scripts/reports.js" type="text/javascript"></script>
 			    <script type="text/javascript" src="../scripts/CalendarCtrl.js"></script> 
 			<script type="text/javascript" src="../scripts/reports.js"></script>
			<body onload="page_size()">
			 
			
<%  
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			Controller obj=new Controller();
			if (userid == null) {

				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			 
			
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
			
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			
			String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO", 
					 " where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")","sch_sno","","class='select' style='width: 262'  onchange='transaction(5,6)'" );
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

			%>
			<form action="" name="bud">
					<table width="100%" class="table" align="center" border=1 cellpadding="5">
						 <tr> <td colspan="2" align="center" class="tdH"><b>Budget Estimate Entry</b></td></tr>
						 <tr> <td width="25%"><label class="lbl">Division Office Name :</label></td><td align="left"> <label class="lbl"><%=Office_Name%></label></td></tr>
						  <tr>
	          <td><label class="lbl">Financial Year</label> </td>
	          
	           
			  <Td colspan="2">
			  
	<select class="select"  id="pyear" name="pyear"  style="width:80pt;" onchange="transaction(5,6)" >
			   <option value="0">Select</option>
			  <%
			  
			  for (int i=2010;i<=year;i++)
			  {
			    
			   %>
			  <option value="<%=i%>-<%=i+1%>"><%=i%>-<%=i+1%></option>
			  
			   
			  
			 
			  <%} %>
			  </select> 
			   
			  </tD>
			  </tr> 
						 
					 	<tr> <td width="25%"><label class="lbl">Scheme  Name : </label></td><td align="left"> <%=sch%></td></tr>
					 	
				<tr>
					<td class="lbl">Ref.No</td>
					<td><input class="tb4" type=text class="tb1" id='ref' name='ref'></td>
				</tr>    
				<tr>
					<td class="lbl">Ref.Date</td>
					<td><input class="tb2" type=text id='refdate' name='refdate'>
<img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.bud.refdate,1);"  alt="Show Calendar"  ></td>
				</tr>
				<tr>
					<td class="lbl" width="30%">Budget Estimate Amount</td>
					<td><input class="tb2" type=text id='budamt' name='budamt' value="0"  onkeyup="isInteger(this,9,event,'budamt'),digit_control('budamt',2)"><font size=2>(Rs. in lakhs)</font></td>
				</tr>
				<tr>  
					<td class="lbl" width="30%">Revised Estimate Amount</td>
					<td><input class="tb2" type=text id='revbudamt' name='revbudamt'  value="0" onkeyup="isInteger(this,9,event,'revbudamt'),digit_control('revbudamt',2)"><font size=2>(Rs. in lakhs)</font></td>
				</tr>
				<tr>
					<td class="lbl" width="30%">Total Maintenance Estimate</td>
					<td><input class="tb2" type=text id='mainestamt' name='mainestamt'  readonly="readonly" value="0"><font size=2>(Rs. in lakhs)</font></td>
				</tr>
				 
				<tr><td align="center" colspan="2">
					<input type="button" value="Save" id='save' class="btn" onclick="transaction(5,1)"/>	
					<input type="Reset" value="Reset"  class="btn" /> 			 	 
					<input type="button" class="btn" value="Report" onclick="report(3)">
					<input type="button" value="Exit"  class="btn" onclick="window.close()"/> 
				</td></tr>
			</table>	
			  
</body>
</html>
 