<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<jsp:useBean id="bean" class="Servlets.PMS.PMS1.DCB.reports.Process_status_bean" scope="page"></jsp:useBean>
<link href="../../../../../css/txtbox.css" rel="stylesheet"  media="screen"/>
<title>Process Status</title>
<%
	String userid = "0", Office_id = "", Office_Name = "", obfile = "", report_head = "",oid="";
	Connection con = null;
	String bmonth = "", byear = "", html = "";
	Controller obj = null;
	int obstatusflag = 0;
	String path = "", path2 = "", path3 = "", path4 = "",path5="", offname = "";
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
			response.sendRedirect(request.getContextPath()+ "/index.jsp");
		}
		Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",
				"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		
		if (Office_id.equals("")) 	Office_id = "0";
		
		bmonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID=" + Office_id), 1);
		byear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
		
		oid=obj.setValue("oid",request);	 		
	 	if (!oid.trim().equalsIgnoreCase("0")) 		Office_id=oid;   
	 	
	 	String pmonth="",pyear="";
	 	pmonth=obj.setValue("pmonth",request);
	 	pyear=obj.setValue("pyear",request);
	 	
		if (!pmonth.trim().equalsIgnoreCase("0")) 		bmonth=pmonth;   
		if (!pyear.trim().equalsIgnoreCase("0")) 		byear=pyear; 	
	 	
	 		
		bean.setOffid(Integer.parseInt(Office_id));

		bean.setYear(Integer.parseInt(byear));
		bean.setMonth(Integer.parseInt(bmonth));
		
		
		offname = bean.getProcess();
		path = bean.getTariff_img();
		path2 = bean.getPump_img();
		path3 = bean.getOb_img();
		path4 = bean.getWc_img();
		path5 = bean.getDemand_generation_img();
		obstatusflag = bean.getObstatusflag();
		
		 
		if (obstatusflag == 1) 
		{
			obfile = "OpeningBalanceReport.jsp";
			report_head = " Opening Balance Report";
		} else 
		{
			obfile = "OBMissingReport.jsp";
			report_head = " Opening Balance Missing Report";
		}

		 
	} catch (Exception e) {
		userid = "0";
	}
%>
		<script type="text/javascript">
		function rld()
		{
			document.forms["othercharges"].submit();
		  
		}
		
		</script>
		
</head>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/other_charges_collection.js"></script>
		<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
		<body  >
		<form action="FullDeposit_demand.jsp" method="get" name="othercharges">
			<table align="center" width="95%" cellpadding="5" cellspacing="0" border=1  style="border-collapse:collapse; " bordercolor="skyblue">
		 <tr class="tdH">
		 	 <td  valign="middle" colspan="5" align="center"  > <label class="lbl"><b> <%=offname%> <br> Process Status </b></label>   </td>
		 </tr>
		 <TR>
			<td colspan="3"><label class="lbl"> Bill Month : <%=obj.month_val(bmonth)%></label> </td>
			<td colspan="3"><label class="lbl"> Bill Year : <%=byear%></label></td>
		</TR>
		 	<tr  align="left" class="tdH">
		 	<th  align="center" width="5%"><label class="lbl">Sl. No</label> </th>  
		 	<th  align="center" width="25%"  ><label class="lbl">Process</label> </th>
		 	<th   align="center"><label class="lbl">Status</label> </th>
		 	<th   align="center"><label class="lbl">Action</label> </th>
		 	<th  align="center" width="10%" ><label class="lbl">Verification Status</label> </th>
		 	</tr>  
		 
	   <TR>  
	    	<td><label class="lbl">1</label> </td>
			<td><label class="lbl">Tariff Rate</label> </td>
			<td><label class="lbl"> </label></td>
			<td><label class="lbl"><a href="Pms_Dcb_Mst_Beneficiary_Scheme_Report.jsp" target="_blank"><%=bean.getMsg_late1()%> Meter Drill Down Report</a></label> </td>
			<td align="center"> </td>
		</TR>
	 
		<TR>   
		 	<td><label class="lbl">2</label> </td>
			<td><label class="lbl">Opening Balance</label> </td>
			<td><label class="lbl"><jsp:getProperty name="bean" property="ob"/></label></td>
			<td><label class="lbl" id="mst"><a href="../reports/jsps/<%=obfile%>" target="_blank"><%=bean.getMsg_late2()%> <%=report_head%></a></label></td>
			<td align="center"><img src="<%=path3%>"   width=20 height=20/> </td>
		</TR>
		 <TR>    
		 	<td><label class="lbl">3</label></td>
			<td><label class="lbl">Pumping Return</label></td>
			<td><label class="lbl"><jsp:getProperty name="bean" property="pump_rtrn"/></label></td>
			<td><label class="lbl"> <%=bean.getMsg_late3()%>  </label></td>
			<td align="center"><img src="<%=path2%>"   width='20' height='20'/> </td>
		</TR>	    
		 <TR>
		 	<td><label class="lbl">4</label></td> 
			<td><label class="lbl">Water Charges</label></td>
			<td><label class="lbl"><jsp:getProperty name="bean" property="wc"/></label></td>
			<td><label class="lbl"><a href="pr_amount.jsp" target="_blank"><%=bean.getMsg_late4()%> Water Charges Calculation Report</a></label></td>
			<td align="center"><img src="<%=path4%>"  width=20 height=20/> </td>
		</TR>
		 <TR>
		 	<td><label class="lbl">5</label></td> 
			<td><label class="lbl">Demand Notice Generation</label></td>
			<td><label class="lbl"><jsp:getProperty name="bean" property="demand_generation"/></label></td>
			<td><label class="lbl"><a href="Demand_Report_List.jsp" target="_blank"><%=bean.getMsg_late5()%> Demand List</a></label></td>
			<td align="center"><img src="<%=path5%>"   width=20 height=20/> </td>
		</TR>	  	  
          	<tr>
          		<td valign="middle" colspan="5" align="center" class="tdH">
          		<% if   (!oid.trim().equalsIgnoreCase("0"))  { %>
          		<input type="button" class="fb2" value="Back" onclick="history.go(-1)"/>
          		<%} %>
          		<input type="button" class="fb2" value="Exit" onclick="self.close()"   >&nbsp;&nbsp;  </td>
          	</tr>
			</table> 
			  
		</form>
		</body> 
		
		 <% obj.conClose(con); %>
</html>

	
