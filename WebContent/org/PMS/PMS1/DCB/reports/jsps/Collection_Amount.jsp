<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Calendar" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%>
<%@page import="java.sql.Connection"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>DCB Ledger Report</title>
		<link href='../../../../../../css/AME.css' rel='stylesheet' media='screen'/>
	 	<script type="text/javascript" src="../scripts/collection_amount_new.js"></script>
	 	<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
	 	<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
	</head>
	<body>
	<% 
	 	String userid="0",Office_id="",Office_Name="";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
	 	String Region_id="0";		
		String office_="",combo="";
		Controller obj=null;
		try
		{ 
			obj=new Controller();
	 		Connection con=obj.con();
	 		obj.createStatement(con);
	 		try {
					userid = (String) session.getAttribute("UserId");
				} catch (Exception e) 
				{
					userid = "0";
				}
			if (userid == null) 
			{
				userid = "0";
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			
	 	//	  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
       	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//		Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING",	"OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')"), 1));
			//out.println(Office_id);
			if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
			String rd="";
		 	Office_Name=obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+Office_id);
		 	
			if (Integer.parseInt(Office_id)==5000) rd=""; else rd="where OFFICE_ID="+Office_id;
			office_=obj.combo_str("COM_MST_OFFICES","OFFICE_NAME","OFFICE_ID"," where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP "+rd+" )","div",Office_id," class=select "  );	
 			Common_Impl impl_obj=new Common_Impl();		  
		 	Region_id=impl_obj.regionId(userid,obj);		 
		 	combo=impl_obj.Div_RegionWise(Region_id,"div");
		 	//out.println(combo);
		}catch(Exception e) { out.println(e);}
		
		int prvmonth=obj.prv_month(year,month);
		int prvyear=obj.prv_year(year,month);
%>
<form name="pms_dcb_ledger_report">
<table border="1" width="75%" align="center" cellpadding="4" cellspacing="0" class="alerts2" >
	<tr bgcolor="#408099">
 		<td colspan="4" align="center" > 
  	<b>COLLECTION AMOUNT </b>
  </td>
 </tr>
  <tr> 
 		<td colspan="4" align="right" > <font color=blue> <%=Office_Name%></font></td>
 </tr><tr class="tdText">
 	<td  width="40%" colspan="2" >
  	<label id="benname">Year</label>
 </td>
  <td colspan="2">
  	<select id="year"  tabindex="5" style="width:220pt"  class="select" onchange="report_period_verify(document.getElementById('month'),this)">
  	<option value="0" selected="selected">- - - Select - - -</option>
  	<%
  	for (int j=year-6;j<=year;j++)
  	{
  	%>
  	<option value="<%=j%>" <% if (j==prvyear) {%>selected<%} %>><%=j%></option>
  	<%} %>
   </select>
   </td>
 </tr>
 <tr class="tdText">
  	<td width="40%" colspan="2">Month(Running Month)
 </td>
 <td colspan="2">
	 <select id="month"  style="width: 220pt;" class="select" readonly=true onchange="report_period_verify(this,document.getElementById('year'))">
	 	<option value="0" selected="selected" >- - - Select - - -</option>
	 	<%
	 	String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" }; 	 	
	 	for (int i=1;i<=12;i++) 
	 	{
	 	%>
	 	<option value="<%=i%>" <% if (i==prvmonth) {%>selected<%} %>><%=amonth[i]%></option>
	 	<%} %>
	  </select>
 </td>
 </tr>

 	<% if ( Region_id.equalsIgnoreCase("0")) { %>
 <tr>	<td class=tdText width="40%" colspan="2" >Division </td>
 	 <td colspan="2"><%=office_%></td>
 </tr>
 <% } else {%>
 <tr>	
	<!--<td class="tdText"  colspan="2" >Division Name </td><td><%=combo%></td>
	
	-->
	<td class="tdText"  colspan="2" >Division Name</td><td>
	<select id="div" class="select" name="div">
<option value="0">Select </option>
<option selected="" value="6777"><%=Office_Name %></option>
</select>
</td>
	</tr>
 <%} %>

 <tr>
    <td colspan="4" align="center"> 
  	
    <input type="button" name="Print" value="Print" class="bprint"  id="cmdprint" onclick="funcmddata(1);"  style="font-style: italic;font-weight:bold;font-size: 7;color:red"  />&nbsp;<input type="button" name="Clear" value="Clear" id="clear" onclick="refresh();" style="font-style: italic;font-weight:bold;font-size: 7;color:blue" class="fb2"> 
    <input type="button" name="Exit" value="Exit" id="exit" onclick="exitwindow();"  style="font-style: italic;font-weight:bold;font-size: 7;color:red"  class="fb2"/>
   <img src="../../../../../../images/help_images.jpg" height="18px" width="45px"  style="font-style: italic;font-weight:bold;font-size: 7;color:red" onClick="window.open('../../jsps/help1.jsp?fn=56','mywindow','width=600,height=400,scrollbars=yes')">
    </td>
 </tr>
 <tr bgcolor="#408099">
 <td colspan="4"><a href="javascript:funcmdprint2(1)"></a>&nbsp;<a href="javascript:funcmdprint2(2)"></a></TD>
   
 </tr>
</table>
</form>
</body>
</html>