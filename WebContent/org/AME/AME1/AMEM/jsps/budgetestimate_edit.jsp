 
<html>
			<%@ page import="java.sql.*,java.util.ResourceBundle"%>
 			<%@ page session="false" contentType="text/html;charset=windows-1252"  errorPage="../../../../../error.jsp"%>
 			<%@page import="java.util.Calendar" %>
 			<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title> Budget Estimate Entry - Edit</title>
			<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 700,700 );
			}
			function sub()
			{
				document.bud.submit()
			}
			 
			</script>
</head>
			<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 			 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
 			
  			<script type="text/javascript" src="../scripts/transcat.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
 			<script src="../scripts/reports.js" type="text/javascript"></script>
 			<script type="text/javascript" src="../scripts/CalendarCtrl.js"></script> 
 			
<body onload="page_size()">
<form action="budgetestimate_edit.jsp" method="post" name="bud">
		<%  
			String key_value="0";
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");			
			Controller obj=new Controller();
			if (userid == null) {response.sendRedirect(request.getContextPath() + "/index.jsp");}
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" + userid + "')");
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String sch_sno= obj.setValue("sch_sno",request);
			String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")","sch_sno",sch_sno,"class='select' style='width: 262'  " );
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};			
			String pyear= obj.setValue("pyear",request);
			String cond=" where SCH_SNO="+sch_sno+" and FIN_YEAR='"+pyear+"' and OFFICE_ID="+Office_id;
			String REF_NO="",REF_DATE ="",BUDGET_EST_AMT="", REVISED_EST_AMT="", MAINT_EST_AMT="";
			if (!pyear.equalsIgnoreCase(""))
			{
			  REF_NO= obj.getValue("PMS_AME_TRN_BUDGET","REF_NO",cond);
			  REF_DATE= obj.getDate("PMS_AME_TRN_BUDGET","REF_DATE",cond);			
			  BUDGET_EST_AMT= obj.getValue("PMS_AME_TRN_BUDGET","BUDGET_EST_AMT",cond);
			  REVISED_EST_AMT= obj.getValue("PMS_AME_TRN_BUDGET","REVISED_EST_AMT",cond);
			  MAINT_EST_AMT= obj.getValue("PMS_AME_TRN_BUDGET","MAINT_EST_AMT",cond);
			  key_value= obj.getValue("PMS_AME_TRN_BUDGET","BUDGET_SNO",cond);
			}
			%>
			  
<table width="100%" class="table" align="center" border=1 cellpadding="5">
		<tr><td colspan="2" align="center" class="tdH"><b>Budget Estimate Entry - Edit</b></td></tr>
		<tr><td width="25%"><label class="lbl">Division Office Name :</label></td><td align="left"> <label class="lbl"><%=Office_Name%></label></td></tr>
		<tr><td width="25%"><label class="lbl">Scheme  Name : </label></td><td align="left"> <%=sch%></td></tr>
		<tr><td><label class="lbl">Financial Year</label> </td>
			<Td colspan="2">
			   <select class="select"  id="pyear" name="pyear"  style="width:80pt;" onchange="sub()" >
			   		<option value="0">Select</option>
			  		<%
			  			  for (int i=2010;i<=year;i++)
			  			  {
			    			if (pyear.equalsIgnoreCase(i+"-"+(i+1)))
			    				{ %>
			  					<option value="<%=i%>-<%=i+1%>" selected><%=i%>-<%=i+1%></option>
			  				 <% } else {  %>
			   					<option value="<%=i%>-<%=i+1%>"><%=i%>-<%=i+1%></option>
			  	   			  <%} %>
			 
					  <%} %>
			  </select></td></tr> 
		<tr><td class="lbl">Ref.No</td><td><input class="tb4" type=text class="tb1" id='ref' name='ref' value="<%=REF_NO%>"></td></tr>   
 		<tr><td class="lbl">Ref.Date</td>
			<td><input class="tb2" type="text" id='refdate' name='refdate' value="<%=REF_DATE%>" >
				<img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.getElementById('refdate'),1);"  alt="Show Calendar"  ></td>
		</tr>
		<tr><td class="lbl" width="30%">Budget Estimate Amount</td><td><input class="tb2" type="text" id='budamt' name='budamt' value="<%=BUDGET_EST_AMT%>" onKeyup="isInteger(this,9,event,'budamt'),digit_control('budamt',2)"><font size="2">(Rs. in lakhs)</font></td></tr>
		<tr><td class="lbl" width="30%">Revised Estimate Amount</td><td><input class="tb2" type="text" id='revbudamt' name='revbudamt'  value="<%=REVISED_EST_AMT%>"  onKeyup="isInteger(this,9,event,'revbudamt'),digit_control('revbudamt',2)"><font size="2">(Rs. in lakhs)</font></td></tr>
		<tr><td class="lbl" width="30%">Total Maintenance Estimate</td><td><input class="tb2" type="text" id='mainestamt' name='mainestamt'  value="<%=MAINT_EST_AMT%>" readonly  onKeyup="isInteger(this,9,event)"><font size="2">(Rs. in lakhs)</font></td></tr>				 
		<tr>
			<td align="center" colspan="2">
				<input type="button" value="Update" id='save' class="btn"  onclick="transaction(5,2)" />	
				<input type="reset" value="Reset"  class="btn" /> 			 	 
				<input type="button" value="Exit"  class="btn" onclick="window.close()"/> 
				<input type="hidden" name="key_value" id="key_value" value="<%=key_value%>">
			</td>
	    </tr>
</table>	
</form>	
</body>

</html>
 