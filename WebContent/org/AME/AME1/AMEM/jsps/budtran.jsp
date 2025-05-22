<html>
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
 			<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 			<%@page import="java.util.Calendar" %>
 			<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Annual Maintenance / Budget Estimate Entry</title>
			</head>
		 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
  			<script type="text/javascript" src="../scripts/transcat.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<body onload="transaction(1,5)">
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
					 " where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")","sch_sno","","class='select' style='width: 262'" );
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

			%>
			 
				<form action="" >
					<table width="85%" class="tab" align="center" border=1 cellpadding="5">
						 <tr>
						 	 <td colspan="2" align="center">Annual Maintenance / Budget Estimate Entry</td></tr>
						 <tr>
						 	 <td width="25%"><label class="lbl">Division Office Name :</label></td><td align="left"> <label class="lbl"><%=Office_Name%></label></td></tr>
					 		<tr>
					 		<td width="25%"><label class="lbl">Scheme  Name : </label></td><td align="left"> <%=sch%></td></tr>
					 	 <tr>
	          <td><label class="lbl">Financial Year</label> </td>
			  <Td colspan="2"><select class="select"  id="pyear" name="pyear"  style="width:80pt;" >
			   <option value="0">Select</option>
			  <%
				  for (int i=2010;i<=year;i++)
				  {
			   %>
				  <option value="<%=i%>-<%=i+1%>"><%=i%>-<%=i+1%></option>
			  <%} %>
			  </select> </tD>
			  </tr>
					</table>	
					<table id="etable" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="85%" border="1" cellspacing="0" cellpadding="1">
					<tr><td align="right"><font size=2>(Rs. in lakhs)</font></td></tr>
					<tr><td align="center">						
					<table id="etable" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">					
		 				<tbody id="edata" align="center" valign="top"/>		 			 
			      	</table>
				</td></tr>
				<tr><td align="center">
					<input type="button" value="Save" class="btn" onclick="transaction(1,1)"/>					 
					<input type="button" value="Exit" class="btn" onclick="window.close()"/> 
				</td></tr>
				</table>
				
				</form>
				<input type="hidden" id="rowcount" name="rowcount" value="0"/>
			</body>
		</html>  
