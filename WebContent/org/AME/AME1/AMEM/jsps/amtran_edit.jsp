
		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle"%>
 			<%@ page session="false" contentType="text/html;charset=windows-1252" errorPage="../../../../../error.jsp"%>
 			<%@page import="java.util.Calendar" %>
 			<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Annual Maintenance / Budget Estimate Entry - Edit</title>
			<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 700,700 )
			}
			  
			</script> 
			</head>
			 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
  			<script type="text/javascript" src="../scripts/transcat.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<body onload="page_size(),flash(),toolout('dv') ">
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
					 " where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")","sch_sno","","class='select' style='width: 262'   " );
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String rate=obj.getValue("PMS_AME_MST_CENTAGE","CENTAGE"," where ACTIVE_STATUS='A'");

			%>
			 
				<form action="" >
					<table width="100%" class="table" align="center" border=1 cellpadding="5">
					<tr> <td colspan="2" align="center" class="tdH">Annual Maintenance Estimate Entry - Edit</td></tr>
					<tr> <td width="25%"><label class="lbl">Division Office Name :</label></td><td align="left"> <label class="lbl"><%=Office_Name%></label></td></tr>
					<tr> <td width="25%"><label class="lbl">Scheme  Name : </label></td><td align="left"> <%=sch%></td></tr>
					<tr><td><label class="lbl">Financial Year</label> </td>
			  		<Td colspan="2">
			     		<select class="select"  id="pyear" name="pyear"  style="width:80pt; ">
			  			<option value="0">Select</option>
			  			<%
			  			 for (int i=2010;i<=year;i++)
			  				{
			    		%>
			  			<option value="<%=i%>-<%=i+1%>"><%=i%>-<%=i+1%></option>			 
			  				<%} %>
			  			</select>
			  		 </td>
			  		</tr>
			      
					</table>	
					<table id="etable_ed" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
					
					<tr><td align="left"><label id="msg"></label></td><td align="right"><font size=3 color="red">(Rs. in lakhs)</font></td></tr>
					<tr><td align="center" colspan="2">						
					<table id="etable_ed" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
						
		 				<tbody id="edata_ed" align="center" valign="top"/>		 			 
		 				
			      	</table>  
				</td></tr>   
				
				<tr ><td align="center" colspan="2">
					<input type="button" value="Update" class="btn" onclick="transaction(1,2)"/>					 
					<input type="button" value="Centage" class="btn" onclick="centage(9)"/> 
					<input type="Reset" value="Reset"  class="btn" onclick=""/>
					<input type="button" value="Exit"  class="btn" onclick="window.close()"/> 
				</td></tr>
				<tr><td align="left"><font size=3 color=blue> <b>Note: Items highlighted in green included for centage calculation </b> </font> </td></tr>
				</table>
				<input type="hidden" id="rowcount" name="rowcount" value="0"/>
				<input type="hidden" id="group" name="rowcount" value="0"/>
				<input type="hidden" id="main" name="rowcount" value="0"/>
				<input type="hidden" id="pr_status" value="1">
								<input type="hidden" id="rate" name="rowcount" value="<%=rate%>"/>
				
				<div id='dv' style="height: 20;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" >Click Here For Centage Calculation</div>
				</form>    
				
			</body>
		</html>  
