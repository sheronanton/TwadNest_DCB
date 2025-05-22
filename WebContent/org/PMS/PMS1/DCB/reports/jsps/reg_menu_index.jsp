<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%><html>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD Nest II | Division Wise DCB Reports</title>
 <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
	 <script type="text/javascript">
	 function rld1(a)
	 {
		document.forms["myform"].submit();
	 } 
	 </script> 
 <script type="text/javascript" src="../scripts/reg_menu_index.js"></script>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body> 
<form action="reg_menu_index.jsp" method="get" name="myform">
<table align="center" width="75%" cellpadding="2">
<tr bgcolor="skyblue">
	<td colspan="2" align="center" class="tdText"><b>Division Wise DCB Reports</b></td>
</tr>
	<%
		String combo1="";
		Controller obj=null;
		Connection con;
		try
		{
		 	String userid=(String)session.getAttribute("UserId");
			if(userid==null)
			{ 
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			} 
			obj=new Controller();
			con=obj.con();
		 	obj.createStatement(con);
		 	String cb=obj.combo_str("PMS_DCB_MIS_REPORT_TITLE","REPORT_HEADING","REPORT_REF"," order by DISPLAYORDER","rpt","","class=select style='width:290px' ");  
		 	Calendar cal = Calendar.getInstance();
		 	int day = cal.get(Calendar.DATE);
		 	int month = cal.get(Calendar.MONTH) + 1;     
		 	int year = cal.get(Calendar.YEAR);
		 	Common_Impl impl_obj=new Common_Impl(); 
			String Region_id="0";	
			
			
		 //	String Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	String Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			
		// 	String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		 	if (Office_id.equalsIgnoreCase("0")) Office_id="5000";  
		 	String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
		 	String disable="";
		 	if (Integer.parseInt(Office_id)!=5000)
		 		disable=" disabled=disabled";
		 		
		 		combo1=obj.combo_str("com_mst_offices","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID ","regdiv",regdiv," class=select onchange='rld1(this.value)' "+disable);
			 // Region_id=impl_obj.regionId(userid,obj);		 
		 String combo=impl_obj.Div_RegionWise(regdiv,"off_id",Office_id);
		 int prvmonth=obj.prv_month(year,month);
		 int prvyear=obj.prv_year(year,month);
%>
	<tr class="tdText">
		<td><label id="Regions" >Regions</label></td>
		<td><%=combo1%></td>
	</tr>
	<tr class="tdText">
		<td><label id="Division" >Division</label></td>
		<td><%=combo%></td>
	</tr> 
 
 <tr class="tdText">
 	<td>
 		<label id="benname" >Year</label>
	</td>
<td colspan="2">
 	<select id="year" tabindex="5" style="width:220pt" class="select" onchange="report_period_verify(document.getElementById('selmonth'),this)">
 	<option value="0" selected="selected">- - - Select - - -</option>
 	<%
 	
 	for (int j=2010;j<=year;j++)
 	{
 	%>
 	<option value="<%=j%>"  <% if (j==prvyear) {%>selected<%} %>><%=j%></option>
 	<%} %>
 </select>
 </td>
 </tr>
 <tr class="tdText"> 
 	<td><label id="Month" >Month</label></td>
 <td>
	 <select id="selmonth" style="width: 220pt" class="select" onchange="report_period_verify(this,document.getElementById('year'))">
	 <option value="0" selected="selected" >- - - Select - - -</option>
	 <%
	 	String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
	 	for (int i=1;i<=12;i++) 
	 	{
	 	%>
	 	<option value="<%=i%>"  <% if (i==prvmonth) {%>selected<%} %>><%=amonth[i]%></option>
	 	<%} %>
	 </select>
 </td>
 </tr>
	<tr>
	
	<td class="tdText" width="15%">Report Title</td><td><%=cb%></td>
	</tr>
	<% if (! Region_id.equalsIgnoreCase("0")) { %> 
	<tr>	
	<td class="tdText" width="15%"><label id="benname" >Division Name</label></td><td><%=combo%></td>
	</tr>
	<%} %>
	<tr>
		<td>Report Type</td>
		<td>
			<select id="reporttype" name="reporttype">
				<option value="1">PDF</option>
				<option value="2">Excel</option>
				<option value="3">HTML</option>
				
			</select>
		</td> 
	</tr> 
	<!-- <Tr><Td colspan="2" ><a href="sch_master_report.jsp">Report Condition</a></Td></Tr>-->
	<tr bgcolor="skyblue" >
	 <td colspan="2" align="center"><input type="button" onclick="rpt_process()" value="Print" style="font-style: italic;font-size:7;color:black;font-weight: bold" class="fb2">&nbsp;&nbsp;<input type="button" onclick="window.close()" value="Exit" style="font-style: italic;font-size:7;color:red;font-weight: bold" class="fb2"><img src="../../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('../../jsps/help1.jsp?fn=67','mywindow','width=600,height=400,scrollbars=yes')"></td>
	</tr>
<%
}catch(Exception e) { 
	%>
	<tr bgcolor="skyblue">
	 <td colspan="2" align="left" class="tdText"><%=e%></td>
	</tr>
	<% 
}
%>
</table>
</form>
</body>
</html>