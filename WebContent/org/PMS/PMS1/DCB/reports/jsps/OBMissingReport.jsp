 <%@ page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="java.util.Calendar,java.sql.*,java.util.ResourceBundle"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <html> 
 	<head>   
 	<title>Opening Balance Omission Report</title>
 	<script type="text/javascript" src="../scripts/OpeningBalanceReport.js"></script>
 	<script type="text/javascript" src="../../scripts/Basic.js"></script>
 	<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 	<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 	<link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
	<%
	 	String userid="112",Office_id="",Office_Name="";
		String smonth="",syear="",html="";
		String process="0";
		Controller obj=new Controller();
		Connection con; 
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);	
		int prv_month=0;
		if (month==1) prv_month=12;
		else
		prv_month=month-1;   
		int prv_year=year;
		if (prv_month==12)  prv_year=year-1;
		try
		{
			con=obj.con();
			obj.createStatement(con);
			userid=(String)session.getAttribute("UserId");
			if(userid==null)
			{
			 	response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			   // Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			    if (Office_id.equals("")) Office_id="0";
				Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
				smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		   		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
			    obj.conClose(con);
			    process=obj.setValue("process",request);
			}catch(Exception e) {
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		 %>
 	</head>
 	 <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
	 <body onload=" ">
	 <table cellpadding="10" class="table" id="data_table" width="65%" align="center" border="1" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  >
	 <tr class="tdH">
	      <td align="center" height="20px" colspan="4" height="10">
	       			Opening Balance Omission Report - <jsp:expression>Office_Name</jsp:expression> <font class="tdText" color="red"><label id="msg"></label></font>
	     </td>
	</tr>
	<tr class="tdText"> 
		 <td width="20%" class="tdText"><b>Selected Month & Year</b>  </td>
         <td width="50%" align="left">
         <select class="select" id="smonth" name="smonth"  style="width:100pt" onchange="report_period_verify(this,document.getElementById('year'))"  >
         <option value="0">Select</option>
		 	<%
				for (int i=1;i<=12;i++)
				{
					if (i==prv_month) 
					{ 
					%><option value="<%=i%>" selected="selected"><%=cmonth[i]%></option><%} else { %>
					<option value="<%=i%>"><%=cmonth[i]%></option><%} %>
			<%	} %>
		 </select>
         <select class="select" id="year" name="year" onchange="report_period_verify(document.getElementById('smonth'),this)">
			<option value="">-Year-</option>
			<%
             for (int j=year-6;j<=year;j++)
             {
             	if (j==prv_year)
                {
                %><option value="<%=j%>" selected="selected"><%=j%></option><%} else { %>
                <option value="<%=j%>"><%=j%></option><%} %>
             <%} %>
		 </select></td>
		 
		 <td>
		    <input type="hidden" class="fb2" name="dcbb2" id="dcbb2"  value="Beneficiary Wise Omission" onclick="callServer2('missing');">
		  </td>
       </tr> 
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
		<tr>
				<td colspan="3" align="right"><input type="button" class="fb2" name="dcbb2" id="dcbb2"  value="Beneficiary-Scheme Wise Omission" onclick="callServer2('missing2');"> </Td>
		</tr>
		<tr><td colspan="3"><center><input type="button" class="fb2" value="Exit" onclick="self.close();"/></center></td></tr>
		<tr class="tdH"><td align="center" height="20px" colspan="4" height="10"></td>		  
		 </tr> 
		</table>
		<input type="hidden" id="month" name="month" value='<%=smonth%>'> 
		<input type="hidden" id="process" name="process" value='<%=process%>'>
	</body>
</html>