<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ page import="java.sql.Connection"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill and Ledger Posting Statistics</title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function rld2()
{
	var pyear=document.getElementById("finyear").value;	 
	window.open("../../../../../../Bill_Ledger_Posting_Statistics?option=1&command=Statistics&year="+pyear);  	   
}
function rld3()        
{  
	var pyear=document.getElementById("finyear").value;	 
	window.open("../../../../../../Bill_Ledger_Posting_Statistics?option=1&command=divisionnames&year="+pyear);  	   
}	
</script>
 <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>  
</head>
<body>
<%
		Connection con=null;String Schemestr="",Office_id="0";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj=null;
		try
		{
			HttpSession session1 = request.getSession(false);
			String userid = (String) session1.getAttribute("UserId");	
			obj = new Controller();		
			con=obj.con();
			obj.createStatement(con);
			if (userid == null) 
			{		
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
	  	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;   
		String cyear="";
	    Schemestr=obj.combo_str("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","BENEFICIARY_SNO","where office_id="+Office_id+" and status='L' order by  BENEFICIARY_NAME asc","ben","  style='width: 150;font-size: 0.7em; color: blue;' class='select'  ");
		}catch(Exception e) 
		{ 
			out.println(e);	
		}
		String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
		String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		int year_incr=0;
		if (month <4)
			  year_incr=0;    
		else
			  year_incr=1; 
		int prvyear=obj.prvfinyear(year,month);

%>
	<table align="center" width="40%" border=1 class="table" cellpadding="20" cellspacing="0" style="vertical-align: top;">
		<tr class="tdH">
			<td colspan="2" align="center" class="tdH"> Bill and Ledger Posting Statistics</td>
		</tr>			
	    <tr>
		    <td> 
				<font color=blue>Financial Year</font>
			</td>
			 <td>
            	<select id="finyear"   tabindex="5" style="width:220pt"  class="select">
                	<option value="0">- - - Select - - -</option>
                	<%                	
                	for (int j=year-5;j<=year+year_incr;j++)
                	{
                	%>
                	<option value="<%=j-1%>" <% if (prvyear==(j-1)){%> selected><%} else {%>><% } %><%=j-1%>-<%=j%></option>
                	<%} %>               	
                 </select>
             </td>
        </tr> 
		<tr>	   
			<td colspan="2" align="center">
				<input type="button" value="Show Statistics" onclick="rld2()">
				<input type="button" value="Division List" onclick="rld3()">
				<input type="button" value="EXIT" class="fb2"  onclick="window.close()"> 
			</td>	 
	</tr>
	</table>
</body>
</html>