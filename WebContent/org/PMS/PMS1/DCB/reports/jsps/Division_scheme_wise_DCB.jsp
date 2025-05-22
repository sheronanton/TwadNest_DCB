<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheme Wise DCB</title>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function report(a)
{
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value;
	var rtype=document.getElementById("rtype").value;
	var res=month_year_check(month,year);
	if (a==1)
	{  
		  
		var src="../../../../../../Water_Charges_Report?option="+rtype+"&month="+month+"&year="+year+"&command=scheme_wise_demand_collection";
		window.open(src);
	}  
	else
	{
		if (res!=1)
		{
			 //document.getElementById("rtype").value;
			var src="../../../../../../dcb_statement_new?option="+rtype+"&fmonth="+month+"&fyear="+year+"&process_code=21";
			window.open(src);
		}  
	}
}
</script>
</head>
<body>   
<%
Controller obj=null;
Connection con;
try
{
	  obj=new  Controller(); 
	  con=obj.con();
	  obj.createStatement(con);
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
	  String combo=obj.combo_str("com_mst_all_offices_view","office_name","region_office_id","where office_level_id='RN' and Region_office_id <> 6777 ","reg_no","   ");
	  obj.createStatement(obj.con());
	  	String userid = (String) session.getAttribute("UserId");
		if (userid == null) {
	 	response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	 	// String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID  from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		String Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
%>	  
	<table width="50%" align="center" border="1" bordercolor="skyblue" cellpadding="10" cellspacing="0" style="border-collapse: collapse; ">
		<tr>
			<td colspan="2" align="center">
				&nbsp;Scheme Wise DCB  <%=Office_Name %>
			</td>
		</tr>
		
	       <tr><td>&nbsp;Year</td>
	            <td><select id="year" name="year"  tabindex="5" onchange="report_period_verify(document.getElementById('month'),this)">
                <option value="0" selected="selected">Select</option>
                <%
            	for (int j=year;j>=2015;j--)

           //     for (int j=year-6;j<=year;j++)
                {
                	if (j==prv_year) 
    		   		{
    		   		%>
    		   		<option value="<%=j%>" selected="selected"><%=j%></option>
    		  		<% } else {	%> <option value="<%=j%>"><%=j%></option><%}
                 } %>  
    </select> </td>
		</tr>
		 <tr> 
			<td width="20%"  align="left">&nbsp;Month</td>
			<td>
			 <select name="month" id="month" onchange="report_period_verify(this,document.getElementById('year'))">
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++) {
	         		  if (i==prv_month) { 
					   		%>  <option value="<%=i%>" selected="selected"><%=amonth[i]%></option><% }  else { %>
						    	<option value="<%=i%>"><%=amonth[i]%></option> <% }
	         	%>
	         		 
	         	<%} %>
	          </select>
	         </td>
	       </tr>
		  <tr>  
		<td colspan="2" align="left"><select id="rtype" name="rtype" class='select'>
						<option value="0">Select </option>							
							<option value="1" selected="selected">  PDF </option>
							<option value="2">  Excel </option>
							<option value="3">  HTML </option>    
				</select>
			</td>
			</tr>
		 
		<tr>
			<td align="center" colspan="2">
				<input type=Button value="Report" onclick="report(2)">
				
				<input type=Button value="Exit" onclick="javascript:window.close()">
			</td>
		</tr>
	</table> 	
	  
	  
	  
	  
<%	  
}catch(Exception e){ }
%>
</body>
</html>